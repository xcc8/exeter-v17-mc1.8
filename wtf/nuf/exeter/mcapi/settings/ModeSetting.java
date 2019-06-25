package wtf.nuf.exeter.mcapi.settings;

public class ModeSetting extends Setting {
    private final Enum[] modes;
    private Enum value;

    public ModeSetting(String label, String[] aliases, Enum value, Enum[] modes) {
        super(label, aliases);
        this.value = value;
        this.modes = modes;
    }

    public Enum getValue() {
        return value;
    }

    public void setValue(Enum value) {
        if (value != null) {
            for (int index = 0; index < modes.length; index++) {
                if (!value.equals(modes[index])) {
                    this.value = modes[index];
                    break;
                }
            }
        }
    }

    public Enum getMode(String label) {
        for (Enum mode : modes) {
            if (mode.name().equalsIgnoreCase(label)) {
                return mode;
            }
        }
        return null;
    }

    public Enum[] getModes() {
        return modes;
    }
}
