sealed abstract class Building permits House, Office, WareHouse {
	protected int numberOfRooms = 10;

	protected int numberOfRooms() {
		return numberOfRooms;
	}
}

final class House extends Building {

	public House () {
		numberOfRooms = 5; 
	}
}

final class Office extends Building {
}

final class WareHouse extends Building {
}

static String getBuildingType(Building building) {
	return switch (building) {
		case House _ -> "House" ;
		case Office _ -> "Office" ;
		case WareHouse _ -> "Warehouse" ;
	};
}

static void printBuildings(List<? extends Building> buildings) {
	println("Contents of the buildings list: ");
	buildings.forEach(building -> println(getBuildingType(building) + " Rooms: " + building.numberOfRooms()));
}

void main() {
	printBuildings(
		List.of(
			new Office(),
			new House(),
			new WareHouse(),
			new Office()
		)
	);
}