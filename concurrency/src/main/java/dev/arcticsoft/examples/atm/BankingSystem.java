package dev.arcticsoft.examples.atm;

public class BankingSystem {

    public static void main(String[] args) {
		// Create: threads and thread groups
        ThreadGroup threadGroup1 = new ThreadGroup("humanThreads");
        ThreadGroup threadGroup2 = new ThreadGroup("nonHumanThreads");
        BankAccount bankAccount = new BankAccount();
        Student s = new Student(threadGroup1, bankAccount);
        Grandmother g = new Grandmother(threadGroup1, bankAccount);
        LoanCompany lc = new LoanCompany(threadGroup2, bankAccount);
        University u = new University(threadGroup2, bankAccount);
		// Start Student & other threads
        s.start();
        g.start();
        lc.start();
        u.start();
    }
}
