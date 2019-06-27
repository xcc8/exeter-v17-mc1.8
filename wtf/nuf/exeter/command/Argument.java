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

    /**
     *
     * @return whether or not the argument is present
     * i.e. in the toggle command there is one argument, the mod
     * so if you do '.t' and you don't entire a mod name then
     * the argument will be flagged !present
     */
    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
