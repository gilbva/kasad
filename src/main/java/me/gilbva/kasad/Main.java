package me.gilbva.kasad;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.util.concurrent.ConcurrentMap;

public class Main {

    public static void main(String[] args) {
        var kasad = new Kasad();
        kasad.addNode("good", "simple")
                .set("name", "node1")
                .set("age", 32);
        kasad.addNode("good")
                .set("name", "node2");
        kasad.addNode("bad")
                .set("name", "node3");
        kasad.addNode("bad", "complex")
                .set("name", "node4");

        kasad.getNodes().forEach(n -> {
            System.out.print(n.getKey());
            System.out.print(" - ");
            System.out.print(n.getLabels());
            System.out.print(" - ");
            System.out.println(n.getProps());
        });
    }
}
