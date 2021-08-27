import interfaces.Factory;
import models.Circle;

import java.awt.*;

public class PlayWithFactory {

	public static void main(String[] args) {
		var factory1 = Factory.createFactory(Circle::new, Color.RED);

		var circle = factory1.newInstance();
		System.out.println("Circle = " + circle);

		var list = factory1.create5();
		System.out.println("List = " + list);
	}
}