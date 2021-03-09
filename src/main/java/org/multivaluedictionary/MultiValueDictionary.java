package org.multivaluedictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Data structure to hold key-member(s) pairs and all other business logic to interact with them
 */
public class MultiValueDictionary {

    /** Key-member(s) dictionary */
    private Map<String, Set<String>> dictionary;

    /**
     * Initializes dictionary to be empty upon creation
     */
    public MultiValueDictionary() {
        dictionary = new HashMap<>();
    }

    /**
     * Initializes dictionary to be an initial key-member(s) pairing
     * @param dictionary initial key-member(s) pairing
     */
    public MultiValueDictionary(Map<String, Set<String>> dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Obtains all keys in multi-value dictionary
     * @return all keys in multi-value dictionary or null if there are none
     */
    public Set<String> getKeys() {
        if (dictionary == null || dictionary.isEmpty()
                || dictionary.keySet() == null || dictionary.keySet().isEmpty()) {
            return null;
        }
        return dictionary.keySet();
    }

    /**
     * Obtains all members of a specific key within the multi-value dictionary
     * @param key key to obtain all members from
     * @return all member of a specific key
     * @throws NoSuchElementException key does not exist
     */
    public Set<String> getMembers(String key) throws NoSuchElementException {
        if (dictionary == null || dictionary.isEmpty() || dictionary.get(key) == null) {
            throw new NoSuchElementException("key does not exist");
        }
        return dictionary.get(key);
    }

    /**
     * Adds a new member to a key, or a new key-member pair if the key does not already exist
     * @param key key to add a member to
     * @param member member to add
     * @throws UnsupportedOperationException member already exist for key
     */
    public void add(String key, String member) throws UnsupportedOperationException {
        if (dictionary.keySet().contains(key) && dictionary.get(key).contains(member)) {
            throw new UnsupportedOperationException("value already exists");
        }

        if (dictionary.keySet().contains(key)) {
            dictionary.get(key).add(member);
        } else {
            dictionary.put(key, new HashSet<String>(Arrays.asList(member)));
        }
    }

    /**
     * Removes desired member from the desired key, or will remove the key if the member being removed is the only
     * member for that key
     * @param key key to remove member from
     * @param member member to remove
     * @throws NoSuchElementException member or key does not exist
     */
    public void remove(String key, String member) throws NoSuchElementException {
        if (!dictionary.keySet().contains(key)) {
            throw new NoSuchElementException("key does not exist");
        } else if (!dictionary.get(key).contains(member)) {
            throw new NoSuchElementException("value does not exist");
        }

        if (dictionary.get(key).size() > 1) {
            dictionary.get(key).remove(member);
        } else {
            dictionary.remove(key);
        }
    }

    /**
     * Removes all members of the desired key as well as the key itself
     * @param key key to remove
     * @throws NoSuchElementException key does not exist
     */
    public void removeAll(String key) throws NoSuchElementException {
        if (!dictionary.keySet().contains(key)) {
            throw new NoSuchElementException("key does not exist");
        }

        dictionary.remove(key);
    }

    /**
     * Clears entire multi-value dictionary
     */
    public void clear() {
        dictionary.clear();
    }

    /**
     * Checks whether or not the key in question exist in the multi-value dictionary
     * @param key key to verify
     * @return true if the key exists, false otherwise
     */
    public boolean keyExists(String key) {
        if (dictionary == null || dictionary.isEmpty()) {
            return false;
        }
        return dictionary.keySet().contains(key);
    }

    /**
     * Checks whether or not the desired member exists for a specific key in the multi-value dictionary
     * @param key key whose members will be checked for existence of a desired member
     * @param member member to verify
     * @return true if the desired member exists for the key in question, false otherwise
     */
    public boolean valueExists(String key, String member) {
        if (dictionary == null || dictionary.isEmpty() || !dictionary.keySet().contains(key)) {
            return false;
        }
        return dictionary.get(key).contains(member);
    }

    /**
     * Obtains all members across all keys in the multi-value dictionary
     * @return list of all members across all keys in the multi-value dictionary
     */
    public List<String> getAllMembers() {
        if (dictionary == null || dictionary.isEmpty()
                || dictionary.keySet() == null || dictionary.keySet().isEmpty()) {
            return null;
        }

        List<String> allMembers = new ArrayList<>();
        for (String key : dictionary.keySet()) {
            allMembers.addAll(dictionary.get(key));
        }
        return allMembers;
    }

    /**
     * Obtains all key-member(s) pairings in the multi-value dictionary
     * @return all key-member(s) pairings in the multi-value dictionary
     */
    public Map<String, Set<String>> getItems() {
        if (dictionary == null || dictionary.isEmpty()) {
            return null;
        }
        return dictionary;
    }
}
