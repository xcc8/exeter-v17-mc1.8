package wtf.nuf.exeter.keybind;

import wtf.nuf.exeter.mcapi.interfaces.Labeled;
import wtf.nuf.exeter.printer.Printer;

public abstract class Keybind implements Labeled {
    private final String label;
    private int key;

    protected Keybind(String label, int key) {
        this.label = label;
        this.key = key;
        Printer.getPrinter().print(String.format("Registered new keybind \"%s\".", label));
    }

    @Override
    public String getLabel() {
        return label;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public abstract void onPressed();
}
