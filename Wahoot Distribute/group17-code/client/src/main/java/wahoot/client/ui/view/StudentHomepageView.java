package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.db.Manager;
import wahoot.db.models.Student;
import wahoot.db.models.User;

/**
 * The StudentHomepageView class serves as the primary interface for student users after successful login,
 * facilitating access to various functionalities within the application inlcuding navigating to the classroom enroll
 * page, quizzes page, and the student tutorial. This view extends {@link FXMLView} and utilizes JavaFX components to
 * create a user-friendly and interactive dashboard for the student user.
 */
public class StudentHomepageView extends FXMLView<AnchorPane> {
    @FXML
    private Button classroomsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button progressButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button quizzesButton;

    @FXML
    private Button tutorial_Button;

    @FXML
    private Button creditsButton;

    private Manager manager;
    private Student student;

    /**
     * Constructs a StudentHomepageView with references to the database manager and the
     * currently logged-in student user.
     *
     * @param manager The database manager for data handling.
     * @param user The currently logged-in student, represented as a {@link User} object.
     */
    public StudentHomepageView(Manager manager, User user) {
        this.manager = manager;
        this.student = (Student) user;

        student.getClasses();
    }

    /**
     * Navigates to the quizzes view when the quizzes button is clicked.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "quizzesButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void quizzesButtonClicked(MouseEvent event) {
        Stage stage = (Stage) quizzesButton.getScene().getWindow();
        stage.setScene(new Scene(new QuizStudentView(manager, student)));

        stage.setResizable(true);
    }

    /**
     * Navigates to the classroom enrollment view when the classrooms button is clicked.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "classroomsButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void classesButtonClicked(MouseEvent event) {
        Stage stage = (Stage) classroomsButton.getScene().getWindow();
        stage.setScene(new Scene(new StudentClassroomEnrollView(manager, student)));

        stage.setResizable(true);
    }

    /**
     * Navigates to the tutorials view when the tutorial button is clicked.
     *
     * @param event The mouse event that triggers this method.
     */

    @FXMLMethod (node = "tutorial_Button", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void tutorialButtonClicked(MouseEvent event) {
        Stage stage = (Stage) tutorial_Button.getScene().getWindow();
        stage.setScene(new Scene(new TutorialView(student.getUsername(), manager, student)));

        stage.setResizable(true);
    }

    /**
     * Logs out the current user and returns to the login view when the logout button is clicked.
     * This method provides a secure exit path for the current student to end their session.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "logoutButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void logoutButtonClicked(MouseEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(new Scene(new ALoginView(manager)));

        stage.setResizable(false);
    }

    /**
     * Exits the application when the quit button is clicked. This  allows students to
     * close the application directly from the homepage.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "quitButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void quitButtonClicked(MouseEvent event) {
        System.exit(0);
    }

    /**
     * Displays the application credits when the credits button is clicked.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "creditsButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void creditsButtonClicked(MouseEvent event) {
        Stage stage = (Stage) creditsButton.getScene().getWindow();
        stage.setScene(new Scene(new EndCreditsView(manager, student)));

        stage.setResizable(true);
    }

}
