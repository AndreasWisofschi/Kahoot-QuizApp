package wahoot.client.ui.view;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.User;

/**
 * Represents a tutorial view specifically designed for teachers within the Wahoot application.
 * Extends {@link ResizeableFXMLView} with {@link BorderPane} as the root layout.
 *
 * <p>The view integrates both {@link SideView} and {@link TopView} components to offer a consistent
 * navigation experience</p>
 *
 * <p>Upon initialization, the view dynamically updates a welcome message to personalize the tutorial
 * experience</p>
 */
public class TeacherTutorialView extends ResizeableFXMLView<BorderPane> {

    @FXML
    private BorderPane root;

    @FXML
    private Label lbl_welcome;

    private Manager manager;
    private User user;

    /**
     * Constructs a new {@code TeacherTutorialView} with the specified username, manager, and user.
     * Initializes the tutorial view by setting up the side and top navigation components and displaying
     * a personalized welcome message.
     *
     * @param username The username of the logged-in teacher, used in the welcome message.
     * @param manager  The {@link Manager} instance for database interactions.
     * @param user     The {@link User} instance representing the logged-in teacher.
     */
    public TeacherTutorialView (String username, Manager manager, User user) {
        this.manager = manager;
        this.user = user;
        root.setLeft(new SideView(user, manager));
        root.setTop(new TopView());
        lbl_welcome.setText("Welcome to Wahoot " + username+"!");
    }
}
