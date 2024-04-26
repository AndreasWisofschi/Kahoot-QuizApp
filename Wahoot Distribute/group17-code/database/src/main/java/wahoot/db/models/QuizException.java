package wahoot.db.models;

/**
 * Exception class for handling quiz-related errors and exceptions.
 * This exception is used throughout the quiz application to indicate conditions are met.
 */
public class QuizException extends RuntimeException {

    /**
     * Constructs a new QuizException with the specified detail message.
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     *
     * @param message The detail message. The detail message is saved for
     *                later retrieval by the {@link Throwable#getMessage()} method.
     */
    public QuizException(String message){
        super(message);
    }

}
