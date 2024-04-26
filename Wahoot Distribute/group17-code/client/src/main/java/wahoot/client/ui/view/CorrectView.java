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

import java.util.Timer;
import java.util.TimerTask;


/**
 * Represents a view displayed when a user answers question correctly
 */
public class CorrectView extends ResizeableFXMLView<StackPane> {

    @FXML
    private Label pointsLabel;

    @FXML
    private Label streakLabel;

    Media finishSound = new Media(getClass().getResource("/sounds/finish.mp3").toExternalForm());

    MediaPlayer player = new MediaPlayer(finishSound);

    /**
     * Constructor for correct view
     * @param streak current answer streak
     * @param score total score
     */
    public CorrectView(int streak, double score) {

        pointsLabel.setText(String.format("+%d", (int) score));


        streakLabel.setText(String.format("%d", streak));

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
