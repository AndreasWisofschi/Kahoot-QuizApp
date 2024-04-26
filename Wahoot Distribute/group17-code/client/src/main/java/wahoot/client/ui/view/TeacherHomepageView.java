package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.db.Manager;
import wahoot.db.models.Teacher;
import wahoot.db.models.User;

/**
 * The TeacherHomepageView class serves as the central navigation hub for teacher users after they have logged into the system.
 * It extends {@link FXMLView} to user JavaFX for creating a user interface that is both intuitive and functional.
 * This view includes buttons for various teacher-specific functionalities such as creating and viewing quizzes, managing
 * classrooms, tracking student progress, accessing teacher tutorials, and managing the application session (closing and logging out).
 */
public class TeacherHomepageView extends FXMLView<AnchorPane> {
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
    private Button tutorialButton;

    @FXML
    private Button creditsButton;

    private Manager manager;
    private Teacher teacher;


    /**
     * Initializes a TeacherHomepageView with references to the database manager and the logged-in teacher user.
     * This setup enables the teacher to interact with the application in a way that is tailored to their role,
     * allowing them to manage course materials, quizzes, and student progress efficiently.
     *
     * @param manager The database manager for managing data and handling database interactions.
     * @param user The logged-in teacher, represented as a {@link User}, but cast to {@link Teacher} internally.
     */
    public TeacherHomepageView(Manager manager, User user) {
        this.manager = manager;
        this.teacher = (Teacher) user;
    }

    /**
     * Navigates to the quiz management view when the quizzes button is clicked, enabling teachers to create,
     * view, and manage quizzes for their courses.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "quizzesButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void quizzesButtonClicked(MouseEvent event) {

        Stage stage = (Stage) quizzesButton.getScene().getWindow();
        stage.setScene(new Scene(new QuizCreatorView(manager, teacher)));
        stage.setResizable(true);
    }

    /**
     * Transitions to the classroom management view when the classrooms button is clicked, allowing teachers to
     * enroll students, create classroom materials, and organize classroom settings.
     * *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "classroomsButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void classroomsButtonClicked(MouseEvent event) {
        Stage stage_ = (Stage) classroomsButton.getScene().getWindow();
        stage_.setScene(new Scene(new TeacherEnrollView(manager, teacher)));
        stage_.setResizable(true);
    }

     /**
      * Opens the progress tracking view when the progress button is clicked, offering teachers a detailed overview
      * of student performance and progress within their courses.
      *
     * @param event The mouse event that triggers this method.
     */
     @FXMLMethod (node = "progressButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void progressButtonClicked(MouseEvent event) {
        Stage stage = (Stage) progressButton.getScene().getWindow();
        stage.setScene(new Scene(new ProgressTrackerView(teacher, manager)));
        stage.setResizable(true);
    }

    /**
     * Accesses the tutorial view tailored for teachers when the tutorial button is clicked, providing resources and
     * guidance on using the application.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "tutorialButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void tutorialButtonClicked(MouseEvent event) {
        Stage stage = (Stage) tutorialButton.getScene().getWindow();

        stage.setScene(new Scene(new TeacherTutorialView(teacher.getUsername(), manager, teacher)));
        stage.setResizable(true);
    }

    /**
     * Logs the teacher out of the application and returns to the login screen when the logout button is clicked,
     * ensuring a secure exit point from the teacher's current session.
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
     * Closes the application when the quit button is clicked, providing an immediate way for teachers to exit the program.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "quitButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void quitButtonClicked(MouseEvent event) {
        System.exit(0);
    }

    /**
     * Shows the application credits when the credits button is clicked, acknowledging those who contributed to the
     * development of the application.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "creditsButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void creditsButtonClicked(MouseEvent event) {
        Stage stage = (Stage) creditsButton.getScene().getWindow();
        stage.setScene(new Scene(new EndCreditsView(manager, teacher)));

        stage.setResizable(true);
    }

}
