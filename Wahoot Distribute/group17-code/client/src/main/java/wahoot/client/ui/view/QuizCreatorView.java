package wahoot.client.ui.view;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import wahoot.client.ui.event.SaveEvent;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.Classroom;
import wahoot.db.models.Quiz;
import wahoot.db.models.Teacher;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * controller for UI that allows teachers to make their own quizzes
 */
public class QuizCreatorView extends ResizeableFXMLView<BorderPane> {

    @FXML
    private Button newQuizButton;

    @FXML
    private TabPane tabpane;

    @FXML
    private Label autoSaveIndicator;

    @FXML
    private ListView<String> quizListView;

    private List<Quiz> quizzes;
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button saveButton;

    @FXML
    private TextField quizNameField;

    @FXML
    private ComboBox<Classroom> classroomComboBox;

    @FXML
    private RadioButton publicClassRadioButton;

    @FXML
    private Label savedLabel;

    @FXML
    private BorderPane root;

    private Thread autosaveDaemon;

    private BlockingQueue<Event> saveQueue = new ArrayBlockingQueue<Event>(15);

    private Manager manager;

    Set<Classroom> classrooms;

    private Teacher teacher;


    @FXML
    private Tab templateTab;

    /**
     * initializes all data needed for view
     * @param manager Manager to communicate with database
     * @param teacher Teacher that is creating the quiz
     */
    public QuizCreatorView(Manager manager, Teacher teacher) {
        this();
        this.manager = manager;
        this.teacher = teacher;

        root.setLeft(new SideView(teacher, manager));
        root.setTop(new TopView());

        quizzes = manager.getAllByClass(Quiz.class);

       classrooms = teacher.getClassesTaught();


       classroomComboBox.setConverter(new StringConverter<Classroom>() {
           /**
            * turns a classroom object into its string name
            * @param classroom Classroom object to be converted to string
            * @return name of classroom passed in
            */
           @Override
           public String toString(Classroom classroom) {
               return classroom.getClassName();
           }

           /**
            * reverses the classroom.toString method
            * @param s takes classroom name as String
            * @return classroom object corresponding to input name
            */
           @Override
           public Classroom fromString(String s) {

               classrooms.stream().filter((x)-> x.getClassName().equals(s)).findFirst().get();


               return classrooms.stream().filter((x)-> x.getClassName().equals(s)).findFirst().get();
           }
       });

       classroomComboBox.getItems().addAll(classrooms);

        for(Quiz quiz: quizzes){
            quizListView.getItems().add(quiz.getName());
        }
    }

    /**
     * Handles case where new quiz button is clicked and modifies ui accordingly
     * @param event user input is taken in
     */
    @FXML
    void newQuizButtonClicked(MouseEvent event) {

        Tab tab = new Tab("Unsaved Quiz*(1)", new TabView(this));
        tabpane.getTabs().add(tab);
        tabpane.getSelectionModel().select(tabpane.getTabs().size() - 1);
    }


    /**
     * saves a newly created quiz to database
     * @param event user input taken in
     */
    @FXML
    void saveButtonClicked(MouseEvent event) {
        Tab select = tabpane.getSelectionModel().selectedItemProperty().get();

        Optional<Quiz> q  = quizzes.stream().filter(x -> x.getName().equals(select.getText())).findFirst();

        Quiz savedQuiz = null;

        if(q.isPresent()) savedQuiz = q.get();

        Classroom selectedClassroom = classroomComboBox.getSelectionModel().getSelectedItem();

        select.getContent().fireEvent(new SaveEvent(SaveEvent.QUIZ_SAVE, manager, select.getText(), savedQuiz, selectedClassroom, teacher));
        quizzes = manager.getAllByClass(Quiz.class);

    }

    /**
     *handles event when key is pressed in quiz name field
     * @param event KeyEvent input from user
     */
    @FXML
    void keyPressQuizField(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            String quizName = quizNameField.getText();
            Tab current = tabpane.getSelectionModel().selectedItemProperty().get();
            current.setText(quizName);
            quizListView.getItems().add(quizName);
            Quiz quiz = new Quiz();
            quiz.setName(quizName);
            quizzes.add(quiz);
            quizNameField.setText("");
        }
    }


    /**
     * Handles event when item in quiz list is selected
     * Opens selected quiz
     * @param event MouseEvent taken from user input
     */
    @FXML
    void quizListClicked(MouseEvent event) {
        String select = quizListView.getSelectionModel().getSelectedItem();
        long count = tabpane.getTabs().stream().filter((x)-> x.getText().equals(select)).count();
        if(event.getClickCount() >= 2 && count < 1) {
            Quiz q = quizzes.stream().filter((x) -> x.getName().equals(select)).findFirst().get();
            Tab tab = new Tab(select, new TabView(this, q));

            tab.setOnSelectionChanged( (evt) -> {
                if(tab.isSelected() && q.getRoom() != null) {
                    classroomComboBox.getSelectionModel().select(q.getRoom());
                }
                else if(tab.isSelected()) {
                    classroomComboBox.getSelectionModel().select(null);
                }
            });
            tabpane.getTabs().add(tab);


            classroomComboBox.getSelectionModel().select(q.getRoom());

            tabpane.getSelectionModel().select(tabpane.getTabs().size() - 1);
        }
    }

    /**
     * displays visual cue of quiz being saved
     */
    public void displaySave() {
        savedLabel.setVisible(true);
        new Thread(() -> {
            try {
                Thread.sleep(300);
                Platform.runLater(() -> {
                    savedLabel.setVisible(false);
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    /**
     * new Quiz creator view with default settings
     * sets up listeners for autosave
     */
    public QuizCreatorView() {

        publicClassRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            /**
             *
             * @return
             * @throws Exception
             */
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!aBoolean && t1) {
                    classroomComboBox.setDisable(true);
                    classroomComboBox.getSelectionModel().select(null);
                }
                else {
                    classroomComboBox.setDisable(false);
                }
            }
        });


        Task<Void> save = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Event e = saveQueue.take();
                    Thread.sleep(300);
                    Platform.runLater(() -> {
                        if(saveQueue.isEmpty()) autoSaveIndicator.setVisible(false);
                    });
                }
            }
        };

        autosaveDaemon = new Thread(save);

        autosaveDaemon.setDaemon(true);

        autosaveDaemon.start();

        autoSaveIndicator.setVisible(false);
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            /**
             *
             * @param keyEvent the event which occurred
             */
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() != KeyCode.ENTER) {
                    autoSaveIndicator.setVisible(true);
                    saveQueue.offer(keyEvent);
                }
            }
        });
        addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            /**
             *
             * @param keyEvent the event which occurred
             */
            @Override
            public void handle(KeyEvent keyEvent) {

            }
        });
    }
}
