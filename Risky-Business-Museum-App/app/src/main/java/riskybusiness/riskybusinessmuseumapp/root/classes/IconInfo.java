package riskybusiness.riskybusinessmuseumapp.root.classes;

import android.graphics.drawable.Drawable;

/**
 * Created by Tom on 27/01/2015.
 */

public class IconInfo {
    public final String name; // Name of the icon
    public int iconID; // ID of the icon

   //  public final Drawable icon; // We don't want to pass drawables around

    public IconInfo(String name, int iconID) {
        this.name = name;
        this.iconID = iconID;

        // this.icon = icon;
    }
}

