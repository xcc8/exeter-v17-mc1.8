package wtf.nuf.exeter.mod;

import net.minecraft.client.Minecraft;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.mcapi.interfaces.Labeled;
import wtf.nuf.exeter.mcapi.settings.Setting;
import wtf.nuf.exeter.printer.Printer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Mod implements Labeled {
    private final String label, description;
    private final String[] aliases;

    private final ModType modType;

    protected final Minecraft minecraft = Minecraft.getMinecraft();

    protected final Exeter exeter = Exeter.getInstance();

    protected final List<Setting> settings = new CopyOnWriteArrayList<>();

    protected Mod() {
        this.label = this.getClass().getAnnotation(ModValues.class).label();
        this.aliases = this.getClass().getAnnotation(ModValues.class).aliases();
        this.description = this.getClass().getAnnotation(ModValues.class).description();
        this.modType = this.getClass().getAnnotation(ModValues.class).modType();
        Printer.getPrinter().print(String.format("Registered new mod \"%s\".", label));
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public ModType getModType() {
        return modType;
    }

    public Setting getSetting(String label) {
        for (Setting setting : this.settings) {
            for (String alias : setting.getAliases()) {
                if (alias.equalsIgnoreCase(label)) {
                    return setting;
                }
            }
        }
        return null;
    }

    public List<Setting> getSettings() {
        return settings;
    }
}
