package wtf.nuf.exeter.mod;

import wtf.nuf.exeter.mcapi.eventsystem.EventManager;
import wtf.nuf.exeter.mcapi.interfaces.Toggleable;

public class ToggleableMod extends Mod implements Toggleable {
    private String displayLabel;
    private final int color;
    private boolean enabled, visible;

    protected ToggleableMod() {
        this.displayLabel = this.getClass().getAnnotation(ModValues.class).label();
        this.color = this.getClass().getAnnotation(ModValues.class).color();
        setEnabled(this.getClass().getAnnotation(ModValues.class).enabled());
        this.visible = this.getClass().getAnnotation(ModValues.class).visible();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean flag) {
        this.enabled = flag;
        if (this.enabled) {
            EventManager.getInstance().register(this);
            onEnable();
        } else {
            EventManager.getInstance().unregister(this);
            onDisable();
        }
    }

    @Override
    public void toggle() {
        setEnabled(!isEnabled());
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }

    public int getColor() {
        return color;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }
}
