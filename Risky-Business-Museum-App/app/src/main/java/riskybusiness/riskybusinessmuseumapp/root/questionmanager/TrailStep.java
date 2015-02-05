package riskybusiness.riskybusinessmuseumapp.root.questionmanager;

/**
 * Created by Chris on 05/02/2015.
 * Class to hold a trail steps information retrieved from database
 */
public class TrailStep {
    public int stepID;       // ID of trail step
    public int trailID;      // Trail this step belongs to
    public int questionType; // question type indicator
    public String question;  // Question
    public String answer;    // Answer text - used by multi choice questions
    public String qrCode;    // QR code expected as correct answer for non - multi choice
    public String image;     // Name of image relating to this question (if any)

    // Column number identifiers
    public static final int STEPID_COL = 0;  // Column number of stepid
    public static final int TRAILID_COL = 1;
    public static final int QTYPE_COL = 2;
    public static final int QUESTION_COL = 3;
    public static final int ANSWER_COL = 4;
    public static final int QRCODE_COL = 5;
    public static final int IMAGE_COL = 6;

}
