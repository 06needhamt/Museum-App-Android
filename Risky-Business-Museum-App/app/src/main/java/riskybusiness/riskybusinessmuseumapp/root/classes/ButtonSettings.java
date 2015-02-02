package riskybusiness.riskybusinessmuseumapp.root.classes;

import android.graphics.drawable.Drawable;
import android.widget.Button;

/**
 * Created by Chris on 26/01/2015.
 * Class holds the information for a button, including npn-pressed and pressed images, size, etc
 *
 */
public class ButtonSettings {
    private Drawable underIcon; // Under icon image
    private Drawable overIcon; // Over icon image
    public Button button;

    private int width, height;





    public ButtonSettings(Button button, int notPressed, int pressed) { // Constructor needs button passing in
        this.button = button;

        width = button.getWidth(); // Set the width of the button to existing settings

    }

    public void setUnder(String iconName) { // Set the over icon image



        this.underIcon = underIcon;
    }

    public Drawable getUnderIcon() { // Return the under icon image
        return underIcon;
    }

    public void setOver(Drawable overIcon) { // Set the over icon image
        this.overIcon = overIcon;
    }

    public Drawable getOverIcon() { // Get the over icon image
        return overIcon;
    }

    public void setWidth(int width) { // Set button width
        this.width = width;
    }

    public int getWidth() { // Get the button width
        return width;
    }

    public void setHeight(int height) { // Set the button height

    }

    public int getHeight() { // Get the button width
        return height;
    }

}
