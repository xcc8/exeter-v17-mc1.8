package wtf.nuf.exeter.mod.impl.miscellaneous;

import wtf.nuf.exeter.events.movement.MotionUpdateEvent;
import wtf.nuf.exeter.mcapi.eventsystem.Listener;
import wtf.nuf.exeter.mod.ModType;
import wtf.nuf.exeter.mod.ModValues;
import wtf.nuf.exeter.mod.ToggleableMod;

@ModValues(label = "NoFall",
        aliases = {"nofall", "0fall", "nf"},
        description = "Disable fall damage",
        color = 0xFFf6ffbf,
        modType = ModType.MISCELLANEOUS)
public final class NoFall extends ToggleableMod {
    @Listener
    public void onMotionUpdate(MotionUpdateEvent event) {
        if (minecraft.thePlayer.fallDistance > 3F) {
            event.setOnGround(true);
        }
    }
}
