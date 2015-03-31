package riskybusiness.riskybusinessmuseumapp.root.trailmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 18/03/2015. Modified by Chris 19/03/2015
 * Takes a string dumped from a database cursor via DatabaseUtils.dumpCurrentRowToString
 * and parses it into its Name : Value pairs which can then by accessed directly via the
 * array list nameValuePair or through the getter methods
 */
public class ParseRecord {
    public List<String> nameValuePairs = new ArrayList<>(); // Array list to hold the final Name : Value pairs after parsing

    /**
     * Constructor, takes the original record as string and splits it into name:value pairs
     * @param str Oringinal record string with JSON like structure
     */
    public ParseRecord(String str) {
        String[] splitstr = str.split("\\n"); // remove and split on newlines into String array

        List<String> split = new ArrayList<>();

        for(int t = 1; t < splitstr.length - 1; t++) // Discard the first and last elements of splitstr - they are not needed
        {
            splitstr[t] = splitstr[t].trim(); // Remove any leading or trailing spaces
            split.add(splitstr[t]); // Add the
        }

        for(String s : split) // Now split the name and value into separate List elements and add to nameValuePairs
        {
            // Ensure that there is something after the = symbol. If there is not, append 'null' to it.
            if(s.endsWith("="))
                s = s + "NULL";

            nameValuePairs.addAll(Arrays.asList(s.split("="))); // Split the name value pair on '=' and add to nameValuePairs list
        }
    }

    /**
     * Retrieves a value from nameValuePair via its name (first occurrence)
     * @param name Name of the value to be retrieved
     * @return Value (or NULL) based on the name provided
     */
    public String getValue(String name) {
        int index; // index of the located name

        index = nameValuePairs.indexOf(name); // Search for the name

        if(index == -1) {// Name not found
            return null;
        }

        if(index >= nameValuePairs.size()) { // Name found in last element of nameValuePair so value (index+1) would be out of bounds
            return null;
        }

        return nameValuePairs.get(index + 1); // Return the value (String) paired with name
    }

    /**
     * Returns the name from a name value pair
     * @param index Index of the name from a value pair accessed via the number of the required pair - 0 = first pair, 1 = second pair, etc.
     * @return Name The name of the requested pair or null if index is invalid
     */
    public String getName(int index) {

        index *= 2; // Multiply index by 2 to get to the required pair name

        if(index < 0 || index >= nameValuePairs.size()) { // Check index is legal, if not return null
            return null;
        }

        return nameValuePairs.get(index); // return the required name
    }

    /**
     * Gets the number of pairs in nameValuePair
     * @return int number of pairs in the list nameValuePair
     */
    public int getPairCount() {
        return nameValuePairs.size() < 2 ? 0: nameValuePairs.size()/2;
    }

    /**
     * Outputs all name value pairs to the console
     */
    public void printPairs() {
        for(int t = 0; t < getPairCount(); t++) {
            System.out.println(getName(t) + ":" + getValue(getName(t)));
        }
    }
}
