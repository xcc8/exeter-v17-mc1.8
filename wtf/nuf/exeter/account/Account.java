package wtf.nuf.exeter.account;

import wtf.nuf.exeter.mcapi.interfaces.Labeled;
import wtf.nuf.exeter.printer.Printer;

public class Account implements Labeled {
    private String label, password;

    public Account(String label, String password) {
        this.label = label;
        this.password = password;
        Printer.getPrinter().print(String.format("Registered new account \"%s\".", label));
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
