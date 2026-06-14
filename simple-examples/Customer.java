class Scheduler {
	private final Map<String, List<String>> hairdresserAvailability;

	public Scheduler(Map<String, List<String>> initialAvailability) {
		hairdresserAvailability = initialAvailability;
	}

	void bookHaircut(String hairdresser, String day) {
		var availableDays = hairdresserAvailability.get(hairdresser);

		if (availableDays == null) {
			println("Hairdresser " + hairdresser + " not found.");
			return;
		}

		if (availableDays.contains(day)) {
			println("You have booked " + hairdresser + " on " + day + ".");
			availableDays.remove(day);
		} else println(hairdresser + " is not available on " + day + ".");
	}
}

void main() {
	var scheduler = new Scheduler(
		Map.<String, List<String>>of(
			"Harry", new ArrayList<>(List.of("Monday", "Tuesday")),
			"Jill", new ArrayList<>(List.of("Wednesday"))
		)
	);
	scheduler.bookHaircut("Harry", "Monday");
	scheduler.bookHaircut("Jill", "Friday");
	scheduler.bookHaircut("Harry", "Tuesday");
	scheduler.bookHaircut("Harry", "Wednesday");
}