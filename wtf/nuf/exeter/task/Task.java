package wtf.nuf.exeter.task;

import wtf.nuf.exeter.mcapi.interfaces.Labeled;
import wtf.nuf.exeter.core.Exeter;
import wtf.nuf.exeter.printer.Printer;

public class Task implements Labeled {
    private final String label;
    private boolean running = true;

    public Task(String label) {
        this.label = label;
        Exeter.getInstance().getTaskManager().register(this);
        Printer.getPrinter().print(String.format("Registered new task \"%s\".", label));
    }

    @Override
    public String getLabel() {
        return label;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
