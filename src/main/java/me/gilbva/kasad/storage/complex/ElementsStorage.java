package me.gilbva.kasad.storage.complex;

import me.gilbva.kasad.storage.simple.ElementsLabelsStorage;
import me.gilbva.kasad.storage.simple.ElementsPropsStorage;
import me.gilbva.kasad.storage.simple.KeysStorage;
import me.gilbva.kasad.storage.simple.NamesStorage;
import org.mapdb.DB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ElementsStorage {
    private final DB db;

    private KeysStorage idsSt;

    private NamesStorage labelsSt;

    private NamesStorage propsSt;

    private ElementsLabelsStorage elLabelsSt;

    private ElementsPropsStorage elPropsSt;

    public ElementsStorage(String name, DB db, NamesStorage labelsSt, NamesStorage propsSt) {
        this.db = db;
        this.idsSt = new KeysStorage(name, db);
        this.labelsSt = labelsSt;
        this.propsSt = propsSt;
        this.elLabelsSt = new ElementsLabelsStorage(name, db);
        this.elPropsSt = new ElementsPropsStorage(name, db);
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
                        .map(labelKey -> labelsSt.getName(labelKey))
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

    public boolean hasLabel(int elementKey, String label) {
        Integer labelKey = labelsSt.getKey(label);
        if(labelKey == null) return false;
        return elLabelsSt.exists(elementKey, labelKey);
    }

    public Map<String, Object> getProperties(int elementKey) {
        Map<int[], Object> properties = elPropsSt.getProperties(elementKey);
        Map<String, Object> result = new HashMap<>();
        properties.forEach((k, v) -> result.put(propsSt.getName(k[1]), v));
        return result;
    }

    public Object getProperty(int elementKey, String property) {
        Integer propKey = propsSt.getKey(property);
        if(propKey == null) return null;
        return elPropsSt.getProperty(elementKey, propKey);
    }

    public int setProperty(int elementKey, String name, Object value) {
        int propKey = propsSt.getOrCreate(name);
        elPropsSt.put(elementKey, propKey, value);
        return propKey;
    }
}
