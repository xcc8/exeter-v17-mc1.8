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

    public void increment() {
        Enum[] array;
        for (int length = (array = ((Enum) getValue()).getClass().getEnumConstants()).length, index = 0; index < length; index++) {
            if (array[index].name().equalsIgnoreCase(getValue().name())) {
                index++;
                if (index > array.length - 1) {
                    index = 0;
                }
                setValue(array[index]);
            }
        }
    }

    public void decrement() {
        Enum[] array;
        for (int length = (array = ((Enum) getValue()).getClass().getEnumConstants()).length, index = 0; index < length; index++) {
            if (array[index].name().equalsIgnoreCase(getValue().name())) {
                index--;
                if (index < 0) {
                    index = array.length - 1;
                }
                setValue(array[index]);
            }
        }
    }

    public Enum[] getModes() {
        return modes;
    }
}
