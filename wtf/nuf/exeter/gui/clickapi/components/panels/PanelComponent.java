package wtf.nuf.exeter.gui.clickapi.components.panels;

import wtf.nuf.exeter.gui.clickapi.components.Colors;
import wtf.nuf.exeter.gui.clickapi.components.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class PanelComponent extends Component {
    private final List<Component> components = new CopyOnWriteArrayList<>();
    private int draggingPositionX, draggingPositionY;
    private boolean dragging = false, pinned = false, open = true;

    protected PanelComponent(String label, int positionX, int positionY, int width, int height) {
        super(label, positionX, positionY, width, height);
        registerComponents();
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY, 20)) {
            switch (mouseButton) {
                case 0:
                    setDragging(true);
                    setDraggingPositionX(getPositionX() - mouseX);
                    setDraggingPositionY(getPositionY() - mouseY);
                    break;
                case 1:
                    this.open = !open;
                    break;
            }
        }
        components.forEach(component -> component.onClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        if (isDragging()) {
            setPositionX(getDraggingPositionX() + mouseX);
            setPositionY(getDraggingPositionY() + mouseY);
            //TODO colliding math, make panels not collide; maybe alignment lines
        }
        drawBorderedRectReliant(getPositionX(), getPositionY(), getPositionX() + getWidth(),
                getPositionY() + (open ? getHeight() : 20), 1.7F,
                Colors.PANEL_INSIDE.getColor(), Colors.PANEL_BORDER.getColor());
        font.drawString(getLabel(), getPositionX() + 4, getPositionY() + 1, Colors.PANEL_LABEL.getColor());
        if (open) {
            int componentPositionY = getPositionY() + 20;
            for (Component component : components) {
                component.drawComponent(mouseX, mouseY);
                component.setPositionX(getPositionX());
                component.setPositionY(componentPositionY);
                componentPositionY += component.getHeight();
            }
        }
    }

    @Override
    public int getHeight() {
        int height = 20;
        for (Component component : components) {
            height += component.getHeight();
        }
        return height;
    }

    public void mouseReleased() {
        setDragging(false);
    }

    public List<Component> getComponents() {
        return components;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public int getDraggingPositionX() {
        return draggingPositionX;
    }

    public int getDraggingPositionY() {
        return draggingPositionY;
    }

    public void setDraggingPositionX(int draggingPositionX) {
        this.draggingPositionX = draggingPositionX;
    }

    public void setDraggingPositionY(int draggingPositionY) {
        this.draggingPositionY = draggingPositionY;
    }

    public abstract void registerComponents();
}
