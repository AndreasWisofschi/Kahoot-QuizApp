package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.db.models.Leaderboard;
import wahoot.db.models.Leaderboard.LeaderboardEntry;
import wahoot.db.models.Student;

import java.util.LinkedList;
import java.util.List;


/**
 * View showed after quiz ends
 */
public class EndView extends ResizeableFXMLView<StackPane> {


    @FXML
    private VBox leaderboardVBox;
    /**
     * Constructs endview object displaying leaderboard for given student and score
     * @param leaderboard The leaderboard object to display
     * @param student     student leaderboard is for
     * @param score       score student achieved
     */
    public EndView(Leaderboard leaderboard, Student student, double score) {

        leaderboard.addEntry(new LeaderboardEntry(student.getUsername(), score));

        List<LeaderboardEntry> scoreList = leaderboard.getScoreList();

        scoreList.sort((x,y) -> x.getScore() > y.getScore() ? -1 : 1);


        for(LeaderboardEntry entry: scoreList) {

            EndViewEntryView view = new EndViewEntryView(entry, scoreList.indexOf(entry) + 1);

            VBox.setVgrow(view, Priority.NEVER);

            leaderboardVBox.getChildren().add(view);

        }


    }
}
