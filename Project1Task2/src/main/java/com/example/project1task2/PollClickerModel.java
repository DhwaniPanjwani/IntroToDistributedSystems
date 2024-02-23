package com.example.project1task2;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/*
This is the model for the PollClicker servlet which contains the logic of
how the counts are updated
 */
public class PollClickerModel {
    // Declare a HashMap to store scores, where the key is a Character and the value is an Integer
    HashMap<Character, Integer> scoreCount = new HashMap<Character, Integer>();

    // Define a method named keepScoreCount that takes a String parameter named 'option'
    // and may throw an UnsupportedEncodingException

    //Syntax Reference: https://www.geeksforgeeks.org/java-util-hashmap-in-java-with-examples/
    public HashMap keepScoreCount(String option) throws UnsupportedEncodingException {
        // Check if the HashMap does not contain the Character at the beginning of the 'option' string
        if (!(scoreCount.containsKey(option.charAt(0)))) {
            // If not, add the Character to the HashMap with a value of 1
            scoreCount.put(option.charAt(0), 1);
        } else {
            // If the Character is already in the HashMap, retrieve its current count
            int count = scoreCount.get(option.charAt(0));
            // Increment the count by 1
            count = count + 1;
            // Update the HashMap with the new count
            scoreCount.put(option.charAt(0), count);
        }
        // Print the current state of the scoreCount HashMap to the console
        System.out.println(scoreCount);
        // Return the updated scoreCount HashMap
        return scoreCount;
    }
}

