package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Andreas Wisofschi, Dazzian Boyce, Evan Buckspan, Daniel Cameron, Aydan Karmali
 * Represents the view for students to enroll in classrooms.
 * It extends {@link ResizeableFXMLView} to provide resizable UI component, and borderpane for side-nav view and top-nav view.
 * The view includes functionality to enter a classroom code, which if correct allows the student to enroll themselves in the classroom,
 * and display a list of already enrolled classrooms, in a list view at the top.
 */

public class StudentClassroomEnrollView extends ResizeableFXMLView<BorderPane> {

    private Manager manager;
    private Classroom classroom;
    private Student student;

    @FXML
    private TextField courseID;

    @FXML
    private Label enrollFlag;
    @FXML
    private Button enrollBtn;
    @FXML
    private ListView<String> enrolledView;
    @FXML
    private BorderPane root;

    /**
     * Creates an instance of StudentClassroomEnrollView.
     * Initializes the UI with top and side navigation views and populates the list of currently enrolled classrooms.
     *
     * @param manager The application manager handling data operations.
     * @param student The student instance for whom the classroom enrollment is being managed.
     */
    public StudentClassroomEnrollView(Manager manager, Student student) {
        this.manager = manager;
        this.student = student;
        root.setTop(new TopView());
        root.setLeft(new SideView(student, manager));

        for (Classroom classroom1 : student.getClasses()) {
            enrolledView.getItems().add(classroom1.getClassName());
        }
    }

    /**
     * Handles the action of clicking the enroll button.
     * Validates the course ID entered by the user, and if valid, enrolls the student in the classroom
     * and updates the UI accordingly.
     *
     * @param event The MouseEvent triggered when the enroll button is clicked.
     */
    @FXMLMethod(node = "enrollBtn", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void enrollButtonClicked(MouseEvent event) {
        String course = courseID.getText();

        manager.addClass(Classroom.class);

        Optional<?> allClasses = manager.find(course);

        if(allClasses.isPresent()) {
            Classroom c = (Classroom) allClasses.get();

            enrollFlag.setText("Valid courseID! Classroom Added");
            c.addStudent(student);
            student.addClass(c);

            manager.update(c);
            manager.update(student);

        }
        else {
            enrollFlag.setText("Invalid: "+course+"\n"+"try again!");
        }

    }

}