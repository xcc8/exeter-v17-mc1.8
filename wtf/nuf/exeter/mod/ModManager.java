package wtf.nuf.exeter.mod;

import wtf.nuf.exeter.mcapi.manager.ListManager;
import wtf.nuf.exeter.mod.impl.Statics;
import wtf.nuf.exeter.mod.impl.combat.KillAura;
import wtf.nuf.exeter.mod.impl.miscellaneous.NoFall;
import wtf.nuf.exeter.mod.impl.movement.Fly;
import wtf.nuf.exeter.mod.impl.movement.Speed;

import java.util.Comparator;

public final class ModManager extends ListManager<Mod> {
    public ModManager() {
        register(new Speed());
        register(new KillAura());
        register(new Fly());
        register(new NoFall());
        register(new Statics());

        // java 8 SORTING
        getList().sort(Comparator.comparing(Mod::getLabel));
    }

    public Mod getMod(String label) {
        for (Mod mod : getList()) {
            for (String alias : mod.getAliases()) {
                if (alias.equalsIgnoreCase(label)) {
                    return mod;
                }
            }
        }
        return null;
    }
}
