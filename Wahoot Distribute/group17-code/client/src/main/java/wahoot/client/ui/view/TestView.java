package wahoot.client.ui.view;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.util.Macro;
import wahoot.client.ui.util.WindowUtil;

/**
 * A test view class designed for experimental purposes not used in final project.
 */
public class TestView extends ResizeableFXMLView<BorderPane> {

    public void bindDimensions(Scene stage) {

        scaleXProperty().bind(stage.widthProperty());
        scaleYProperty().bind(stage.heightProperty());
    }
    public TestView() {

       // WindowUtil.activeFocus(this);
        //Macro.groupInferred(KeyCode.ENTER, this);
    }
}
