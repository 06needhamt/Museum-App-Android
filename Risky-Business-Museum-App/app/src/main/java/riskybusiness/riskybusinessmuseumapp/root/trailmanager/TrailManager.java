package riskybusiness.riskybusinessmuseumapp.root.trailmanager;

/**
 * Created by Chris on 21/03/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.Database.DatabaseConstants;
import riskybusiness.riskybusinessmuseumapp.root.Database.DatabaseHelper;


/**
 *
 * The Trail Manager will be a Singleton class implemented globally
 *
 * The TrailManager manages the users interactions within the museum. It includes managing:
 * Interactions with the database retrieving information about artefacts and trails
 * The process of a user simply browsing artefacts
 * The process of joining a trail
 * The process of following a trail
 * The process of leaving a trail
 * Where necessary saving trail progress
 * Re-joining a trail including loading saved progress
 *
 */

public class TrailManager implements AppConstants, DatabaseConstants {
    private static TrailManager trailManagerInstance = null; // This will hold a Singleton instance of the TrailManager

    DatabaseHelper db;
    //DatabaseConstants dc = new DatabaseConstants(); // Constant String values for use with database
    Context context;
    int mode; // Mode of use, e.g. MODE_BROWSE, MODE_TRAIL
    TrailInfo currentTrail;         // Information about the current trail
    boolean onTrail;                // Indicates if user is on a trail
    List<TrailInfo> trailList;      // List of trails associated with the artefact
    List<TrailStepInfo> trailSteps; // List of trail steps for current trail

    private TrailManager(Context context) { // trail manager constructor
        this.context = context;
        db = new DatabaseHelper(context);
        mode = MODE_BROWSE; // Just browsing

        trailList = null; // No trail data loaded yet
        onTrail = false;  // Not on a trail

        try {
            db.openDataBase(); // Open the database for use
        } catch (Exception e) {
            Log.d("TRAILMANAGER CONST:", "Error opening the database in TrailManager: " + e.getMessage());
        }
    }

    /**
     * Creates a Singleton object of the TrailManager
     * @param context Required context
     * @return Instance of TrailManager
     */
    public static synchronized TrailManager getTrailManagerInstance(Context context) {
        if(trailManagerInstance != null)
            return trailManagerInstance;
        else {
            trailManagerInstance = new TrailManager(context);
            return trailManagerInstance;
        }
    }

    /**
     * Gets the current mode - MODE_BROWSE or MODE_ON_TRAIL
     * @return Current mode
     */
    public int getMode() {
        return mode;
    }


    /**
     * Clears any data from the TrailManager
     */
    public void clearData() {
        mode = MODE_BROWSE;  // Mode of use, e.g. MODE_BROWSE, MODE_TRAIL
        currentTrail = null; // Information about the current trail
        trailList = null;    // List of trails associated with the artefact
        trailSteps = null;   // List of trail steps for current trail
    }


    /**
     * browseArtefactID gets information about an artefact including trails when the user is browsing
     * @param artefactID The code from the scanned QR, this will be used when interrogating the database
     * @return Artefact or null
     */
    public ArtefactInfo browseArtefactID(int artefactID) {
        ArtefactInfo artefact; // Result class for the query

        // Query database for artefact information
        artefact = getDatabaseArtefact(artefactID);

        if(getArtefactTrails(artefact.artefactID) != null) { // Get any trails associated with the artefact.
            for (TrailInfo ti : trailList) {                 // There are trails associated, find out what types
                artefact.trailStatus |= ti.trailType;        // Set the trail type using bitwise OR
            }
            Log.d(context.getResources().getString(R.string.app_name) + ": TrailManager, browseArtefactID", "Trails found = " + trailList.size());
        }
        else {
            Log.d(context.getResources().getString(R.string.app_name) + ": TrailManager, browseArtefactID", "No Trails found for Artefact ID " + artefact.artefactID);
        }

        return artefact;
    }


    /**
     * Checks if the artefact is within the current trail
     * @param artefactID Artefact to search for
     * @return indicates whether artefact found or not. -1 = Not Found, 0 = Not on Trail, 1 = Found
     */
    public int checkArtefact(int artefactID) {
        if(mode != MODE_ON_TRAIL) {
            return 0; // Indicate not on a trail
        }

        for(TrailStepInfo s : trailSteps) {
            if(s.qrCode == artefactID) {
                return 1; // Indicate the artefact is in this trail
            }
        }

        return -1; // Artefact not found
    }


