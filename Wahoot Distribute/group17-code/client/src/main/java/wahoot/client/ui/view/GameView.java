package wahoot.client.ui.view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

import javafx.util.Duration;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import wahoot.db.Manager;
import wahoot.db.models.Question;
import wahoot.db.models.Quiz;
import wahoot.db.models.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents the UI view for playing the quiz
 * Displays a question with its option and a timer
 */

public class GameView extends ResizeableFXMLView<BorderPane> {
    @FXML
    private Arc arcSlice;

    @FXML
    private ImageView logoImage;

    @FXML
    private BorderPane root;

    @FXML
    private Group timerGroup;

    @FXML
    private Label timerLabel;

    @FXML
    private Group labelLeftGroup;

    @FXML
    private Label questionLabel;

    @FXML
    private Button optionFourButton;

    @FXML
    private Button optionOneButton;

    @FXML
    private Button optionThreeButton;

    @FXML
    private Button optionTwoButton;

    @FXML
    private Label questionRemainingLabel;

    @FXML
    private VBox gameVBox;

    @FXML
    private BorderPane gameRoot;




    private static final int TIMER_DURATION = 30;

    private Quiz quiz;

    private Student student;

    private Manager manager;
    private SideView side;

    BooleanProperty force = new SimpleBooleanProperty();

    private String answer;

    private String correct;

    Iterator<Question> iterator;

    private int questionLeft;

    private int currentStreak = 0;

    private double cumulativeScore = 0;

    Media quizSong = new Media(getClass().getResource("/sounds/30-second-countdown.mp3").toExternalForm());

    MediaPlayer mediaPlayer = new MediaPlayer(quizSong);

    private TimerTask timerTask;

    private final Timer timer;

    /**
     * Handles event when option is clicked
     * @param event takes in user's selection
     */
    @FXML
    void optionFourClicked(MouseEvent event) {
        answer = optionFourButton.getText();
        resetTimerTask();
    }
    /**
     * Handles event when option is clicked
     * @param event takes in user's selection
     */
    @FXML
    void optionOneClicked(MouseEvent event) {
        answer = optionOneButton.getText();
        resetTimerTask();
    }
    /**
     * Handles event when option is clicked
     * @param event takes in user's selection
     */
    @FXML
    void optionThreeClicked(MouseEvent event) {
        answer = optionThreeButton.getText();
        resetTimerTask();
    }
    /**
     * Handles event when option is clicked
     * @param event takes in user's selection
     */
    @FXML
    void optionTwoClicked(MouseEvent event) {
        answer = optionTwoButton.getText();
        resetTimerTask();
    }

