package wtf.nuf.exeter.gui.clickapi.components.buttons.settings;

import wtf.nuf.exeter.gui.clickapi.components.Colors;
import wtf.nuf.exeter.gui.clickapi.components.Component;
import wtf.nuf.exeter.mcapi.settings.ModeSetting;

public class ModeComponent extends Component {
    private final ModeSetting modeSetting;

    public ModeComponent(ModeSetting modeSetting, String label, int positionX, int positionY, int width, int height) {
        super(label, positionX, positionY, width, height);
        this.modeSetting = modeSetting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY, 20)) {
            switch (mouseButton) {
                case 0:
                    modeSetting.increment();
                    break;
                case 1:
                    modeSetting.decrement();
                    break;
            }
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        drawRect(getPositionX() + 1, getPositionY() + 1, getPositionX() + getWidth() - 1,
                getPositionY() + getHeight() - 1, Colors.BUTTON.getColor());
        font.drawString(String.format("%s (%s)", getLabel(), modeSetting.getValue()), getPositionX() + 2,
                getPositionY() + 1, Colors.BUTTON_LABEL_ENABLED.getColor());
    }
}
