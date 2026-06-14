void main() {
    var scanner = new Scanner(System.in);
    var random = new Random();
    String[] choices = { "Rock", "Paper", "Scissors" };

    IO.println("Welcome to Rock, Paper, Scissors!");

    while (true) {
        // Display choices
        IO.println("\nEnter your choice (0 = Rock, 1 = Paper, 2 = Scissors, 3 = Exit): ");
        int userChoice = scanner.nextInt();

        if (userChoice == 3) {
            IO.println("Thanks for playing!");
            break;
        } else if (userChoice < 0 || userChoice > 2) {
            IO.println("Invalid choice. Try again.");
            continue;
        }

        // Computer's choice
        int computerChoice = random.nextInt(3);
        IO.println("Computer chose: " + choices[computerChoice]);
        IO.println("You chose: " + choices[userChoice]);

        // Determine the winner
        if (userChoice == computerChoice) {
            IO.println("It's a draw!");
        } else if ((userChoice == 0 && computerChoice == 2) || (userChoice == 1 && computerChoice == 0)
                || (userChoice == 2 && computerChoice == 1)) {
            IO.println("You win!");
        } else {
            IO.println("Computer wins!");
        }
    }
    scanner.close();
}
