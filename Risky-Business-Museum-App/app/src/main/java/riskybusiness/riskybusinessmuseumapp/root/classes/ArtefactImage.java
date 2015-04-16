package riskybusiness.riskybusinessmuseumapp.root.classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Chris and Tom on 16/04/2015.
 * Gets an artefact image and returns it as Drawable so that is can be placed into ImageView
 */
public class ArtefactImage extends Drawable {
    private InputStream imageResource;
    private Drawable image;

    private Context cont;

    /**
     * Only for images from assets/images folder!
     * constructs a drawable for use with ImageView etc.
     * @param cont Context
     * @param file Filename excluding path (for images from assets/images folder)
     */
    public ArtefactImage(Context cont, String file) {
        this.cont = cont;

        if (!file.contains(".")) // Check if image extension is part of the name
            file += ".jpg"; // JPG images

        loadImage(file);

        image = Drawable.createFromStream(imageResource, null);
    }

    /**
     * Loads the image resource for the file
     * @param file Required filename
     */
    private void loadImage(String file) {
        try {
            imageResource = cont.getAssets().open("images/" + file);

            //f = Drawable.createFromStream(img, null);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the drawable image
     * @return Drawable image
     */
    public Drawable getImage() {
        return image;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
