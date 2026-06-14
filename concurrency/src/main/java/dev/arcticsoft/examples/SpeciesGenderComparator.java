package dev.arcticsoft.examples;

import java.util.Comparator;

public class SpeciesGenderComparator implements Comparator<Animal> {

    @Override
    public int compare(final Animal one, final Animal two) {
        final int speciesCompare = one.species().compareTo(two.species());
        return (speciesCompare != 0) ? speciesCompare : one.gender().compareTo(two.gender());
    }
}
