package wtf.nuf.exeter.mod.impl.movement;

import wtf.nuf.exeter.events.MotionUpdateEvent;
import wtf.nuf.exeter.events.MoveEntityEvent;
import wtf.nuf.exeter.mcapi.eventsystem.Listener;
import wtf.nuf.exeter.mcapi.settings.ValueSetting;
import wtf.nuf.exeter.mod.ModType;
import wtf.nuf.exeter.mod.ModValues;
import wtf.nuf.exeter.mod.ToggleableMod;

@ModValues(label = "Fly",
        aliases = {"fly", "flight"},
        description = "Soar the skies",
        color = 0xFFf4ffa8,
        modType = ModType.MOVEMENT)
public final class Fly extends ToggleableMod {
    private final ValueSetting<Double> speed = new ValueSetting<>("Speed", new String[]{"speed", "s"}, 2D, 1D, 5D);

    private boolean wasFlying = false;

    public Fly() {
        settings.add(speed);
    }

    @Listener
    public void onMotionUpdate(MotionUpdateEvent event) {
        minecraft.thePlayer.capabilities.isFlying = true;
    }

    @Listener
    public void onMoveEntity(MoveEntityEvent event) {
        event.setMotionX(event.getMotionX() * speed.getValue());
        event.setMotionY(event.getMotionY() * speed.getValue());
        event.setMotionZ(event.getMotionZ() * speed.getValue());
    }

    @Override
    public void onEnable() {
        if (minecraft.thePlayer == null) {
            return;
        }
        wasFlying = minecraft.thePlayer.capabilities.isFlying;
    }

    @Override
    public void onDisable() {
        if (minecraft.thePlayer == null) {
            return;
        }
        minecraft.thePlayer.capabilities.isFlying = wasFlying;
    }
}
