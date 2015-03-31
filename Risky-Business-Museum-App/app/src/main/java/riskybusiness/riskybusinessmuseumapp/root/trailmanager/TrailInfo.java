package riskybusiness.riskybusinessmuseumapp.root.trailmanager;

/**
 * Created by Chris on 21/03/2015.
 */
// Trail contents includes general information about the trail and all questions belonging to the trail
public class TrailInfo implements DatabaseConstants{
    public int trailID;    // Trail ID - database primary key
    public int exhibitID;    // Exhibit ID - database primary key
    public String name;    // name of the trail
    public String description; // Trail description string
    public int trailType;    // Type of trail

    /**
     * Constructs a TrailInfo object based on the data supplied
     * @param trailInfo Data used when constructing the object
     */
    public TrailInfo(ParseRecord trailInfo) { // Constructor
        setData(trailInfo);
    }

    /**
     * Sets the mamber variables based on trailInfo
     * @param trailInfo
     */
    private void setData(ParseRecord trailInfo) {
        String temp;

        // Get the ID
        temp = trailInfo.getValue(TRA_ID);
        trailID = temp.toUpperCase().equals("NULL") ? 0 : Integer.parseInt(temp); // Get value from temp or 0 if null

        // Get the exhibit ID for this trail
        temp = trailInfo.getValue(TRA_EXHIBIT_ID);
        exhibitID = temp.toUpperCase().equals("NULL") ? 0 : Integer.parseInt(temp); // Get value from temp or 0 if null

        // Get the name
        name = trailInfo.getValue(TRA_NAME);

        // Get the description
        description = trailInfo.getValue(TRA_DESCRIPTION);

        // Get the trail type
        temp = trailInfo.getValue(TRA_TYPE);
        trailType = temp.toUpperCase().equals("NULL") ? 0 : Integer.parseInt(temp); // Get value from temp or 0 if null
    }
}




