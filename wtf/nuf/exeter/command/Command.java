package wtf.nuf.exeter.command;

import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.printer.Printer;

import java.util.StringJoiner;

public abstract class Command {
    // instances of the clients main class are in multiple daddy classes
    protected final Exeter exeter = Exeter.getInstance();

    private final String[] aliases;
    private final Argument[] arguments;

    public Command(Argument... arguments) {
        // annotations >_>
        this.aliases = this.getClass().getAnnotation(CommandValues.class).aliases();
        this.arguments = arguments;
        Printer.getPrinter().print(String.format("Registered new command \"%s\".", aliases[0]));
    }

    public String[] getAliases() {
        return aliases;
    }

    public Argument[] getArguments() {
        return arguments;
    }

    protected Argument getArgument(String label) {
        for (Argument argument : arguments) {
            if (label.equalsIgnoreCase(argument.getLabel())) {
                return argument;
            }
        }
        return null;
    }

    /**
     * this is where we'll be handle all the command shit, like what the command does
     * @return a message to return to the user if all arguments are present
     */
    public abstract String execute();

    public String handleArguments(String[] input) {
        Argument[] arguments = getArguments();
        boolean validArguments = true;

        // make sure we have the right amount of arguments right off the bat
        if (arguments.length != input.length - 1) {
            // if not return the proper syntax for this command
            return getSyntax(input[0]);
        }

        // if there are no arguments for this command we don't want to run
        // through this, so we make sure the command requires arguments
        if (arguments.length > 0) {
            // for loop to go through the commands
            for (int index = 0; index < arguments.length; index++) {
                // the argument
                Argument argument = arguments[index];
                // whether or not it is present, we do this by checking the index of the command
                // compared to the length of the user given input
                argument.setPresent(index < input.length);
                // we set the value of the argument which is the input given
                argument.setValue(input[index + 1]);
                // and finally whether or not it is present
                validArguments = argument.isPresent();
            }
        }
        // if the arguments are valid then run the command else just return the syntax
        return validArguments ? execute() : getSyntax(input[0]);
    }

    /***
     * kinda ugly but you get the point, loop through the commands required
     * arguments and create a 'syntax' per say
     * @param start
     * @return
     */
    private String getSyntax(String start) {
        StringJoiner argumentJoiner = new StringJoiner(" ");
        // loop through the arguments and add them to the stringjoiner
        for (Argument argument : arguments) {
            argumentJoiner.add(argument.getLabel());
        }
        return String.format("%s %s", start, argumentJoiner.toString());
    }
}
