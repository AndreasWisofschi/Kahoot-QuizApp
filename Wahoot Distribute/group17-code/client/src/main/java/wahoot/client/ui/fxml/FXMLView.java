package wahoot.client.ui.fxml;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


import java.io.IOException;
import java.lang.reflect.*;

abstract public class FXMLView <T extends Pane> extends Parent {
    private static final String PATH = "/fxml/";
    private static final String SUFFIX = ".fxml";

    FXMLLoader loader;

    protected FXMLView() {
        Class<T> inferredType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            Constructor<?> constructor = inferredType.getConstructor();
            Object root = constructor.newInstance();
            String resource = getClass().getSimpleName();
            loader = new FXMLLoader(getClass().getResource(PATH + resource + SUFFIX));
            loader.setController(this);
            loader.setRoot(root);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        load();
    }
    protected void load() {
        try {
            Node p = loader.load();
            getChildren().add(p);
            for(Method m: this.getClass().getDeclaredMethods()) {
                if(m.isAnnotationPresent(FXMLMethod.class)) {
                    FXMLMethod annotation = m.getAnnotation(FXMLMethod.class);
                    String element = annotation.node();
                    FXMLEvent event = annotation.event();

                    Field field = this.getClass().getDeclaredField(element);
                    field.setAccessible(true);

                    if(Node.class.isAssignableFrom(field.getType())) {
                        Node node = (Node) field.get(this);

                        node.addEventFilter(event.getEventType(), (EventHandler<Event>) event1 -> {
                            if(event1.getEventType().equals(event1.getEventType())) {
                                try {
                                    m.setAccessible(true);
                                    m.invoke(this, event1);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
