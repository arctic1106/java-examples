class Customer {
	private String name;
	private boolean served = false;

	public Customer(String name) {
		this.name = name;
	}

	public void serve() {
		served = true;
		println(name + " has been served");
	}
}

public static void serveCustomer(Queue<Customer> queue) {
	if (!queue.isEmpty()) queue.poll().serve();
	else println("Queue is empty.");
}

void main() {
	var queue = new ArrayDeque<>(
		List.of(
			new Customer("Sally"),
			new Customer("Ben"),
			new Customer("Emma"),
			new Customer("Fred")
		)
	);

	serveCustomer(queue);
	serveCustomer(queue);
	serveCustomer(queue);
	serveCustomer(queue);
	serveCustomer(queue);
}