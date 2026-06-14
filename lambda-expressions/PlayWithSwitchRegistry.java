import interfaces.SwitchRegistry;
import models.Circle;
import models.Rectangle;

public class PlayWithSwitchRegistry {

	public static void main(String[] args) {
		var registry = new SwitchRegistry();
		var rectangleFactory = registry.buildShapeFactory(new Rectangle());
		var circleFactory = registry.buildShapeFactory(new Circle());
		System.out.println("Rectangle: " + rectangleFactory.newInstance());
		System.out.println("Circle: " + circleFactory.newInstance());
		
		var c1 = circleFactory.newInstance();
		var c2 = circleFactory.newInstance();
		System.out.println(c1 == c2);
	}
}