package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import wahoot.client.ui.fxml.FXMLEvent;
import wahoot.client.ui.fxml.FXMLMethod;
import wahoot.client.ui.fxml.FXMLView;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.SideView;
import wahoot.db.Manager;
import wahoot.db.models.Classroom;
import wahoot.db.models.Quiz;
import javafx.scene.input.MouseEvent;
import wahoot.db.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * PublicQuizzesView is a user interface component that displays a list of public quizzes
 * available for users to participate in. It extends the FXMLView to utilize JavaFX components
 * for displaying the UI elements.
 */
public class PublicQuizzesView extends ResizeableFXMLView<BorderPane> {
    @FXML
    private ListView<String> QuizzesList;
    @FXML
    private BorderPane root;
    private Manager manager;
    private Student student;
    private List<Quiz> publicQuizzes;

    /**
     * Constructor for PublicQuizzesView.
     * Initializes the view with a list of public quizzes fetched from the database manager.
     *
     * @param manager The database manager used to fetch quizzes.
     */
    public PublicQuizzesView(Manager manager, Student student){
        this.student = student;
        this.manager = manager;
        manager.addClass(Quiz.class);

        root.setLeft(new SideView(student,manager));

        this.publicQuizzes = new ArrayList<>();
        List<Quiz> allQuizzes = manager.getAllByClass(Quiz.class);
        for (Quiz q: allQuizzes) {
            if (q.getRoom() == null) {
                publicQuizzes.add(q);
                QuizzesList.getItems().add(q.getName());
            }
        }
    }

    /**
     * Handles the double-click event on an item in the QuizzesList.
     * Opens the game view for the selected quiz.
     *
     * @param event The mouse event that triggers this method.
     */
    @FXMLMethod (node = "QuizzesList", event = FXMLEvent.MOUSE_EVENT_CLICKED)
    private void quizPicked(MouseEvent event){
        String selectedQuiz = QuizzesList.getSelectionModel().getSelectedItem();
        if(event.getClickCount() >= 2) {
            Quiz quiz = publicQuizzes.stream()
                    .filter((x)->x.getName().equals(selectedQuiz))
                    .findFirst().get();
            Stage stage = (Stage) QuizzesList.getScene().getWindow();
            stage.setScene(new Scene(new GameView(manager, quiz, student)));
        }
    }
}
