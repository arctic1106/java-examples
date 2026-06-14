void main() {
    Shape shape = (int side) -> {
        return side * side;
    };
    println("Area: " + shape.getArea(5));
}

@FunctionalInterface
interface Shape {

    int getArea(int side);
}
