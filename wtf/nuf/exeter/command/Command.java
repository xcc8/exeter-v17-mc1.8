package wtf.nuf.exeter.command;

import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.printer.Printer;

import java.util.StringJoiner;

public abstract class Command {
    protected final Exeter exeter = Exeter.getInstance();

    private final String[] aliases;
    private final Argument[] arguments;

    public Command(Argument... arguments) {
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

    public abstract String execute();

    public String handleArguments(String[] input) {
        Argument[] arguments = getArguments();
        boolean validArguments = true;

        if (arguments.length != input.length - 1) {
            return getSyntax(input[0]);
        }

        if (arguments.length > 0) {
            for (int index = 0; index < arguments.length; index++) {
                Argument argument = arguments[index];
                argument.setPresent(index < input.length);
                argument.setValue(input[index + 1]);
                validArguments = argument.isPresent();
            }
        }
        return validArguments ? execute() : getSyntax(input[0]);
    }

    private String getSyntax(String start) {
        StringJoiner argumentJoiner = new StringJoiner(" ");
        for (Argument argument : arguments) {
            argumentJoiner.add(argument.getLabel());
        }
        return String.format("%s %s", start, argumentJoiner.toString());
    }
}