    /**
     * Resets timer when an option is selected
     */
    void resetTimerTask() {
        if(!force.get()) return;
        mediaPlayer.stop();

        timerTask.cancel();

        timerAnimation.stop();

        arcSlice.setLength(360);

        checkAnswer();

        countdown = TIMER_DURATION;

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                gameRoot.setCenter(gameVBox);
                timerAnimation.play();
                side.setVisible(true);
                if(iterator.hasNext()) {
                    mediaPlayer.play();
                    setQuestion(iterator.next());
                    timerTask = new QuizTimer();

                    timer.schedule(timerTask, 2, 1000);
                }
                else {
                    gameRoot.setCenter(new EndView(quiz.getLeaderboard(), student, cumulativeScore));

                    manager.update(quiz);

                    student.addScore((long) cumulativeScore);

                    student.addObservation(new Student.Observation(java.sql.Date.valueOf(LocalDate.now()), cumulativeScore));

                    manager.update(student);

                    side.setVisible(true);
                }
            });
        }).start();
    }

    /**
     * Sets up next question, updating UI accordingly
     * @param question question next question to be set up
     */
    private void setQuestion(Question question) {

        questionLeft -= 1;

        questionLabel.setText(question.getPrompt());

        questionRemainingLabel.setText(String.format("%d", questionLeft));

        optionOneButton.setText(question.getOptionA());
        optionTwoButton.setText(question.getOptionB());
        optionThreeButton.setText(question.getOptionC());
        optionFourButton.setText(question.getOptionD());

        correct = question.getCorrect();

    }

    Animation timerAnimation;

    /**
     * Checks to see if the selected answer is correct
     * updates score accordingly
     */
    private void checkAnswer() {
        side.setVisible(false);
        if(answer != null && answer.equals(correct)) {
            currentStreak += 1;


            double effectiveSpeedMultiplier = ((double) countdown / TIMER_DURATION) * quiz.getSpeedMultiplier();

            double scorePerQuestion = (double) quiz.getMaxScore() / quiz.size();


            if(effectiveSpeedMultiplier > 1) scorePerQuestion *= effectiveSpeedMultiplier;

            if(currentStreak > 1) scorePerQuestion += currentStreak * 50;

            cumulativeScore += scorePerQuestion;

            gameRoot.setCenter(new CorrectView(currentStreak, scorePerQuestion));
        }
        else  {
            currentStreak = 0;
            gameRoot.setCenter(new IncorrectView());
        }
    }
    private int countdown = TIMER_DURATION;

    /**
     * Class represent timer that qis displayed
     */
    class QuizTimer extends TimerTask {

        boolean finished = false;

        /**
         * Method that controls when the timer starts and resets
         * Ends timer once no questions left
         */
        @Override
        public void run() {
            Platform.runLater(() -> {
                timerLabel.setText("%d".formatted(countdown));
            });

            countdown -= 1;

            if(countdown <= 0) {

                if(questionLeft >= 0) {
                    timerAnimation.setOnFinished((evt) -> {
                        mediaPlayer.stop();
                        checkAnswer();
                        arcSlice.setLength(360);
                        if(iterator.hasNext()) setQuestion(iterator.next());
                        else {
                            finished = true;
                            cancel();
                        }
                    });

                    try {
                        Thread.sleep(4000);
                        side.setVisible(true);
                        if(!finished) {
                            Platform.runLater(() -> {
                                gameRoot.setCenter(gameVBox);
                                timerAnimation.play();
                                mediaPlayer.play();
                            });
                        }
                        else Platform.runLater(() -> {
                            mediaPlayer.stop();
                            checkAnswer();
                            side.setVisible(true);
                            gameRoot.setCenter(new EndView(quiz.getLeaderboard(), student, cumulativeScore));

                            student.addScore((long) cumulativeScore);

                            student.addObservation(new Student.Observation(java.sql.Date.valueOf(LocalDate.now()), cumulativeScore));

                            manager.update(student);


                            manager.update(quiz);

                        });
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    countdown = TIMER_DURATION;
                }
            }
        }
    }


    /**
     * Constructor for the Game View
     * @param manager Manager is for communicating with data
     * @param quiz quiz that will be displayed
     * @param student student that will take quiz
     */
    public GameView(Manager manager, Quiz quiz, Student student) {
        this.side = new SideView(student, manager);
        this.quiz = quiz;
        this.student = student;
        this.manager = manager;

        quiz.shuffle();

        iterator = quiz.getQuiz();

        questionLeft = quiz.size();

        Question first = iterator.next();

        setQuestion(first);

        NumberBinding scale = Bindings.min(widthProperty().divide(663), heightProperty().divide(228));

        TopView topView = new TopView();

        topView.setQuizCheckBoxVisibility(true);

        force.bind(topView.getQuizCheckBoxSelectedProperty());

        root.setTop(topView);
        root.setLeft(side);


        timerGroup.scaleXProperty().bind(scale);
        timerGroup.scaleYProperty().bind(scale);

        logoImage.scaleXProperty().bind(scale);
        logoImage.scaleYProperty().bind(scale);

        labelLeftGroup.scaleXProperty().bind(scale);
        labelLeftGroup.scaleYProperty().bind(scale);

        timer = new Timer();


        timerAnimation = new Transition() {
            {
                setCycleDuration(Duration.seconds(TIMER_DURATION));
            }
            @Override
            protected void interpolate(double v) {
                arcSlice.setLength((360) - v *  360);
            }
        };

        timerTask = new QuizTimer();

        mediaPlayer.play();

        timerAnimation.play();
        timer.schedule(timerTask, 0, 1000);

    }
}
