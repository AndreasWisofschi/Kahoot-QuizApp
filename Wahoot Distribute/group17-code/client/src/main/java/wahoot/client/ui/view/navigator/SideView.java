package wahoot.client.ui.view.navigator;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.*;
import wahoot.db.Manager;
import wahoot.db.models.AccountType;
import wahoot.db.models.Student;
import wahoot.db.models.Teacher;
import wahoot.db.models.User;

/**
 * A controller class for the side navigation view in the Wahoot application. This class manages navigation
 * through different sections of the application like home, quizzes, learning materials, and enrollment based
 * on the user's role (teacher or student). It extends {@link ResizeableFXMLView} with a {@link StackPane} as
 * the root layout container.
 *
 * <p>Interaction with different sections triggers scene changes to the selected view for the current user,
 * facilitating a dynamic and role-specific user experience. This class utilizes {@link FXMLMethod} annotations
 * to link UI elements with their respective event handlers for mouse click events.</p>
 *
 * @see ResizeableFXMLView
 * @see StackPane
 * @see User
 * @see Manager
 */
public class SideView extends ResizeableFXMLView<StackPane> {
    @FXML
    private Pane rootStackPane;

    @FXML
    private HBox rootHBox;

    @FXML
    private HBox homeBox;
    @FXML
    private HBox QuizBox;

    @FXML
    private HBox LearnBox;

    @FXML
    private HBox EnrollBox;

    User user;
    Manager manager;

    private User u;

    /**
     * Handles click events on the home navigation box, redirecting users to their respective homepage view.
     *
     * @param event The mouse event that triggered this method.
     */
   @FXMLMethod(node = "homeBox", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void logoutButtonClicked(MouseEvent event) {
        Stage stage = (Stage) homeBox.getScene().getWindow();
        if(user.getAccountType()== AccountType.STUDENT)
        {
            stage.setScene(new Scene(new StudentHomepageView(new Manager(), user)));
        }
        if(user.getAccountType()== AccountType.TEACHER)
        {
            stage.setScene(new Scene(new TeacherHomepageView(new Manager(), user)));
        }
        stage.setResizable(false);
    }

    /**
     * Handles click events on the quiz navigation box, directing users to their respective quiz-related view.
     *
     * @param event The mouse event that triggered this method.
     */
   @FXMLMethod(node = "QuizBox", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void quizBoxClicked(MouseEvent event) {
        Stage stage = (Stage) QuizBox.getScene().getWindow();
        if(user.getAccountType()== AccountType.STUDENT)
        {
            stage.setScene(new Scene(new QuizStudentView(manager, (Student) user)));
        }
        if(user.getAccountType()== AccountType.TEACHER)
        {
            stage.setScene(new Scene(new QuizCreatorView(manager, (Teacher)user)));
        }

    }

    /**
     * Handles click events on the learn navigation box, taking users to a tutorial view appropriate to their account type.
     *
     * @param event The mouse event that triggered this method.
     */
    @FXMLMethod(node = "LearnBox", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void LearnBoxClicked(MouseEvent event) {
        Stage stage = (Stage) QuizBox.getScene().getWindow();
        if(user.getAccountType()== AccountType.STUDENT)
        {

            stage.setScene(new Scene(new TutorialView(user.getUsername(), manager, user)));
        }
        if(user.getAccountType()== AccountType.TEACHER)
        {
            stage.setScene(new Scene(new TeacherTutorialView(user.getUsername(), manager, user)));
        }

    }

    /**
     * Handles click events on the enrollment navigation box, navigating users to their respective enrollment view.
     *
     * @param event The mouse event that triggered this method.
     */
    @FXMLMethod(node = "EnrollBox", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void EnrollBoxClicked(MouseEvent event) {
        Stage stage = (Stage) QuizBox.getScene().getWindow();
        if(user.getAccountType()== AccountType.STUDENT)
        {
            stage.setScene(new Scene(new StudentClassroomEnrollView(manager, (Student) user)));
        }
        if(user.getAccountType()== AccountType.TEACHER)
        {
            stage.setScene(new Scene(new TeacherEnrollView(manager, (Teacher)user)));
        }

    }


    /**
     * Constructs a SideView with specified user and manager, setting up the navigation for different user roles and
     * initializing the sidebar animation for showing and hiding the navigation panel.
     *
     * @param u The current user, determining the navigation paths based on their role.
     * @param m The manager responsible for application logic and data handling.
     */
    public SideView(User u, Manager m) {
        this.user = u;
        this.manager = m;

        TranslateTransition sidebarAnimation = new TranslateTransition(Duration.millis(300), rootStackPane);

        sidebarAnimation.setFromX(-205);
        sidebarAnimation.setToX(0);

        rootStackPane.setTranslateX(-238);

        Parent root = rootStackPane.getParent();


        double expandedWidth = 138;

        //this.setPrefWidth(expandedWidth);
        this.setMinWidth(0);
        root.minWidth(0);


        //root.minWidth(0);
        //root.prefWidth(expandedWidth);

        rootStackPane.prefWidthProperty().bind(this.prefWidthProperty());


        final Animation hideSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }

            protected void interpolate(double frac) {
                final double curWidth = expandedWidth * (1.0 - frac);
                setPrefWidth(curWidth);
                setTranslateX(expandedWidth + curWidth);
            }
        };

        hideSidebar.play();

        hideSidebar.onFinishedProperty().set(actionEvent -> {

            translateXProperty().subtract(10);
        });

        final Animation showSidebar = new Transition() {
            { setCycleDuration(Duration.millis(250)); }
            protected void interpolate(double frac) {
                final double curWidth = expandedWidth * frac;
                setPrefWidth(curWidth);
                setTranslateX(expandedWidth + curWidth);
            }
        };

        rootStackPane.setOnMouseEntered((event)-> {
            //sidebarAnimation.setRate(1);
            //sidebarAnimation.play();
            showSidebar.play();
        });
       rootStackPane.setOnMouseExited((event)-> {
            //sidebarAnimation.setRate(-1);
            hideSidebar.play();
        });

    }

}
