package models;

import java.awt.*;

public final class Circle extends Shape {
    private final Color color;

    public Circle() {
        this(Color.WHITE);
    }

    public Circle(Color color) {
        super();
        this.color = color;
    }

    @Override
    public String toString() {
        return "Circle [color=" + color + "]";
    }
}
