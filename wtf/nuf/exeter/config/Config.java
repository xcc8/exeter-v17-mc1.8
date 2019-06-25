package wtf.nuf.exeter.config;

import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.printer.Printer;

import java.io.IOException;

public abstract class Config {
    private final java.io.File file;

    public Config(java.io.File file) {
        this.file = file;
        Exeter.getInstance().getConfigManager().register(this);
        if (!file.exists()) {
            try {
                Printer.getPrinter().print(String.format("%s file %s.",
                        file.createNewFile() ? "Created" : "Failed to create", file.getName()));
            } catch (IOException exception) {
                exception.printStackTrace();
                Printer.getPrinter().print(String.format("Failed to create file %s.", file.getName()));
            }
        }
        Printer.getPrinter().print(String.format("Registered new file \"%s\".", file.getName()));
    }

    public java.io.File getFile() {
        return file;
    }

    public abstract void load();

    public abstract void save();
}
