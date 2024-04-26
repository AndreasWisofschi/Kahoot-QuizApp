package wahoot.db.models;
import java.util.Timer;

/**
 * A stopwatch utility class to measure time intervals in milliseconds.
 */

public class Stopwatch {

    private boolean running = false;
    private long startTime;
    private long stopTime;

    /**
     * Starts the stopwatch unless it is already running, in which case it throws an exception.
     *
     * @throws TimerException if the stopwatch is already running.
     */
    public void start() throws TimerException{
        if(!running){
            this.startTime = System.currentTimeMillis();
            running = true;
        }
        else{
            throw new TimerException("Stopwatch already running");
        }
    }

    /**
     * Checks if the stopwatch is currently running.
     *
     * @return true if the stopwatch is running, false otherwise.
     */
    public boolean getRunning() {
        return running;
    }

    /**
     * Stops the stopwatch unless it is not running, in which case it throws an exception.
     *
     * @throws TimerException if the stopwatch is not running.
     */
    public void stop() throws TimerException{
        if(running){
            this.stopTime = System.currentTimeMillis();
            running = false;
        }
        else{
            throw new TimerException("Stopwatch not running");
        }
    }

    /**
     * Resets the stopwatch to its initial state.
     */
    public void reset(){
        this.startTime = 0;
        this.stopTime = 0;
        this.running = false;
    }

    /**
     * Calculates the duration in seconds that the stopwatch was running.
     *
     * @return the duration in seconds or -1 if the stopwatch is still running.
     */
    public long durationSeconds(){
        return running ? -1 : (this.stopTime - this.startTime) / 1000;

    }
}
