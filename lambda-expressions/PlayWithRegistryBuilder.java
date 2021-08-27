import interfaces.Factory;
import interfaces.Builder;
import interfaces.Registry;
import models.Rectangle;
import models.Shape;
import models.Triangle;

import java.util.function.Consumer;

public class PlayWithRegistryBuilder {

	public static void main(String[] args) {
		Consumer<Builder<Shape>> consumer1 = builder -> builder.register("rectangle", Rectangle::new);
		Consumer<Builder<Shape>> consumer2 = builder -> builder.register("triangle", Triangle::new);
		Consumer<Builder<Shape>> initializer = consumer1.andThen(consumer2);
		
		var registry = Registry.createRegistry(
				initializer, s -> { throw new IllegalArgumentException("Unknown shape: " + s); } 
		);
		
		var buildRectangleFactory = (Factory<Rectangle>) registry.buildShapeFactory("rectangle");
		var rectangle = buildRectangleFactory.newInstance();
		System.out.println("Rectangle = " + rectangle);
		
		var buildTriangleFactory = (Factory<Triangle>) registry.buildShapeFactory("triangle");
		System.out.println("Triangle = " + buildTriangleFactory.newInstance());
	}
}