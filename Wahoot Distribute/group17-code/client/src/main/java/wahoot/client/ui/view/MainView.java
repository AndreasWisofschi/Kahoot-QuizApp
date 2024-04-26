package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.db.Manager;
import wahoot.db.models.AccountType;
import wahoot.db.models.Classroom;
import wahoot.db.models.User;
import wahoot.db.models.UserBuilder;
/**
 * {@code MainView} class represents the main user interface view of the application, extending {@link FXMLView} with a generic
 * {@link BorderPane}. This class is responsible for initializing the main scene's layout and handling user interactions
 * through its UI components, notably buttons.
 *
 * <p>It is designed to interact with a database via a {@link Manager} instance, allowing for operations such as saving a new {@link User}
 * upon certain actions, for example, clicking a submit button. The view includes FXML-annotated fields for UI components like buttons,
 * which are intended to be linked with their counterparts defined in an FXML layout file.</p>
 *
 * <p>Event handling methods are annotated with {@link FXMLMethod}, specifying the UI component (node) and the event type they handle,
 * facilitating a clear separation between the UI layout and the logic processing user actions.</p>
 *
 * <p>Usage of this class involves initializing it with a {@link Manager} instance responsible for database interactions,
 * and defining event handling methods for UI actions, such as button clicks, to perform application-specific logic
 * like navigating to different views or saving data to a database.</p>
 *
 * @see FXMLView
 * @see BorderPane
 * @see Manager
 * @see User
 * @see FXMLMethod
 * @see FXMLEvent
 */
public class MainView extends FXMLView<BorderPane> {
    @FXML
    private Button submitButton;

    @FXML
    private Button coolButton;

    private Manager manager;

    /**
     * Handles the click event on the {@code submitButton}. This method creates a new {@link User} instance,
     * saves it using the {@link Manager}, and then transitions the scene to a new {@link LoginView}.
     *
     * @param event The {@link MouseEvent} triggered by clicking the submit button.
     */
    @FXMLMethod(node = "submitButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    public void submitButtonClicked(MouseEvent event) {
        Stage stage = (Stage) submitButton.getScene().getWindow();

        User user = new UserBuilder()
                .setFirstName("Dazzian")
                .setEmail("dazzboyce@gmail.com")
                .build(AccountType.STUDENT);

        manager.save(user);

        String username = submitButton.getText();

        manager.save(user);

        stage.setScene(new Scene(new LoginView()));
    }
    public MainView(Manager manager) {
        this.manager = manager;
    }

}
