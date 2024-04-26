package wahoot.client.ui.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.util.Macro;
import wahoot.db.Manager;
import wahoot.db.models.AccountType;
import wahoot.db.models.Student;
import wahoot.db.models.User;

import java.io.IOError;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

/**
 * ALoginView provides a user interface for login functionality within the application.
 * It extends FXMLView to use JavaFX components for displaying UI elements. This view allows users
 * to login with a username and password, register a new account, or cancel and exit the application.
 *
 * Extending {@link FXMLView}, ALoginView leverages FXML for UI definition, allowing for a separation
 * of presentation and logic. This class interacts with a {@link Manager} instance to verify user
 * credentials against stored data.
 */
public class ALoginView extends FXMLView<BorderPane> {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Label invalidLoginLabel;

    private Manager manager;

    /**
     * Constructs a new ALoginView with a reference to the database manager.
     *
     * @param manager The database manager used for accessing user credentials.
     */
    public ALoginView(Manager manager) {

        this.manager = manager;
        manager.addClass(User.class);

        Macro.group(KeyCode.ENTER, usernameTextField, passwordField);
    }

    /**
     * Handles the action when the login button is clicked. Validates the user's credentials
     * and navigates to the corresponding homepage based on the user's account type.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "loginButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void loginButtonClicked(MouseEvent event) {
        // checks if username and password match to an existing user in the database
        if(checkInfo(usernameTextField.getText(), passwordField.getText())){
            Optional<?> result = manager.find(usernameTextField.getText());
            if (result.isPresent()) {
                Object object = result.get();
                if (object instanceof User u) {
                    if (u.getAccountType() == AccountType.STUDENT) {
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(new Scene(new StudentHomepageView(manager, u)));
                    }
                    else if (u.getAccountType() == AccountType.TEACHER) {
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(new Scene(new TeacherHomepageView(manager, u)));
                    }
                    else if (u.getAccountType() == AccountType.ADMINISTRATOR) {
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(new Scene(new DatabaseView(manager)));
                    }
                }
            }
        }
        else if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            invalidLoginLabel.setText("Please fill in all fields!");
        }
        else {
            invalidLoginLabel.setText("Invalid log in! Try again.");
        }
    }

    /**
     * Handles the action when the cancel button is clicked. Exits the application.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "cancelButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void cancelButtonClicked(MouseEvent event) {
        System.exit(0);
    }

    /**
     * Handles the action when the sign up button is clicked. Navigates to the registration view.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "signUpButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void signUpButtonClicked(MouseEvent event) {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.setScene(new Scene(new RegisterView(manager)));
    }

    /**
     * Checks the provided username and password against the stored credentials.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return {@code true} if the credentials match an existing user, {@code false} otherwise.     */
    private boolean checkInfo(String username, String password) {
        Optional<?> usernameResult = manager.find(username);
        if(usernameResult.isPresent() ) {
            Object obj = usernameResult.get();

            if (obj instanceof User u) {
                if (u.getPassword().equals(password))
                    return true;
            }
        }
        return false;
    }
}

