package wtf.nuf.exeter.command.impl.client;

import net.minecraft.util.EnumChatFormatting;
import wtf.nuf.exeter.command.Argument;
import wtf.nuf.exeter.command.Command;
import wtf.nuf.exeter.command.CommandValues;
import wtf.nuf.exeter.mcapi.interfaces.Toggleable;
import wtf.nuf.exeter.mod.Mod;
import wtf.nuf.exeter.mod.ToggleableMod;

@CommandValues(aliases = {"toggle", "t"})
public final class Toggle extends Command {
    public Toggle() {
        super(new Argument("<mod>"));
    }

    @Override
    public String execute() {
        Mod mod = exeter.getModManager().getMod(getArgument("<mod>").getValue());
        if (mod == null || !(mod instanceof Toggleable)) {
            return "Mod does not exist or is not toggleable.";
        }
        ToggleableMod toggleableMod = (ToggleableMod) mod;
        toggleableMod.toggle();
        return String.format("%s toggled %s%s%s.", toggleableMod.getLabel(),
                toggleableMod.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED,
                toggleableMod.isEnabled() ? "on" : "off", EnumChatFormatting.GRAY);
    }
}
