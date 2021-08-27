package interfaces;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FunctionalInterface
public interface Factory<T> extends Supplier<T> {

	static <T> Factory<T> createFactory(Supplier<T> supplier) {
		T singleton = supplier.get();
		return () -> singleton;
	}

	static <T, P> Factory<T> createFactory(Function<P, T> constructor, P color) {
		return () -> constructor.apply(color);
	}

	default T newInstance() {
		return get();
	}

	default List<T> create5() {
		return IntStream.range(0, 5)
				.mapToObj(index -> newInstance())
				.collect(Collectors.toList());
	}
}