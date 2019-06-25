package wtf.nuf.exeter.task.tasks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import wtf.nuf.exeter.mcapi.utilities.minecraft.RotationHelper;
import wtf.nuf.exeter.events.MotionUpdateEvent;
import wtf.nuf.exeter.task.Task;

public class RotateTask extends Task {
    private final Minecraft minecraft = Minecraft.getMinecraft();

    public RotateTask(String label) {
        super(label);
    }

    public void targetEntity(MotionUpdateEvent event, Entity entity, boolean silent) {
        float[] rotations = RotationHelper.getEntityRotations(entity);
        event.setSilent(silent);
        if (silent) {
            event.setRotationYaw(rotations[0]);
            event.setRotationPitch(rotations[1]);
        } else {
            minecraft.thePlayer.rotationYaw = rotations[0];
            minecraft.thePlayer.rotationPitch = rotations[1];
        }
    }

    public void targetBlock(MotionUpdateEvent event, BlockPos position, EnumFacing facing, boolean silent) {
        float[] rotations = RotationHelper.getBlockRotations(position, facing);
        event.setSilent(silent);
        if (silent) {
            event.setRotationYaw(rotations[0]);
            event.setRotationPitch(rotations[1]);
        } else {
            minecraft.thePlayer.rotationYaw = rotations[0];
            minecraft.thePlayer.rotationPitch = rotations[1];
        }
    }
}
