void main() {
	var lines = getLines();
	for (var line : lines) {
		var words = line.split(" ");
		for (var word : words) {
			IO.print(word + " ");
			try {
				Thread.sleep(100);
			} catch (InterruptedException _) {
			}
		}
		IO.println();
	}
}

private static String[] getLines() {
	return """
			Why did the Java programmer bring a shovel to work? . . . . To 'dig' into the data structures!
			Why was the Java developer always so calm? . . . . Because they never lost their stack trace!
			Why did the Java developer go broke? . . . . Because he used up all his cache!
			Why did the Java developer always carry a pen? . . . . To 'interface' with their notes!
			""".split("\n");
}