    /**
     * Sets the Trail based on ID and loads the appropriate trail steps
     * @param trailID Required trail ID
     * @return True = trail set, False = trail not set
     */
    public boolean setTrail(int trailID) {
        currentTrail = null; // Reset any current trail details
        mode = MODE_BROWSE;  // Not on a trail
        boolean trailFound = false;  // Indicate whether trail found or not

        // First check if the trail is already loaded
        if(trailList != null) {
            for(int t = 0; t < trailList.size(); t++) { // search for trail info in trailList and set currentTrail
                if(trailList.get(t).trailID == trailID) {
                    currentTrail = trailList.get(t); // Set the current trail
                    trailFound = true; // flag trail found
                }
            }
        }

        // If the required trail is not already loaded, try to get it from the database
        if(!trailFound) {
            clearData(); // Clear any old data from the trail manager

            if(getTrail(trailID)) {// If the trail was loaded from the database
                currentTrail = trailList.get(0); // Get the trail from the list
            }
            else {// trail not found
                return false;
            }
        }

        // Trail found

        mode = MODE_ON_TRAIL; // Indicate on a trail

        trailFound = getTrailSteps(trailID); // Populate the trail steps and indicate True = success or False = failure

        return trailFound; // Indicate if getTrailSteps was successful
    }

    /**
     * Gets all trails that the specified artefact is associated with.
     * This initialises a new TrailList, thus getting rid of old information.
     * @param artefactID int holding required artefact ID
     * @return boolean True method succeeded, False = method failed
     */
    public List<TrailInfo> getArtefactTrails(int artefactID) {
        String queryString;

        trailList = new ArrayList<>(); // Start a new empty trailList

        // Query string to retrieve trails associated with the artefact
        queryString =
                "SELECT trail._id as " + TRA_ID + ", * FROM trail " +
                "JOIN trailstep ON trail._id = trailStep.stp_trailID AND trailStep.stp_qrCode = " + artefactID;

        // Query database for the trails info that artefactID is part of
        if(!getDatabaseTrails(queryString)) {
            Log.d(context.getResources().getString(R.string.app_name), "Query failed, No trails found for artefactID: " + artefactID);
            trailList = null; // Method failed - no trails found
        }

        return trailList; // Return trails or null
    }


    /**
     * Get all trails that belong to a specific exhibit, these are stored in trailList
     * @param exhibitType Exhibit Type from database that requires trails
     * @return List of trails found or NULL if query failed
     */
    public List<TrailInfo> getExhibitTrails(int exhibitType) {
        String queryString;

        // Query string to retrieve trails associated with the artefact
        queryString =
                "SELECT trail._id as " + TRA_ID + ", * FROM trail " +
                        "JOIN exhibit on trail.tra_exhibitID = exhibit._id AND exhibit.exh_type = " + exhibitType;

        // Query database for the trails info that artefactID is part of
        if(!getDatabaseTrails(queryString)) { // This populates trailList or set it to null
            Log.d(context.getResources().getString(R.string.app_name), "Query failed, No trails found of type: " + exhibitType);
            return null; // method failed no trails found
        }

        return trailList; // Return the trails
    }

    /**
     * Gets the steps for the current trail
     * @return List of trailStepInfo containing trail steps
     */
    public List<TrailStepInfo> getCurrentTrailSteps() {
        return trailSteps;
    }


    /**
     * Gets a specific trail from the database based on the trailID
     * @param trailID Required trail ID
     * @return Trail infor
     */
    private boolean getTrail(int trailID) {
        TrailInfo trail = null;

        // Querty string used to retrieve the trail information from the database
        String queryString = "SELECT _id AS " + TRA_ID + ", * FROM trail WHERE " + TRA_ID + " = " + trailID;

        // Query database for the trails info that artefactID is part of.
        if(!getDatabaseTrails(queryString)) { // This populates trailList or set it to null
            Log.d(context.getResources().getString(R.string.app_name), "Query failed, No trail found with ID: " + trailID);
            return false; // method failed no trails found
        }

        return true; // Trail found
    }

