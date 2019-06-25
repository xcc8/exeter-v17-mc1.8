package wtf.nuf.exeter.gui.clickapi;

import net.minecraft.client.gui.GuiScreen;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.gui.clickapi.components.buttons.ModButtonComponent;
import wtf.nuf.exeter.gui.clickapi.components.panels.PanelComponent;
import wtf.nuf.exeter.mod.Mod;
import wtf.nuf.exeter.mod.ModType;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ClickGuiScreen extends GuiScreen {
    private final List<PanelComponent> panels = new CopyOnWriteArrayList<>();

    public ClickGuiScreen() {
        int positionX = 30;
        for (ModType modType : ModType.values()) {
            panels.add(new PanelComponent(modType.getLabel(), positionX, 20, 100, 20) {
                @Override
                public void registerComponents() {
                    for (Mod mod : Exeter.getInstance().getModManager().getList()) {
                        if (mod.getModType().equals(modType)) {
                            getComponents().add(new ModButtonComponent(mod, mod.getLabel(), getPositionX(), getPositionY(), 100, 20));
                        }
                    }
                }
            });
            positionX += 112;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        panels.forEach(panelComponent -> panelComponent.drawComponent(mouseX, mouseY));
        Exeter.getInstance().getOverlayManager().drawScreen(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        panels.forEach(panelComponent -> panelComponent.onClicked(mouseX, mouseY, mouseButton));
        Exeter.getInstance().getOverlayManager().mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        panels.forEach(PanelComponent::mouseReleased);
        Exeter.getInstance().getOverlayManager().mouseReleased();
    }

    @Override
    public void onGuiClosed() {
        //client.fileManager.getFile("gui_config.json").save();
        //client.fileManager.getFile("overlay_config.json").save();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public List<PanelComponent> getPanels() {
        return panels;
    }
}
