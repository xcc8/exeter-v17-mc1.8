package wtf.nuf.exeter.config;

import net.minecraft.client.Minecraft;
import wtf.nuf.exeter.mcapi.manager.SetManager;
import wtf.nuf.exeter.printer.Printer;

import java.io.File;

public final class ConfigManager extends SetManager<Config> {
    private final File directory;

    public ConfigManager(String folder) {
        this.directory = new File(Minecraft.getMinecraft().mcDataDir, folder);
        if (!directory.exists()) {
            Printer.getPrinter().print(String.format("%s directory.", directory.mkdir() ? "Created" : "Failed to create"));
        }
    }

    public File getDirectory() {
        return directory;
    }

    public Config getConfig(String label) {
        for (Config config : getSet()) {
            if (config.getFile().getName().equalsIgnoreCase(label)) {
                return config;
            }
        }
        return null;
    }
}
