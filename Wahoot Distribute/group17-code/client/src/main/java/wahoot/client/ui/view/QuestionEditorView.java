package wahoot.client.ui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.util.Macro;
import wahoot.db.models.Question;

/**
 * A view for editing and creating questions within the Wahoot application, extending {@link ResizeableFXMLView} with
 * {@link AnchorPane} as the root layout. This class provides a user interface for entering question text and options,
 * as well as selecting the correct answer from those options.
 *
 * <p>The view is made of of multiple {@link TextField} components for inputting the question and its options, and a
 * {@link ComboBox} for selecting which option is correct.</p>
 *
 * <p>Two constructors are provided: a no-argument constructor for initializing a new question, and a constructor that
 * accepts a {@link Question} object for editing an existing question. Initialization includes setting up key event handling
 * for efficient data entry and populating the combo box with options.</p>
 *
 */
public class QuestionEditorView extends ResizeableFXMLView<AnchorPane> {
    @FXML
    private ComboBox<String> questionComboBox;

    @FXML
    private TextField optionFourField;

    @FXML
    private TextField optionOneField;

    @FXML
    private TextField optionThreeField;

    @FXML
    private TextField optionTwoField;

    @FXML
    private TextField questionTitleField;

    /**
     * Retrieves the text entered into the option one text field.
     *
     * @return A {@link String} representing the text of option one.
     */
    public String getOptionOneField() {
        return optionOneField.getText();
    }

    /**
     * Retrieves the text entered into the option two text field.
     *
     * @return A {@link String} representing the text of option two.
     */
    public String getOptionTwoField() {
        return optionTwoField.getText();
    }

    /**
     * Retrieves the text entered into the option three text field.
     *
     * @return A {@link String} representing the text of option three.
     */
    public String getOptionThreeField() {
        return optionThreeField.getText();
    }

    /**
     * Retrieves the text entered into the option four text field.
     *
     * @return A {@link String} representing the text of option four.
     */
    public String getOptionFourField() {
        return optionFourField.getText();
    }

    /**
     * Retrieves the text entered into the option title text field.
     *
     * @return A {@link String} representing the text question title.
     */
    public String getQuestionTitleField() {
        return questionTitleField.getText();
    }

    /**
     * Determines and returns the correct answer based on the selection made in the combo box.
     *
     * @return A {@link String} representing the correct answer to the question.
     */
    public String getCorrect() {
        return switch (questionComboBox.getSelectionModel().getSelectedItem()) {
            case "Option #1" -> getOptionOneField();
            case "Option #2" -> getOptionTwoField();
            case "Option #3" -> getOptionThreeField();
            case "Option #4" -> getOptionFourField();
            default -> null;
        };
    }

    ObservableList<String> options = FXCollections.observableArrayList("Option #1", "Option #2", "Option #3", "Option #4");

    QuestionEditorView() {
        Macro.group(KeyCode.ENTER, questionTitleField, optionOneField, optionTwoField, optionThreeField, optionFourField);
        questionComboBox.setItems(options);
    }

    /**
     * For editing an existing question. Initializes the view with the data from the specified
     * question, populating the input fields and selecting the correct answer in the {@code ComboBox}.
     *
     * @param question The {@link Question} object whose data is to be edited.
     */
    QuestionEditorView(Question question){
        this();

        optionOneField.setText(question.getOptionA());
        optionTwoField.setText(question.getOptionB());
        optionThreeField.setText(question.getOptionC());
        optionFourField.setText(question.getOptionD());

        if(question.getCorrect().equals(question.getOptionA())) {
            questionComboBox.getSelectionModel().select(0);
        } else if(question.getCorrect().equals(question.getOptionB())) {
            questionComboBox.getSelectionModel().select(1);
        } else if(question.getCorrect().equals(question.getOptionC())) {
            questionComboBox.getSelectionModel().select(2);
        } else {
            questionComboBox.getSelectionModel().select(3);
        }
        questionTitleField.setText(question.getPrompt());

    }
}
