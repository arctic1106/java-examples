import java.util.function.Consumer;

public class ConsoleOutput implements Consumer<boolean[][]> {

    @Override
    public void accept(boolean[][] cells) {
        for (boolean[] cell : cells) {
            for (boolean b : cell) {
                if (b) {
                    System.out.print("X ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println("");
        }
    }
}
