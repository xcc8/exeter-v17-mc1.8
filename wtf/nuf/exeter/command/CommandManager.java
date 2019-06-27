package wtf.nuf.exeter.command;

import wtf.nuf.exeter.command.impl.client.Help;
import wtf.nuf.exeter.command.impl.client.Toggle;
import wtf.nuf.exeter.mcapi.manager.ListManager;

public final class CommandManager extends ListManager<Command> {
    /**
     * add the commands to the list here
     */
    public CommandManager() {
        register(new Toggle());
        register(new Help());
    }

    /**
     *
     * @param input given to the client by the user
     * @return if the command is null, we return a message that informs the user if so,
     * otherwise handle the arguments given
     */
    public String execute(String[] input) {
        Command command = getCommand(input[0]);
        if (command == null) {
            return "Command was not found.";
        }
        return command.handleArguments(input);
    }

    public Command getCommand(String label) {
        for (Command command : getList()) {
            for (String alias : command.getAliases()) {
                if (label.equalsIgnoreCase(alias)) {
                    return command;
                }
            }
        }
        return null;
    }
}
