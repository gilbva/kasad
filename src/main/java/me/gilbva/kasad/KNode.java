package me.gilbva.kasad;

import me.gilbva.kasad.storage.complex.ElementsStorage;

public class KNode extends KElement<KNode> {
    public KNode(int key, ElementsStorage storage) {
        super(key, storage);
    }
}
