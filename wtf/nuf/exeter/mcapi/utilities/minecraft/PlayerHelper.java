package wtf.nuf.exeter.mcapi.utilities.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class PlayerHelper {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    public static Block getBlockBelowPlayer(double height) {
        return BlockHelper.getBlock(MINECRAFT.thePlayer.posX, MINECRAFT.thePlayer.posY - height,
                MINECRAFT.thePlayer.posZ);
    }

    public static Block getBlockAbovePlayer(double height) {
        return BlockHelper.getBlock(MINECRAFT.thePlayer.posX, MINECRAFT.thePlayer.posY + height,
                MINECRAFT.thePlayer.posZ);
    }

    public static boolean isInLiquid() {
        return getBlockBelowPlayer(0D) instanceof BlockLiquid;
    }

    public static boolean isOnLiquid() {
        AxisAlignedBB boundingBox = MINECRAFT.thePlayer.getEntityBoundingBox();
        boundingBox = boundingBox.contract(0.00D, 0.0D, 0.00D).offset(0.0D, -0.02D, 0.0D);
        boolean onLiquid = false;
        int y = (int) boundingBox.minY;
        for (int x = net.minecraft.util.MathHelper.floor_double(boundingBox.minX);
             x < net.minecraft.util.MathHelper.floor_double(boundingBox.maxX + 1D); x++) {
            for (int z = net.minecraft.util.MathHelper.floor_double(boundingBox.minZ);
                 z < MathHelper.floor_double(boundingBox.maxZ + 1D); z++) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block != Blocks.air) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(MINECRAFT.thePlayer.getEntityBoundingBox().minX);
             x < MathHelper.floor_double(MINECRAFT.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(MINECRAFT.thePlayer.getEntityBoundingBox().minY);
                 y < MathHelper.floor_double(MINECRAFT.thePlayer.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(MINECRAFT.thePlayer.getEntityBoundingBox().minZ);
                     z < MathHelper.floor_double(MINECRAFT.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = BlockHelper.getBlock(x, y, z);
                    if (block == null || block instanceof BlockAir) {
                        continue;
                    }
                    if (block instanceof BlockTallGrass) {
                        return false;
                    }
                    AxisAlignedBB boundingBox = block.getSelectedBoundingBox(Minecraft.getMinecraft().theWorld,
                            new BlockPos(x, y, z));
                    if (boundingBox != null && MINECRAFT.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void damagePlayer() {
        for (int index = 0; index < 81; index++) {
            MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MINECRAFT.thePlayer.posX,
                    MINECRAFT.thePlayer.posY + 0.05D, MINECRAFT.thePlayer.posZ, false));
            MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MINECRAFT.thePlayer.posX,
                    MINECRAFT.thePlayer.posY, MINECRAFT.thePlayer.posZ, false));
        }
    }

    public static void drownPlayer() {
        for (int index = 0; index < 500; index++) {
            MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer());
            MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer());
            MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer());
            MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer());
            MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer());
        }
    }

    public static boolean isMoving() {
        return (MINECRAFT.thePlayer.moveForward != 0D || MINECRAFT.thePlayer.moveStrafing != 0D);
    }
}
