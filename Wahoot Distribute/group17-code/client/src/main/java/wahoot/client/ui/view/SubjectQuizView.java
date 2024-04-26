package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.*;
import java.awt.event.MouseEvent;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Andreas Wisofschi, Dazzian Boyce, Evan Buckspan, Daniel Cameron, Aydan Karmali
 * A view for displaying and selecting quizzes based on a specific subject area [ENGLISH,MATH,GEOGRPAPHY,COMPSCI,CHEMISTRY].
 * Extends {@link ResizeableFXMLView} to provide a resizable interface for quiz selection, and borderpane for side-nav view and top-nav view.
 * This class is responsible for rendering a list of quizzes for a given subject and handling clicks on said listed quiz to push to gameview and take the quiz.
 * the selection of a quiz to play.
 */

public class SubjectQuizView extends ResizeableFXMLView<BorderPane> {
    @FXML
    private ListView<String> QuizzesList;

    /**
     * Handles double-click events on the quizzes list to select a quiz.
     * This method is bound to double-click events on the {@link ListView} of quizzes.
     * On a double-click, it finds the selected quiz and initiates the quiz game view.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "QuizzesList", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void quizPicked(MouseEvent event){
        String selectedQuiz = QuizzesList.getSelectionModel().getSelectedItem();
        if(event.getClickCount() >= 2) {
            Quiz quiz = subjectQuizzes.stream().filter((x)->x.getName().equals(selectedQuiz)).findFirst().get();
            Stage stage = (Stage) QuizzesList.getScene().getWindow();
            stage.setScene(new Scene(new GameView(manager, quiz, student)));
        }
    }

    private Manager manager;

    private Student student;
    private Classroom classroom;
    private List<Quiz> subjectQuizzes;
    @FXML
    private BorderPane root;

    /**
     * Constructs a new instance of the SubjectQuizView.
     * Initializes the UI components and populates the quizzes list based on the selected subject.
     *
     * @param manager The manager responsible for handling data operations.
     * @param student The student whose classes are considered for quiz selection.
     * @param subject The subject for which quizzes are being displayed.
     */
    public SubjectQuizView(Manager manager, Student student, Subject subject){
        this.subjectQuizzes = new LinkedList<Quiz>();
        this.manager = manager;
        this.student = student;
        root.setTop(new TopView());
        root.setLeft(new SideView(student,manager));
        manager.addClass(Quiz.class);

        Set<Classroom> userClasses = student.getClasses();

        for (Classroom c: userClasses) {
            if ((c.getSubject()).equals(subject)) {
                subjectQuizzes.addAll(c.getQuizzes());
                QuizzesList.getItems().addAll(c.getQuizzes().stream().map(Quiz::getName).toList());
            }
        }
    }


}