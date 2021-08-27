package interfaces;

import models.*;

public class SwitchRegistry {

    public Factory<Shape> buildShapeFactory(Shape shape) {
        return switch (shape) {
            case Square s -> () -> new Square();
            case Triangle t -> Triangle::new;
            case Rectangle r -> Rectangle::new;
            case Circle c -> Circle::new;
        };
    }
}
