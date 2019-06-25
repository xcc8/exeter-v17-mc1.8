package wtf.nuf.exeter.mcapi.manager;

import wtf.nuf.exeter.core.Exeter;

import java.util.HashMap;
import java.util.Map;

public class MapManager<V, K> {
    protected final Exeter exeter = Exeter.getInstance();

    private final Map<K, V> map;

    public MapManager() {
        map = new HashMap<>();
    }

    public void register(K key, V value) {
        map.put(key, value);
    }

    public void unregister(K key) {
        map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public Map<K, V> getMap() {
        return map;
    }
}
