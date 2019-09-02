package wtf.nuf.exeter.events.render;

import wtf.nuf.exeter.mcapi.eventsystem.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event {
    private ScaledResolution scaledResolution;

    public Render2DEvent(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public void setScaledResolution(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }
}
