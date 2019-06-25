package wtf.nuf.exeter.gui.accounts.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.gui.accounts.components.GuiPasswordField;

import java.io.IOException;

public class GuiLoginScreen extends GuiScreen {
    private GuiTextField usernameField;
    private GuiPasswordField passwordField;

    private Minecraft mc = Minecraft.getMinecraft();

    private final GuiScreen parentScreen;

    public GuiLoginScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Direct Login", this.width / 2, 17, 0xFFFFFFFF);
        this.drawString(this.fontRendererObj, "Email", this.width / 2 - 100, 53, 0xFFFFFFFF);
        this.drawString(this.fontRendererObj, "Password", this.width / 2 - 100, 94, 0xFFFFFFFF);
        usernameField.drawTextBox();
        passwordField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        usernameField.updateCursorCounter();
        passwordField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.usernameField.textboxKeyTyped(typedChar, keyCode);
        this.passwordField.textboxKeyTyped(typedChar, keyCode);
        if (typedChar == '\t') {
            if (this.usernameField.isFocused()) {
                this.usernameField.isFocused = false;
                this.passwordField.isFocused = true;
            } else {
                this.usernameField.isFocused = true;
                this.passwordField.isFocused = false;
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        usernameField.mouseClicked(mouseX, mouseY, mouseButton);
        passwordField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                Exeter.getInstance().getAccountManager().login(usernameField.getText(), passwordField.getText());
                mc.displayGuiScreen(new GuiMainMenu());
                break;
            case 1:
                mc.displayGuiScreen(parentScreen);
                break;
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Login"));
        buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Cancel"));
        usernameField = new GuiTextField(0, fontRendererObj, this.width / 2 - 100, 66, 200, 20);
        passwordField = new GuiPasswordField(1, fontRendererObj, this.width / 2 - 100, 106, 200, 20);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}
