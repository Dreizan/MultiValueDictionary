package org.multivaluedictionary;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 * Interface to interact with the in-memory multi-value dictionary
 */
public class Application {

    /** In-memory multi-value dictionary */
    private static MultiValueDictionary dictionary = new MultiValueDictionary();

    /**
     * Entry point into the application. Reroutes to commandLineInterface to begin taking in user input and interactions
     * with the multi-value dictionary
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        commandLineInterface();
    }

    /**
     * Takes in user input in order to interact with the in-memory multi-value dictionary
     */
    private static void commandLineInterface() {
        String userInput;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print("> ");
            userInput = in.nextLine();
            List<String> splitInput = Arrays.asList(userInput.split(" "));

            switch (splitInput.get(0)) {
                case "KEYS":
                    Set<String> keys = dictionary.getKeys();
                    if (keys == null) {
                        System.out.println("(empty set)\n");
                    } else {
                        int keyNum = 1;
                        for (String key : keys) {
                            System.out.printf("%d) %s\n", keyNum++, keys);
                        }
                        System.out.println();
                    }
                    break;
                case "MEMBERS":
                    try {
                        Set<String> members = dictionary.getMembers(splitInput.get(1));
                        int memberNum = 1;
                        for (String member : members) {
                            System.out.printf("%d) %s\n", memberNum++, member);
                        }
                        System.out.println();
                    } catch (NoSuchElementException nsee) {
                        System.out.println("> ERROR, " + nsee.getMessage());
                    }
                    break;
                case "ADD":
                    try {
                        dictionary.add(splitInput.get(1), splitInput.get(2));
                        System.out.println(") Added");
                    } catch (UnsupportedOperationException uoe) {
                        System.out.println("> ERROR, " + uoe.getMessage());
                    }
                    break;
                case "REMOVE":
                    try {
                        dictionary.remove(splitInput.get(1), splitInput.get(2));
                        System.out.println(") Removed");
                    } catch (NoSuchElementException nsee) {
                        System.out.println("> ERROR, " + nsee.getMessage() + "\n");
                    }
                    break;
                case "REMOVEALL":
                    try {
                        dictionary.removeAll(splitInput.get(1));
                        System.out.println(") Removed");
                    } catch (NoSuchElementException nsee) {
                        System.out.println("> ERROR, " + nsee.getMessage());
                    }
                    break;
                case "CLEAR":
                    dictionary.clear();
                    System.out.println(") Cleared\n");
                    break;
                case "KEYEXISTS":
                    System.out.println(") " + dictionary.keyExists(splitInput.get(1)));
                    break;
                case "VALUEEXISTS":
                    System.out.println(") " + dictionary.valueExists(splitInput.get(1), splitInput.get(2)));
                    break;
                case "ALLMEMBERS":
                    List<String> members = dictionary.getAllMembers();
                    if (members == null) {
                        System.out.println("(empty set)");
                    } else {
                        int memberNum = 1;
                        for (String member : members) {
                            System.out.printf("%d) %s\n", memberNum++, member);
                        }
                        System.out.println();
                    }
                    break;
                case "ITEMS":
                    Map<String, Set<String>> items = dictionary.getItems();
                    if (items == null) {
                        System.out.println("(empty set)");
                    } else {
                        int itemNum = 1;
                        for (String key : items.keySet()) {
                            for (String member : items.get(key)) {
                                System.out.printf("%d) %s: %s\n", itemNum++, key, member);
                            }
                        }
                        System.out.println();
                    }
                    break;
                case "EXIT":
                    break;
                default:
                    System.out.println("Unsupported operation: " + splitInput.get(0));
                    break;
            }
        } while (!userInput.equals("EXIT"));

        in.close();
    }
}
