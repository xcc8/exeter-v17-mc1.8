package wtf.nuf.exeter.events;

import net.minecraft.util.AxisAlignedBB;
import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class BoundingBoxEvent extends Event {
    private AxisAlignedBB boundingBox;

    public BoundingBoxEvent(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}
