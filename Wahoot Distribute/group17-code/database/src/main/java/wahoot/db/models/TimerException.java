package wahoot.db.models;

/**
 * Custom exception class for handling errors related to timer operations.
 * This exception can be thrown in scenarios where timer-related issues occur,
 */
public class TimerException extends RuntimeException {

    /**
     * Constructs a new TimerException with the specified detail message.
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     *
     * @param message The detail message which provides more information on the timer error.
     */
    public TimerException(String message){
        super(message);
    }
}