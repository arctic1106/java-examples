package dev.arcticsoft.examples;

public record Animal(Species species, Gender gender) {

    public boolean isPotentialMate(final Animal other) {
        return this.species == other.species && this.gender != other.gender;
    }
}
