package me.gilbva.kasad.storage.simple;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.Collection;
import java.util.NavigableSet;

public class KeysStorage {
    private final DB db;

    private final Atomic.Integer counter;

    private final NavigableSet<Integer> keys;

    public KeysStorage(String name, DB db) {
        this.db = db;
        counter = this.db.atomicInteger(name + "Counter", 0).createOrOpen();
        keys = this.db.treeSet(name + "Keys", Serializer.INTEGER).createOrOpen();
    }

    public int create() {
        var result = counter.addAndGet(1);
        keys.add(result);
        return result;
    }

    public int count() {
        return keys.size();
    }

    public Collection<Integer> getElements() {
        return keys;
    }
}
