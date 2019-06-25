package wtf.nuf.exeter.mcapi.utilities.minecraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public final class BlockHelper {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    public static Block getBlock(double x, double y, double z) {
        return MINECRAFT.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static int getBestTool(Block block) {
        int maxStrSlot = -1;
        float hardness = 1F;

        for (int index = 44; index >= 9; index--) {
            ItemStack stack = MINECRAFT.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null) {
                float strength = stack.getStrVsBlock(block.getDefaultState().getBlock());
                if (strength > 1F) {
                    int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
                    strength += efficiencyLevel * efficiencyLevel + 1;
                }
                if ((strength > hardness) && (strength > 1F)) {
                    hardness = strength;
                    maxStrSlot = index;
                }
            }
        }
        return maxStrSlot;
    }
}
