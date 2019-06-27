package wtf.nuf.exeter.friend;

import wtf.nuf.exeter.mcapi.manager.MapManager;

/**
 * so simple omgomgomgomg
 */
public final class FriendManager extends MapManager<String, String> {
    public boolean isFriend(String label) {
        return getMap().containsKey(label);
    }
}
