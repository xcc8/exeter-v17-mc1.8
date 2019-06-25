package wtf.nuf.exeter.task;

import wtf.nuf.exeter.mcapi.manager.SetManager;

public final class TaskManager extends SetManager<Task> {
    public Task getTask(String label) {
        for (Task task : getSet()) {
            if (label.equalsIgnoreCase(task.getLabel())) {
                return task;
            }
        }
        return null;
    }
}
