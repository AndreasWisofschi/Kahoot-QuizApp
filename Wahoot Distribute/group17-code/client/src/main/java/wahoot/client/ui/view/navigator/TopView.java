package wahoot.client.ui.view.navigator;

import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import com.jfoenix.controls.JFXToggleButton;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.ResizeableFXMLView;

/**
 * A controller class for the top navigation view in the Wahoot application, extending {@link ResizeableFXMLView}
 * with an {@code HBox} as the root layout. This class manages the UI components located at the top of the application window,
 *
 * <h2>UI Components:</h2>
 * <ul>
 * <li>{@code brightModeImage}: An {@link ImageView} displayed when the application is in bright mode.</li>
 * <li>{@code darkModeImage}: An {@link ImageView} displayed when the application is in dark mode.</li>
 * <li>{@code darkModeToggleButton}: A {@link JFXToggleButton} for toggling between dark and bright modes.</li>
 * <li>{@code forceQuizCheckBox}: A {@link JFXCheckBox} for enabling or disabling force quiz mode.</li>
 * <li>{@code xImage}: An {@link ImageView} serving as an exit button to close the application.</li>
 * </ul>
 *
 * @see ResizeableFXMLView
 * @see HBox
 * @see ImageView
 * @see JFXToggleButton
 * @see JFXCheckBox
 */

public class TopView extends ResizeableFXMLView<HBox> {

    @FXML
    private ImageView brightModeImage;

    @FXML
    private ImageView darkModeImage;

    @FXML
    private JFXToggleButton darkModeToggleButton;

    @FXML
    private JFXCheckBox forceQuizCheckBox;

    @FXML
    private ImageView xImage;

    /**
     * Handles click events on the exit image, terminating the application.
     *
     * @param event The mouse event that triggered this method.
     */
    @FXMLMethod (node = "xImage", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void xImageClicked(MouseEvent event) {
        System.exit(0);
    }


    /**
     * Sets the visibility of the force quiz mode checkbox.
     *
     * @param visible {@code true} to make the checkbox visible; {@code false} otherwise.
     */
    public void setQuizCheckBoxVisibility(boolean visible) {
        forceQuizCheckBox.setVisible(visible);
    }

    /**
     * Retrieves the selected property of the force quiz mode checkbox.
     *
     * @return The selected property, allowing for binding and listening to its changes.
     */
    public BooleanProperty getQuizCheckBoxSelectedProperty() {
        return forceQuizCheckBox.selectedProperty();
    }

    public TopView() {

        darkModeImage.visibleProperty().bind(darkModeToggleButton.selectedProperty());
        brightModeImage.visibleProperty().bind(darkModeToggleButton.selectedProperty().not());
    }
}
