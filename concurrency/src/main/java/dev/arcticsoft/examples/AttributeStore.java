package dev.arcticsoft.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Holding a lock longer than necessary
 *
 * AttributeStore shows an example of holding a lock longer than necessary.
 */
public class AttributeStore {

    public final Map<String, String> attributes = new HashMap<>();

    /**
     * The userLocationMatches method looks up the user's location in a Map and
     * uses regular expression matching the see if the resulting value matches
     * the supplied pattern. The entire userLocationMatches method is
     * synchronized, but the only portion of the code that actually needs the
     * lock is the all to Map.get
     */
    public synchronized boolean userLocationMatches(final String name, final String regexp) {
        final String key = "users." + name + ".location";
        final String location = this.attributes.get(key);
        if (location == null) {
            return false;
        } else {
            return Pattern.matches(regexp, location);
        }
    }
}
