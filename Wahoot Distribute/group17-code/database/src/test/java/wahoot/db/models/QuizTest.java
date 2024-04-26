package wahoot.db.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class QuizTest {

    Quiz q;
    @BeforeEach
    void setUp(){
        Classroom room = new Classroom("a");
        Teacher teacher = new Teacher();
        q = new Quiz(1, teacher, room);

    }
    @Test
    void setRoom(){
        Classroom newRoom = new Classroom("abc");
        q.setRoom(newRoom);
        assertEquals(newRoom, q.getRoom());
    }

    @Test
    void getRoom(){
        Classroom newRoom = new Classroom("abc");
        q.setRoom(newRoom);
        assertEquals(newRoom, q.getRoom());
    }

    @Test
    void getID() {
        Quiz quiz = new Quiz(123);
        assertEquals(123, quiz.getID());
    }

    @Test
    void setName() {
        Quiz quiz = new Quiz();
        quiz.setName("Test Quiz");
        assertEquals("Test Quiz", quiz.getName());
    }

    @Test
    void getName() {
        Quiz quiz = new Quiz();
        quiz.setName("Test Quiz");
        assertEquals("Test Quiz", quiz.getName());
    }

    @Test
    void setMaxTimeSeconds() {
        Quiz quiz = new Quiz();
        quiz.setMaxTimeSeconds(60.0);
        assertEquals(60.0, quiz.getMaxTimeSeconds());
    }

    @Test
    void getMaxTimeSeconds(){
        Quiz quiz = new Quiz();
        quiz.setMaxTimeSeconds(60.0);
        assertEquals(60.0, quiz.getMaxTimeSeconds());
    }

    @Test
    void getMaxScore() {
        Quiz quiz = new Quiz();
        assertEquals(1000, quiz.getMaxScore());
    }

    @Test
    void setSpeedMultiplier() {
        Quiz quiz = new Quiz();
        quiz.setSpeedMultiplier(0.5);
        assertEquals(0.5, quiz.getSpeedMultiplier());
    }

    @Test
    void getSpeedMultiplier() {
        Quiz quiz = new Quiz();
        quiz.setSpeedMultiplier(0.5);
        assertEquals(0.5, quiz.getSpeedMultiplier());
    }

    @Test
    void startQuiz() {

        q.startQuiz();
        assertThrows(TimerException.class, q::startQuiz);
    }

    @Test
    void stopQuiz(){
        q.startQuiz();
        q.stopQuiz();
        assertThrows(TimerException.class, q::stopQuiz);
    }


    @Test
    void setAuthor() {
        Quiz quiz = new Quiz();
        Teacher teacher = new Teacher();
        quiz.setAuthor(teacher);
        assertEquals(teacher, quiz.getAuthor());
    }

    @Test
    void getAuthor() {
        Quiz quiz = new Quiz();
        Teacher teacher = new Teacher();
        quiz.setAuthor(teacher);
        assertEquals(teacher, quiz.getAuthor());
    }

    @Test
    void getElapsedTime(){
        Quiz quiz = new Quiz();
        assertEquals(0, quiz.getElapsedTime());
    }
    @Test
    void setElapsedTime(){
        Quiz quiz = new Quiz();
        quiz.setElpasedTime(30);
        assertEquals(30, quiz.getElapsedTime());
    }
    @Test
    void addQuestion() {
        Quiz quiz = new Quiz();
        Question question = new Question("What is the capital of France?", "Paris", "Paris", "London", "Berlin", "Rome");
        quiz.addQuestion(question);
        assertTrue(quiz.getQuestions().contains(question));
    }

    @Test
    void removeQuestion() {
        Quiz quiz = new Quiz();
        Question question = new Question("What is the capital of France?", "Paris", "Paris", "London", "Berlin", "Rome");
        quiz.addQuestion(question);
        try {
            quiz.removeQuestion(question);
        } catch (QuizException e) {
            fail("Exception not expected: " + e.getMessage());
        }
        assertFalse(quiz.getQuestions().contains(question));
    }

    @Test
    void shuffle() {
        Quiz quiz = new Quiz();
        Question question1 = new Question("Question 1", "A", "A", "B", "C", "D");
        Question question2 = new Question("Question 2", "B", "A", "B", "C", "D");
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);

        // Create a copy of the original set of questions
        List<Question> originalQuestions = new ArrayList<>(quiz.getQuestions());

        quiz.shuffle();

        assertTrue(originalQuestions.containsAll(quiz.getQuestions()) && quiz.getQuestions().containsAll(originalQuestions));
    }
    @Test
    void getQuiz() {
        Quiz quiz = new Quiz();
        Question question1 = new Question("Question 1", "A", "A", "B", "C", "D");
        Question question2 = new Question("Question 2", "B", "A", "B", "C", "D");
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);

        // Get an iterator over the questions
        Iterator<Question> iterator = quiz.getQuiz();

        // Check that both questions are returned by the iterator
        assertTrue(iterator.hasNext());
        assertEquals(question1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(question2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void getQuestions() {
        Quiz quiz = new Quiz();
        Question question1 = new Question("Question 1", "A", "A", "B", "C", "D");
        Question question2 = new Question("Question 2", "B", "A", "B", "C", "D");
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);

        assertEquals(2, quiz.getQuestions().size());
        assertTrue(quiz.getQuestions().contains(question1));
        assertTrue(quiz.getQuestions().contains(question2));
    }

    @Test
    void gradeQuestion() {
        Quiz quiz = new Quiz();
        Question question = new Question("What is the capital of France?", "Paris", "Paris", "London", "Berlin", "Rome");
        quiz.addQuestion(question);

        assertEquals(1, quiz.gradeQuestion(question, "Paris"));
        assertEquals(1000, quiz.getMaxScore());

        assertEquals(0, quiz.gradeQuestion(question, "London"));
        assertNotEquals(1000, quiz.gradeQuestion(question, "London"));
    }


    @Test
    void finishQuiz() {
        Quiz quiz = new Quiz();
        Quiz quiz2 = new Quiz();
        quiz.setMaxTimeSeconds(10);
        quiz2.setMaxTimeSeconds(5);
        assertEquals(1000, quiz.finishQuiz());//ceiling calculation
        quiz.startQuiz();
        try{
            synchronized (quiz) {
                quiz.wait(6000);
                assertEquals(800, quiz.finishQuiz());//middle calculation((total time - elapsed time) / 0.5(totalTime)
            }
            quiz2.startQuiz();
            synchronized (quiz2){
                quiz2.wait(5000);
                assertEquals(700, quiz2.finishQuiz());
            }
        }
        catch (InterruptedException e){
            fail();
        }


    }


}
