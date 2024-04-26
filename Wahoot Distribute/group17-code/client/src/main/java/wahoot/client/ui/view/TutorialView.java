package wahoot.client.ui.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.User;

import javax.swing.*;
import javafx.scene.control.Button;

/**
 * Represents the tutorial view within the Wahoot application, providing users with an introductory guide to the platform.
 * This class extends {@link ResizeableFXMLView} using a {@link BorderPane} as the primary layout container to organize
 * the tutorial content and navigation elements.
 *
 * <p>The tutorial includes a welcome message, a {@link ScrollPane} for navigating through the tutorial content, and
 * a {@link Button} that could be used for interactive tutorial steps or navigation. Additional navigation components are
 * included through integration with {@link SideView} and {@link TopView}.
 *
 * <p>The view is personalized with the user's username</p>
 */

public class TutorialView extends ResizeableFXMLView<BorderPane> {



    @FXML
    private Label lbl_welcome;
    @FXML
    private ScrollPane myScrollPane;
    @FXML
    private Button testButton;

    @FXML
    private BorderPane root;

    private Manager manager;
    private User user;


    /**
     * Constructs a TutorialView with personalized user information.
     *
     * @param username The username of the user, used to personalize the welcome message.
     * @param manager The manager responsible for application logic and database handling.
     * @param user The user object, providing context and information for the tutorial.
     */
    public TutorialView (String username, Manager manager, User user) {
        this.manager = manager;
        this.user = user;

        root.setLeft(new SideView(user, manager));
        root.setTop(new TopView());

        lbl_welcome.setText("Welcome to Wahoot " + username +"!");
    }



}
