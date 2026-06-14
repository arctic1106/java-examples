package dev.arcticsoft.examples;

import java.util.HashSet;
import java.util.Set;

/**
 * Candidate for lock splitting
 *
 * The two state variables are completely independent; ServerStatus could even
 * be split into two separate classes with no loss of functionality.
 */
public class ServerStatusBeforeSplit {

    public final Set<String> users;
    public final Set<String> queries;

    public ServerStatusBeforeSplit() {
        this.users = new HashSet<>();
        this.queries = new HashSet<>();
    }

    public synchronized void addUser(final String u) {
        this.users.add(u);
    }

    public synchronized void addQuery(final String q) {
        this.queries.add(q);
    }

    public synchronized void removeUser(final String u) {
        this.users.remove(u);
    }

    public synchronized void removeQuery(final String q) {
        this.queries.remove(q);
    }
}
