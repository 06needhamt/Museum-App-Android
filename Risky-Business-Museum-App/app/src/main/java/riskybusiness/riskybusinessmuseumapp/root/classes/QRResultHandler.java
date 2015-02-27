package riskybusiness.riskybusinessmuseumapp.root.classes;

/**
 * this class handles the string returned from QRScannerActivity
 * Created by Thomas Needham on 27/02/2015.
 */
public final class QRResultHandler {
    private final String identifier = "http://riskybuisiness.co.uk/";
    // result string to holsd the final result to be returned to the intent calling this class
    private String result;
    public QRResultHandler(String Content)
    {
        returnresult(Content);
    }
    private String returnresult(String Content)
    {
        if(Content.contains(identifier))
        {
            result = Content.substring(identifier.length(),Content.length());
        }
        else
        {
            result = "No Identifier";
        }
        return result;
    }

}
