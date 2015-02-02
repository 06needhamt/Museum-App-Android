package riskybusiness.riskybusinessmuseumapp.root.classes;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Chris & Tom on 28/01/2015.
 * Holds a searchable array of icon info's
 */
public class IconList {
    public ArrayList<IconInfo> icons = new ArrayList<IconInfo>(); // Holds tha name and ID of icons from Res folder
    Activity act;

    // Constructor
    public IconList(Activity a) {
        this.act = a;
    }


    /**
     * @param icon the iconInfo to be added to the list
     * @return
     */
    public IconInfo add(IconInfo icon) {
        icons.add(icon);

        return icon;
    }

    /**
     * Returns the index of the icon within the list
     * @param name
     * @return
     */
    public IconInfo getIcon(String name) {
        ///// Add image to button

        int iconId = 0;
        int index = 0;

        for (IconInfo i : icons) {
            if (i.name.equals(name)) {
                iconId = act.getResources().getIdentifier(name, "drawable", act.getPackageName());

                return icons.get(index); // Icon found return the
            }

            index++;
        }

        return null; // Icon not found, null indicates error
    }

    /**
     * Returns the ID of the icon
     * @param name name of the icon being searched for
     * @return the icon ID number
     */
    public int getId(String name) {

        for (IconInfo i : icons) {
            if (i.name.equals(name)) {
                return i.iconID; // return the icon ID from i

                // iconId = act.getResources().getIdentifier(name, "drawable", act.getPackageName());
            }
        }

        return -1; // Icon not found, -1 indicate error
    }
}
