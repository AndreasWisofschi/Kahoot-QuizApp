package wahoot.client;


import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.setProperty("javafx.preloader", WahootPreloader.class.getCanonicalName());
        Application.launch(Wahoot.class, args);

        /**
        Manager manager = new Manager();

        manager.addClass(User.class);

        Optional<?> result = manager.find("Izanzad");

        if (result.isPresent()) {
            Object obj = result.get();
            if (obj instanceof User u) {
                switch (u.getAccountType()) {
                    case STUDENT:
                        // Do something.
                        break;
                    case ADMINISTRATOR:
                        break;
                    case TEACHER:
                        break;
                }
            }
        }
        **/
    }
}
