package wtf.nuf.exeter.gui.clickapi.components.buttons;

import wtf.nuf.exeter.gui.clickapi.components.Component;
import wtf.nuf.exeter.mcapi.settings.ToggleableSetting;

public class SettingButtonComponent extends Component {
    private final ToggleableSetting toggleableSetting;

    public SettingButtonComponent(ToggleableSetting toggleableSetting, String label, int positionX, int positionY, int width, int height) {
        super(label, positionX, positionY, width, height);
        this.toggleableSetting = toggleableSetting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            toggleableSetting.toggle();
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {

    }
}
