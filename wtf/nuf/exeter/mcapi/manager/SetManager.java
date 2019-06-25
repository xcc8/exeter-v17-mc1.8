package wtf.nuf.exeter.mcapi.manager;

import wtf.nuf.exeter.core.Exeter;

import java.util.HashSet;
import java.util.Set;

public class SetManager<T> implements Manager<T> {
    protected final Exeter exeter = Exeter.getInstance();

    private final Set<T> set;

    public SetManager() {
        this.set = new HashSet<>();
    }

    @Override
    public void register(T object) {
        set.add(object);
    }

    @Override
    public void unregister(T object) {
        set.remove(object);
    }

    @Override
    public int size() {
        return set.size();
    }

    public Set<T> getSet() {
        return set;
    }
}
