// Programmer: Gregory Smith
// Date: 06/13/2022
// Program: Hash Map
// References: https://docs.oracle.com/javase/8/docs/api/java/util/Map.html,
// https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html,
// https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html,
// https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html,
// Building Java Programs, 5th Edition: Chapters 1-18
//
// Purpose: Implement a hash map data structure
// IDE: IntelliJ

package GSmithHashing;

import java.util.Arrays;

public class HashMap<K,V> implements Map<K,V> {
    private Node<K,V>[] nodeArray;
    private int size;

    public HashMap() {
        int initialCapacity = 10;
        nodeArray = new Node[initialCapacity];
        size = 0;

    } // end of constructor

    public void clear() {
        Arrays.fill(nodeArray, null);
        size = 0;

    } // end of clear

    public boolean containsKey(K key) {
        int bucket = hashFunction(key);
        Node<K,V> currentNode = nodeArray[bucket];

        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return true;

            } else {
                currentNode = currentNode.next;

            } // end of if/else

        } // end of while loop
        return false;

    } // end of containsKey

    public boolean containsValue(V value) {
        // we traverse all the buckets until we find a node with value
        for (Node<K,V> currentNode : nodeArray) {
            while (currentNode != null) {
                if (currentNode.value.equals(value)) {
                    return true;

                } else {
                    currentNode = currentNode.next;

                } // end of if/else

            } // end of while loop

        } // end of for-each
        return false;

    } // end of containsValue

    public V get(K key) {
        int bucket = hashFunction(key);
        Node<K,V> currentNode = nodeArray[bucket];

        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return currentNode.value;

            } else {
                currentNode = currentNode.next;

            } // end of if/else

        } // end of while loop
        // the node with that key does not exist
        return null;

    } // end of get

    public boolean isEmpty() {
        return size == 0;

    } // end of isEmpty

    public void put(K key,V value) {
        rehashIfNeeded();
        int bucket = hashFunction(key);
        Node<K,V> currentNode = nodeArray[bucket];

        // when there aren't any nodes in that bucket yet
        if (currentNode == null) {
            nodeArray[bucket]= new Node<>(key, value);
            size++;
            return;

        } // end of if

        // we see if the linked list already has a node with that key;
        // if so, we replace the value
        while (currentNode.next != null) {
            if (currentNode.key.equals(key)) {
                currentNode.value = value;
                return;

            } else {
                currentNode = currentNode.next;

            } // end of if/else

        } // end of while loop

        // if we reach the end of the linked list without finding the key,
        // it doesn't exist yet, so we add it with the corresponding value
        currentNode.next = new Node<>(key, value);
        size++;

    } // end of put

    public V remove(K key) {
        int bucket = hashFunction(key);
        Node<K,V> currentNode = nodeArray[bucket];

        // when there aren't any nodes in that bucket yet
        if (currentNode == null) {
            return null;

        // if the desired node is the first in the linked list
        } else if (currentNode.key.equals(key)) {
            V returnValue = currentNode.value;
            nodeArray[bucket] = currentNode.next;
            size--;
            return returnValue;

        } // end of if/else

        while (currentNode.next != null) {
            if (currentNode.next.key.equals(key)) {
                V returnValue = currentNode.next.value;
                currentNode.next = currentNode.next.next;
                size--;
                return returnValue;

            } else {
                currentNode = currentNode.next;

            } // end of if/else

        } // end of while loop
        // the node with that key does not exist
        return null;

    } // end of remove

    public int size() {
        return size;

    } // end of size

    // a basic hashing function that should work for all classes;
    // in practice the distribution of hash values may be less than ideal
    private int hashFunction(K key) {
        return Math.abs(key.hashCode()) % nodeArray.length;

    } // end of hashFunction

    private void rehashIfNeeded() {
        double maxLoadFactor = 0.75;

        if (loadFactor() > maxLoadFactor) {
            rehash();

        } // end of if

    } // end of rehashIfNeeded

    private void rehash() {
        Node<K,V>[] oldNodeArray = nodeArray; // locally save current nodeArray
        nodeArray = new Node[nodeArray.length * 2]; // create a larger empty nodeArray
        size = 0; // new nodeArray is empty

        // we traverse all buckets, and put the nodes in their corresponding
        // location in the new nodeArray
        for (Node<K,V> currentNode : oldNodeArray) {
            while (currentNode != null) {
                this.put(currentNode.key, currentNode.value);
                currentNode = currentNode.next;

            } // end of while loop

        } // end of for-each

    } // end of rehash

    private double loadFactor() {
        return 1.0 * size / nodeArray.length;

    } // end of loadFactor

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");

        boolean singletonBool = true;
        for (Node<K,V> currentNode: nodeArray) {
            while (currentNode != null) {
                // if it is the first element in the linked list
                if (singletonBool) {
                    singletonBool = false;

                } else {
                    stringBuilder.append(", ");

                } // end of i/else
                stringBuilder.append(currentNode);
                currentNode = currentNode.next;

            } // end of while loop

        } // end of for-each
        return stringBuilder.append("]").toString();

    } // end of toString

    // see how the nodes are structured in the hash map
    public void printBuckets() {
        Node<K,V> currentNode;

        for (int index = 0; index < nodeArray.length; index++) {
            currentNode = nodeArray[index];
            System.out.printf("%d  ", index);

            while (currentNode != null) {
                System.out.printf("%s -> ", currentNode);
                currentNode = currentNode.next;

            } // end of while loop
            System.out.println();

        } // end of for loop

    } // end of toString

    // to implement chaining we need Node objects
    private static class Node<K,V> {
        public K key;
        public V value;
        public Node<K,V> next;

        // default constructor
        public Node(K key, V value, Node<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;

        } // end of default constructor

        // null next constructor
        public Node(K key, V value) {
            this(key, value, null);

        } // end of null next constructor

        public String toString() {
            return "<" + key.toString() + ", " + value.toString() + ">";

        } // end of toString

    } // end of Node class

} // end of HashMap class

// we implement the Map interface defined here,
// not the built-in one to reduce the methods required
interface Map<K,V> {
    void clear();
    boolean containsKey(K key);
    boolean containsValue(V value);
    V get(K key);
    boolean isEmpty();
    void put(K key, V value);
    V remove(K key);
    int size();

} // end of Map interface

