package wtf.nuf.exeter.mcapi.settings;

public class ValueSetting<T extends Number> extends Setting {
    private final T minimum, maximum;
    private T value;

    private boolean clamp = true;

    public ValueSetting(String label, String[] aliases, T value, T minimum, T maximum) {
        super(label, aliases);
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public ValueSetting(String label, String[] aliases, T value) {
        this(label, aliases, value, null, null);
        clamp = false;
    }

    public void setValue(T value) {
        if (clamp) {
            if (this.value instanceof Float) {
                if (value.floatValue() > this.getMaximum().floatValue()) {
                    value = this.getMaximum();
                } else if (value.floatValue() < this.getMinimum().floatValue()) {
                    value = this.getMinimum();
                }
            } else if (this.value instanceof Double) {
                if (value.doubleValue() > this.getMaximum().doubleValue()) {
                    value = this.getMaximum();
                } else if (value.doubleValue() < this.getMinimum().doubleValue()) {
                    value = this.getMinimum();
                }
            } else if (this.value instanceof Integer) {
                if (value.intValue() > this.getMaximum().intValue()) {
                    value = this.getMaximum();
                } else if (value.intValue() < this.getMinimum().intValue()) {
                    value = this.getMinimum();
                }
            }
        }
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public T getMinimum() {
        return minimum;
    }

    public T getMaximum() {
        return maximum;
    }
}
