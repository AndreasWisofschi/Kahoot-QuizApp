package wahoot.client.ui.fxml;

import javafx.event.EventType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public enum FXMLEvent {

    KEY_EVENT_PRESSED(KeyEvent.KEY_PRESSED),
    KEY_EVENT_ANY(KeyEvent.ANY),
    KEY_EVENT_RELEASED(KeyEvent.KEY_RELEASED),
    KEY_EVENT_TYPED(KeyEvent.KEY_TYPED),
    MOUSE_EVENT(MouseEvent.ANY),
    MOUSE_EVENT_CLICKED(MouseEvent.MOUSE_CLICKED),

    MOUSE_EVENT_ENTERED(MouseEvent.MOUSE_ENTERED),
    MOUSE_EVENT_EXITED(MouseEvent.MOUSE_EXITED),
    DRAG_EVENT(DragEvent.ANY),

    NONE(null);

    final EventType<?> event;


    public EventType<?> getEventType() {
        return event;
    }

    FXMLEvent(EventType<?> event) {
        this.event = event;
    }
}
