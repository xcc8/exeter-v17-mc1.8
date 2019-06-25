package wtf.nuf.exeter.command;

import wtf.nuf.exeter.mcapi.interfaces.Labeled;

public class Argument implements Labeled {
    private final String label;
    private String value;
    private boolean present = false;

    public Argument(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
