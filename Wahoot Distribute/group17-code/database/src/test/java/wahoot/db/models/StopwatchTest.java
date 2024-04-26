package wahoot.db.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class StopwatchTest {

    @BeforeEach
    void setUp() {
    }


    @Test
    void start() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        assertThrows(TimerException.class, stopwatch::start);

    }

    @Test
    void stop() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        stopwatch.stop();
        assertThrows(TimerException.class, stopwatch::stop);
    }

    @Test
    void reset() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.reset();
        assertFalse(stopwatch.getRunning());
    }

    @Test
    void durationSeconds() throws InterruptedException {

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        assertEquals(-1, stopwatch.durationSeconds());

        try {
            synchronized (stopwatch) {
                stopwatch.wait(2000);
            }
            stopwatch.stop();
            assertEquals(2, stopwatch.durationSeconds());
        } catch (InterruptedException e) {
            fail();
        }
    }
}