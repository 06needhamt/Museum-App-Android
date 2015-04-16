package riskybusiness.riskybusinessmuseumapp.root;

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

    //HTML page names
        public static final String DEFAULT_PAGE = "file:///android_asset/webpages/index.html";
        public static final String APP_INFO_PAGE = "file:///android_asset/webpages/AppInfo-4.html";
        //TODO add museum information page
        public static final String CAFE_AND_SHOP_PAGE = "file:///android_asset/webpages/Cafe and shop-2.html";
        public static final String FACILITIES_PAGE = "file:///android_asset/webpages/Facilites-1.html";
        public static final String INFO_DESK_PAGE = "file:///android_asset/webpages/InfoDesk-3.html";
        public static final String PLANETARIUM_PAGE = "file:///android_asset/webpages/Planetarium-1.html";


    //Score data
    public static final int MAX_SCORE_FOR_ONE_QUESTION = 100;
    public static final String QR_RESULT_IDENTIFIER = "http://www.riskybuisiness.co.uk/";
}
