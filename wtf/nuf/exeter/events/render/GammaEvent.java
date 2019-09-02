package wtf.nuf.exeter.events.render;

import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class GammaEvent extends Event {
    private float gamma;

    public GammaEvent(float gamma) {
        this.gamma = gamma;
    }

    public float getGamma() {
        return gamma;
    }

    public void setGamma(float gamma) {
        this.gamma = gamma;
    }
}
