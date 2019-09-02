package wtf.nuf.exeter.gui.clickapi.components.buttons.settings;

import wtf.nuf.exeter.gui.clickapi.components.Colors;
import wtf.nuf.exeter.gui.clickapi.components.Component;
import wtf.nuf.exeter.mcapi.settings.ToggleableSetting;

public class ToggleableComponent extends Component {
    private final ToggleableSetting toggleableSetting;

    public ToggleableComponent(ToggleableSetting toggleableSetting, String label, int positionX, int positionY, int width, int height) {
        super(label, positionX, positionY, width, height);
        this.toggleableSetting = toggleableSetting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY, 20) && mouseButton == 0) {
            toggleableSetting.toggle();
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        drawRect(getPositionX() + 1, getPositionY() + 1, getPositionX() + getWidth() - 1,
                getPositionY() + getHeight() - 1, toggleableSetting.isEnabled() ?
                        Colors.BUTTON_ENABLED.getColor() : Colors.BUTTON.getColor());
        font.drawString(getLabel(), getPositionX() + 4, getPositionY() + 1, toggleableSetting.isEnabled() ?
                Colors.BUTTON_LABEL_ENABLED.getColor() : Colors.BUTTON_LABEL.getColor());
    }
}
