package wtf.nuf.exeter.mod.impl.movement;

import wtf.nuf.exeter.events.movement.SprintingAttackEvent;
import wtf.nuf.exeter.mcapi.eventsystem.Listener;
import wtf.nuf.exeter.events.movement.MotionUpdateEvent;
import wtf.nuf.exeter.mcapi.settings.ToggleableSetting;
import wtf.nuf.exeter.mod.ModType;
import wtf.nuf.exeter.mod.ModValues;
import wtf.nuf.exeter.mod.ToggleableMod;

@ModValues(label = "Speed",
        aliases = {"speed", "sprint"},
        description = "Run faster",
        color = 0xFFc9fffa,
        modType = ModType.MOVEMENT)
public final class Speed extends ToggleableMod {
    private final ToggleableSetting keepSprint = new ToggleableSetting("Keep-Sprint", new String[]{"keep-sprint", "keepsprint", "sprint"}, true);

    public Speed() {
        settings.add(keepSprint);
    }

    @Listener
    public void onMotionUpdate(MotionUpdateEvent event) {
        if (minecraft.thePlayer.movementInput.moveForward > 0F) {
            event.setSprinting(true);
        }
    }

    @Listener
    public void onSprintingAttack(SprintingAttackEvent event) {
        event.setCancelled(keepSprint.isEnabled());
    }
}
