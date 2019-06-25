package wtf.nuf.exeter.gui.clickapi.overlay.components;

import net.minecraft.client.Minecraft;
import wtf.nuf.exeter.gui.clickapi.ClickGuiScreen;
import wtf.nuf.exeter.mcapi.interfaces.Labeled;
import wtf.nuf.exeter.mcapi.utilities.minecraft.render.RenderHelper;

public abstract class Component implements Labeled {
    private final String label;
    private int positionX, positionY, originalPositionX, originalPositionY, draggingPositionX, draggingPositionY, width = 12, height = 12;
    private boolean drawn, dragging = false;

    private Component(String label, int positionX, int positionY, boolean drawn) {
        this.label = label;
        this.positionX = this.originalPositionX = positionX;
        this.positionY = this.originalPositionY = positionY;
        this.drawn = drawn;
    }

    protected Component(String label, int positionX, int positionY) {
        this(label, positionX, positionY, true);
    }

    @Override
    public String getLabel() {
        return label;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    protected void setDiameter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY) {
        if (dragging) {
            positionX = draggingPositionX + mouseX;
            positionY = draggingPositionY + mouseY;
        }
        if (Minecraft.getMinecraft().currentScreen instanceof ClickGuiScreen) {
            RenderHelper.drawBorderedRect(getPositionX() - 2, getPositionY() - 2, getPositionX() + width,
                    getPositionY() + height, 1F, 0x33000000, !drawn ? 0x55FF0000 : 0x55000000);
        }
        if (drawn || Minecraft.getMinecraft().currentScreen instanceof ClickGuiScreen) {
            drawComponent(mouseX, mouseY);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY)) {
            switch (mouseButton) {
                case 0:
                    dragging = true;
                    draggingPositionX = positionX - mouseX;
                    draggingPositionY = positionY - mouseY;
                    break;
                case 1:
                    drawn = !drawn;
                    break;
            }
        }
    }

    public void mouseReleased() {
        dragging = false;
    }

    public void reset() {
        this.positionX = this.originalPositionX;
        this.positionY = this.originalPositionY;
    }

    public abstract void drawComponent(int mouseX, int mouseY);

    private boolean isHovering(int mouseX, int mouseY) {
        return ((mouseX >= getPositionX() - 2)
                && (mouseX <= (getPositionX() + width))
                && ((mouseY >= getPositionY() - 2)
                && (mouseY <= (getPositionY() + height))));
    }
}
