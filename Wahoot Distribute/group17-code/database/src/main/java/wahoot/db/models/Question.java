package wahoot.db.models;

import jakarta.persistence.Embeddable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a question for a quiz, including the question prompt, and correct answer,
 * with multiple-choice options.
 */
@Embeddable
public class Question {
    String prompt;
    String correct;
    String optionA;
    String optionB;
    String optionC;
    String optionD;

    /**
     * Gets the first option of the question.
     *
     * @return The first option.
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * Gets the second option of the question.
     *
     * @return The first option.
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * Gets the third option of the question.
     *
     * @return The first option.
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * Gets the fourth option of the question.
     *
     * @return The first option.
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * Constructs a Question with the given prompt, correct answer, and options.
     *
     * @param prompt  The question text presented to the quiz taker.
     * @param correct The correct answer for the question.
     * @param options An array of 4 option to select the answer from.
     * @throws QuizException if the options array does not contain four choices
     *                       or the correct answer is not among the options.
     */
    public Question(String prompt, String correct, String ... options){
        this.prompt = prompt;
        this.correct = correct;

        if(options.length != 4){
            throw new QuizException("You're question must have four choices.");
        }
        boolean containsCorrect = false;
        for(String option : options) {
            if(option.equals(correct)) {
                containsCorrect = true;
                break;
            }
        }
        if(!containsCorrect) throw new QuizException("One of the options must be the correct answer");

        optionA = options[0];
        optionB = options[1];
        optionC = options[2];
        optionD = options[3];

    }

    /**
     * Gets the prompt of the question.
     *
     * @return The prompt text.
     */
    public String getPrompt(){
        return prompt;
    }

    /**
     * Gets the correct answer for the question.
     *
     * @return The correct answer.
     */
    public String getCorrect() {
        return correct;
    }

    /**
     * Retrieves all the options for the question.
     *
     * @return A set containing all the options.
     */
    public Set<String> getOptions(){
        return new HashSet<>(){{add(optionA); add(optionB); add(optionC); add(optionD);}};
    }

    /**
     * Checks if the provided option is the correct answer to the question.
     *
     * @param option The option to check.
     * @return {@code true} if the option is correct, {@code false} otherwise.
     */
    public boolean isCorrect(String option){
        return option.equals(correct);
    }

    public Question(){

    }
}
