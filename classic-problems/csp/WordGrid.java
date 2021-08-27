package csp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordGrid {
    private final int rows, columns;
    private final char[][] grid;

    public WordGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new char[rows][columns];
        // initialize grid with random letters
        Random random = new Random();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                char ALPHABET_LENGTH = 26;
                char FIRST_LETTER = 'A';
                char randomLetter = (char) (random.nextInt(ALPHABET_LENGTH) + FIRST_LETTER);
                grid[row][column] = randomLetter;
            }
        }
    }

    public void mark(String word, List<GridLocation> locations) {
        for (int i = 0; i < word.length(); i++) {
            GridLocation location = locations.get(i);
            grid[location.row()][location.column()] = word.charAt(i);
        }
    }

    // get a pretty printed version of the grid
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] rowArray : grid) {
            sb.append(rowArray);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public List<List<GridLocation>> generateDomain(String word) {
        List<List<GridLocation>> domain = new ArrayList<>();
        int length = word.length();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (column + length <= columns) {
                    // left to right
                    fillRight(domain, row, column, length);
                    // diagonal towards bottom right
                    if (row + length <= rows) {
                        fillDiagonalRight(domain, row, column, length);
                    }
                }
                if (row + length <= rows) {
                    // top to bottom
                    fillDown(domain, row, column, length);
                    // diagonal towards bottom left
                    if (column - length >= 0) {
                        fillDiagonalLeft(domain, row, column, length);
                    }
                }
            }
        }
        return domain;
    }

    private void fillRight(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int c = column; c < (column + length); c++) {
            locations.add(new GridLocation(row, c));
        }
        domain.add(locations);
    }

    private void fillDiagonalRight(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        int r = row;
        for (int c = column; c < (column + length); c++) {
            locations.add(new GridLocation(r, c));
            r++;
        }
        domain.add(locations);
    }

    private void fillDown(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int r = row; r < (row + length); r++) {
            locations.add(new GridLocation(r, column));
        }
        domain.add(locations);
    }

    private void fillDiagonalLeft(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        int c = column;
        for (int r = row; r < (row + length); r++) {
            locations.add(new GridLocation(r, c));
            c--;
        }
        domain.add(locations);
    }
}