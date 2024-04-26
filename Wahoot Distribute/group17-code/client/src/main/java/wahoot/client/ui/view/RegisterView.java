package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.util.Macro;
import wahoot.db.Manager;
import wahoot.db.models.AccountType;
import wahoot.db.models.User;
import wahoot.db.models.UserBuilder;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Optional;

/**
 * The RegisterView class provides a graphical user interface for the registration process,
 * allowing new users to create an account within the application. It extends {@link FXMLView} to
 * and uses JavaFX components for creating the UI elements. Users can input their first name, last name, username, and
 * password. This class ensures validation of these inputs, checks for username uniqueness, and supports account
 * creation for different roles (e.g., teacher or student) based on the selection.
 */
public class RegisterView extends FXMLView<AnchorPane>{

    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameTakenLabel;

    @FXML
    private CheckBox TeacherButton;

    private Manager manager;



    /**
     * Initializes a new instance of the RegisterView class with a given database manager.
     * This constructor sets up the UI components and prepares the class to interact with
     * the database for user registration.
     *
     * @param manager The database manager used for checking usernames and saving new users.
     */
    public RegisterView(Manager manager) {
        this.manager = manager;
        manager.addClass(User.class);
    }

    /**
     * Handles the action when the cancel button is clicked. Returns to the login view.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "cancelButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void cancelButtonClicked(MouseEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setScene(new Scene(new ALoginView(manager)));
    }

    /**
     * Handles the create account button click event by validating the provided input fields,
     * checking the uniqueness of the username, and creating a new user account if all conditions
     * are met.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "createAccountButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void createAccountButtonClicked(MouseEvent event) {

        // if all fields are filled
        if(!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && !passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {
            // if password and confirm password match
            if (passwordField.getText().equals(confirmPasswordField.getText())) {
                // if username not taken
                if (!this.isTaken(usernameField.getText())) {
                    if (TeacherButton.isSelected()) {
                        User user = new UserBuilder()
                                .setFirstName(firstNameField.getText())
                                .setLastName(lastNameField.getText())
                                .setUsername(usernameField.getText())
                                .setPassword(passwordField.getText())
                                .build(AccountType.TEACHER);
                        manager.save(user);
                    }
                    else {
                        User user = new UserBuilder()
                                .setFirstName(firstNameField.getText())
                                .setLastName(lastNameField.getText())
                                .setUsername(usernameField.getText())
                                .setPassword(passwordField.getText())
                                .build(AccountType.STUDENT);
                        manager.save(user);
                    }


                    Stage stage = (Stage) createAccountButton.getScene().getWindow();
                    stage.setScene(new Scene(new ALoginView(manager)));
            }
            // if username is taken
                else {

                    usernameTakenLabel.setText("Username Taken!");
                }
            }
            // if password and confirm password does not match
            else {
                usernameTakenLabel.setText("Passwords do not match!");
            }
        }
        //if not all fields are filled in
        else {
            usernameTakenLabel.setText("Please fill in all fields!");
        }
    }

    /**
     * Checks whether the provided username is already associated with an existing account
     * in the database to ensure each user has a unique username within the system.
     *
     * @param username The username to check.
     * @return {@code true} if the username is already taken, {@code false} otherwise.
     */
    private boolean isTaken(String username) {
        Optional<?> result = manager.find(username);
        if(result.isPresent()) {
            Object obj = result.get();
            if(obj instanceof User) {
                return true;
            }
        }
        return false;
    }
}

