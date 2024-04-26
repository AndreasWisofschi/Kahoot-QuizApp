package wahoot.client.ui.util;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.stream.Collectors;

public class Macro {

    public abstract static class SwitchEventHandler implements EventHandler<KeyEvent> {

        private final Node next;

        public SwitchEventHandler(Node next) {
            this.next = next;

        }

        public Node getNext() {
            return next;
        }

    }

    public static final Comparator<Node> comparator = (o1, o2) -> {
        Bounds first = o1.getBoundsInParent();
        Bounds second = o2.getBoundsInParent();

        return (int) ((first.getCenterX() + first.getCenterY()) - (second.getCenterX() + second.getCenterY()));
    };

    public static void group(KeyCode keycode, Node ... args) {
        LinkedList<Node> list = Arrays.stream(args).collect(Collectors.toCollection(LinkedList::new));

        ListIterator<Node> iterator = list.listIterator();


        while (iterator.hasNext()) {
            Node current = iterator.next();
            if (iterator.hasNext()) {
                current.setOnKeyPressed(new SwitchEventHandler(list.get(iterator.nextIndex())) {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode().equals(keycode)) {
                            getNext().requestFocus();
                        }
                    }
                });
            } else if (iterator.hasPrevious()) {
                current.setOnKeyPressed(new SwitchEventHandler(list.getFirst()) {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode().equals(keycode)) {
                            getNext().requestFocus();
                        }
                    }
                });
            }

        }
    }

    public static void groupInferred(KeyCode keyCode, Parent parent) {
        List<Node> list = parent.getChildrenUnmodifiable().
                stream().
                sorted(comparator).
                toList();

        if(list.size() <= 1) {
            for (Node node : list) {
                Bounds bound = node.getBoundsInParent();

                if (node instanceof Pane nested) {
                    groupInferred(keyCode, parent);
                }


            }
        }
        else group(keyCode, list.toArray(Node[]::new));
    }

    public Macro() {

    }

}




