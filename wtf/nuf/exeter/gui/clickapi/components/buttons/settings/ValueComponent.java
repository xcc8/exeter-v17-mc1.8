package wtf.nuf.exeter.gui.clickapi.components.buttons.settings;

import wtf.nuf.exeter.gui.clickapi.components.Colors;
import wtf.nuf.exeter.gui.clickapi.components.Component;
import wtf.nuf.exeter.mcapi.settings.ValueSetting;

public class ValueComponent extends Component {
    private final ValueSetting valueSetting;

    public ValueComponent(ValueSetting valueSetting, String label, int positionX, int positionY, int width, int height) {
        super(label, positionX, positionY, width, height);
        this.valueSetting = valueSetting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        //TODO slidING or avo type shit
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        drawRect(getPositionX() + 1, getPositionY() + 1, getPositionX() + getWidth() - 1,
                getPositionY() + getHeight() - 1, Colors.BUTTON.getColor());
        font.drawString(String.format("%s (%s)", getLabel(), valueSetting.getValue()), getPositionX() + 2, getPositionY() + 1,
                Colors.BUTTON_LABEL_ENABLED.getColor());
    }
}
