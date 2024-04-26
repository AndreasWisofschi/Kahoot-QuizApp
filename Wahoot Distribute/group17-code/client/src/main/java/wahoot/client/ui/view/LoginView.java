package wahoot.client.ui.view;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import wahoot.client.ui.fxml.FXMLView;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wahoot.client.ui.util.Macro;
import wahoot.client.ui.util.WindowUtil;

import java.util.LinkedList;

/**
 *  No usage, version 1 of Login View has been overwritten by ALoginView.java
 */

public class LoginView extends FXMLView<AnchorPane> {
    final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    void exitButtonEntered(MouseEvent event) {
        exitButton.setStyle(HOVERED_BUTTON_STYLE);
    }

    @FXML
    void exitButtonExited(MouseEvent event) {
        exitButton.setStyle(IDLE_BUTTON_STYLE);
    }

    @FXML
    void exitButtonClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void loginButtonClicked(MouseEvent event) {
    }

    public LoginView() {
        WindowUtil.activeFocus(this);
        // Manually ordering
        Macro.group(KeyCode.ENTER, usernameTextField, passwordTextField, loginButton);
        // Inferred automatic ordering
        Macro.groupInferred(KeyCode.ENTER, this);
    }



}
