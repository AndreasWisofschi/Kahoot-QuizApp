package wahoot.client.ui.view;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import wahoot.client.ui.fxml.ResizeableFXMLView;

/**
 * Display for an incorrect selection when taking a quiz
 */
public class IncorrectView extends ResizeableFXMLView<StackPane> {
    @FXML
    private Label pointsLabel;

    @FXML
    private Label streakLabel;

    Media finishSound = new Media(getClass().getResource("/sounds/finish.mp3").toExternalForm());

    MediaPlayer player = new MediaPlayer(finishSound);

    /**
     * Constructor for IncorrectView
     * Allows for user to get instant feedback
     */
    public IncorrectView() {

        FadeTransition pointsFadeTransition = new FadeTransition(Duration.seconds(2), pointsLabel);

        pointsFadeTransition.setFromValue(0f);
        pointsFadeTransition.setToValue(1.0f);


        TranslateTransition pointsTranslateTransition = new TranslateTransition(Duration.seconds(2), pointsLabel);

        pointsTranslateTransition.setFromX(pointsLabel.getLayoutX() - 50);
        pointsTranslateTransition.setToX(pointsLabel.getLayoutX());

        ParallelTransition combined = new ParallelTransition();

        combined.getChildren().addAll(pointsFadeTransition, pointsTranslateTransition);

        combined.play();

        player.setStopTime(Duration.seconds(2));
        player.play();
    }
}
