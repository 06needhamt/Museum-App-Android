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
    public static final String MUSEUM_INFO_PAGE = "file:///android_asset/webpages/ THIS IS MISSING"; //TODO
    public static final String CAFE_AND_SHOP_PAGE = "file:///android_asset/webpages/Cafe and shop-2.html";
    public static final String FACILITIES_PAGE = "file:///android_asset/webpages/Facilites-1.html";
    public static final String INFO_DESK_PAGE = "file:///android_asset/webpages/InfoDesk-3.html";
    public static final String PLANETARIUM_PAGE = "file:///android_asset/webpages/Planetarium-1.html";

    //Score data
    public static final int MAX_SCORE_FOR_ONE_QUESTION = 100;
    public static final String QR_RESULT_IDENTIFIER = "http://www.riskybusiness.co.uk/";

    //Bundle identifiers
    public static final String FROM_TAG = "FROM";
    public static final String QUESTION_TAG = "QUESTION";
    public static final String ANSWER_TAG = "ANSWER";
    public static final String SCORE_TAG = "SCORE";
    public static final String TRAIL_POSITION_TAG = "TRAIL_POSITION";
    public static final String TRAIL_LENGTH_TAG = "TRAIL_LENGTH";
    public static final String EXIT_TAG = "EXIT";
    public static final String SKIPPED_TAG = "SKIPPED";
    public static final String IMAGE_TAG = "IMAGE";
    public static final String QUESTION_SCORES_TAG = "QUESTION SCORES";
    public static final String TRAIL_NAME_TAG = "TRAIL_NAME";
    public static final String CONTENT_TAG = "CONTENT";
    public static final String FORMAT_TAG = "FORMAT";
    public static final String QR_ANSWER_TAG = "QR_ANSWER";

    //Bundle "FROM" Identifiers
    public static final String FROM_MULTI_CHOICE = "MultiChoiceActivity";
    public static final String FROM_SINGLE_ANSWER = "SingleAnswerActivity";
    public static final String FROM_PICTURE_MULTI_CHOICE = "PictureMultiChoiceActivity";
    public static final String FROM_PICTURE_QR_QUESTION = "PictureQRQuestionActivity";
    public static final String FROM_QR_SCANNER = "QRScannerActivity";
    public static final String FROM_TRAIL_RESULT_SCREEN = "TrailResultActivity";
}
