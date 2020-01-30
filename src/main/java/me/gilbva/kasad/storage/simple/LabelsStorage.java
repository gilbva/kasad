package me.gilbva.kasad.storage.simple;

import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class LabelsStorage {
    private final DB db;

    private final Atomic.Integer counter;

    private final HTreeMap<Integer, String> idsToLabels;

    private final HTreeMap<String, Integer> labelsToIds;

    public LabelsStorage(String name, DB db) {
        this.db = db;
        counter = this.db.atomicInteger(name + "Counter", 0).createOrOpen();
        idsToLabels = this.db.hashMap(name + "IdsToLabels", Serializer.INTEGER, Serializer.STRING).createOrOpen();
        labelsToIds = this.db.hashMap(name + "LabelsToIds", Serializer.STRING, Serializer.INTEGER).createOrOpen();
    }

    public int getOrCreate(String label) {
        var id = labelsToIds.get(label);
        if(id == null) {
            id = counter.addAndGet(1);
            idsToLabels.put(id, label);
            labelsToIds.put(label, id);
        }
        return id;
    }

    public String getLabel(int id) {
        return idsToLabels.get(id);
    }

    public Integer getLabelKey(String label) {
        return labelsToIds.get(label);
    }
}
