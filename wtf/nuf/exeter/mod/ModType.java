package wtf.nuf.exeter.mod;

public enum ModType {
    COMBAT("Combat"),
    EXPLOITS("Exploits"),
    MOVEMENT("Movement"),
    MISCELLANEOUS("Miscellaneous"),
    RENDER("Render");

    private final String label;

    ModType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