    /**
     * Gets trail steps based on the specified trail
     * This initialises a new TrailStepInfo list, thus getting rid of old information.
     * @param trailID The ID for the required trail
     * @return Boolean True = Trail steps retrieved, False = No trail steps for trail found
     */
    private boolean getTrailSteps(int trailID) {
        Cursor cursor;
        String resultString;
        ParseRecord trailInfo;

        trailSteps = new ArrayList<>(); // Start a new empty trailStep list

        // Query = SELECT trail._id as tra_trailID, tra_name, trailStep.* FROM trail join trailStep on stp_trailID = tra_trailID
        String query = "SELECT _id AS " + STP_ID + ", * FROM trailStep WHERE " + STP_TRAIL_ID + " = " + trailID;

        cursor = db.queryDatabase(query);

        if(cursor == null) {
            Log.d(context.getResources().getString(R.string.app_name), "Query failed, No trail steps for trail: " + trailID);
            return false; // Method failed
        }

        cursor.moveToFirst(); // Make sure at first record

        while(!cursor.isAfterLast()) { // Iterate through result list adding each trail record to artefact

            resultString = DatabaseUtils.dumpCurrentRowToString(cursor); // Get artefact info as string
            trailInfo = new ParseRecord(resultString); // Parse the artefact info into name:value pairs

            trailSteps.add(new TrailStepInfo(trailInfo)); // Add the trail step to trailSteps list

            cursor.moveToNext();
        }

        cursor.close(); // Cursor finished with, so close it

        return true;// Method succeeded - trails added to the list
    }


    /**
     * Query database for artefact information based on ID and populate result into ArtefactInfo
     * @param artefactID The ID for the required artefact
     * @return ArtefactInfo containing the artefact information and any trails that it belongs to
     */
    private ArtefactInfo getDatabaseArtefact(int artefactID) {
        Cursor cursor;
        ParseRecord artefactInfo;
        ArtefactInfo artefact; // = new ArtefactInfo(); // Instantiate new empty ArtefactInfo
        String resultString;
        String queryString =
                "SELECT _id as " + ART_ID + ", * from artefact where artefact._id = " + artefactID;

        // Query database for the artefact info and retrieve the details
        cursor = db.queryDatabase(queryString);

        if(cursor == null) { // Query failed handle this - no such record
            // Do something clever here
            Log.d(context.getResources().getString(R.string.app_name), "Query failed, no such artefact: " + artefactID);
            return null;
        }

        cursor.moveToFirst(); // Make sure at first record (there should only be one)

        resultString = DatabaseUtils.dumpCurrentRowToString(cursor); // Get artefact info as string

        cursor.close(); // Cursor finished with, so close it

        artefactInfo = new ParseRecord(resultString); // Parse the artefact info into name:value pairs

        artefact = new ArtefactInfo(artefactInfo); // Instantiate and populate new artefactInfo from the cursor

        return artefact;
    }


    /**
     * Query the database for trails based on supplied SQL statement, these are stored in trailList
     * @param queryString SQL query
     * @return True = found trails, False = no trails found
     */
    private boolean getDatabaseTrails(String queryString) {
        Cursor cursor;
        ParseRecord trailInfo;
        String resultString;

        trailList = new ArrayList<>(); // Initialise trailList to new Empty List

        // Query database for the trails info that artefactID is part of
        cursor = db.queryDatabase(queryString);

        if(cursor == null) { // Query failed handle this - no such record
            // Do something clever here
            Log.d(context.getResources().getString(R.string.app_name), "Query failed, No trails found! ");
            return false; // Method failed
        }

        cursor.moveToFirst(); // Make sure at first record

        while(!cursor.isAfterLast()) { // Iterate through result list adding each trail record to artefact

            resultString = DatabaseUtils.dumpCurrentRowToString(cursor); // Get artefact info as string
            trailInfo = new ParseRecord(resultString); // Parse the artefact info into name:value pairs

            trailList.add(new TrailInfo(trailInfo)); // Add the trail to trailList

            cursor.moveToNext();
        }

        cursor.close(); // Cursor finished with, so close it

        return true;// Method succeeded - trails added to the list
    }

}

