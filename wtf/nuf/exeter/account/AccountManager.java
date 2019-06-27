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
        // this is so we are displaying the same screen over and over again
        // and not a new 'GuiAccountScreen' every time the accounts button
        // is clicked
        this.guiAccountScreen = new GuiAccountScreen(new GuiMainMenu());

        // we use .txt here just because people will use the import button to
        // import lists from other clients, this just makes it much much
        // easier, other file saving/loading will use .json
        new Config(new File(exeter.getConfigManager().getDirectory(), "accounts.txt")) {
            @Override
            public void load() {
                try {
                    // if there is no file make one
                    if (!getFile().exists()) {
                        getFile().createNewFile();
                    }

                    BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
                    String line;
                    // read the file until the next line is blank/null
                    while ((line = bufferedReader.readLine()) != null) {
                        // split the line at ':'
                        String[] accountLine = line.split(":");
                        // add the account to our list
                        register(new Account(accountLine[0], accountLine[1]));
                    }
                    // close the reader
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

                    // if our account list is empty then don't even bother
                    if (getList().isEmpty()) {
                        return;
                    }

                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getFile()));
                    // loop through all the accounts in our client
                    for (Account account : getList()) {
                        // write them to the text file and separate the
                        // username/email and password using the ':' character
                        bufferedWriter.write(String.format("%s:%s\n", account.getLabel(), account.getPassword()));
                    }
                    // close the writer
                    bufferedWriter.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        };
    }

    /**
     * instance of the account manager screen, use to display the screen when the
     * account manager button in the main menu is clicked
     * @return instance of the account screen
     */
    public GuiAccountScreen getGuiAccountScreen() {
        return guiAccountScreen;
    }

    /**
     * this isn't my method but all it does is use yggdrasil to authenticate your
     * account and then set your session
     * @param username / email
     * @param password ?
     */
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
