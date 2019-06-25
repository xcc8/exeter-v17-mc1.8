package wtf.nuf.exeter.mcapi.manager;

import wtf.nuf.exeter.core.Exeter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListManager<T> implements Manager<T> {
    protected final Exeter exeter = Exeter.getInstance();

    private final List<T> list;

    protected ListManager() {
        this.list = new CopyOnWriteArrayList<>();
    }

    @Override
    public void register(T object) {
        list.add(object);
    }

    @Override
    public void unregister(T object) {
        list.remove(object);
    }

    @Override
    public int size() {
        return list.size();
    }

    public List<T> getList() {
        return list;
    }
}
