package wtf.nuf.exeter.mcapi.settings;

public class StringSetting extends Setting {
    private String value;

    public StringSetting(String label, String[] aliases, String value) {
        super(label, aliases);
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value.replaceAll("\"", "");
    }

    public String getValue() {
        return value;
    }
}
