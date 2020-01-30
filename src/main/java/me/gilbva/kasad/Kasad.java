package me.gilbva.kasad;

import me.gilbva.kasad.storage.complex.ElementsStorage;
import me.gilbva.kasad.storage.simple.LabelsStorage;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Kasad {

    private final DB db;

    private LabelsStorage labels;

    private ElementsStorage nodes;

    private ElementsStorage edges;

    public Kasad() {
        db = DBMaker
                .memoryDB()
                .make();
        init();
    }

    public Kasad(File file) {
        db = DBMaker
                .fileDB(file)
                .make();
        init();
    }

    void init() {
        labels = new LabelsStorage("labels", db);
        nodes = new ElementsStorage("nodes", db, labels);
        edges = new ElementsStorage("edges", db, labels);
    }

    public KNode addNode(String... labels) {
        int key = nodes.create(labels);
        return new KNode(key, nodes);
    }

    public List<KNode> getNodes() {
        return nodes.getElements()
                    .stream()
                    .map(key -> new KNode(key, nodes))
                    .collect(Collectors.toList());
    }
}
