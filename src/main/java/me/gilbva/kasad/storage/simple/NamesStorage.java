package me.gilbva.kasad.storage.simple;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class NamesStorage {
    private final DB db;

    private final Atomic.Integer counter;

    private final HTreeMap<Integer, String> keysToNames;

    private final HTreeMap<String, Integer> namesToKeys;

    public NamesStorage(String name, DB db) {
        this.db = db;
        counter = this.db.atomicInteger(name + "Counter", 0).createOrOpen();
        keysToNames = this.db.hashMap(name + "KeysToNames", Serializer.INTEGER, Serializer.STRING).createOrOpen();
        namesToKeys = this.db.hashMap(name + "NamesToKeys", Serializer.STRING, Serializer.INTEGER).createOrOpen();
    }

    public int getOrCreate(String name) {
        var id = namesToKeys.get(name);
        if(id == null) {
            id = counter.addAndGet(1);
            keysToNames.put(id, name);
            namesToKeys.put(name, id);
        }
        return id;
    }

    public String getName(int id) {
        return keysToNames.get(id);
    }

    public Integer getKey(String name) {
        return namesToKeys.get(name);
    }
}
