package wahoot.client.ui.view;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.User;

/**
 * represents a view displaying end credits animation
 */
public class EndCreditsView extends ResizeableFXMLView<BorderPane> {

    @FXML
    private VBox creditVBox;

    @FXML
    private StackPane root;
    @FXML
    private BorderPane Root;

    MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("/sounds/end_credits.mp3").toExternalForm()));

    TranslateTransition translateCredits;

    private Manager manager;
    private User user;

    /**
     * constructor for end credits view
     * starts animation automatically after everything is initialized
     */
    public EndCreditsView(Manager manager, User user) {

        this.manager = manager;
        this.user = user;

        Root.setTop(new TopView());
        Root.setLeft(new SideView(user, manager));

        root.setTranslateY(620);

        root.setCache(true);
        root.setCacheHint(CacheHint.SPEED);


        translateCredits = new TranslateTransition(Duration.seconds(45), root);

        translateCredits.setFromY(620);
        translateCredits.setToY(0);


        translateCredits.play();
        player.setStartTime(Duration.seconds(144));
        player.play();


    }
}
