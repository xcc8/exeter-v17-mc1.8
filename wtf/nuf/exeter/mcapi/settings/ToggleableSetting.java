package wtf.nuf.exeter.mcapi.settings;

import wtf.nuf.exeter.mcapi.interfaces.Toggleable;

public class ToggleableSetting extends Setting implements Toggleable {
    private boolean enabled;

    public ToggleableSetting(String label, String[] aliases, boolean value) {
        super(label, aliases);
        this.enabled = value;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    @Override
    public void toggle() {
        setEnabled(!isEnabled());
    }
}
