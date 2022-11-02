/*
 Ricardo Andres Hernandez
 2/18/2022
 CS 145
 Ryan Parsons
 Assignment 2 - Tag
 This program manages a special game of tag where every player only knows  who they are trying to tag. They don't know who other players are trying to tag
 nor who's trying to tag them.
 */
import java.util.List;
public class TagManager {
    PlayerNode currentFront;
    PlayerNode historyFront; //null if empty

    /**
     * Constructs new TagManager that adds players to current game ring.
     * 
     * @param names Names of players to add to the current game ring.
     * @throws IllegalArgumentException when empty list of names is passed.
     */
    public TagManager(List < String > names) throws IllegalArgumentException {
        if (names == null || names.size() == 0)
            throw new IllegalArgumentException();

        PlayerNode tailNode = new PlayerNode(names.get(0));
        PlayerNode current = tailNode;


        for (int i = 1; i < names.size(); i++) {
            while (current.next != null) {
                current = current.next;
            }

            current.next = new PlayerNode(names.get(i));
        }

        currentFront = tailNode;
    }

    /**
     * Prints the names of all players in game ring.
     */
    public void printGameRing() {
        PlayerNode current = currentFront;

        while (current != null) {
            if (current.next == null) {
                System.out.println("   " + current.name + 
                		" is trying to tag " + currentFront.name);
            } else {
                System.out.println("   " + current.name + 
                		" is trying to tag " + current.next.name);
            }
            current = current.next;
        }
    }

    /**
     * Prints the names of players in history.
     */
    public void printHistory() {
        if (historyFront == null) {
            return;
        }

        PlayerNode current = historyFront;

        while (current != null) {
            System.out.println("   " + current.name + " was tagged by " + current.tagger);
            current = current.next;
        }

    }

    /**
     * Returns whether player with given name is in the current game ring or not.
     * 
     * @param name Name of player to look for in game ring.
     * @return True if name is in game ring. False otherwise.
     */
    public boolean gameRingContains(String name) {
        PlayerNode current = currentFront;

        while (current != null) {
            if (current.name.toLowerCase().equals(name.toLowerCase())) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /**
     * Returns whether given name is in the history or not.
     * 
     * @param name Name of player to look for in game ring.
     * @return True if name is in history. False otherwise.
     */
    public boolean historyContains(String name) {
        PlayerNode current = historyFront;

        while (current != null) {
            if (current.name.toLowerCase().equals(name.toLowerCase())) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /**
     * Returns whether the game is over or not.
     * 
     * @return True if game ring contains only one player, false otherwise
     */
    public boolean isGameOver() {
        return (currentFront.next == null);
    }

    /**
     * Returns the name of the player who remains after the game is over.
     * 
     * @return Name of the player who remains if game is over. Null if otherwise. 
     */
    public String winner() {
        if (isGameOver()) {
            return currentFront.name;
        } else {
            return null;
        }

    }

    /**
     * Records the tagging of the player with the given name, transferring the player from the
     * game ring to the front of the history.
     * 
     * @param name Name of player to remove from current game ring and add to front of history.
     */
    public void tag(String name) {
        PlayerNode current = currentFront;
        PlayerNode tagged = null;

        if (current != null && current.name.toLowerCase().equals(name.toLowerCase())) {
            current = current.next;
            tagged = currentFront;
            currentFront = currentFront.next;
        }

        while (current.next != null) {
            if (current.next.name.toLowerCase().equals(name.toLowerCase())) {
                tagged = current.next;
                tagged.tagger = current.name;
                current.next = current.next.next;
            } else {
                current = current.next;
            }

        }
        if (tagged.tagger == null) {
            tagged.tagger = current.name;
        }

        PlayerNode playerOut = new PlayerNode(tagged.name, historyFront);
        playerOut.tagger = tagged.tagger;

        historyFront = playerOut;
    }

}