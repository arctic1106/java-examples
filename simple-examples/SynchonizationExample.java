class Account {
	private int balance;
	private int maxDebit;

	private Account(int initialBalance, int maxDebit) {
		this.balance = initialBalance;
		this.maxDebit = maxDebit;
	}

	public static class Builder {
		private int balance = 0;
		private int maxDebit = 0;

		public Builder setInitialAmount(int initialAmount) {
			this.balance = initialAmount;
			return this;
		}

		public Builder setMaxDebit(int maxDebit) {
			this.maxDebit = maxDebit;
			return this;
		}

		public Account build() {
			return new Account(balance, maxDebit);
		}
	}

	public synchronized void add(int amount) {
		balance += amount;
	}

	public synchronized void withdraw(int amount) {
		if (balance - amount >= -maxDebit) balance -= amount;
		else throw new IllegalStateException("Withdrawal exceeds max debit limit.  Current balance: " + balance);
	}

	public int getBalance() {
		return balance;
	}

	public int getMaxDebit() {
		return maxDebit;
	}
}

class ATM {

	static synchronized void withdraw(Account account, int amount) {
		try {
			System.out.println("Trying to withdraw " + amount + "$ ...");
			account.withdraw(amount);
			System.out.println("$" + amount + " successfully withdrawn. Current balance: " + account.getBalance());
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}
}

public class SynchonizationExample {
	
	public static void main(String[] args) {
		var account = new Account.Builder().setInitialAmount(100).setMaxDebit(50).build();
		new Thread(() -> ATM.withdraw(account, 10)).start();
		new Thread(() -> ATM.withdraw(account, 100)).start();
		new Thread(() -> ATM.withdraw(account, 50)).start();
		new Thread(() -> ATM.withdraw(account, 20)).start();
	}
}