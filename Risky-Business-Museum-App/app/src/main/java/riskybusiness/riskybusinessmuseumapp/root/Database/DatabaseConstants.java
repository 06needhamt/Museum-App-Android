package riskybusiness.riskybusinessmuseumapp.root.Database;

/**
 * Created by Chris on 23/03/2015.
 * Database Constants defining String field name constants used in queries etc.
 */
public interface DatabaseConstants {

    // Museum Table
    public static final String MUS_ID = "mus_museumID";     // This is an alias to be set in the query string
    public static final String MUS_NAME = "mus_name";

    // Artefact Table
    public static final String ART_ID = "art_artefactID";   // This is an alias to be set in the query string
    public static final String ART_EXHIBIT_ID = "art_exhibitID";
    public static final String ART_MUSEUM_NO = "art_museumNo";
    public static final String ART_NAME = "art_name";
    public static final String ART_DESCRIPTION = "art_description";
    public static final String ART_IMAGE = "art_image";

    // Exhibit Table
    public static final String EXH_ID = "exh_exhibitID";    // This is an alias to be set in the query string
    public static final String EXH_MUSEUM_ID = "exh_museumID";
    public static final String EXH_NAME = "exh_name";
    public static final String EXH_DESCRIPTION = "exh_description";
    public static final String EXH_FLOOR = "exh_floor";
    public static final String EXH_TYPE = "exh_type";  // The type of exhibit: 0=Aquarium, 1=Bugs, 2=Ancient World, 3=World Culture, 4=Dinosaurs, 5=Space

    // Trail Table
    public static final String TRA_ID = "tra_trailID";          // This is an alias to be set in the query string
    public static final String TRA_EXHIBIT_ID = "tra_exhibitID";
    public static final String TRA_NAME = "tra_name";
    public static final String TRA_DESCRIPTION = "tra_description";
    public static final String TRA_TYPE = "tra_trailType";

    // Trail Step Table
    public static final String STP_ID = "stp_trailStepID";  // This is an alias to be set in the query string
    public static final String STP_TRAIL_ID = "stp_trailID";
    public static final String STP_QUESTION_TYPE = "stp_questionType";
    public static final String STP_QUESTION = "stp_question";
    public static final String STP_ANSWER = "stp_answer";
    public static final String STP_QR_CODE = "stp_qrCode";
    public static final String STP_IMAGE = "stp_image";

    // Image Table
    public static final String IMG_ID = "img_imageID";  // This is an alias to be set in the query string
    public static final String IMG_NAME = "img_name";
    public static final String IMG_IMG = "img_image";   // Actual image blob

}
