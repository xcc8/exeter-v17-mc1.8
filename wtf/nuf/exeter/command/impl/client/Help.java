package wtf.nuf.exeter.command.impl.client;

import net.minecraft.util.EnumChatFormatting;
import wtf.nuf.exeter.command.Argument;
import wtf.nuf.exeter.command.Command;
import wtf.nuf.exeter.command.CommandValues;
import wtf.nuf.exeter.mcapi.interfaces.Toggleable;
import wtf.nuf.exeter.mod.Mod;
import wtf.nuf.exeter.mod.ToggleableMod;

import java.util.StringJoiner;

/**
 * over-complicated help command
 */
@CommandValues(aliases = {"help", "?"})
public final class Help extends Command {
    public Help() {
        super(new Argument("commands/mods/<command/mod>"));
    }

    @Override
    public String execute() {
        String value = getArgument("commands/mods/<command/mod>").getValue();
        switch (value) {
            case "commands":
            case "command":
            case "c":
            case "cmd":
                StringJoiner commandJoiner = new StringJoiner(", ");
                for (Command command : exeter.getCommandManager().getList()) {
                    commandJoiner.add(command.getAliases()[0]);
                }
                return String.format("Commands (%s) %s.", exeter.getCommandManager().size(), commandJoiner.toString());
            case "mods":
            case "mod":
            case "hacks":
                StringJoiner modJoiner = new StringJoiner(", ");
                for (Mod mod : exeter.getModManager().getList()) {
                    if (mod instanceof Toggleable) {
                        ToggleableMod toggleableMod = (ToggleableMod) mod;
                        modJoiner.add(String.format("%s%s%s",
                                toggleableMod.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED,
                                toggleableMod.getLabel(), EnumChatFormatting.GRAY));
                    } else {
                        modJoiner.add(mod.getLabel());
                    }
                }
                return String.format("Mods (%s) %s.", exeter.getModManager().size(), modJoiner.toString());
            default:
                return handleSpecifiedHelp(value);
        }
    }

    private String handleSpecifiedHelp(String input) {
        Mod mod = exeter.getModManager().getMod(input);
        if (mod != null) {
            return String.format("%s - %s.", mod.getLabel(), mod.getDescription());
        } else {
            Command command = exeter.getCommandManager().getCommand(input);
            if (command != null) {
                StringJoiner argumentJoiner = new StringJoiner(" ");
                for (Argument argument : command.getArguments()) {
                    argumentJoiner.add(argument.getLabel());
                }
                return String.format("%s %s", command.getAliases()[0], argumentJoiner.toString());
            }
        }
        return "Could not find anything.";
    }
}
