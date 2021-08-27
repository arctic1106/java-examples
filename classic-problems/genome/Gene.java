package genome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Gene {

    private final ArrayList<Codon> codons = new ArrayList<>();

    public Gene(String geneStr) {
        for (int i = 0; i < geneStr.length() - 3; i += 3) {
            // Take every 3 characters in the String and form a Codon
            codons.add(new Codon(geneStr.substring(i, i + 3)));
        }
    }

    public static void main(String[] args) {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT";
        Gene myGene = new Gene(geneStr);
        Codon acg = new Codon("ACG");
        Codon gat = new Codon("GAT");
        System.out.println(myGene.linearContains(acg)); // true
        System.out.println(myGene.linearContains(gat)); // false
        System.out.println(myGene.binaryContains(acg)); // true
        System.out.println(myGene.binaryContains(gat)); // false

    }

    public boolean linearContains(Codon key) {
        for (Codon codon : codons) {
            if (codon.compareTo(key) == 0) {
                return true; // found a match
            }
        }
        return false;
    }

    public boolean binaryContains(Codon key) {
        // binary search only works on sorted collections
        ArrayList<Codon> sortedCodons = new ArrayList<>(codons);
        Collections.sort(sortedCodons);
        int low = 0;
        int high = sortedCodons.size() - 1;
        while (low <= high) { // while there is still a search space
            int middle = (low + high) / 2;
            int comparison = sortedCodons.get(middle).compareTo(key);
            if (comparison < 0) { // middle codon is less than key
                low = middle + 1;
            } else if (comparison > 0) { // middle codon is greater than key
                high = middle - 1;
            } else { // middle codon is equal to key
                return true;
            }
        }
        return false;
    }

    public enum Nucleotide {
        A, C, G, T
    }

    public static class Codon implements Comparable<Codon> {
        public final Nucleotide first, second, third;
        private final Comparator<Codon> comparator = Comparator.comparing((Codon c) -> c.first)
                .thenComparing((Codon c) -> c.second)
                .thenComparing((Codon c) -> c.third);

        public Codon(String codonStr) {
            first = Nucleotide.valueOf(codonStr.substring(0, 1));
            second = Nucleotide.valueOf(codonStr.substring(1, 2));
            third = Nucleotide.valueOf(codonStr.substring(2, 3));
        }

        @Override
        public int compareTo(Codon other) {
            // first is compared first, then second, etc.
            // IOW first takes precedence over second and second over third
            return comparator.compare(this, other);
        }
    }
}