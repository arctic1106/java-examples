package concurrency.carpark;

public class DisplayCarPark extends CarParkControl {

	CarParkCanvas disp;
	boolean occupied[];

	public DisplayCarPark(CarParkCanvas disp, int size) {
		super(size);
		this.disp = disp;
		occupied = new boolean[size];
		for (int i = 0; i < size; i++) occupied[i] = false;
	}

	private void display() {
		disp.setvalue(spaces, occupied);
	}

	synchronized public void arrive() throws InterruptedException {
		super.arrive();
		occupied[place(false)] = true;
		display();
		Thread.sleep(400);
	}

	synchronized public void depart() throws InterruptedException {
		super.depart();
		occupied[place(true)] = false;
		display();
	}

	private int place(boolean v) {
		int start = ((int) (Math.random() * 1000)) % capacity;
		for (int i = 0; i < capacity; i++) {
			int j = (start + i) % capacity;
			if (occupied[j] == v) return j;
		}
		return 0; //should never happen
	}

}
