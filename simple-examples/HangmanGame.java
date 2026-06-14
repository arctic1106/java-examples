void main() {
	String[] GAME_WORDS = { "java", "programming", "computer", "algorithm", "developer" };
	int MAX_ATTEMPTS = 7;

	var targetWord = GAME_WORDS[(int) (Math.random() * GAME_WORDS.length)];
	var guessedLetters = "_".repeat(targetWord.length());
	int attemptsLeft = MAX_ATTEMPTS;

	IO.println("Welcome to Hangman!");
	IO.println("Try to guess the word. You have " + MAX_ATTEMPTS + " attempts.");
	var scanner = new Scanner(System.in);
	while (attemptsLeft > 0) {
		IO.println("Current word: " + guessedLetters);
		IO.print("Enter a letter: ");

		// Ensure valid input (non-empty and single character)
		var input = scanner.nextLine().trim();
		if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
			IO.println("Invalid input. Please enter a single letter.");
			continue;
		}
		var guessedChar = input.charAt(0);
		var updatedWord = new StringBuilder(guessedLetters);
		boolean found = false;
		for (int i = 0; i < targetWord.length(); i++) {
			if (targetWord.charAt(i) == guessedChar && guessedLetters.charAt(i) == '_') {
				updatedWord.setCharAt(i, guessedChar);
				found = true;
			}
		}
		if (found) {
			guessedLetters = updatedWord.toString();
			IO.println("Correct guess! The word has been updated: " + guessedLetters);
		} else {
			attemptsLeft--;
			IO.println("Incorrect guess! You have " + attemptsLeft + " attempts left.");
		}
		if (guessedLetters.equals(targetWord)) {
			IO.println("Congratulations! You guessed the word: " + targetWord);
			break;
		}
	}
	if (attemptsLeft == 0)
		IO.println("Game Over. The word was: " + targetWord);
	scanner.close();
}