package wahoot.db.models;

import jakarta.persistence.*;

import java.util.*;

/**
 * Entity class representing a quiz, which contains a collection of
 * questions and is associated with a specific classroom.
 */

@Entity(name = "Quiz")
public class Quiz {
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "Question",
            joinColumns = @JoinColumn(name = "quiz_id")
    )
    //list of questions that belong to this quiz
    private List<Question> questions = new ArrayList<>();

    @Id
    @GeneratedValue
    private long id;
    private int maxScore = 1000;
    private double speedMultiplier = 1.0f;

    private String name;

    @ManyToOne
    private Teacher author;

    @ManyToOne
    @JoinColumn(name="classroom_id")
    Classroom room;
    double maxTimeSeconds;
    @Transient
    Stopwatch stopwatch = new Stopwatch();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="leaderdboard_id", referencedColumnName = "id")
    private Leaderboard leaderboard;

    private int questionsAnswered;

    private long elapsedTime;

    private double grade;

    public Classroom getRoom() {
        return room;
    }
    /**
     * Creates a new quiz with the given ID, author, and classroom.
     *
     * @param quizID The unique identifier for the quiz.
     * @param author The teacher who authored the quiz.
     * @param room   The classroom the quiz is associated with.
     */
    public Quiz(long quizID, Teacher author, Classroom room){
        this.id = quizID;
        this.author = author;
        this.room = room;
        questionsAnswered = 0;
    }

    public Quiz() {
        leaderboard = new Leaderboard();
    }

    /**
     * Sets the classroom associated with this quiz.
     *
     * @param room The classroom to associate with the quiz.
     */
    public void setRoom(Classroom room) {
        this.room = room;
    }

    /**
     * Constructs a new Quiz with the specified unique identifier.
     *
     * @param id The unique identifier for the quiz.
     */
    public Quiz(long id) {
        this.id = id;
    }

    /**
     * Retrieves the unique identifier of the quiz.
     *
     * @return The unique identifier for the quiz.
     */
    public long getID() {
        return id;
    }

    /**
     * Constructs a new Quiz with the specified author.
     *
     * @param author The teacher who is creating the quiz.
     */
    public Quiz(Teacher author) {
        this.author = author;
    }

    /**
     * Retrieves the maximum score that can be achieved in the quiz.
     *
     * @return The maximum score for the quiz.
     */
    public int getMaxScore(){
        return maxScore;
    }

    /**
     * Sets the name of the quiz.
     *
     * @param name The name for the quiz.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the quiz.
     *
     * @return The name of the quiz.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the maximum time allowed for the quiz.
     *
     * @param maxTimeSeconds The maximum time in seconds.
     */
    public void setMaxTimeSeconds(double maxTimeSeconds) {
        this.maxTimeSeconds = maxTimeSeconds;
    }

    /**
     * Retrieves the maximum time allowed for the quiz.
     *
     * @return The maximum time in seconds.
     */
    public double getMaxTimeSeconds(){
        return this.maxTimeSeconds;
    }

    /**
     * Sets the speed multiplier for the quiz. This can affect the scoring.
     *
     * @param speedMultiplier The multiplier affecting the quiz's pace.
     */
    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    /**
     * Retrieves the speed multiplier for the quiz.
     *
     * @return The speed multiplier.
     */
    public double getSpeedMultiplier(){
        return this.speedMultiplier;
    }

    /**
     * Provides an iterator over the questions in the quiz.
     *
     * @return An iterator for the questions list.
     */
    public Iterator<Question> getQuiz(){
        return questions.iterator();
    }

    /**
     * Retrieves the list of questions in the quiz.
     *
     * @return The list of questions.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Starts the quiz, which begins the stopwatch timer. Throws an exception if the quiz is already started.
     *
     * @throws TimerException if the quiz has already been started.
     */
    public void startQuiz() throws TimerException{
        if(stopwatch.getRunning()){
            throw new TimerException("Quiz already started");
        }
        stopwatch.start();
    }

    /**
     * Stops the quiz and stops the internal stopwatch. Throws an exception if the quiz hasn't started.
     *
     * @throws TimerException if the quiz has not started yet.
     */
    public void stopQuiz() throws TimerException{
        if(!stopwatch.getRunning()){
            throw new TimerException("Quiz hasnt started yet");
        }
        stopwatch.stop();
    }

    /**
     * Sets the author of the quiz.
     *
     * @param author The teacher who is the author of the quiz.
     */
    public void setAuthor(Teacher author) {
        this.author = author;
    }

    /**
     * Retrieves the author of the quiz.
     *
     * @return The teacher who authored the quiz.
     */
    public Teacher getAuthor(){
        return this.author;
    }

    /**
     * Sets the elapsed time for the quiz.
     *
     * @param elapsed The time in seconds that has elapsed since the quiz started.
     */
    public void setElpasedTime(long elapsed){
        this.elapsedTime = elapsed;
    }

    /**
     * Gets the elapsed time for the quiz.
     *
     * @return The time in seconds that has elapsed since the quiz started.
     */
    public long getElapsedTime(){
        return elapsedTime;
    }

    /**
     * Adds a new question to the quiz.
     *
     * @param question The question to be added to the quiz.
     */
    public void addQuestion(Question question){
        questions.add(question);
    }

    /**
     * Returns the number of questions in the quiz.
     *
     * @return The size of the quiz as an integer.
     */
    public int size() {
        return questions.size();
    }

    /**
     * Removes a question from the quiz if it exists. Throws an exception if the question is not found.
     *
     * @param question The question to remove.
     * @throws QuizException if the question is not contained in the quiz.
     */
    public void removeQuestion(Question question) throws QuizException{
        boolean success = questions.removeIf(q -> q == question);
        if(!success){
            throw new QuizException("Question is not in Quiz");
        }
    }

    /**
     * Randomly shuffles the order of questions in the quiz.
     */
    // @TODO This
    public void shuffle(){
        Collections.shuffle(questions);
    }

    /**
     * Grades a single question by comparing the provided response with the correct answer.
     * Updates the maximum score based on the correctness of the response.
     *
     * @param question The question to be graded.
     * @param response The provided response to the question.
     * @return Returns 1 if the response is correct, otherwise returns 0.
     */
    public int gradeQuestion(Question question, String response){
        this.questionsAnswered++;
        if(!question.isCorrect(response)){
            maxScore -= (maxScore/questions.size());
            return 0;
        }
        return 1;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    /**
     * Completes the quiz, stops the timer, and calculates the final grade based on speed and correctness.
     *
     * @return The final grade for the quiz.
     */
    public double finishQuiz(){
        if(stopwatch.getRunning()){
            stopwatch.stop();
        }
        elapsedTime = stopwatch.durationSeconds();

        if(maxTimeSeconds - elapsedTime > maxTimeSeconds/2) {
            speedMultiplier = 1;
        }
        else{
            speedMultiplier = (maxTimeSeconds - elapsedTime) / (maxTimeSeconds / 2);
            speedMultiplier = Math.max(speedMultiplier, 0.7);
        }
        grade = speedMultiplier*maxScore;
        return grade;
    }


}
