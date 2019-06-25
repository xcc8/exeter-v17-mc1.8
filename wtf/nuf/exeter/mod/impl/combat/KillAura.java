package wtf.nuf.exeter.mod.impl.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import wtf.nuf.exeter.mcapi.eventsystem.Listener;
import wtf.nuf.exeter.mcapi.settings.ModeSetting;
import wtf.nuf.exeter.mcapi.settings.ToggleableSetting;
import wtf.nuf.exeter.mcapi.settings.ValueSetting;
import wtf.nuf.exeter.mcapi.stopwatch.Stopwatch;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.events.MotionUpdateEvent;
import wtf.nuf.exeter.mod.ModType;
import wtf.nuf.exeter.mod.ModValues;
import wtf.nuf.exeter.mod.ToggleableMod;
import wtf.nuf.exeter.task.tasks.AttackTask;
import wtf.nuf.exeter.task.tasks.RotateTask;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@ModValues(label = "Kill Aura",
        aliases = {"killaura", "aura", "ka"},
        description = "Automatically attack surrounding entities",
        color = 0xFFffc9fe,
        modType = ModType.COMBAT)
public final class KillAura extends ToggleableMod {
    private final ToggleableSetting silent = new ToggleableSetting("Silent", new String[]{"silent", "lockview"}, true);
    private final ToggleableSetting rayTrace = new ToggleableSetting("Ray-Trace", new String[]{"ray-trace", "raytrace", "trace"}, true);
    private final ToggleableSetting realClicks = new ToggleableSetting("Real-Clicks", new String[]{"real-clicks", "realclicks", "clicks"}, false);
    private final ToggleableSetting randomSpeed = new ToggleableSetting("Random Speed", new String[]{"randomspeed", "random", "rs"}, false);

