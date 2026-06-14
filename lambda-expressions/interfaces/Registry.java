package interfaces;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface Registry<T> {

    Factory<? extends T> buildShapeFactory(String shape);

    static <T> Registry<T> createRegistry(Consumer<Builder<T>> consumer, Function<String, Factory<T>> errorFunction) {
        var map = new HashMap<String, Factory<T>>();
        Builder<T> builder = map::put;
        consumer.accept(builder);
        return shape -> map.computeIfAbsent(shape, errorFunction);
    }
}