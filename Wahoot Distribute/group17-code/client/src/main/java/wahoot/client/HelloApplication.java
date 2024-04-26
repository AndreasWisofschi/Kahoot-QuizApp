package wahoot.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wahoot.client.ui.view.LoginView;
import wahoot.client.ui.view.MainView;
import wahoot.client.ui.view.RegisterView;
import wahoot.db.Manager;
import wahoot.db.models.AccountType;
import wahoot.db.models.User;
import wahoot.db.models.UserBuilder;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Manager manager = new Manager();


        MainView view = new MainView(manager);

        Scene scene = new Scene(view);

        primaryStage.setScene(scene);



        primaryStage.show();

    }

}
