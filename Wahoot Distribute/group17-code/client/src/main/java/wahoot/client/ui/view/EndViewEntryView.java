package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.db.models.Leaderboard.LeaderboardEntry;

/**
 * Represents the leaderboard view at end of quiz
 */
public class EndViewEntryView extends ResizeableFXMLView<HBox> {

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView placeImageView;

    @FXML
    private Label placeLabel;

    @FXML
    private Label pointsLabel;

    /**
     *
     * @param entry entry into leaderboard
     * @param place leaderboard placement
     */
    public EndViewEntryView(LeaderboardEntry entry, int place) {

        placeImageView.setImage(null);

        placeLabel.setText(String.format("%d.", place));

        nameLabel.setText(entry.getName());

        pointsLabel.setText(String.format("%d", (int) entry.getScore()));

        switch(place) {
            case 1:
                placeImageView.setImage(new Image(getClass().getResource("/img/icons8-first-place-48.png").toExternalForm()));
                break;
            case 2:
                placeImageView.setImage(new Image(getClass().getResource("/img/icons8-medal-second-place-48.png").toExternalForm()));
                break;
            case 3:
                placeImageView.setImage(new Image(getClass().getResource("/img/icons8-medal-third-place-48.png").toExternalForm()));
                break;
            default:
                break;
        }
    }
}
