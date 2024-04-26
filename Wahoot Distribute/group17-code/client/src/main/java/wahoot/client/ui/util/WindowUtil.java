package wahoot.client.ui.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class WindowUtil {

    final static String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    final static String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";

    public static void activeFocus(Parent parent) {
        for(Node node: parent.getChildrenUnmodifiable()) {
            if(node instanceof Parent nested) {
                activeFocus(nested);
            }
            else {
                node.focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        if(t1) {
                            node.setStyle(HOVERED_BUTTON_STYLE);
                        }
                        else {
                            node.setStyle(IDLE_BUTTON_STYLE);
                        }
                    }
                });
            }
        }
    }
    public static void resizer(Stage stage) {
        stage.addEventHandler(MouseEvent.MOUSE_PRESSED, pressEvent -> {
            stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent -> {
                stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
    }
}
