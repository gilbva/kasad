package me.gilbva.kasad.storage.simple;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.serializer.GroupSerializer;

import java.util.Collection;
import java.util.Map;

public class ElementsPropsStorage {
    private final DB db;

    private final BTreeMap<int[], Object> elementsProps;

    public ElementsPropsStorage(String name, DB db) {
        this.db = db;
        elementsProps = this.db.treeMap(name + "ElementsProps", GroupSerializer.INT_ARRAY, GroupSerializer.JAVA).createOrOpen();
    }

    public void put(int element, int property, Object value) {
        elementsProps.put(new int[] { element, property }, value);
    }

    public Map<int[], Object> getProperties(int element) {
        return elementsProps.prefixSubMap(new int[] {element});
    }

    public Object getProperty(int element, int prop) {
        return elementsProps.get(new int[]{ element, prop });
    }
}
