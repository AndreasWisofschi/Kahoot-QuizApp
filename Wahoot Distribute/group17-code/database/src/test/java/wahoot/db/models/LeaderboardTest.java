package wahoot.db.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardTest {

    Leaderboard leaderboard;
    Leaderboard.LeaderboardEntry entry1;
    Leaderboard.LeaderboardEntry entry2;
    Leaderboard.LeaderboardEntry entry3;


    @BeforeEach
    void setUp() {
        leaderboard = new Leaderboard();
        entry1 = new Leaderboard.LeaderboardEntry("Dan", Integer.MAX_VALUE);
        entry2 = new Leaderboard.LeaderboardEntry("John", 1000);
        entry3 = new Leaderboard.LeaderboardEntry("Secret man", 1000);

    }

    @Test
    void equals(){
        Object obj = new Object();
        assertTrue(entry1.equals(entry1));
        assertFalse(entry1.equals(entry2));
        assertFalse(entry1.equals(obj));
    }

    @Test
    void getScoreList() {
        List<Leaderboard.LeaderboardEntry> list = new LinkedList<>();
        leaderboard.addEntry(entry1);
        list.add(entry1);

        assertEquals(list, leaderboard.getScoreList());


    }

    @Test
    void addEntry() {
        List<Leaderboard.LeaderboardEntry> list = new LinkedList<>();
        leaderboard.addEntry(entry1);
        leaderboard.addEntry(entry2);
        leaderboard.addEntry(entry3);
        list.add(entry1);
        list.add(entry2);
        list.add(entry3);

        Set<Leaderboard.LeaderboardEntry> testList = new HashSet<>(list);
        Set<Leaderboard.LeaderboardEntry> result = new HashSet<>(leaderboard.getScoreList());

        assertEquals(testList, result);


    }


}