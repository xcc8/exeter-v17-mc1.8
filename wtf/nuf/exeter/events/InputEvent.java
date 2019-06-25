package wtf.nuf.exeter.events;

import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class InputEvent extends Event {
    private int key;
    private final InputType inputType;

    public InputEvent(int key, InputType inputType) {
        this.key = key;
        this.inputType = inputType;
    }

    public InputEvent(InputType inputType) {
        this(0, inputType);
    }

    public int getKey() {
        return key;
    }

    public InputType getInputType() {
        return inputType;
    }

    public enum InputType {
        MOUSE_MIDDLE_CLICK, KEY_PRESS
    }
}
