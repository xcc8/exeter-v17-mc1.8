package wtf.nuf.exeter.core;

import wtf.nuf.exeter.mcapi.manager.SetManager;
import wtf.nuf.exeter.mcapi.settings.Setting;
import wtf.nuf.exeter.mcapi.settings.StringSetting;
import wtf.nuf.exeter.mcapi.settings.ToggleableSetting;

/**
 * i liked the idea of vape where enabling players would enable them globally for all mods, so yea
 */
public final class ExeterSettings extends SetManager<Setting> {//TODO maybe ttf setting (?)
    public final ToggleableSetting attackPlayers = new ToggleableSetting("Players",
            new String[]{"players", "player"}, true);
    public final ToggleableSetting attackPassives = new ToggleableSetting("Passives",
            new String[]{"passives", "passive"}, true);
    public final ToggleableSetting attackHostiles = new ToggleableSetting("Hostiles",
            new String[]{"hostiles", "hostile"}, true);
    public final ToggleableSetting attackInvisibles = new ToggleableSetting("Invisibles",
            new String[]{"invisibles", "invisible"}, true);
    public final ToggleableSetting protectTeam = new ToggleableSetting("Team",
            new String[]{"team", "t"}, false);

    public final StringSetting commandPrefix = new StringSetting("Prefix",
            new String[]{"prefix", "pref", "p"}, ".");

    /**
     * just add settings here so when the instance is set in the main class they're registered
     */
    public ExeterSettings() {
        add(attackPlayers,
                attackPassives,
                attackHostiles,
                attackInvisibles,
                protectTeam,
                commandPrefix);
    }

    /**
     * why not?
     *
     * @param settings
     */
    private void add(Setting... settings) {
        for (Setting setting : settings) {
            register(setting);
        }
    }
}
