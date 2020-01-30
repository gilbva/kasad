package me.gilbva.kasad.storage.complex;

import me.gilbva.kasad.storage.simple.ElementsLabelsStorage;
import me.gilbva.kasad.storage.simple.KeysStorage;
import me.gilbva.kasad.storage.simple.LabelsStorage;
import org.mapdb.DB;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ElementsStorage {
    private final DB db;

    private KeysStorage idsSt;

    private LabelsStorage labelsSt;

    private ElementsLabelsStorage elLabelsSt;

    public ElementsStorage(String name, DB db, LabelsStorage labelsSt) {
        this.db = db;
        this.idsSt = new KeysStorage(name, db);
        this.labelsSt = labelsSt;
        this.elLabelsSt = new ElementsLabelsStorage(name, db);
    }

    public int count() {
        return idsSt.count();
    }

    public int create(String... labels) {
        int elementKey = idsSt.create();
        for (String label : labels) {
            addLabel(elementKey, label);
        }
        return elementKey;
    }

    public int addLabel(int elementKey, String label) {
        int labelKey = labelsSt.getOrCreate(label);
        elLabelsSt.put(elementKey, labelKey);
        return labelKey;
    }

    public Set<String> getLabels(int element) {
        return elLabelsSt.getLabels(element)
                        .stream()
                        .map(labelKey -> labelsSt.getLabel(labelKey))
                        .collect(Collectors.toSet());
    }

    public List<Integer> getElements() {
        return idsSt.getElements()
                .stream()
                .collect(Collectors.toList());
    }

    public List<Integer> getElements(int label) {
        return elLabelsSt.getElements(label)
                .stream()
                .collect(Collectors.toList());
    }

    public boolean hasLabel(int element, String label) {
        Integer labelKey = labelsSt.getLabelKey(label);
        if(labelKey == null) return false;
        return elLabelsSt.exists(element, labelKey);
    }
}
