package wahoot.db.models;

import jakarta.persistence.PrimaryKeyJoinColumn;
/**
 * Class represents a grade for a student's performance on a quiz.
 * This class associates a student with a quiz and their grade achieved.
 */

public class Grade {

    long studentId;
    long quizId;
    int grade;

    /**
     * Constructs a Grade instance with student, quiz, and grade.
     *
     * @param quiz The {@link Quiz} for which the grade is awarded.
     * @param grade The grade achieved by the student on the quiz.
     */
    public Grade(Quiz quiz, int grade){
        this.quizId = quiz.getID();
        this.grade = grade;
    }

    /**
     * Gets the quiz ID.
     *
     * @return The ID of the quiz.
     */
    public long getQuizId() {
        return quizId;
    }

    /**
     * Sets the quiz ID.
     *
     * @param quizId The new ID of the quiz.
     */
    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    /**
     * Gets the grade.
     *
     * @return The grade awarded.
     */
    public int getGrade() {
        return grade;
    }


    /**
     * Sets the grade.
     *
     * @param grade The new grade to be set.
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Grade(){}
}
