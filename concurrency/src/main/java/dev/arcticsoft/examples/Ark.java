package dev.arcticsoft.examples;

import java.util.HashSet;
import java.util.Set;

public class Ark {

    public final Set<AnimalPair> loadedAnimals = new HashSet<>();

    public void load(final AnimalPair pair) {
        this.loadedAnimals.add(pair);
    }
}
