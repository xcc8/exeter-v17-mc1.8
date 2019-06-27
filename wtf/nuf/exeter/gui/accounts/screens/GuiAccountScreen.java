package wtf.nuf.exeter.gui.accounts.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import org.lwjgl.input.Keyboard;
import wtf.nuf.exeter.account.Account;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.gui.accounts.components.GuiAccountSlot;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class GuiAccountScreen extends GuiScreen {
    private final GuiScreen parentScreen;
    private GuiAccountSlot guiAccountSlot;

    private boolean removeAccount = false;

    public GuiAccountScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
        this.guiAccountSlot = new GuiAccountSlot(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        guiAccountSlot.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, "Accounts", this.width / 2, 17, 0xFFFFFFFF);
        fontRendererObj.drawStringWithShadow(mc.getSession().getUsername(), 2,
                Minecraft.getMinecraft().getScaledResolution().getScaledHeight() - 22,
                0xFFFFFFFF);
        fontRendererObj.drawStringWithShadow(String.format("%s accounts found.",
                Exeter.getInstance().getAccountManager().size()),
                2, Minecraft.getMinecraft().getScaledResolution().getScaledHeight() - 12,
                0xFFFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        guiAccountSlot.handleMouseInput();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_UP) {
            guiAccountSlot.setSelectedAccount(guiAccountSlot.getSelectedAccount() - 1);
        }
        if (keyCode == Keyboard.KEY_DOWN) {
            guiAccountSlot.setSelectedAccount(guiAccountSlot.getSelectedAccount() + 1);
        }
        if (keyCode == Keyboard.KEY_RETURN) {
            Account account = Exeter.getInstance().getAccountManager().getList().get(guiAccountSlot.getSelectedAccount());
            Exeter.getInstance().getAccountManager().login(account.getLabel(), account.getPassword());
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(new GuiAccountAddScreen(this));
                break;
            case 2:
                Account account = Exeter.getInstance().getAccountManager().getList().get(guiAccountSlot.getSelectedAccount());
                if (account != null) {
                    Exeter.getInstance().getAccountManager().login(account.getLabel(), account.getPassword());
                }
                break;
            case 3:
                removeAccount = true;
                mc.displayGuiScreen(new GuiYesNo(this,
                        "Are you sure you want to delete this account?",
                        "", "Remove", "Cancel",
                        guiAccountSlot.getSelectedAccount()));
                break;
            case 4:
                mc.displayGuiScreen(parentScreen);
                break;
            case 5:
                mc.displayGuiScreen(new GuiLoginScreen(this));
                break;
            case 6:
                mc.displayGuiScreen(new GuiAccountEditScreen(this));
                break;
            case 7:
                openImportFrame();
                break;
        }
    }

    @Override
    public void initGui() {
        buttonList.clear();
        buttonList.add(new GuiButton(1, width / 2 - 154,
                height - 48, 73, 20, "Add"));
        buttonList.add(new GuiButton(2, width / 2 - 76,
                height - 48, 73, 20, "Login"));
        buttonList.add(new GuiButton(3, width / 2 + 78,
                height - 48, 73, 20, "Remove"));
        buttonList.add(new GuiButton(4, width / 2 - 76,
                height - 26, 149, 20, "Cancel"));
        buttonList.add(new GuiButton(5, width / 2,
                height - 48, 73, 20, "Direct"));
        buttonList.add(new GuiButton(6, width / 2 - 154,
                height - 26, 73, 20, "Edit"));
        buttonList.add(new GuiButton(7, width / 2 + 78,
                height - 26, 73, 20, "Import"));
        guiAccountSlot = new GuiAccountSlot(this);
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        super.confirmClicked(result, id);
        if (removeAccount) {
            removeAccount = false;
            if (result) {
                Exeter.getInstance().getAccountManager().getList().remove(id);
            }
            mc.displayGuiScreen(this);
        }
    }


    @Override
    public void onGuiClosed() {
        Exeter.getInstance().getConfigManager().getConfig("accounts.txt").save();
    }

    public GuiAccountSlot getAccountSlot() {
        return guiAccountSlot;
    }

    private void openImportFrame() {
        JFileChooser chooser = new JFileChooser();
        chooser.setVisible(true);
        chooser.setSize(500, 400);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileNameExtensionFilter("File", "txt"));
        JFrame frame = new JFrame("Select a file");

        chooser.addActionListener(event -> {
            if (event.getActionCommand().equals("ApproveSelection") && chooser.getSelectedFile() != null) {
                try {
                    Scanner scanner = new Scanner(new FileReader(chooser.getSelectedFile()));
                    scanner.useDelimiter("\n");
                    while (scanner.hasNext()) {
                        String[] split = scanner.next().trim().split(":");
                        Exeter.getInstance().getAccountManager().register(new Account(split[0], split[1]));
                    }
                    scanner.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Account account : Exeter.getInstance().getAccountManager().getList()) {
                        stringBuilder.append(String.format("%s:%s\n", account.getLabel(), account.getPassword()));
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter(Exeter.getInstance().
                            getConfigManager().getConfig("accounts.txt").getFile()));
                    writer.write(stringBuilder.toString());
                    writer.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                frame.setVisible(false);
                frame.dispose();
            }
            if (event.getActionCommand().equals("CancelSelection")) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        frame.setAlwaysOnTop(true);
        frame.add(chooser);
        frame.setVisible(true);
        frame.setSize(750, 600);
    }
}
