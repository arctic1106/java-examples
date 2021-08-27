package dev.arcticsoft.examples;

import java.util.HashSet;
import java.util.Set;

/**
 * ServerStatus refactored to use split locks
 *
 * Instead of guarding both "users" and "queries" with the ServerStatus lock, we
 * can instead guard each with a separate lock.
 *
 * After splitting the lock, each new fine-grained lock will see less locking
 * traffic than the original coarser lock would have. (Delegating to a
 * thread-safe Set implementation instead of using explicit synchronization
 * would implicitly provide lock splitting, as each Set would use a different
 * lock to guard state)
 */
public class ServerStatusAfterSplit {

    public final Set<String> users;
    public final Set<String> queries;

    public ServerStatusAfterSplit() {
        this.users = new HashSet<>();
        this.queries = new HashSet<>();
    }

    public void addUser(final String u) {
        synchronized (this.users) {
            this.users.add(u);
        }
    }

    public void addQuery(final String q) {
        synchronized (this.queries) {
            this.queries.add(q);
        }
    }

    public void removeUser(final String u) {
        synchronized (this.users) {
            this.users.remove(u);
        }
    }

    public void removeQuery(final String q) {
        synchronized (this.users) {
            this.queries.remove(q);
        }
    }
}
