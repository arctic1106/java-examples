import java.util.Comparator;

import models.Person;

public class PlayWithComparators {

	public static void main(String[] args) {
		var mary = new Person("Mary", 28);
		var john = new Person("John", 22);
		var linda = new Person("Linda", 26);
		var james = new Person("James", 32);
		var jamesBis = new Person("James", 26);
		var cmp = Comparator
				.comparing(Person::getName)
				.thenComparing(Person::getAge);

		System.out.println("Mary > John : " + (cmp.compare(mary, john) > 0));
		System.out.println("John > James : " + (cmp.compare(john, james) > 0));
		System.out.println("Linda > John : " + (cmp.compare(linda, john) > 0));
		System.out.println("James > James Bis : " + (cmp.compare(james, jamesBis) > 0));
	}
}