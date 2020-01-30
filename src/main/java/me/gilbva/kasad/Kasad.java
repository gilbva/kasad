package me.gilbva.kasad;

import me.gilbva.kasad.storage.complex.ElementsStorage;
import me.gilbva.kasad.storage.simple.NamesStorage;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Kasad {

    private final DB db;

    private NamesStorage labels;

    private NamesStorage props;

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
        labels = new NamesStorage("labels", db);
        props = new NamesStorage("props", db);
        nodes = new ElementsStorage("nodes", db, labels, props);
        edges = new ElementsStorage("edges", db, labels, props);
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
