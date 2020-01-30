package me.gilbva.kasad;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.util.concurrent.ConcurrentMap;

public class Main {

    public static void main(String[] args) {
        var kasad = new Kasad();
        kasad.addNode("good", "simple");
        kasad.addNode("good");
        kasad.addNode("bad");
        kasad.addNode("bad", "complex");

        kasad.getNodes().forEach(n -> {
            System.out.print(n.getKey());
            System.out.print(" - ");
            System.out.println(n.getLabels());
        });
    }
}
