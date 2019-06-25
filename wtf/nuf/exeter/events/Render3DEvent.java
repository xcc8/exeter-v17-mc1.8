package wtf.nuf.exeter.events;

import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class Render3DEvent extends Event {
    private float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
