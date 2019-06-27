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
        // create a local mod instance using the getMod method from the mod manager
        // we use the argument for the mod name
        Mod mod = exeter.getModManager().getMod(getArgument("<mod>").getValue());
        // if the mod doesn't exist or it is not toggleable then inform the user
        if (mod == null || !(mod instanceof Toggleable)) {
            return "Mod does not exist or is not toggleable.";
        }
        // create a toggleable mod
        ToggleableMod toggleableMod = (ToggleableMod) mod;
        // toggle the mod
        toggleableMod.toggle();
        // send the user an informative message about the state of the mod
        return String.format("%s toggled %s%s%s.", toggleableMod.getLabel(),
                toggleableMod.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED,
                toggleableMod.isEnabled() ? "on" : "off", EnumChatFormatting.GRAY);
    }
}
