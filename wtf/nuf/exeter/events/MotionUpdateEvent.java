package wtf.nuf.exeter.events;

import wtf.nuf.exeter.mcapi.eventsystem.Event;

public class MotionUpdateEvent extends Event {
    private State state;
    private boolean sprinting, wasSprinting, sneaking, wasSneaking, onGround, wasOnGround;
    private double positionX, positionY, positionZ, motionX, motionY, motionZ, originalPositionX, originalPositionY, originalPositionZ;
    private float rotationYaw, rotationPitch, originalRotationYaw, originalRotationPitch;
    private boolean silent;

    public MotionUpdateEvent(boolean sprinting, boolean sneaking, boolean onGround, double motionX, double motionY, double motionZ, double positionX, double positionY, double positionZ, float rotationYaw, float rotationPitch) {
        this.sprinting = this.wasSprinting = sprinting;
        this.sneaking = this.wasSneaking = sneaking;
        this.onGround = this.wasOnGround = onGround;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.positionX = this.originalPositionX = positionX;
        this.positionY = this.originalPositionY = positionY;
        this.positionZ = this.originalPositionZ = positionZ;
        this.rotationYaw = this.originalRotationYaw = rotationYaw;
        this.rotationPitch = this.originalRotationPitch = rotationPitch;
        this.state = State.BEFORE;
    }

    public MotionUpdateEvent() {
        this.state = State.AFTER;
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public boolean wasSprinting() {
        return wasSprinting;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean wasSneaking() {
        return wasSneaking;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean wasOnGround() {
        return wasOnGround;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getPositionZ() {
        return positionZ;
    }

    public double getOriginalPositionX() {
        return originalPositionX;
    }

    public double getOriginalPositionY() {
        return originalPositionY;
    }

    public double getOriginalPositionZ() {
        return originalPositionZ;
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setPositionZ(double positionZ) {
        this.positionZ = positionZ;
    }

    public double getMotionX() {
        return motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }

    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public void setMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }

    public float getRotationYaw() {
        return rotationYaw;
    }

    public void setRotationYaw(float rotationYaw) {
        this.rotationYaw = rotationYaw;
    }

    public float getRotationPitch() {
        return rotationPitch;
    }

    public void setRotationPitch(float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }

    public float getOriginalRotationYaw() {
        return originalRotationYaw;
    }

    public float getOriginalRotationPitch() {
        return originalRotationPitch;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public State getState() {
        return state;
    }

    public enum State {
        BEFORE, AFTER
    }
}
