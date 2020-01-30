package me.gilbva.kasad;

import me.gilbva.kasad.storage.complex.ElementsStorage;

import java.util.Map;
import java.util.Set;

public class KElement<T extends KElement<T>> {
    private int key;

    private ElementsStorage storage;

    public KElement(int key, ElementsStorage storage) {
        this.key = key;
        this.storage = storage;
    }

    public int getKey() {
        return key;
    }

    public Set<String> getLabels() {
        return storage.getLabels(key);
    }

    public boolean hasLabel(String label) {
        return storage.hasLabel(key, label);
    }

    public Map<String, Object> getProps() {
        return storage.getProperties(key);
    }

    public Object get(String prop) {
        return storage.getProperty(key, prop);
    }

    public T set(String prop, Object value) {
        storage.setProperty(key, prop, value);
        return (T)this;
    }
}
