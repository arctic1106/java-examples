package dev.arcticsoft.examples;

import java.util.LinkedList;
import java.util.List;

/**
 * Node
 *
 * @list 8.14
 * @smell Good
 * @author Brian Goetz and Tim Peierls
 *
 * <p>
 * Represents a position that has been reached through some series of moves,
 * holding a reference to the move that created the position and the previous
 * Node.
 */
public class Node<P, M> {

    public final P pos;
    final M move;
    final Node<P, M> prev;

    public Node(P pos, M move, Node<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }
	
    // Get a list of moves to the current position.
    public List<M> asMoveList() {         
        List<M> solution = new LinkedList<>();
        for (Node<P, M> n = this; n.move != null; n = n.prev) {
            solution.add(0, n.move);
        }
        return solution;
    }
}
