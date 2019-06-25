package wtf.nuf.exeter.mcapi.settings;

import wtf.nuf.exeter.mcapi.interfaces.Labeled;
import wtf.nuf.exeter.printer.Printer;

public class Setting implements Labeled {
    private final String label;
    private final String[] aliases;

    public Setting(String label, String[] aliases) {
        this.label = label;
        this.aliases = aliases;
        Printer.getPrinter().print(String.format("Registered new setting \"%s\".", label));
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String[] getAliases() {
        return aliases;
    }
}
