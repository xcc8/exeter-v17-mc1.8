package wtf.nuf.exeter.gui.overlay.components;

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

    /**
     * this is what we will use to determine the size of the component, the bigger then the more area we have
     * to click and drag
     * @param width
     * @param height
     */
    protected void setDiameter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * we do this the same way we handle dragging our click gui
     * @param mouseX
     * @param mouseY
     */
    public void drawScreen(int mouseX, int mouseY) {
        if (dragging) {
            positionX = draggingPositionX + mouseX;
            positionY = draggingPositionY + mouseY;
        }
        // before we draw the rectangle that gives us the clickable area of the component
        // make sure we are in the 'edit' screen so we aren't showing this
        // rectangle in the hud
        if (Minecraft.getMinecraft().currentScreen instanceof ClickGuiScreen) {
            RenderHelper.drawBorderedRect(getPositionX() - 2, getPositionY() - 2, getPositionX() + width,
                    getPositionY() + height, 1F, 0x33000000, !drawn ? 0x55FF0000 : 0x55000000);
        }
        // we do this so even if it's not 'drawn' we can see it at least in the edit screen
        if (drawn || Minecraft.getMinecraft().currentScreen instanceof ClickGuiScreen) {
            drawComponent(mouseX, mouseY);
        }
    }

    /**
     * make sure we are hovering over the component, left click = start dragging, right click = set the
     * visibility to the opposite of the current state
     * @param mouseX
     * @param mouseY
     * @param mouseButton 0 = left click, 1 = right click
     */
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

    /**
     * whether dragging is true or false, still setting it to false
     */
    public void mouseReleased() {
        dragging = false;
    }

    /**
     * sets the pos x and y back to the original positions given upon initialization
     */
    public void reset() {
        this.positionX = this.originalPositionX;
        this.positionY = this.originalPositionY;
    }

    public abstract void drawComponent(int mouseX, int mouseY);

    /**
     * simple check to make sure the users mouse is hovering over the component
     * @param mouseX
     * @param mouseY
     * @return whether or not we are hovering over the component
     */
    private boolean isHovering(int mouseX, int mouseY) {
        return ((mouseX >= getPositionX() - 2)
                && (mouseX <= (getPositionX() + width))
                && ((mouseY >= getPositionY() - 2)
                && (mouseY <= (getPositionY() + height))));
    }
}
