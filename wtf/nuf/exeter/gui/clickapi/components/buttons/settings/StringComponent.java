package wtf.nuf.exeter.gui.clickapi.components.buttons.settings;

import wtf.nuf.exeter.gui.clickapi.components.Colors;
import wtf.nuf.exeter.gui.clickapi.components.Component;
import wtf.nuf.exeter.mcapi.settings.StringSetting;

public class StringComponent extends Component {
    private final StringSetting stringSetting;

    public StringComponent(StringSetting stringSetting, String label, int positionX, int positionY, int width, int height) {
        super(label, positionX, positionY, width, height);
        this.stringSetting = stringSetting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        //TODO make a way for this to be typed into, using keyTyped
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        drawRect(getPositionX() + 1, getPositionY() + 1, getPositionX() + getWidth() - 1,
                getPositionY() + getHeight() - 1, Colors.BUTTON.getColor());
        font.drawString(String.format("%s (\"%s\")", getLabel(), stringSetting.getValue()), getPositionX() + 4,
                getPositionY() + 1, Colors.BUTTON_LABEL_ENABLED.getColor());
    }
}
