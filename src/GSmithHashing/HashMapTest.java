// Programmer: Gregory Smith
// Date: 06/13/2022
// Program: Hash Map
// References: https://www.javatpoint.com/java-ascii-table
// Purpose: Demonstrate the HashMap class
// IDE: IntelliJ

package GSmithHashing;

import java.util.Arrays;
import java.util.Random;

public class HashMapTest {
    private static Random randomObj;
    private static int asciiStartValue;
    private static int asciiEndValue;
    private static int asciiDiff;

    static {
        randomObj = new Random();
        asciiStartValue = 65; // A
        asciiEndValue = 90; // Z
        asciiDiff = asciiEndValue - asciiStartValue;

    } // end of static block

    public static void main(String[] args) {
        HashMap<String, Integer> HashMapTest = new HashMap<>();
        int numElements = 20;
        String[] stringArray = getRandomStrings(numElements, 5);
        int[] intArray = getRandomInts(numElements, 101);

        System.out.println("The elements used to make the hash map");
        System.out.println(Arrays.toString(stringArray));
        System.out.println(Arrays.toString(intArray));
        System.out.println();

        for (int count = 0; count < numElements; count++) {
            HashMapTest.put(stringArray[count], intArray[count]);

        } // end of for loop

        System.out.println("The original hash map");
        System.out.println(HashMapTest);
        System.out.println();
        System.out.println("How the data is structured in the hash map");
        HashMapTest.printBuckets();
        System.out.println();
        System.out.println("Size: " + HashMapTest.size());
        System.out.println("Empty: " + HashMapTest.isEmpty());
        System.out.println();

        System.out.println("Test key we know does not exist");
        System.out.println(HashMapTest.containsKey("AAA"));
        System.out.println();
        System.out.println("Test random key we know does exist");
        System.out.println(HashMapTest.containsKey(stringArray[randomObj.nextInt(numElements)]));
        System.out.println();

        System.out.println("Test value we know does not exist");
        System.out.println(HashMapTest.containsValue(101));
        System.out.println();
        System.out.println("Test random value we know does exist");
        System.out.println(HashMapTest.containsValue(intArray[randomObj.nextInt(numElements)]));
        System.out.println();

        System.out.println("Remove some entries, we should except 1-4 entries deleted depending on duplicates");
        HashMapTest.remove(stringArray[randomObj.nextInt(numElements)]);
        HashMapTest.remove(stringArray[randomObj.nextInt(numElements)]);
        HashMapTest.remove(stringArray[randomObj.nextInt(numElements)]);
        HashMapTest.remove(stringArray[randomObj.nextInt(numElements)]);

        System.out.println("Updated hash map");
        System.out.println(HashMapTest);
        System.out.println();
        System.out.println("How the data is structured in the hash map");
        HashMapTest.printBuckets();
        System.out.println();
        System.out.println("Size: " + HashMapTest.size());
        System.out.println("Empty: " + HashMapTest.isEmpty());
        System.out.println();

        // we might run into a key that is no longer in the hash map, so we
        // keep looking until we find one that exists
        String randomKey;
        Integer randomValue;
        do {
            randomKey = stringArray[randomObj.nextInt(numElements)];
            randomValue = HashMapTest.get(randomKey);

        } while (randomValue == null); // end of do-while

        System.out.printf("Get value of key: %s%n", randomKey);
        System.out.printf("Corresponding value: %d%n%n", randomValue);

        System.out.println("We can now clear the hash map");
        HashMapTest.clear();
        System.out.println(HashMapTest);
        System.out.println("Size: " + HashMapTest.size());
        System.out.println("Empty: " + HashMapTest.isEmpty());
        System.out.println();

    } // end of main

    public static String[] getRandomStrings(int numStrings, int stringLength) {
        String[] stringArray = new String[numStrings];

        for (int index=0; index < numStrings; index++) {
            stringArray[index] = getRandomString(stringLength);

        }
        return stringArray;

    } // end of getRandomStrings

    public static int[] getRandomInts(int numInts, int maxInt) {
        int[] intArray = new int[numInts];

        for (int index=0; index < numInts; index++) {
            intArray[index] = randomObj.nextInt(maxInt);

        }
        return intArray;

    } // end of getRandomInts

    public static String getRandomString(int stringLength) {
        StringBuilder stringBuilder = new StringBuilder();
        stringLength = Math.abs(stringLength);

        for (int index=0; index < stringLength; index++) {
            stringBuilder.append(getRandomChar());

        }
        return stringBuilder.toString();

    } // end of getRandomString

    public static char getRandomChar() {
        // get a random int in [0, asciiDiff] and shift it to get
        // random int in [asciiStartValue, asciiEndvalue]
        int charInt = randomObj.nextInt(asciiDiff+1) + asciiStartValue;

        return (char) charInt;

    } // end of getRandomChar

} // end of HashMapTest class
