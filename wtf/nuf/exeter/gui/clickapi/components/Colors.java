package wtf.nuf.exeter.gui.clickapi.components;

/**
 * handles the colors for the clickgui theme, instead of hardcoding them
 */
public enum Colors {
    BUTTON(0xAAaaaaa9),
    BUTTON_ENABLED(0xAAffbd23),
    BUTTON_HOVER(0xAAc4c4c4),
    BUTTON_ENABLED_HOVER(0xAAeaab19),
    BUTTON_LABEL(0xFFFFFFFF),
    BUTTON_LABEL_ENABLED(0xFFDDDDDD),
    BUTTON_LABEL_HOVER(0xFFCCCCCC),
    PANEL_INSIDE(0x88000000),
    PANEL_BORDER(0x55000000),
    PANEL_LABEL(0xFFFFFFFF);

    private final int color;

    Colors(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
