package wtf.nuf.exeter.printer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public final class Printer {
    private final Minecraft minecraft = Minecraft.getMinecraft();

    private static final Printer PRINTER = new Printer();

    public void print(String message) {
        System.out.println(String.format("> %s", message));
        if (minecraft.thePlayer != null && minecraft.theWorld != null) {
            minecraft.thePlayer.addChatMessage(new ChatComponentText(String.format("%s>%s %s",
                    EnumChatFormatting.GOLD, EnumChatFormatting.GRAY, message))
                    .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        }
    }

    public static Printer getPrinter() {
        return PRINTER;
    }
}
