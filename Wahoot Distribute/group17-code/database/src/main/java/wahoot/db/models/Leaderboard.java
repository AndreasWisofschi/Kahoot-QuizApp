package wahoot.db.models;
import jakarta.persistence.*;
import java.util.LinkedList;
import java.util.List;


/**
 * Represents a leaderboard for quizzes.
 * It contains a list of leaderboard entries that represent scores of participants.
 */
@Entity(name = "Leaderboard")
public class Leaderboard {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @OneToOne
    private Quiz quiz;

    /**
     * Embedded static class representing a single entry in the leaderboard.
     */
    @Embeddable
    public static class LeaderboardEntry {
        private String name;
        private double score;

        /**
         * Gets the name associated with the leaderboard entry.
         *
         * @return The name of the participant.
         */
        public String getName(){
            return name;
        }

        /**
         * Gets the score of the leaderboard entry.
         *
         * @return The score achieved by the participant.
         */
        public double getScore() {
            return score;
        }

        /**
         * Sets the score for this leaderboard entry.
         *
         * @param score The new score to set.
         */
        public void setScore(double score) {
            this.score = score;
        }

        /**
         * Constructs a new leaderboard entry with the specified name and score.
         *
         * @param name The name of the participant.
         * @param score The score achieved by the participant.
         */
        public LeaderboardEntry(String name, double score) {
            this.name = name;
            this.score = score;
        }

        public LeaderboardEntry() {

        }

        /**
         * Indicates whether some other object is "equal to" this one.
         * Equality is based on the name of the participant.
         *
         * @param o The reference object with which to compare.
         * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LeaderboardEntry that = (LeaderboardEntry) o;
            return name.equals(that.name);
        }

    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "LeaderboardEntry",
            joinColumns = @JoinColumn(name = "leaderboard_id")
    )
    public List<LeaderboardEntry> scoreList;


    /**
     * Adds an entry to the leaderboard. If an entry with the same name already exists,
     * it updates the score if the new score is higher. Entries are ordered by score.
     *
     * @param entry The new entry to add or update in the leaderboard.
     */
    public void addEntry(LeaderboardEntry entry) {
        if(scoreList.isEmpty()) scoreList.add(entry);

        else {
            boolean inserted = false;

            if(scoreList.contains(entry)) {
                int index = scoreList.indexOf(entry);
                LeaderboardEntry previous = scoreList.get(index);
                if(previous.getScore() < entry.getScore()) {
                    previous.setScore(entry.getScore());
                }
                inserted = true;
            }
            else {
                for (LeaderboardEntry position : scoreList) {
                    if (position.getScore() < entry.getScore()) {
                        int index = scoreList.indexOf(position);
                        scoreList.add(index, entry);
                        inserted = true;
                        break;
                    }
                }
            }
            if(!inserted) scoreList.add(scoreList.size() - 1, entry);
        }
    }

    public Leaderboard() {
        scoreList = new LinkedList<>();
    }

    /**
     * Retrieves the list of leaderboard entries, sorted by score.
     *
     * @return A sorted list of leaderboard entries.
     */
    public List<LeaderboardEntry> getScoreList() {
        return scoreList;
    }
}