    private final ValueSetting<Integer> aps = new ValueSetting<>("Attacks per second", new String[]{"attackspersecond", "attacks", "aps"}, 10, 1, 20);
    private final ValueSetting<Double> range = new ValueSetting<>("Range", new String[]{"range"}, 4D, 3D, 5.5D);

    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"mode", "m"}, Mode.SWITCH, Mode.values());

    private final RotateTask rotateTask;
    private final AttackTask attackTask;

    private final Stopwatch stopwatch = new Stopwatch();

    private final Random random = new Random();

    private final List<Entity> targets = new CopyOnWriteArrayList<>();

    private Entity target = null;

    public KillAura() {
        rotateTask = new RotateTask("kill_aura_rotate_task");
        attackTask = new AttackTask("kill_aura_attack_task");
        settings.add(rayTrace);
        settings.add(range);
        settings.add(realClicks);
        settings.add(silent);
        settings.add(mode);
        settings.add(randomSpeed);
        settings.add(aps);
    }

    /**
     * actual method where our kill aura process is ran, BEFORE is where we verify, target and rotate to
     * the entity, AFTER is where we attack the entity, and repeat
     * @param event MotionUpdateEvent called in EntityPlayerSP
     */
    @Listener
    public void onMotionUpdate(MotionUpdateEvent event) {
        if (minecraft.thePlayer.isDead || minecraft.playerController.isSpectatorMode()) {
            return;
        }
        switch (event.getState()) {
            case BEFORE:
                if (mode.getValue() == Mode.TRIGGER) {
                    this.triggerbot();
                    return;
                }

                setDisplayLabel("Kill Aura");

                if (targets.isEmpty() || targets.size() < minecraft.theWorld.loadedEntityList.size()) {
                    populateTargets();
                }

                verifyTargets();

                if (targets.isEmpty()) {
                    return;
                }

                target = targets.get(0);

                if (!isValidTarget(target)) {
                    target = null;
                    return;
                }

                rotateTask.targetEntity(event, target, silent.isEnabled());
                break;
            case AFTER:
                if (isValidTarget(target)) {
                    if (hasReachedAttackDelay()) {
                        attackTask.attackEntity(target);
                        if (mode.getValue() == Mode.SWITCH) {
                            if (targets.size() < 2) {
                                stopwatch.reset();
                            } else {
                                targets.remove(target);
                                target = null;
                            }
                        } else {
                            stopwatch.reset();
                        }
                    }
                } else {
                    stopwatch.reset();
                    targets.remove(target);
                    target = null;
                }
                break;
        }
    }

    /**
     * collects entities within your reach and verifies they can be targeted and adds them to our target list
     */
    private void populateTargets() {
        minecraft.theWorld.loadedEntityList.stream().filter(entity -> isValidTarget((Entity) entity)).
                forEach(entity -> targets.add((Entity) entity));
        verifyTargets();
    }

    /**
     * verifies the targets in our list are still able to be targeted because things change
     */
    private void verifyTargets() {
        targets.stream().filter(target -> !isValidTarget(target)).forEach(target -> targets.remove(target));
    }

    /**
     * this is our attack delay, subtracts a number between 0 - 2 if the random speed is enabled
     * @return one second divided by our attacks per second, i.e. 1000 / 10(default) = 100, every 100ms we will attack
     */
    private long getAttackDelay() {
        return 1000L / (randomSpeed.isEnabled() ? (aps.getValue() - random.nextInt(2)) : aps.getValue());
    }

    /**
     * doesn't need to be a method but i do what i want
     * @return true or false (lma0)
     */
    private boolean hasReachedAttackDelay() {
        return stopwatch.hasCompleted(getAttackDelay());
    }

    /**
     * simple triggerbot method using objectMouseOver, real clicks settings toggles actually clicking
     * or just sending an attack packet using our attack task
     */
    private void triggerbot() {
        setDisplayLabel("Triggerbot");
        if (minecraft.objectMouseOver != null && minecraft.objectMouseOver.typeOfHit ==
                MovingObjectPosition.MovingObjectType.ENTITY) {
            Entity entity = minecraft.objectMouseOver.entityHit;
            if (isValidTarget(entity) && minecraft.currentScreen == null) {
                if (hasReachedAttackDelay()) {
                    if (realClicks.isEnabled()) {
                        KeyBinding.setKeyBindState(-100, true);
                        KeyBinding.onTick(-100);
                    } else {
                        attackTask.attackEntity(entity);
                    }
                    stopwatch.reset();
                } else {
                    if (realClicks.isEnabled()) {
                        KeyBinding.setKeyBindState(-100, false);
                    }
                }
            }
        }
    }

    /**
     * verifies the target given meets our 'attackable' criteria
     * @param entity given to us by loadedEntityList
     * @return where we should/can attack this target or not >_<
     */
    private boolean isValidTarget(Entity entity) {
        if (entity == null || entity.equals(minecraft.thePlayer) || entity.isDead) {
            return false;
        }
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if (minecraft.thePlayer.getDistanceToEntity(entity) > range.getValue()) {
            return false;
        }
        if (entity.isInvisible() && !exeter.getSettings().attackInvisibles.isEnabled()) {
            return false;
        }
        if (!minecraft.thePlayer.canEntityBeSeen(entity) && !rayTrace.isEnabled()) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            if (exeter.getSettings().protectTeam.isEnabled() && minecraft.thePlayer.isOnSameTeam((EntityLivingBase) entity)) {
                return false;
            }
            return exeter.getSettings().attackPlayers.isEnabled() && !Exeter.getInstance().getFriendManager().isFriend(entity.getName());
        }
        if (entity instanceof EntityAnimal) {
            return exeter.getSettings().attackPassives.isEnabled();
        }
        if (entity instanceof IMob) {
            return exeter.getSettings().attackHostiles.isEnabled();
        }
        return true;
    }

    @Override
    public void onEnable() {
        targets.clear();
        target = null;
        stopwatch.reset();
    }

    private enum Mode {
        TRIGGER, SINGLE, SWITCH
    }
}
