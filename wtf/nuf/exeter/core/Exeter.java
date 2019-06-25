package wtf.nuf.exeter.core;

import org.lwjgl.opengl.Display;
import wtf.nuf.exeter.account.AccountManager;
import wtf.nuf.exeter.command.CommandManager;
import wtf.nuf.exeter.config.Config;
import wtf.nuf.exeter.config.ConfigManager;
import wtf.nuf.exeter.friend.FriendManager;
import wtf.nuf.exeter.gui.clickapi.GuiManager;
import wtf.nuf.exeter.gui.clickapi.overlay.OverlayManager;
import wtf.nuf.exeter.keybind.KeybindManager;
import wtf.nuf.exeter.mod.ModManager;
import wtf.nuf.exeter.printer.Printer;
import wtf.nuf.exeter.task.TaskManager;

/**
 * @author nuf
 * @since May 15, 2019
 */
public final class Exeter {
    private static Exeter instance = null;

    public static final int CLIENT_BUILD = 1;

    private final AccountManager accountManager;
    private final ExeterSettings settings;
    private final ConfigManager configManager;
    private final CommandManager commandManager;
    private final FriendManager friendManager;
    private final GuiManager guiManager;
    private final KeybindManager keybindManager;
    private final ModManager modManager;
    private final OverlayManager overlayManager;
    private final TaskManager taskManager;

    /**
     * initializes the entire client upon startup
     */
    public Exeter() {
        Printer.getPrinter().print("Initializing Exeter...");

        instance = this;

        this.taskManager = new TaskManager();
        this.configManager = new ConfigManager("/exeter");
        this.settings = new ExeterSettings();
        this.commandManager = new CommandManager();
        this.accountManager = new AccountManager();
        this.friendManager = new FriendManager();
        this.keybindManager = new KeybindManager();
        this.modManager = new ModManager();
        this.guiManager = new GuiManager();
        this.overlayManager = new OverlayManager();

        Display.setTitle(String.format("Minecraft 1.8 (Exeter version-17 build-%s)", CLIENT_BUILD));

        this.configManager.getSet().forEach(Config::load);

        Runtime.getRuntime().addShutdownHook(new Thread("exeter_shutdown_thread") {
            @Override
            public void run() {
                Printer.getPrinter().print("Shutting down Exeter...");
                configManager.getSet().forEach(Config::save);
                Printer.getPrinter().print("Shutdown Exeter.");
            }
        });

        Printer.getPrinter().print("Initialized Exeter.");
    }

    public static Exeter getInstance() {
        return instance;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public ExeterSettings getSettings() {
        return settings;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public KeybindManager getKeybindManager() {
        return keybindManager;
    }

    public ModManager getModManager() {
        return modManager;
    }

    public OverlayManager getOverlayManager() {
        return overlayManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
