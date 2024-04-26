package wahoot.client.ui.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.db.Manager;
import wahoot.db.models.Classroom;
import wahoot.db.models.Quiz;
import wahoot.db.models.Student;
import java.util.Optional;
import java.util.Set;

/**
 * This class represents a view for displaying quizzes associated with a student in a JavaFX application.
 * It extends {@link FXMLView} with a generic type {@link AnchorPane} as its root layout.
 * The view fetches student data and their associated classrooms and quizzes from a database
 * and dynamically generates the UI components to display this information.
 *
 * <p>The view consists of a {@link VBox} as the main container which matches the {@code fx:id} specified in FXML.
 * Each classroom associated with the student is represented as a separate {@link VBox} within the main container,
 * and each quiz within a classroom is represented as a {@link Button} within the classroom's VBox.</p>
 *
 * <p>The UI components are styled using inline CSS to distinguish different parts of the UI,
 * such as a border around each classroom section and bold font for classroom names.</p>
 *
 * <p>This class requires an instance of {@link Manager} for database operations and a {@link Student} object
 * representing the student whose quizzes are to be displayed.</p>
 *
 * @see FXMLView
 * @see AnchorPane
 * @see VBox
 * @see Button
 * @see Label
 */

public class StudentQuizzesView extends FXMLView<AnchorPane> {
    /**
     * The main container for adding classroom and quiz components.
     * This should be linked with an {@code fx:id} in FXML to automatically initalize.
     */
    public VBox mainContainer; // This matches fx:id in FXML

    public void initialize() {}

    private Manager manager;
    private Student student;


    /**
     * Constructs a {@code StudentQuizzesView} with the specified database manager and student.
     * It fetches the student's classrooms and quizzes from the database, then dynamically creates
     * and adds UI components to display this information.
     *
     * @param manager The database manager to retrieve student, classroom, and quiz information.
     * @param student The student whose quizzes are to be displayed.
     */
    public StudentQuizzesView(Manager manager, Student student) {
        this.manager = manager;
        this.student = student;

        Optional<?> result = manager.find(student.getUsername());
        if (result.isPresent()) {
            Object object = result.get();
            if (object instanceof Student s) {
                for (Classroom classroom : s.getClasses()) {
                    // Create a box for each classroom
                    VBox classroomBox = new VBox(5); // 5 is the spacing between elements
                    classroomBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10;");

                    // Classroom label
                    Label classroomLabel = new Label(classroom.getClassName());
                    classroomLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
                    classroomBox.getChildren().add(classroomLabel);

                    Set<Quiz> quizzes = classroom.getQuizzes();

                    if (quizzes.isEmpty()) {
                        Label noQuizLabel = new Label("No quizzes available");
                        classroomBox.getChildren().add(noQuizLabel);
                    } else {
                        for (Quiz quiz : quizzes) {
                            Button quizButton = new Button(quiz.getName());
                            quizButton.setOnAction(event -> {

                            });
                            classroomBox.getChildren().add(quizButton);
                        }
                    }
                    mainContainer.getChildren().add(classroomBox);
                }
            }
        }
    }
}


