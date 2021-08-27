package concurrency.time;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class ImmutableListIterator<T> implements Iterator<T> {

	private ImmutableList<T> current;

	ImmutableListIterator(ImmutableList<T> l) {
		current = l;
	}

	public boolean hasNext() {
		return current != null;
	}

	public T next() {
		if (current != null) {
			T o = current.item;
			current = current.next;
			return o;
		}
		throw new NoSuchElementException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
