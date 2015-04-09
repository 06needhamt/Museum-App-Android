package riskybusiness.riskybusinessmuseumapp.root.trailmanager;

import java.lang.Integer;
import java.lang.String;

import riskybusiness.riskybusinessmuseumapp.root.Database.DatabaseConstants;

//import riskybusiness.databasetest.DatabaseConstants;
//import riskybusiness.databasetest.ParseRecord;

/**
 * Created by Chris on 21/03/2015.
 * ArtefactInfo holds the information about an artefact.
 */
// Holds the information about a single artefact
public class ArtefactInfo implements DatabaseConstants {
    public int artefactID; // ID of the artefact
    public String name; // Title of the artefact
    public String description; // desctription of artefact
    public String imageName;   // Name of image file
    public String museumNo;    // Museum's code for the artefact (if available)
    public int exhibitID;      // The exhibit that the artefact belongs to
    public int trailStatus;    // Is the artefact: a) on its own, b) part of a normal trail, c) part of explorer trail, d) trail and explorer
    public int floor;          // floor number of artefact

    //DatabaseConstants dc = new DatabaseConstants();

    /**
     * Construct an ArtefactInfo object based on ParseRecord
     * @param artefactInfo
     */
    public ArtefactInfo(ParseRecord artefactInfo) {
        artefactID = 0; // Initialise artefactID to 0 indicates artefact not yet initialised by call to populateArtefactInfo

        populateArtefactInfo(artefactInfo);
    }

    /**
     * Takes the information from the ParseRecord and populates the artefact with it
     * @param record  ParseRecord holding the artefact information in name:value pairs
     */
    public void populateArtefactInfo(ParseRecord record) {
        String resultString; // holds the raw result string when retrieved from cursor
        ParseRecord artefactInfo; // This holds the parsed record information

        artefactID = Integer.parseInt(record.getValue(ART_ID));

        exhibitID = Integer.parseInt(record.getValue(ART_EXHIBIT_ID));

        name = record.getValue(ART_NAME);
        //title = record.getValue("title"); // Get the title of the record

        description = record.getValue(ART_DESCRIPTION);
        museumNo = record.getValue(ART_MUSEUM_NO);

        record.printPairs(); // Output the pairs to the console
    }
}

