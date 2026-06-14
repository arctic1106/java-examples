import interfaces.Validator;
import models.Person;

public class PlayWithValidator {

	public static void main(String[] args) {
		var sarah = new models.Person("Sarah", 29);
		var sarahValidated = Validator.validate(p -> p.getName() != null, "The name should not be null")
				.on(sarah)
				.validate();
		System.out.println("Sarah : " + sarahValidated);

		var linda = new Person(null, 1_000);
		var lindaValidated = Validator.validate(p -> p.getName() != null, "The name should not be null")
				.thenValidate(p -> p.getAge() > 0, "The age should be greater than 0")
				.thenValidate(p -> p.getAge() < 150, "The age should be lesser than 150")
				.on(linda)
				.validate();
		System.out.println("Linda : " + lindaValidated);
	}
}