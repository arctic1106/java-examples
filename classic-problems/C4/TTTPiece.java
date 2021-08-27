package C4;

public enum TTTPiece implements Piece {
    X, O, E; // E is Empty

    @Override
    public TTTPiece opposite() {
        return switch (this) {
            case X -> TTTPiece.O;
            case O -> TTTPiece.X;
            default -> // E, empty
                    TTTPiece.E;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case X -> "X";
            case O -> "O";
            default -> // E, empty
                    " ";
        };

    }
}