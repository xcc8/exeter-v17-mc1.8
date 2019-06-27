package wtf.nuf.exeter.gui.clickapi.components.buttons;

import wtf.nuf.exeter.gui.clickapi.components.Colors;
import wtf.nuf.exeter.gui.clickapi.components.Component;
import wtf.nuf.exeter.gui.clickapi.components.buttons.settings.ModeComponent;
import wtf.nuf.exeter.gui.clickapi.components.buttons.settings.StringComponent;
import wtf.nuf.exeter.gui.clickapi.components.buttons.settings.ToggleableComponent;
import wtf.nuf.exeter.gui.clickapi.components.buttons.settings.ValueComponent;
import wtf.nuf.exeter.mcapi.interfaces.Toggleable;
import wtf.nuf.exeter.mcapi.settings.*;
import wtf.nuf.exeter.mod.Mod;
import wtf.nuf.exeter.mod.ToggleableMod;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModComponent extends Component {
    private final Mod mod;
    private boolean open = false;
    private final List<Component> components = new CopyOnWriteArrayList<>();

    public ModComponent(Mod mod, String label, int positionX, int positionY, int width, int height) {
        super(label, positionX, positionY, width, height);
        this.mod = mod;
        for (Setting setting : mod.getSettings()) {
            if (setting instanceof ModeSetting) {
                components.add(new ModeComponent((ModeSetting) setting, setting.getLabel(), positionX,
                        positionY, width, height));
            } else if (setting instanceof StringSetting) {
                components.add(new StringComponent((StringSetting) setting, setting.getLabel(), positionX,
                        positionY, width, height));
            } else if (setting instanceof ValueSetting) {
                components.add(new ValueComponent((ValueSetting) setting, setting.getLabel(), positionX,
                        positionY, width, height));
            } else if (setting instanceof ToggleableSetting) {
                components.add(new ToggleableComponent((ToggleableSetting) setting, setting.getLabel(), positionX,
                        positionY, width, height));
            }
        }
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
                    this.open = !open;
                    break;
            }
        }
        components.forEach(component -> component.onClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        if (mod instanceof ToggleableMod) {
            ToggleableMod toggleableMod = (ToggleableMod) mod;
            drawBorderedRectReliant(getPositionX() + 1, getPositionY() + 1, getPositionX() + getWidth() - 1,
                    getPositionY() + 19, 1.7F, toggleableMod.isEnabled() ?
                            Colors.BUTTON_ENABLED.getColor() : Colors.BUTTON.getColor(),
                    Colors.PANEL_BORDER.getColor());
            font.drawString(getLabel(), getPositionX() + 2, getPositionY() + 1, toggleableMod.isEnabled() ?
                    Colors.BUTTON_LABEL_ENABLED.getColor() : Colors.BUTTON_LABEL.getColor());
        } else {
            drawBorderedRectReliant(getPositionX() + 1, getPositionY() + 1, getPositionX() + getWidth() - 1,
                    getPositionY() + 19, 1.7F, Colors.BUTTON.getColor(),
                    Colors.PANEL_BORDER.getColor());
            font.drawString(getLabel(), getPositionX() + 2, getPositionY() + 1, Colors.BUTTON_LABEL.getColor());
        }
        setHeight(!open ? 20 : 20 + components.size() * 20);
        if (open) {
            int positionY = getPositionY() + 20;
            for (Component component : components) {
                component.drawComponent(mouseX, mouseY);
                component.setPositionX(getPositionX());
                component.setPositionY(positionY);
                positionY += 20;
            }
        }
    }

    public List<Component> getComponents() {
        return components;
    }

    public Mod getMod() {
        return mod;
    }
}
