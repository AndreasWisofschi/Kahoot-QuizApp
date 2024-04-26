package wahoot.client.ui.view;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import javafx.fxml.FXML;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.*;
import javafx.scene.control.Label;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Andreas Wisofschi, Dazzian Boyce, Evan Buckspan, Daniel Cameron, Aydan Karmali
 * Represents the teacher enrollment view in the application. This is a create-a-class view, where the teacher user can create A classroom (ie Calculus, Algebra etc.)
 * This view allows teachers to create new classes, including setting a class name, choosing a subject, and providing a description.
 * Once successfully created the label will print the courseID, where the student user can also query to enroll into later.
 * It extends {@link ResizeableFXMLView} to support resizable UI components, and borderpane for side-nav view and top-nav view.
 */

public class TeacherEnrollView extends ResizeableFXMLView<BorderPane> {

    private Teacher teacher;
    private Manager manager;


    /**
     * Constructs a new TeacherEnrollView with the specified manager and teacher.
     * Initializes the UI with top and side views and allows the teacher to add classes, in this case classes they wish to create.
     *
     * @param manager The application's data manager.
     * @param teacher The teacher for whom the class is being created.
     */
    public TeacherEnrollView(Manager manager, Teacher teacher) {
        this.manager=manager;
        this.teacher=teacher;
        root.setTop(new TopView());
        root.setLeft(new SideView(teacher, manager));
        manager.addClass(Classroom.class);

        for (Classroom classroom : teacher.getClassesTaught()) {
            Label x = new Label(classroom.getClassName());
            classVbox.getChildren().add(x);
        }
    }
    @FXML
    private RadioButton chemistry;

    @FXML
    private TextField classNameField;

    @FXML
    private RadioButton computerScience;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ToggleGroup subjectToggleGroup;

    @FXML
    private RadioButton english;

    @FXML
    private RadioButton geography;

    @FXML
    private RadioButton math;
    @FXML
    private VBox classVbox;
    @FXML
    private ScrollPane currentClassList;

    @FXML
    private Button submitButton;

    @FXML
    private Label addedLabel;

    @FXML
    private Label courseLabel;

    @FXML
    private BorderPane root;

    /**
     * Handles the action of the submit button being clicked.
     * Gathers the class name and selected subject from the form, generates a unique course code,
     * creates a new Classroom instance, and saves it using the manager. Updates the UI to show success.
     *
     * @param event The MouseEvent triggered when the submit button is clicked.
     */
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    @FXMLMethod(node = "submitButton", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void handleSubmitButton(MouseEvent event) {
        String className = classNameField.getText();    //CLass name ie. Calculus
        RadioButton selectedSubject = (RadioButton) subjectToggleGroup.getSelectedToggle();     //subject field ie. Math
        String subjectName = selectedSubject.getId().toUpperCase();

        Random random = new Random();
        String courseCode = "";

        for(int i = 0; i < 5; i++) {
            courseCode += alphabet.charAt(random.nextInt(0, alphabet.length() - 1));
        }

        Classroom newclass = new Classroom(courseCode,teacher);
        newclass.setName(className);
        newclass.setSubject(Subject.valueOf(subjectName.toUpperCase()));
        manager.save(newclass);

        addedLabel.setText("Successfully created class");
        courseLabel.setText("Generated Course Code: "+courseCode);

    }

}
