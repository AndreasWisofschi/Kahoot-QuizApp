package wahoot.client.ui.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import wahoot.db.Manager;
import wahoot.db.models.Classroom;
import wahoot.db.models.Quiz;
import wahoot.db.models.Teacher;

public class SaveEvent extends Event {
    private Manager manager ;

    private String name;

    private Quiz quiz;

    private Classroom classroom;

    private Teacher teacher ;
    public static EventType<SaveEvent> QUIZ_SAVE = new EventType<>(Event.ANY, "QUIZ_SAVE");
    public SaveEvent(EventType<? extends Event> eventType, Manager manager, String name, Quiz quiz, Classroom classroom, Teacher teacher) {
        super(eventType);
        this.manager = manager;
        this.name = name;
        this.quiz = quiz;
        this.classroom = classroom;
        this.teacher = teacher;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Manager getManager() {
        return manager;
    }

    public String getName() {
        return name;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public SaveEvent(Object source, EventTarget target) {
        super(source, target, QUIZ_SAVE);

    }

    public SaveEvent(Object o, EventTarget eventTarget, EventType<? extends Event> eventType) {
        super(o, eventTarget, eventType);
    }
}
