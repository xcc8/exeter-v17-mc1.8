package wtf.nuf.exeter.events.movement;

import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class StepEvent extends Event {
    private float height;
    private final State state;

    public StepEvent(float height) {
        this.height = height;
        this.state = State.BEFORE;
    }

    public StepEvent() {
        this.state = State.AFTER;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public State getState() {
        return state;
    }

    public enum State {
        BEFORE, AFTER
    }
}
