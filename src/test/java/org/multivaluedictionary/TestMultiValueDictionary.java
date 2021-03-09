package org.multivaluedictionary;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests various functionalities provided by the multi-value dictionary
 */
public class TestMultiValueDictionary {

    // KEYS

    /**
     * Tests KEYS functionality - gathers all keys in the multi-value dictionary
     */
    @Test
    public void testGetKeys_ItemsExist() {
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("bar")));
        mvdValues.put("baz", new HashSet<>(Arrays.asList("bar")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        assertEquals(mvdValues.keySet(), mvd.getKeys());
    }

    /**
     * Tests KEYS functionality - returns null if there's nothing in the multi-value dictionary
     */
    @Test
    public void testGetKeys_ItemsDoNotExist() {
        MultiValueDictionary mvd = new MultiValueDictionary();
        assertNull(mvd.getKeys());
    }

    // MEMBERS

    /**
     * Tests MEMBERS functionality - gathers all members pertaining to a specific key in the multi-value dictionary
     */
    @Test
    public void testGetMembers_KeyExists() {
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        assertEquals(mvdValues.get("foo"), mvd.getMembers("foo"));
    }

    /**
     * Tests MEMBERS functionality - ensures exception is thrown when attempting to obtain members of a key that does
     * not exist
     */
    @Test
    public void testGetMembers_KeyDoesNotExist() {
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () -> {
            mvd.getMembers("bar");
        });
        assertEquals("key does not exist", nsee.getMessage());    }

    // ADD

    /**
     * Tests ADD functionality - adds a new item to the multi-value dictionary
     */
    @Test
    public void testAdd_NewKey() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("foo", new HashSet<>(Arrays.asList("bar")));
        MultiValueDictionary mvd = new MultiValueDictionary();
        mvd.add("foo", "bar");
        assertEquals(expectedValues, mvd.getItems());
    }

    /**
     * Tests ADD functionality - adds a new member to an existing key in the multi-value dictionary
     */
    @Test
    public void testAdd_ExistingKey() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        MultiValueDictionary mvd = new MultiValueDictionary();
        mvd.add("foo", "bar");
        mvd.add("foo", "baz");
        assertEquals(expectedValues, mvd.getItems());
    }

    /**
     * Tests ADD functionality - ensures exception is thrown when attempting to add a member to a key when it already
     * exists, and ensures multi-value dictionary is unchanged
     */
    @Test
    public void testAdd_ExistingItem() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("foo", new HashSet<>(Arrays.asList("bar")));
        MultiValueDictionary mvd = new MultiValueDictionary();
        mvd.add("foo", "bar");
        UnsupportedOperationException uoe = assertThrows(UnsupportedOperationException.class, () -> {
            mvd.add("foo", "bar");
        });
        assertEquals("value already exists", uoe.getMessage());
        assertEquals(expectedValues, mvd.getItems());
    }

    // REMOVE

    /**
     * Tests REMOVE functionality - removes the key if attempting to remove the only member the key has
     */
    @Test
    public void testRemove_SingleValueExists() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        Map<String, Set<String>> initialValues = new HashMap<>();
        initialValues.put("foo", new HashSet<>(Arrays.asList("bar")));
        initialValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        MultiValueDictionary mvd = new MultiValueDictionary(initialValues);
        mvd.remove("foo", "bar");
        assertEquals(expectedValues, mvd.getItems());
    }

    /**
     * Tests REMOVE functionality - removes the desired member from the desired key, but ensures desired key and other
     * members of the key still exist
     */
    @Test
    public void testRemove_MultipleValuesExist() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("foo", new HashSet<>(Arrays.asList("baz")));
        expectedValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        Map<String, Set<String>> initialValues = new HashMap<>();
        initialValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        initialValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        MultiValueDictionary mvd = new MultiValueDictionary(initialValues);
        mvd.remove("foo", "bar");
        assertEquals(expectedValues, mvd.getItems());
    }

    /**
     * Tests REMOVE functionality - ensures exception is thrown when attempting to remove a member that doesn't exist on
     * a key, and ensures that the multi-value dictionary is unchanged
     */
    @Test
    public void testRemove_MemberDoesNotExist() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        MultiValueDictionary mvd = new MultiValueDictionary(expectedValues);
        NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () -> {
            mvd.remove("baz", "bar");
        });
        assertEquals("value does not exist", nsee.getMessage());
        assertEquals(expectedValues, mvd.getItems());

    }

    /**
     * Tests REMOVE functionality - ensures exception is thrown when attempting to remove a key that doesn't exist, and
     * ensures that the multi-value dictionary is unchanged
     */
    @Test
    public void testRemove_KeyDoesNotExist() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        MultiValueDictionary mvd = new MultiValueDictionary(expectedValues);
        NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () -> {
            mvd.remove("foo", "bar");
        });
        assertEquals("key does not exist", nsee.getMessage());
        assertEquals(expectedValues, mvd.getItems());

    }

    // REMOVEALL

    /**
     * Tests REMOVEALL functionality - removes key and all members if the key exists
     */
    @Test
    public void testRemoveAll_Exists() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        Map<String, Set<String>> initialValues = new HashMap<>();
        initialValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        initialValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        MultiValueDictionary mvd = new MultiValueDictionary(initialValues);
        mvd.removeAll("foo");
        assertEquals(expectedValues, mvd.getItems());
    }

    /**
     * Tests REMOVEALL functionality - ensures exception is thrown when attempting to remove all members of a key that
     * does not exist
     */
    @Test
    public void testRemoveAll_DoesNotExist() {
        Map<String, Set<String>> expectedValues = new HashMap<>();
        expectedValues.put("baz", new HashSet<>(Arrays.asList("bang", "bar")));
        MultiValueDictionary mvd = new MultiValueDictionary(expectedValues);
        NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () -> {
            mvd.removeAll("foo");
        });
        assertEquals("key does not exist", nsee.getMessage());
        assertEquals(expectedValues, mvd.getItems());
    }

    // CLEAR

    /**
     * Tests CLEAR functionality - clears all items from the multi-value dictionary
     */
    @Test
    public void testClear() {
        Map<String, Set<String>> initialValues = new HashMap<>();
        initialValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        initialValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        MultiValueDictionary mvd = new MultiValueDictionary(initialValues);
        mvd.clear();
        assertNull(mvd.getItems());
    }

    // KEYEXISTS

    /**
     * Tests KEYEXISTS functionality - checks to see if the desired key exists in the multi-value dictionary; returns
     * true if it does, otherwise false
     */
    @Test
    public void testKeyExists() {
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        mvdValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        assertTrue(mvd.keyExists("foo"));
        assertTrue(mvd.keyExists("baz"));
        assertFalse(mvd.keyExists("bar"));
    }

    // VALUEEXISTS

    /**
     * Tests VALUEEXISTS functionality - checks to see if the desired value exists in the multi-value dictionary; returns
     * true if it does, otherwise false
     */
    @Test
    public void testValueExists() {
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        mvdValues.put("baz", new HashSet<>(Arrays.asList("bang")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        assertTrue(mvd.valueExists("foo", "bar"));
        assertTrue(mvd.valueExists("foo", "baz"));
        assertTrue(mvd.valueExists("baz", "bang"));
        assertFalse(mvd.valueExists("foo", "foo"));
        assertFalse(mvd.valueExists("ball", "foo"));
    }

    // ALLMEMBERS

    /**
     * Tests ALLMEMBERS functionality - obtains list of all members from all keys
     */
    @Test
    public void testGetAllMembers_ItemsDoExist() {
        List<String> expectedValues = Arrays.asList("bar", "baz", "bang", "bar");
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        mvdValues.put("baz", new HashSet<>(Arrays.asList("bang", "bar")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        for (String expectedValue : expectedValues) {
            assertTrue(mvd.getAllMembers().contains(expectedValue));
            if (expectedValue.equals("bar")) {
                assertNotEquals(mvd.getAllMembers().indexOf(expectedValue),
                        mvd.getAllMembers().lastIndexOf(expectedValue));
            }
        }
    }

    /**
     * Tests ALLMEMBERS functionality - returns null if there's nothing in the multi-value dictionary
     */
    @Test
    public void testGetAllMembers_ItemsDoNotExist() {
        MultiValueDictionary mvd = new MultiValueDictionary();
        assertNull(mvd.getAllMembers());
    }

    // ITEMS

    /**
     * Tests ITEMS functionality - gathers entire contents of the multi-value dictionary
     */
    @Test
    public void testGetItems_ItemsExist() {
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        mvdValues.put("baz", new HashSet<>(Arrays.asList("bar")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        assertEquals(mvdValues, mvd.getItems());
    }

    /**
     * Tests ITEMS functionality - returns null if there's nothing in the multi-value dictionary
     */
    @Test
    public void testGetItems_ItemsDoNotExist() {
        MultiValueDictionary mvd = new MultiValueDictionary();
        assertNull(mvd.getItems());
    }

    // INTERSECTION

    @Test
    public void testGetIntersection_commonValues() {
        // expected value
        Set<String> expectedValues = new HashSet<>(Arrays.asList("bar"));

        // values for multi value dictionary
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("bar", "baz")));
        mvdValues.put("baz", new HashSet<>(Arrays.asList("bar")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        assertEquals(expectedValues, mvd.getIntersection("foo", "baz"));
    }

    @Test
    public void testGetIntersection_noCommonValues() {
        // expected value
        Set<String> expectedValues = new HashSet<>();

        // values for multi value dictionary
        Map<String, Set<String>> mvdValues = new HashMap<>();
        mvdValues.put("foo", new HashSet<>(Arrays.asList("baz")));
        mvdValues.put("baz", new HashSet<>(Arrays.asList("bar")));
        MultiValueDictionary mvd = new MultiValueDictionary(mvdValues);
        assertEquals(expectedValues, mvd.getIntersection("foo", "baz"));
    }
}
