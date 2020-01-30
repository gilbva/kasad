package me.gilbva.kasad.storage.simple;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.serializer.GroupSerializer;

import java.util.Collection;
import java.util.Collections;

public class ElementsLabelsStorage {
    private final DB db;

    private final BTreeMap<int[], Integer> elementsToLabels;

    private final BTreeMap<int[], Integer> labelsToElements;

    public ElementsLabelsStorage(String name, DB db) {
        this.db = db;
        elementsToLabels = this.db.treeMap(name + "ElementsToLabels", GroupSerializer.INT_ARRAY, GroupSerializer.INTEGER).createOrOpen();
        labelsToElements = this.db.treeMap(name + "LabelsToElements", GroupSerializer.INT_ARRAY, GroupSerializer.INTEGER).createOrOpen();
    }

    public void put(int element, int label) {
        elementsToLabels.put(new int[] { element, label }, label);
        labelsToElements.put(new int[] { label, element }, element);
    }

    public Collection<Integer> getLabels(int element) {
        return elementsToLabels.prefixSubMap(new int[] {element}).values();
    }

    public Collection<Integer> getElements(int label) {
        return labelsToElements.prefixSubMap(new int[] {label}).values();
    }

    public boolean exists(int element, int label) {
        return labelsToElements.containsKey(new int[] { label, element });
    }
}
