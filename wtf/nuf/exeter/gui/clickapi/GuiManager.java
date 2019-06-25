package wtf.nuf.exeter.gui.clickapi;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.gui.clickapi.components.panels.PanelComponent;
import wtf.nuf.exeter.keybind.Keybind;
import wtf.nuf.exeter.mcapi.manager.SetManager;

public final class GuiManager extends SetManager<PanelComponent> {
    private final ClickGuiScreen clickGuiScreen;

    public GuiManager() {
        clickGuiScreen = new ClickGuiScreen();

        Exeter.getInstance().getKeybindManager().register(new Keybind("click_gui_keybind", Keyboard.KEY_GRAVE) {
            @Override
            public void onPressed() {
                Minecraft.getMinecraft().displayGuiScreen(clickGuiScreen);
            }
        });

        /*new File(nivea, "gui_config.json") {
            @Override
            public void load() {//TODO this
                try {
                    if (!getFile().exists()) {
                        getFile().createNewFile();
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();;
                }
            }

            @Override
            public void save() {
                try {
                    if (!getFile().exists()) {
                        getFile().createNewFile();
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();;
                }
            }
        };*/
    }

    public ClickGuiScreen getClickGuiScreen() {
        return clickGuiScreen;
    }
}
