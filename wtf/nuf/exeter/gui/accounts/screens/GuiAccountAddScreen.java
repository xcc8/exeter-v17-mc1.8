package wtf.nuf.exeter.gui.accounts.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import wtf.nuf.exeter.account.Account;
import wtf.nuf.exeter.core.Exeter;

import java.io.IOException;

public class GuiAccountAddScreen extends GuiScreen {
    private GuiTextField usernameField;
    private GuiTextField passwordField;

    private Minecraft mc = Minecraft.getMinecraft();

    private final GuiScreen parentScreen;

    public GuiAccountAddScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Add Account", this.width / 2, 17, 0xFFFFFFFF);
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
        if (button.id == 0) {
            Exeter.getInstance().getAccountManager().register(new Account(usernameField.getText(), passwordField.getText()));
        }
        mc.displayGuiScreen(parentScreen);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18,
                "Add"));
        buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18,
                "Cancel"));
        usernameField = new GuiTextField(0, fontRendererObj, this.width / 2 - 100, 66,
                200, 20);
        passwordField = new GuiTextField(1, fontRendererObj, this.width / 2 - 100, 106,
                200, 20);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}
