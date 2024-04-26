package wahoot.client.ui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import wahoot.client.ui.event.SaveEvent;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.db.Manager;
import wahoot.db.models.Classroom;
import wahoot.db.models.Question;
import wahoot.db.models.Quiz;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * A view component for managing the tabs within a quiz creation or editing session in the Wahoot application.
 * It extends {@link ResizeableFXMLView} with {@link StackPane} as the root layout. This class is responsible
 * for handling the dynamic creation, display, and navigation of question editing views within a paginated interface.
 *
 * <p>It incorporates control to allow users to navigate through multiple {@link QuestionEditorView} views for questions.
 *
 * <p>Two constructors are provided to support both the creation of a new quiz and the editing of an existing quiz.
 * This differentiation allows for initializing the view with a pre-populated set of questions or starting with a blank quiz.</p>
 *
 */

public class TabView extends ResizeableFXMLView<StackPane> {

    ArrayList<QuestionEditorView> questions = new ArrayList<>();

    @FXML
    private Pagination pagination;

    @FXML
    private ScrollPane quizContent;

    @FXML
    private Button plusPageButton;

    @FXML
    private Button minusPageButton;

    /**
     * Handles the action of clicking the minus button to remove the current question page.
     *
     * @param event The {@link MouseEvent} associated with the button click.
     */
    @FXML
    void minusButtonClicked(MouseEvent event) {
        if(pagination.getPageCount() - 1 > 0) {
            questions.remove(pagination.getPageCount() - 1);
        }
        pagination.setPageCount(pagination.getPageCount() - 1);
    }

    /**
     * Handles the action of clicking the plus button to add a new question page.
     *
     * @param event The {@link MouseEvent} associated with the button click.
     */
    @FXML
    void plusButtonClicked(MouseEvent event) {
      pagination.setPageCount(pagination.getPageCount() + 1);
      pagination.setCurrentPageIndex(pagination.getPageCount());

    }

    private QuizCreatorView editorView;

    /**
     * Constructor for initializing a new TabView for quiz creation. Sets up event handling for quiz saving
     * and dynamic question addition/removal.
     *
     * @param editorView The {@link QuizCreatorView} that this TabView is part of.
     */
    public TabView(QuizCreatorView editorView) {
        this.editorView = editorView;

        questions.add(new QuestionEditorView());
        questions.add(new QuestionEditorView());
        questions.add(new QuestionEditorView());
        questions.add(new QuestionEditorView());
        questions.add(new QuestionEditorView());

        addEventHandler(SaveEvent.QUIZ_SAVE, new EventHandler<SaveEvent>() {
            @Override
            public void handle(SaveEvent saveEvent) {
                Manager manager = saveEvent.getManager();

                Classroom classroom = saveEvent.getClassroom();

                Quiz quiz = new Quiz();

                quiz.setAuthor(saveEvent.getTeacher());

                quiz.setName(saveEvent.getName());

                quiz.setRoom(classroom);

                for(QuestionEditorView view: questions) {
                    try {
                        Question question = new Question(view.getQuestionTitleField(), view.getCorrect(), view.getOptionOneField(), view.getOptionTwoField(), view.getOptionThreeField(), view.getOptionFourField());
                        quiz.addQuestion(question);
                    }
                    catch(NullPointerException e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText("Error while attempting to save.");
                        error.setContentText("Please fill out all fields.");
                        error.showAndWait();

                        return;
                    }
                }

                if(classroom != null) {
                    classroom.addQuiz(quiz);
                    manager.update(classroom);
                }

                manager.save(quiz);
                editorView.displaySave();

                removeEventHandler(SaveEvent.QUIZ_SAVE, this);

                addEventHandler(SaveEvent.QUIZ_SAVE, saveEvent1 -> {
                    Classroom classroom1 = saveEvent1.getClassroom();

                    Manager manager1 = saveEvent1.getManager();

                    Quiz quiz1 = new Quiz(saveEvent1.getQuiz().getID());

                    if(classroom1 != null) {
                        classroom1.addQuiz(quiz1);
                        manager1.update(classroom1);
                    }

                    quiz1.setAuthor(saveEvent1.getTeacher());

                    quiz1.setName(saveEvent1.getName());

                    quiz1.setRoom(classroom1);

                    for(QuestionEditorView view: questions) {
                        try {
                            Question question = new Question(view.getQuestionTitleField(), view.getCorrect(), view.getOptionOneField(), view.getOptionTwoField(), view.getOptionThreeField(), view.getOptionFourField());
                            quiz1.addQuestion(question);
                        } catch(NullPointerException e) {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setTitle("Save Error");
                            error.setHeaderText("Error while attempting to save.");
                            error.setContentText("Please fill out all fields.");
                            error.showAndWait();

                            return;
                        }
                    }
                    manager1.update(quiz1);
                    editorView.displaySave();
                });
            }
        });
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer integer) {
                if(integer < questions.size()) {
                    return questions.get(integer);
                }
                else {
                    QuestionEditorView view = new QuestionEditorView();
                    questions.add(view);
                    return view;
                }
            }

        });

    }

    /**
     * Constructor for initializing a TabView for editing an existing quiz. Populates the pagination with
     * {@link QuestionEditorView} instances corresponding to the questions in the provided {@link Quiz}.
     *
     * @param editorView The {@link QuizCreatorView} that this TabView is part of.
     * @param quiz The {@link Quiz} object to be edited.
     */
    public TabView(QuizCreatorView editorView, Quiz quiz) {
        this.editorView = editorView;
        addEventHandler(SaveEvent.QUIZ_SAVE, new EventHandler<SaveEvent>() {
            @Override
            public void handle(SaveEvent saveEvent) {

                Classroom classroom = saveEvent.getClassroom();

                Manager manager = saveEvent.getManager();

                Quiz quiz = new Quiz(saveEvent.getQuiz().getID());

                if(classroom != null) {
                    classroom.addQuiz(quiz);
                    manager.update(classroom);
                }

                quiz.setAuthor(saveEvent.getTeacher());

                quiz.setName(saveEvent.getName());

                quiz.setRoom(classroom);

                for(QuestionEditorView view: questions) {
                    try {
                        Question question = new Question(view.getQuestionTitleField(), view.getCorrect(), view.getOptionOneField(), view.getOptionTwoField(), view.getOptionThreeField(), view.getOptionFourField());
                        quiz.addQuestion(question);
                    } catch(NullPointerException e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Save Error");
                        error.setHeaderText("Error while attempting to save.");
                        error.setContentText("Please fill out all fields.");
                        error.showAndWait();

                        return;
                    }
                }
                manager.update(quiz);
                editorView.displaySave();
            }
        });

        pagination.setPageCount(quiz.getQuestions().size());
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer integer) {
                if(integer < questions.size()) {
                    return questions.get(integer);
                }
                else {
                    QuestionEditorView view;
                    if(integer >= quiz.getQuestions().size()) {
                        view = new QuestionEditorView();
                    }
                    else {
                        view = new QuestionEditorView(quiz.getQuestions().stream().toList().get(integer));
                    }
                    questions.add(view);
                    return view;
                }
            }

        });
    }
}
