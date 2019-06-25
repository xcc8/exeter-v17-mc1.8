package wtf.nuf.exeter.mcapi.utilities.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class RotationHelper {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    public static float[] getEntityRotations(Entity entity) {
        double positionX = entity.posX - MINECRAFT.thePlayer.posX;
        double positionZ = entity.posZ - MINECRAFT.thePlayer.posZ;
        double positionY = entity.posY + entity.getEyeHeight() / 1.3D -
                (MINECRAFT.thePlayer.posY + MINECRAFT.thePlayer.getEyeHeight());
        double positions = net.minecraft.util.MathHelper.sqrt_double(
                positionX * positionX + positionZ * positionZ);
        float yaw = (float) (Math.atan2(positionZ, positionX) * 180D / Math.PI) - 90F;
        float pitch = (float) -(Math.atan2(positionY, positions) * 180D / Math.PI);
        return new float[]{yaw, pitch};
    }

    public static float[] getBlockRotations(BlockPos position, EnumFacing facing) {
        double xDifference = position.getX() + 0.5D +
                facing.getDirectionVec().getX() * 0.25D - MINECRAFT.thePlayer.posX;
        double yDifference = position.getY() + 0.5D +
                facing.getDirectionVec().getY() * 0.25D - MINECRAFT.thePlayer.posY;
        double zDifference = position.getZ() + 0.5D +
                facing.getDirectionVec().getZ() * 0.25D - MINECRAFT.thePlayer.posZ;
        double positions = net.minecraft.util.MathHelper.sqrt_double(
                xDifference * xDifference + zDifference * zDifference);
        float yaw = (float) (Math.atan2(zDifference, xDifference) * 180D / Math.PI) - 90F;
        float pitch = (float) -(Math.atan2(yDifference, positions) * 180D / Math.PI);
        return new float[]{yaw, pitch};
    }

    public static boolean isAiming(float yaw, float pitch, int fov) {
        yaw = clampDegrees(yaw);
        pitch = clampDegrees(pitch);
        float curYaw = clampDegrees(MINECRAFT.thePlayer.rotationYaw);
        float curPitch = clampDegrees(MINECRAFT.thePlayer.rotationPitch);
        float yawDiff = Math.abs(yaw - curYaw);
        float pitchDiff = Math.abs(pitch - curPitch);
        return (yawDiff <= fov) && (pitchDiff <= fov);
    }

    private static float clampDegrees(float value) {
        value = value % 360F;
        if (value >= 180F) {
            value -= 360F;
        }
        if (value < -180F) {
            value += 360F;
        }
        return value;
    }
}
