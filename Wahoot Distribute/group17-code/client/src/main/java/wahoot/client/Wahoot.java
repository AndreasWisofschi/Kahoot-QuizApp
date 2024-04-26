package wahoot.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import wahoot.client.ui.util.WindowUtil;
import wahoot.client.ui.view.*;
import wahoot.db.Manager;
import wahoot.db.models.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Main program, uses all other views and acts as an overall controller
 * created javadoc through Intellij Javadoc generator
 */
public class Wahoot extends Application {

    BooleanProperty ready = new SimpleBooleanProperty(false);
    private void longStart() {
        //simulate long init in background
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                int max = 10;
                for (int i = 1; i <= max; i++) {
                    Thread.sleep(80);
                    notifyPreloader(new Preloader.ProgressNotification(((double) i)/max));
                }
                ready.setValue(Boolean.TRUE);

                notifyPreloader(new Preloader.StateChangeNotification(
                        Preloader.StateChangeNotification.Type.BEFORE_START));

                return null;
            }
        };
        new Thread(task).start();
    }

    public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }
    @Override
    public void start(Stage stage) throws Exception {

        Manager manager = new Manager();


        /*Quiz quiz = new Quiz();
        quiz.setName("Example Quiz");
        Question question = new Question("What is my name?", "Dazzian", "Dazzian", "Joshua", "Eric", "Smith");
        Question question2 = new Question("What is 7+7?", "14", "14", "44", "23", "10");

        Teacher teacher = (Teacher) new UserBuilder().setUsername("Izanzad").setFirstName("Dazzian").build(AccountType.TEACHER);

        Classroom test = new Classroom("5wfwfaw", teacher);

        Student student = (Student) new UserBuilder().setUsername("Izanzad2").setFirstName("Dazzian").setLastName("Boyce").build(AccountType.STUDENT);

        test.addStudent(student);

        student.addClass(test);

        test.setSubject(Subject.CHEMISTRY);
        test.setName("Dazzian's Chemistry Class");
        quiz.addQuestion(question);
        quiz.addQuestion(question2);
        manager.save(teacher);
        manager.save(test);
        manager.save(student);
        manager.save(quiz);




        Student student2 = (Student) new UserBuilder().setUsername("Dazzian").build(AccountType.STUDENT);
        Student student3 = (Student) new UserBuilder().setUsername("John").build(AccountType.STUDENT);
        Student student4 = (Student) new UserBuilder().setUsername("Cody").build(AccountType.STUDENT);
*/
        /**
        Quiz pubQuiz1 = new Quiz();
        pubQuiz1.setName("Example Public Chemistry Quiz");
        Question pubQ1Question1 = new Question("What is the chemical formula of water", "H2O", "H30", "HO", "H2O", "HO2");
        Question pubQ1Question2 = new Question("What Colour is the sky during the day?", "Blue", "Blue", "Green", "Red", "Black");
        Question pubQ1Question3 = new Question("What do we call the tiny particles that make up everything around us?", "Atoms", "Stars", "Cells", "People", "Atoms");
        pubQuiz1.addQuestion(pubQ1Question1);
        pubQuiz1.addQuestion(pubQ1Question2);
        pubQuiz1.addQuestion(pubQ1Question3);

        Quiz pubQuiz2 = new Quiz();
        pubQuiz2.setName("Example Math Quiz");
        Question mathQ1 = new Question("What is the sum of 5 and 7?", "12", "11", "12", "10", "13");
        Question mathQ2 = new Question("What is the difference between 10 and 4?", "6", "6", "5", "7", "8");
        Question mathQ3 = new Question("What is the product of 3 and 4?", "12", "11", "12", "9", "14");
        pubQuiz2.addQuestion(mathQ1);
        pubQuiz2.addQuestion(mathQ2);
        pubQuiz2.addQuestion(mathQ3);

        Quiz pubQuiz3 = new Quiz();
        pubQuiz3.setName("Example Public English Quiz");
        Question engQ1 = new Question("What is the synonym of 'happy'?", "Glad", "Sad", "Glad", "Mad", "Bad");
        Question engQ2 = new Question("What is the antonym of 'difficult'?", "Easy", "Hard", "Easy", "Tough", "Complicated");
        Question engQ3 = new Question("What tense is used in 'I am eating'?", "Present continuous", "Past continuous", "Present continuous", "Future simple", "Present simple");
        pubQuiz3.addQuestion(engQ1);
        pubQuiz3.addQuestion(engQ2);
        pubQuiz3.addQuestion(engQ3);

        Quiz quiz = new Quiz();
        quiz.setName("Example Chemistry Quiz");
        Question question = new Question("What is my name?", "Dazzian", "Dazzian", "Joshua", "Eric", "Smith");
        Question question2 = new Question("What is 7+7?", "14", "14", "44", "23", "10");
        quiz.addQuestion(question);
        quiz.addQuestion(question2);

        Quiz quiz2 = new Quiz();
        quiz2.setName("Example Chemistry Quiz 2");
        Question quiz2question = new Question("What is my name?", "Dazzian", "Dazzian", "Joshua", "Eric", "Smith");
        Question quiz2question2 = new Question("What is 7+7?", "14", "14", "44", "23", "10");
        quiz2.addQuestion(quiz2question);
        quiz2.addQuestion(quiz2question2);

        Quiz quiz3 = new Quiz();
        quiz3.setName("Example Chemistry Quiz 3");
        Question quiz3question = new Question("What is my name?", "Dazzian", "Dazzian", "Joshua", "Eric", "Smith");
        Question quiz3question2 = new Question("What is 7+7?", "14", "14", "44", "23", "10");
        quiz3.addQuestion(quiz3question);
        quiz3.addQuestion(quiz3question2);


        Teacher aTeacher = (Teacher) new UserBuilder().setUsername("demoTeacher").setPassword("123").setFirstName("teacher").setLastName("One").build(AccountType.TEACHER);
        Teacher bTeacher = (Teacher) new UserBuilder().setUsername("demoTeacher2").setPassword("password").setFirstName("teacher").setLastName("Two").build(AccountType.TEACHER);
        Student aStudent = (Student) new UserBuilder().setUsername("demoStudent").setPassword("123").setFirstName("student").setLastName("One").build(AccountType.STUDENT);
        Student bStudent = (Student) new UserBuilder().setUsername("demoStudent2").setPassword("password").setFirstName("student").setLastName("Two").build(AccountType.STUDENT);
        Administrator aAdministrator = (Administrator) new UserBuilder().setUsername("demoAdmin").setPassword("123").setFirstName("admin").setLastName("1").build(AccountType.ADMINISTRATOR);

        Classroom classroom = new Classroom("izan12", aTeacher);

        classroom.setSubject(Subject.CHEMISTRY);
        classroom.setName("Test Chemistry Classroom");

        Classroom classroom2 = new Classroom("math", aTeacher);
        classroom2.setSubject(Subject.MATH);
        classroom2.setName("Test Math Classroom");

        classroom.addQuiz(quiz);
        quiz.setRoom(classroom);

        classroom2.addQuiz(pubQuiz2);
        pubQuiz2.setRoom(classroom2);

        pubQuiz2.getLeaderboard().addEntry(new Leaderboard.LeaderboardEntry("Bob", 700));
        pubQuiz2.getLeaderboard().addEntry(new Leaderboard.LeaderboardEntry("Eric", 500));

        classroom.addQuiz(quiz2);
        quiz2.setRoom(classroom);

        classroom.addQuiz(quiz3);
        quiz3.setRoom(classroom);

        classroom.addStudent(aStudent);
        aStudent.addClass(classroom);


        /*classroom3.setSubject(Subject.ENGLISH);
        classroom2.setName("Test English Classroom");


        manager.save(aAdministrator);
        manager.save(aTeacher);
        manager.save(bTeacher);

        manager.save(classroom);
        manager.save(classroom2);

        manager.update(aStudent);
        manager.update(bStudent);

        manager.save(quiz);
        manager.save(quiz2);
        manager.save(quiz3);
        manager.save(pubQuiz1);
        manager.save(pubQuiz2);
        manager.save(pubQuiz3);


        aStudent.getClasses();
        */

        manager.addClass(User.class);
        manager.addClass(Student.class);
        manager.addClass(Teacher.class);
        manager.addClass(Quiz.class);
        manager.addClass(Leaderboard.class);


        ALoginView view = new ALoginView(manager);
        //EndCreditsView view = new EndCreditsView(manager);

        Scene scene = new Scene(view);
        stage.setScene(scene);



        //GameView view = new GameView(quiz);

        //GameView gameView = new GameView(manager, quiz, student);

        //EndCreditsView credits = new EndCreditsView();

        /**


        for(int i = 0; i < 100; i++) {

            LocalDate random = between(LocalDate.now().minusDays(7), LocalDate.now());


            double value = Math.random() * 100 + 1;

            aStudent.addScore( (long) value);

            bStudent.addScore( (long )value);

            aStudent.addObservation(new Student.Observation(Date.valueOf(random), value));
            bStudent.addObservation(new Student.Observation(Date.valueOf(random), value));
        }
         **/

        //manager.update(aStudent);

        //ChartView view = new ChartView(student1);


        //ProgressTrackerView trackerView = new ProgressTrackerView(teacher);

        //TestView view = new TestView();




        //stage.setScene(scene);
        //stage.initStyle(StageStyle.UNDECORATED);



        ready.addListener(new ChangeListener<Boolean>(){
            public void changed(
                    ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (Boolean.TRUE.equals(t1)) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            stage.show();
                        }
                    });
                }
            }
        });


        scene.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ESCAPE) {
                if(scene.getRoot() instanceof EndCreditsView) {
                    System.exit(0);
                }
            }
        });
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/wahoot-logo-icon-256.png")));
        longStart();
    }




}
