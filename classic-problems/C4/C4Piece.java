package C4;

public enum C4Piece implements Piece {
    B, R, E; // E is Empty

    @Override
    public C4Piece opposite() {
        return switch (this) {
            case B -> C4Piece.R;
            case R -> C4Piece.B;
            default -> // E, empty
                    C4Piece.E;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case B -> "B";
            case R -> "R";
            default -> // E, empty
                    " ";
        };

    }
}