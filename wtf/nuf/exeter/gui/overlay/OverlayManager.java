package wtf.nuf.exeter.gui.overlay;

import net.minecraft.client.Minecraft;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.gui.clickapi.ClickGuiScreen;
import wtf.nuf.exeter.gui.overlay.components.Component;
import wtf.nuf.exeter.mcapi.interfaces.Toggleable;
import wtf.nuf.exeter.mcapi.manager.SetManager;
import wtf.nuf.exeter.mcapi.utilities.minecraft.render.RenderHelper;
import wtf.nuf.exeter.mod.Mod;
import wtf.nuf.exeter.mod.ToggleableMod;

/**
 * simple way of making draggable hud elements, maybe not the most efficient or cleanest
 * but it gets the job done
 */
public final class OverlayManager extends SetManager<Component> {
    private final Minecraft minecraft = Minecraft.getMinecraft();

    /**
     * add all of the components here so they're initialized on startup, too easy right?
     */
    public OverlayManager() {
        register(new Component("watermark", 2, 2) {
            @Override
            public void drawComponent(int mouseX, int mouseY) {
                String watermark = String.format("Exeter b%s", Exeter.CLIENT_BUILD);
                minecraft.fontRendererObj.drawStringWithShadow(watermark, getPositionX(),
                        getPositionY(), 0xFFFFFFFF);
                setSize(minecraft.fontRendererObj.getStringWidth(watermark) + 2, 12);
            }
        });
        register(new Component("arraylist", 2, 12) {
            @Override
            public void drawComponent(int mouseX, int mouseY) {
                int positionX = getPositionX(), positionY = getPositionY();
                for (Mod mod : exeter.getModManager().getList()) {
                    if (mod instanceof Toggleable) {
                        ToggleableMod toggleableMod = (ToggleableMod) mod;
                        if (toggleableMod.isEnabled() && toggleableMod.isVisible()) {
                            // ugly but we do this math so the arraylist displays correctly
                            // according to the left or right side of the screen and
                            // the top or button
                            minecraft.fontRendererObj.drawStringWithShadow(toggleableMod.getDisplayLabel(),
                                    positionX > minecraft.getScaledResolution().getScaledWidth() / 2 ? (positionX -
                                            minecraft.fontRendererObj.getStringWidth(toggleableMod.getDisplayLabel()))
                                            + 30 : positionX, positionY, toggleableMod.getColor());
                            positionY += getPositionY() > minecraft.getScaledResolution().getScaledHeight() / 2 ? -10 : 10;
                        }
                    }
                }
                setSize(35, 35);
            }
        });
    }

    /**
     * actual drawing of the components and the reset button, loop through the set
     * and draw each component; check if the gui screen is the clickgui and if so, then
     * draw the reset button
     *
     * @param mouseX
     * @param mouseY
     */
    public void drawScreen(int mouseX, int mouseY) {
        getSet().forEach(component -> component.drawScreen(mouseX, mouseY));

        if (minecraft.currentScreen instanceof ClickGuiScreen) {
            RenderHelper.drawBorderedRect(minecraft.getScaledResolution().getScaledWidth() / 2 - 15, 2,
                    minecraft.getScaledResolution().getScaledWidth() / 2 + 15, 14, 1F,
                    0x33000000, 0x55000000);
            minecraft.fontRendererObj.drawCenteredString("reset",
                    minecraft.getScaledResolution().getScaledWidth() / 2, 4, 0xFFFFFFFF);
        }
    }

    /**
     * this will be what handles clicking the components, called in ClickGuiScreen.java
     * @param mouseX
     * @param mouseY
     * @param mouseButton
     */
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        getSet().forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));

        if (mouseButton == 0 && minecraft.currentScreen instanceof ClickGuiScreen) {
            if ((mouseX >= minecraft.getScaledResolution().getScaledWidth() / 2 - 15 &&
                    mouseX <= minecraft.getScaledResolution().getScaledWidth() / 2 + 15) &&
                    (mouseY >= 2) && (mouseY <= 14)) {
                getSet().forEach(Component::reset);
            }
        }
    }

    /**
     * what to do when you released the button on your mouse, we just set dragging to false
     * no matter what
     */
    public void mouseReleased() {
        getSet().forEach(Component::mouseReleased);
    }
}
