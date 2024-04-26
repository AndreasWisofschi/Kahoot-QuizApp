package wahoot.client.ui.model;

import javafx.beans.property.*;
import wahoot.db.models.Quiz;

public class StudentModel {

    private SimpleStringProperty name;

    private SimpleStringProperty email;

    private SimpleStringProperty username;

    private SimpleLongProperty score;

    private SimpleLongProperty level;


    private ListProperty<Quiz> quizzesCompleted;

    public String getName() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getUsername() {
        return username.get();
    }

    public long getScore() {
        return score.get();
    }

    public long getLevel() {
        return level.get();
    }

    public static StudentModel wrapFromDatabase(wahoot.db.models.Student student) {
        return new StudentModel(student.getFirstname() + " " + student.getFirstname(), student.getUsername(), student.getEmail(), student.getScore(), (long) (Math.random() * 10) + 1);
    }

    public StudentModel(String name, String username, String email, long score, long level) {
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.score = new SimpleLongProperty(score);
        this.level = new SimpleLongProperty(level);
    }
}
