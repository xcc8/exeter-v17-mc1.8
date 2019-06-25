package wtf.nuf.exeter.mcapi.interfaces;

public interface Toggleable {
    boolean isEnabled();

    void setEnabled(boolean flag);

    void toggle();
}
