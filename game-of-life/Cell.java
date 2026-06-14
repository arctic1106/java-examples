import java.util.List;

public class Cell {
    private final Channel<Boolean> tickChannel;
    private final Channel<Boolean> resultChannel;
    private final List<Channel<Boolean>> inChannels;
    private final List<Channel<Boolean>> outChannels;
    private boolean alive;

    Cell(CellOptions options) {
        this.alive = options.alive();
        this.tickChannel = options.tickChannel();
        this.resultChannel = options.resultChannel();
        this.inChannels = options.inChannels();
        this.outChannels = options.outChannels();
    }

    private static boolean nextState(boolean alive, int neighbors) {
        if (alive) {
            return neighbors == 2 || neighbors == 3;
        }
        return neighbors == 3;
    }

    void start() {
        Thread.startVirtualThread(this::run);
    }

    private void run() {
        while (true) {
            tickChannel.take(); // wait for tick stimulus
            outChannels.forEach(ch -> ch.put(alive)); // announce liveness to neighbors
            int neighbors = inChannels.stream().map(Channel::take).mapToInt(b -> b ? 1 : 0).sum(); // receive liveness
                                                                                                   // from neighbors
            alive = nextState(alive, neighbors); // calculate next state based on game of life rules
            resultChannel.put(alive); // announce resulting next state
        }
    }
}
