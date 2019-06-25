package wtf.nuf.exeter.gui.accounts.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import wtf.nuf.exeter.account.Account;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.gui.accounts.screens.GuiAccountScreen;

public class GuiAccountSlot extends GuiSlot {
    private int selectedAccount = 0;

    public GuiAccountSlot(GuiAccountScreen guiAccountScreen) {
        super(Minecraft.getMinecraft(), guiAccountScreen.width, guiAccountScreen.height, 32,
                guiAccountScreen.height - 60, 27);
    }

    @Override
    protected int getSize() {
        return Exeter.getInstance().getAccountManager().size();
    }

    @Override
    protected int getContentHeight() {
        return getSize() * 40;
    }

    @Override
    protected void elementClicked(int var1, boolean var2, int var3, int var4) {
        this.selectedAccount = var1;
        if (var2) {
            Account account = Exeter.getInstance().getAccountManager().getList().get(var1);
            try {
                Exeter.getInstance().getAccountManager().login(account.getLabel(), account.getPassword());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    protected boolean isSelected(int var1) {
        return selectedAccount == var1;
    }

    @Override
    protected void drawBackground() {}

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
        try {
            Account account = Exeter.getInstance().getAccountManager().getList().get(var1);
            mc.fontRendererObj.drawStringWithShadow(account.getLabel(), var2 + 2,
                    var3 + 2, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow(account.getPassword().replaceAll("(?s).", "*"),
                    var2 + 2, var3 + 15, 0xFFFFFFFF);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public int getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(int selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
}
