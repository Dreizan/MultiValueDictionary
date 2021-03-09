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
                    if (splitInput.size() != 1) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        Set<String> keys = dictionary.getKeys();
                        if (keys == null) {
                            System.out.println("(empty set)\n");
                        } else {
                            int keyNum = 1;
                            for (String key : keys) {
                                System.out.printf("%d) %s\n", keyNum++, key);
                            }
                            System.out.println();
                        }
                    }
                    break;
                case "MEMBERS":
                    if (splitInput.size() != 2) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        try {
                            Set<String> members = dictionary.getMembers(splitInput.get(1));
                            int memberNum = 1;
                            for (String member : members) {
                                System.out.printf("%d) %s\n", memberNum++, member);
                            }
                            System.out.println();
                        } catch (NoSuchElementException nsee) {
                            System.out.println(") ERROR, " + nsee.getMessage() + "\n");
                        }
                    }
                    break;
                case "ADD":
                    if (splitInput.size() != 3) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        try {
                            dictionary.add(splitInput.get(1), splitInput.get(2));
                            System.out.println(") Added\n");
                        } catch (UnsupportedOperationException uoe) {
                            System.out.println(") ERROR, " + uoe.getMessage() + "\n");
                        }
                    }
                    break;
                case "REMOVE":
                    if (splitInput.size() != 3) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        try {
                            dictionary.remove(splitInput.get(1), splitInput.get(2));
                            System.out.println(") Removed\n");
                        } catch (NoSuchElementException nsee) {
                            System.out.println(") ERROR, " + nsee.getMessage() + "\n");
                        }
                    }
                    break;
                case "REMOVEALL":
                    if (splitInput.size() != 2) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        try {
                            dictionary.removeAll(splitInput.get(1));
                            System.out.println(") Removed\n");
                        } catch (NoSuchElementException nsee) {
                            System.out.println(") ERROR, " + nsee.getMessage() + "\n");
                        }
                    }
                    break;
                case "CLEAR":
                    if (splitInput.size() != 1) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        dictionary.clear();
                        System.out.println(") Cleared\n");
                    }
                    break;
                case "KEYEXISTS":
                    if (splitInput.size() != 2) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        System.out.println(") " + dictionary.keyExists(splitInput.get(1)) + "\n");
                    }
                    break;
                case "VALUEEXISTS":
                    if (splitInput.size() != 3) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        System.out.println(") " + dictionary.valueExists(splitInput.get(1), splitInput.get(2)) + "\n");
                    }
                    break;
                case "ALLMEMBERS":
                    if (splitInput.size() != 1) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        List<String> members = dictionary.getAllMembers();
                        if (members == null) {
                            System.out.println("(empty set)\n");
                        } else {
                            int memberNum = 1;
                            for (String member : members) {
                                System.out.printf("%d) %s\n", memberNum++, member);
                            }
                            System.out.println();
                        }
                    }
                    break;
                case "ITEMS":
                    if (splitInput.size() != 1) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        Map<String, Set<String>> items = dictionary.getItems();
                        if (items == null) {
                            System.out.println("(empty set)\n");
                        } else {
                            int itemNum = 1;
                            for (String key : items.keySet()) {
                                for (String member : items.get(key)) {
                                    System.out.printf("%d) %s: %s\n", itemNum++, key, member);
                                }
                            }
                            System.out.println();
                        }
                    }
                    break;
                case "INTERSECTION":
                    if (splitInput.size() != 3) {
                        System.out.println(") ERROR, Incorrect number of arguments\n");
                    } else {
                        Set<String> intersection = dictionary.getIntersection(splitInput.get(1), splitInput.get(2));
                        if (intersection.isEmpty()) {
                            System.out.println("(empty set)\n");
                        } else {
                            int memberNum = 1;
                            for (String member : intersection) {
                                System.out.printf("%d) %s\n", memberNum++, member);
                            }
                            System.out.println();
                        }
                    }
                    break;
                case "EXIT":
                    break;
                default:
                    System.out.println(") ERROR, Unsupported operation; please try again\n");
                    break;
            }
        } while (!userInput.equals("EXIT"));

        in.close();
    }
}
