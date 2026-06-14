import interfaces.Visitor;
import models.Body;
import models.Car;
import models.Engine;

public class PlayWithVisitor {

    public static void main(String[] args) {
        var car = new Car();
        var engine = new Engine();
        var body = new Body();
        var consumer = Visitor.<Car, String>forType(Car.class)
                        .execute((Car c) -> "Visiting car: " + c)
                        .forType(Engine.class).execute((Engine e) -> "Visiting engine: " + e)
                        .forType(Body.class).execute((Body b) -> "Visiting body: " + b);
        
						var visitor = Visitor.of(consumer);
        System.out.println("Engine: " + visitor.visit(engine));
        System.out.println("Car: " + visitor.visit(car));
        System.out.println("Body: " + visitor.visit(body));
    }
}