package wahoot.db.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question("Correct answer is A", "A", "A", "B", "C", "D");
    }

    @Test
    void getOptionA() {
        assertEquals("A", question.getOptionA());
    }

    @Test
    void getOptionB() {
        assertEquals("B", question.getOptionB());
    }

    @Test
    void getOptionC() {
        assertEquals("C", question.getOptionC());
    }

    @Test
    void getOptionD() {
        assertEquals("D", question.getOptionD());
    }

    @Test
    void getPrompt() {
        assertEquals("Correct answer is A", question.getPrompt());
    }

    @Test
    void getCorrect() {
        assertEquals("A", question.getCorrect());
    }

    @Test
    void getOptions() {
        assertEquals(4, question.getOptions().size());
        assertTrue(question.getOptions().contains("A"));
        assertTrue(question.getOptions().contains("B"));
        assertTrue(question.getOptions().contains("C"));
        assertTrue(question.getOptions().contains("D"));
    }

    @Test
    void isCorrect() {
        assertTrue(question.isCorrect("A"));
        assertFalse(question.isCorrect("B"));
    }
}