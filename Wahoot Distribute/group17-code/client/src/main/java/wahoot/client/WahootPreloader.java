package wahoot.client;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import wahoot.client.ui.util.WindowUtil;
import wahoot.client.ui.view.PreloaderView;

import java.util.Random;


public class WahootPreloader extends Preloader {
    PreloaderView view;
    Stage preloaderStage;
    ProgressBar progressBar;
    Text text;

    TextArea area;

    Random random = new Random();

    private static int NUM_FILES = 133;

    boolean noLoadingProgress = false;

    @Override
    public void start(Stage stage) {
        this.preloaderStage = stage;
        PreloaderView view = new PreloaderView();
        this.progressBar = view.getPreloaderProgressbar();
        this.text = view.getRetrievingText();
        this.area = view.getLoadingArea();

        WindowUtil.resizer(stage);

        stage.setScene(new Scene(view));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/wahoot-logo-icon-256.png")));
        stage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        if (pn.getProgress() != 1.0 || !noLoadingProgress) {
            progressBar.setProgress(0);
            if (pn.getProgress() > 0) {
                noLoadingProgress = false;
            }
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {

    }
    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof ProgressNotification) {
            double v = ((ProgressNotification) pn).getProgress();
            if (!noLoadingProgress) {
          
            }
            progressBar.setProgress(v);


            text.setText(String.format("(%d of %d)", (int) (v * 133), 133));
       
        } else if (pn instanceof StateChangeNotification) {
            //hide after get any state update from application
            preloaderStage.hide();
        }
    }
}
