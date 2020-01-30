package me.gilbva.kasad;

import me.gilbva.kasad.storage.complex.ElementsStorage;

import java.util.Set;

public class KElement {
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
}
