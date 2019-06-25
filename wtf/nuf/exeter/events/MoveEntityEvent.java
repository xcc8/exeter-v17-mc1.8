package wtf.nuf.exeter.events;

import wtf.nuf.exeter.mcapi.eventsystem.Event;
import net.minecraft.client.Minecraft;

public class MoveEntityEvent extends Event {
    private double motionX, motionY, motionZ;
    private boolean safeWalk = false;

    public MoveEntityEvent(double motionX, double motionY, double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (Minecraft.getMinecraft().thePlayer != null) {
            this.safeWalk = Minecraft.getMinecraft().thePlayer.isSneaking();
        }
    }

    public double getMotionX() {
        return motionX;
    }

    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }

    public void setMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }

    public boolean isSafeWalk() {
        return safeWalk;
    }

    public void setSafeWalk(boolean safeWalk) {
        this.safeWalk = safeWalk;
    }
}
