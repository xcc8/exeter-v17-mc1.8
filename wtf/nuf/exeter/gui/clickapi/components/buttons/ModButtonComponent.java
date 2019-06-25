package wtf.nuf.exeter.gui.clickapi.components.buttons;

import wtf.nuf.exeter.gui.clickapi.components.Colors;
import wtf.nuf.exeter.gui.clickapi.components.Component;
import wtf.nuf.exeter.mcapi.interfaces.Toggleable;
import wtf.nuf.exeter.mod.Mod;
import wtf.nuf.exeter.mod.ToggleableMod;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModButtonComponent extends Component {
    private final Mod mod;
    private final List<Component> components = new CopyOnWriteArrayList<>();

    public ModButtonComponent(Mod mod, String label, int positionX, int positionY, int width, int height) {
        super(label, positionX, positionY, width, height);
        this.mod = mod;
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY, 20)) {
            switch (mouseButton) {
                case 0:
                    if (mod instanceof Toggleable) {
                        ToggleableMod toggleableMod = (ToggleableMod) mod;
                        toggleableMod.toggle();
                    }
                    break;
                case 1:
                    break;
            }
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        if (mod instanceof ToggleableMod) {
            ToggleableMod toggleableMod = (ToggleableMod) mod;
            drawBorderedRectReliant(getPositionX() + 1, getPositionY() + 1, getPositionX() + getWidth() - 1,
                    getPositionY() + getHeight() - 1, 1.7F, toggleableMod.isEnabled() ?
                            Colors.BUTTON_ENABLED.getColor() : Colors.BUTTON.getColor(),
                    Colors.PANEL_BORDER.getColor());
            font.drawString(getLabel(), getPositionX() + 2, getPositionY() + 1, toggleableMod.isEnabled() ?
                    Colors.BUTTON_LABEL_ENABLED.getColor() : Colors.BUTTON_LABEL.getColor());
        } else {
            drawBorderedRectReliant(getPositionX() + 1, getPositionY() + 1, getPositionX() + getWidth() - 1,
                    getPositionY() + getHeight() - 1, 1.7F, Colors.BUTTON.getColor(),
                    Colors.PANEL_BORDER.getColor());
            font.drawString(getLabel(), getPositionX() + 2, getPositionY() + 1, Colors.BUTTON_LABEL.getColor());
        }
    }

    public List<Component> getComponents() {
        return components;
    }

    public Mod getMod() {
        return mod;
    }
}
