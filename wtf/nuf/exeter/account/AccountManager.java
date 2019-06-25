package wtf.nuf.exeter.account;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.Session;
import wtf.nuf.exeter.config.Config;
import wtf.nuf.exeter.gui.accounts.screens.GuiAccountScreen;
import wtf.nuf.exeter.mcapi.manager.ListManager;

import java.io.*;
import java.net.Proxy;

public final class AccountManager extends ListManager<Account> {
    private final GuiAccountScreen guiAccountScreen;

    public AccountManager() {
        this.guiAccountScreen = new GuiAccountScreen(new GuiMainMenu());

        new Config(new File(exeter.getConfigManager().getDirectory(), "accounts.txt")) {
            @Override
            public void load() {
                try {
                    if (!getFile().exists()) {
                        getFile().createNewFile();
                    }

                    BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] accountLine = line.split(":");
                        register(new Account(accountLine[0], accountLine[1]));
                    }
                    bufferedReader.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void save() {
                try {
                    if (!getFile().exists()) {
                        getFile().createNewFile();
                    }

                    if (getList().isEmpty()) {
                        return;
                    }

                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getFile()));
                    for (Account account : getList()) {
                        bufferedWriter.write(String.format("%s:%s\n", account.getLabel(), account.getPassword()));
                    }
                    bufferedWriter.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        };
    }

    public GuiAccountScreen getGuiAccountScreen() {
        return guiAccountScreen;
    }

    public void login(String username, String password) {
        if ((username.length() < 1) || (password.length() < 1)) {
            return;
        }
        YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication) yggdrasilAuthenticationService.createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        try {
            yggdrasilUserAuthentication.logIn();
            Minecraft.getMinecraft().setSession(new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang"));
        } catch (AuthenticationException exception) {
            exception.printStackTrace();
        }
    }
}
