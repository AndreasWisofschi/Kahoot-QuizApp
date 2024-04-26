package wahoot.client.ui.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import wahoot.client.ui.fxml.FXMLView;

import java.util.Random;

/**
 * Represents the loading screen when game is starting up
 */
public class PreloaderView extends FXMLView<AnchorPane> {
    @FXML
    private ProgressBar preloaderProgressbar;

    @FXML
    private Text retrievingText;

    @FXML
    private TextArea loadingArea;

    Random random;

    final Timeline timeline;


    /**
     * Constructor for loading screen
     */
    public PreloaderView() {
        random = new Random(1440L);


        timeline = new Timeline();
        timeline.setCycleCount(6);
        Duration initial = Duration.millis(1200);
        KeyFrame circleFrame = new KeyFrame(initial, actionEvent -> {
            Circle shape = new Circle();

            Bounds bounds = getBoundsInLocal();
            shape.setCenterY(random.nextDouble(0, bounds.getMaxY() * 0.30));
            shape.setCenterX(random.nextDouble(0, bounds.getMaxX() * 0.20));
            shape.setRadius(random.nextDouble(25, 30));
            shape.setFill(Color.FORESTGREEN);



            getChildren().add(shape);
        });
        timeline.getKeyFrames().add(circleFrame);
        //timeline.play();
    }


    /**
     * stops animation of loading progress bar
     */
    public void stopAnimation() {
        timeline.stop();
    }

    /**
     * Getter for the progress bar
     * @return ProgressBar representing loading bar
     */
    public ProgressBar getPreloaderProgressbar() {
        return preloaderProgressbar;
    }

    /**
     * getter for retrievingText
     * @return
     */
    public Text getRetrievingText() {
        return retrievingText;
    }

    /**
     * getter for loading area
     * @return
     */
    public TextArea getLoadingArea() {
        return loadingArea;
    }
}
