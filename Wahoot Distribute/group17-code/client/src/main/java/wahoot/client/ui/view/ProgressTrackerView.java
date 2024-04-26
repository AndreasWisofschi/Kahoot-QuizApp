package wahoot.client.ui.view;

import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.model.StudentModel;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
/**
 * Class that represents the UI of the progress tracker
 * the tracker is for teachers to monitor their students progress
 */
public class ProgressTrackerView extends ResizeableFXMLView<BorderPane> {

    private Teacher teacher;

    @FXML
    private TreeView<String> studentTreeView;


    @FXML
    private Label emailLabel;

    @FXML
    private Button launchGraphButton;

    @FXML
    private Label levelLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label quizzesCompletedLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private BorderPane root;

    private Student currentSelection = null;

    private Manager manager;


    /**
     * Launches graph of progress when selected
     * @param event takes in user input
     */
    @FXML
    void launchGraphButtonClicked(ActionEvent event) {
        if(currentSelection != null) {
            root.setRight(new ChartView(currentSelection));
        }
    }

    List<Classroom> classes = Arrays.asList (
            new Classroom("fewr243", "Mr. Scanga's Grade 8 Class"),
            new Classroom("ggegeg", "Mr. Scanga's Grade 7 Class"),
            new Classroom("fefwf3", "Mr. Scanaga's Grade 6 Class"),
            new Classroom("f34t44", "Mr. Scanga's 5 Class")
    );

    Random random = new Random();

    String choices = "abcdedfghhijklmnopqrstuvwxyz12345667890";

    /**
     *Generates a random string of length 3 to 5 inclusive
     * @return generated string
     */
    private String generateRandomString() {
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < random.nextInt(3, 6); i++) {
            int select = random.nextInt(0, choices.length() -1 );
            string.append(choices.charAt(select));
        }
        return string.toString();
    }


    final Image rootIcon = new Image(getClass().getResource("/img/icons8-teacher-48.png").toExternalForm());
    final Image classroomIcon = new Image(getClass().getResource("/img/icons8-user-folder-48.png").toExternalForm());
    final Image personIcon = new Image(getClass().getResource("/img/icons8-person-30.png").toExternalForm());


    /**
     *
     */
    public void initializeData() {
        for(Classroom classroom: classes) {
            classroom.addAllStudents( Stream.generate(() -> {
                Student student = (Student) new UserBuilder().setFirstName(generateRandomString()).setLastName(generateRandomString()).setUsername(generateRandomString()).build(AccountType.STUDENT);
                return student;
            }).limit(20).toList());
        }
    }
    /**
     * generates the UI that teacher interacts with
     * displays their classrooms and students in those rooms
     * @param teacher Teacher that view is generated for
     */
    public ProgressTrackerView(Teacher teacher, Manager manager) {
        this.manager = manager;

        this.teacher = teacher;

        root.setLeft(new SideView(teacher, manager));
        root.setTop(new TopView());

        levelLabel.setText("");
        quizzesCompletedLabel.setText("");
        emailLabel.setText("");
        usernameLabel.setText("");
        scoreLabel.setText("");
        nameLabel.setText("");


        ImageView rootIconView = new ImageView(rootIcon);

        rootIconView.setFitHeight(30);
        rootIconView.setFitWidth(30);

        TreeItem<String> root = new TreeItem<>("Classes",rootIconView);

        root.setExpanded(true);

        studentTreeView.setRoot(root);

        //initializeData();

        for(Classroom classroom: teacher.getClassesTaught()) {

            ImageView classroomIconView = new ImageView(classroomIcon);

            classroomIconView.setFitHeight(30);
            classroomIconView.setFitWidth(30);

            classroomIconView.setFitHeight(30);
            classroomIconView.setFitWidth(30);


            TreeItem<String> classroomItem = new TreeItem<>(classroom.getClassName(), classroomIconView);

            for(Student student: classroom.getStudents()) {

                ImageView personIconView = new ImageView(personIcon);

                personIconView.setFitHeight(30);
                personIconView.setFitWidth(30);

                TreeItem<String> studentItem = new TreeItem<>(student.getFirstname() + " " + student.getLastname(), personIconView);

                classroomItem.getChildren().add(studentItem);
            }

            studentTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

                for(Classroom classroom1: teacher.getClassesTaught()) {
                    for(Student student: classroom1.getStudents()) {
                        String name = student.getFirstname() + " "+ student.getLastname();
                        if(name.equals(newValue.getValue())) {
                            nameLabel.setText(name);
                            usernameLabel.setText(student.getUsername());
                            emailLabel.setText(student.getEmail());
                            scoreLabel.setText(String.valueOf(student.getScore()));
                            levelLabel.setText(String.valueOf(student.getLevel()));
                            quizzesCompletedLabel.setText(String.valueOf(student.getQuizzesCompleted()));

                            currentSelection = student;
                        }
                    }
                }
            });

            root.getChildren().add(classroomItem);

        }





    }
}
