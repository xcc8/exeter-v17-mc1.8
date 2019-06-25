package wtf.nuf.exeter.events;

import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class ItemUseEvent extends Event {
    private float speed;

    public ItemUseEvent(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
