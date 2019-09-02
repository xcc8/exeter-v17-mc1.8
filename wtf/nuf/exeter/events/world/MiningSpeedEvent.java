package wtf.nuf.exeter.events.world;

import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class MiningSpeedEvent extends Event {
    private float speed;

    public MiningSpeedEvent(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
