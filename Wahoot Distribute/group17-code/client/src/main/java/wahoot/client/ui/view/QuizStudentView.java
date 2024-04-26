package wahoot.client.ui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.*;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wahoot.db.models.*;

/**
 * @author Andreas Wisofschi, Dazzian Boyce, Evan Buckspan, Daniel Cameron, Aydan Karmali
 * Represents the student view for quizzes selection coming from the main menu, within the application.
 * This view allows students to select quizzes based on subject areas and access public quizzes.
 * It extends {@link ResizeableFXMLView} to support resizable UI components, and borderpane for side-nav view and top-nav view.
 */

public class QuizStudentView extends ResizeableFXMLView<BorderPane> {

    private Student student;
    private Manager manager;

    @FXML
    private Button chemBtn;

    @FXML
    private Button compsciBtn;

    @FXML
    private Button englishBtn;

    @FXML
    private Button geoBtn;

    @FXML
    private Button mathBtn;

    @FXML
    private Button publicQuizBtn;
    @FXML
    private BorderPane root;

    /**
     * Initializes a new instance of the QuizStudentView with a specified manager.
     * Sets up the UI's top and side views.
     *
     * @param manager The application's main manager that handles data and navigation.
     */
    public QuizStudentView(Manager manager, Student student) {
        this.manager=manager;
        this.student = student;
        root.setTop(new TopView());
        root.setLeft(new SideView(student,manager));
    }

    /**
     * Handles button clicks to load specific classroom views based on the selected subject.
     * Changes the scene to display quizzes for the chosen subject while ensuring the correct subject is passed.
     *
     * @param event The action event triggered by clicking a subject button.
     */
    @FXML
    void loadClassroom(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        Stage stage = (Stage) clickedButton.getScene().getWindow();

        if ("chemBtn".equals(clickedButton.getId())) {
            stage.setScene(new Scene(new SubjectQuizView(manager,student,Subject.CHEMISTRY)));

        } else if ("compsciBtn".equals(clickedButton.getId())) {
            stage.setScene(new Scene(new SubjectQuizView(manager,student,Subject.COMPUTER_SCIENCE)));

        } else if ("englishBtn".equals(clickedButton.getId())) {
            stage.setScene(new Scene(new SubjectQuizView(manager,student,Subject.ENGLISH)));

        } else if ("geoBtn".equals(clickedButton.getId())) {
            stage.setScene(new Scene(new SubjectQuizView(manager,student,Subject.GEOGRAPHY)));

        } else if ("mathBtn".equals(clickedButton.getId())) {
            stage.setScene(new Scene(new SubjectQuizView(manager,student,Subject.MATH)));

        }
    }

    /**
     * Loads the view for public quizzes.
     * This view displays quizzes that are accessible to all students.
     *
     * @param event The action event triggered by clicking the public quizzes button.
     */
    @FXML
    void loadPublicQuizzes(ActionEvent event) {
        Stage stage = (Stage) publicQuizBtn.getScene().getWindow();
         stage.setScene(new Scene(new PublicQuizzesView(manager, student)));
    }

}
