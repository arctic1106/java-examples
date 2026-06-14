package genome;

import java.util.BitSet;

public class CompressedGene {
    private BitSet bitSet;
    private int length;

    public CompressedGene(String gene) {
        compress(gene);
    }

    public static void main(String[] args) {
        final String original = "TAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATATAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATA";
        CompressedGene compressed = new CompressedGene(original);
        final String decompressed = compressed.decompress();
        System.out.println(decompressed);
        System.out.println("original is the same as decompressed: " + original.equalsIgnoreCase(decompressed));
    }

    private void compress(String gene) {
        length = gene.length();
        // reserve enough capacity for all of the bits
        bitSet = new BitSet(length * 2);
        // convert to upper case for consistency
        final String upperGene = gene.toUpperCase();
        // convert String to bit representation
        for (int i = 0; i < length; i++) {
            final int firstLocation = 2 * i;
            final int secondLocation = 2 * i + 1;
            switch (upperGene.charAt(i)) {
                case 'A' -> { // 00 are next two bits
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, false);
                }
                case 'C' -> { // 01 are next two bits
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, true);
                }
                case 'G' -> { // 10 are next two bits
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, false);
                }
                case 'T' -> { // 11 are next two bits
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, true);
                }
                default ->
                        throw new IllegalArgumentException("The provided gene String contains characters other than ACGT");
            }
        }
    }

    public String decompress() {
        if (bitSet == null) {
            return "";
        }
        // create a mutable place for characters with right capacity
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < (length * 2); i += 2) {
            final int firstBit = (bitSet.get(i) ? 1 : 0);
            final int secondBit = (bitSet.get(i + 1) ? 1 : 0);
            final int lastBits = firstBit << 1 | secondBit;
            switch (lastBits) {
                case 0b00 -> // 00 is 'A'
                        builder.append('A');
                case 0b01 -> // 01 is 'C'
                        builder.append('C');
                case 0b10 -> // 10 is 'G'
                        builder.append('G');
                case 0b11 -> // 11 is 'T'
                        builder.append('T');
            }
        }
        return builder.toString();
    }
}