package riskybusiness.riskybusinessmuseumapp.root;

import riskybusiness.riskybusinessmuseumapp.root.Fragments.QRFragment;

/**
 * Created by Chris on 21/03/2015.
 */
public interface AppConstants {
    // Mode of use
    public static final int MODE_BROWSE = 0; // Just browsing
    public static final int MODE_ON_TRAIL = 1;  // On a trail

    // Types of trail an artefact may be associated with
    public static final int NO_TRAIL = 0;
    public static final int TRAIL = 1;
    public static final int EXPLORER = 2;
    public static final int TRAIL_AND_EXPLORER = 3;

    // These represent button indexes NOT floor numbers.
    public static final int FLOOR_AQUARIUM = 0;
    public static final int FLOOR_BUGS = 1;
    public static final int FLOOR_ANCIENT_WORLD = 2;
    public static final int FLOOR_WORLD_CULTURES = 3;
    public static final int FLOOR_DINOSAURS = 4;
    public static final int FLOOR_SPACE_TIME = 5;

    // Question types
    public static final int QUESTION_SINGLE = 0;
    public static final int QUESTION_MULTI = 1;
    public static final int QUESTION_PICTURE = 2;
    public static final int QUESTION_MULTI_PICTURE = 3;

    public static final Class<QRFragment> QR_FRAGMENT_CLASS = QRFragment.class;

}
