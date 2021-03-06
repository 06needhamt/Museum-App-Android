package riskybusiness.riskybusinessmuseumapp.root.classes;

import android.util.Log;

import riskybusiness.riskybusinessmuseumapp.root.AppConstants;

/**
 * this class handles the string returned from @link QRScannerActivity
 * Created by Thomas Needham on 27/02/2015.
 */
public final class QRResultHandler  implements AppConstants{
    private final String identifier = QR_RESULT_IDENTIFIER;
    // result string to hold the final result to be returned to the intent calling this class
    private String result;
    public QRResultHandler(String Content)
    {
        returnresult(Content);
    }

    /**
     * This method parses and returns the result string taken from a {@link riskybusiness.riskybusinessmuseumapp.root.Activities.QRScannerActivity}
     * @param Content String containing the full contents of a read QR-code
     * @return A substring containing the content without the identifier or "No Content" if content
     * is null or "No Identifier" if the content is not null, but is not a valid QR-code containing
     * the expected identifier.
     */
    private String returnresult(String Content)
    {
        if(Content == null || Content.equals("No Value") || Content.contains("null")) {
            setResult("No Content");
        }
        else if(Content.contains(identifier))
        {
            setResult(Content.substring((identifier.length() + 9),Content.length())); //9 is the amount of characters the Zhinx library adds as a content prefix, i.e. "CONTENT: "
            //For example: "CONTENT: http://riskybusiness.co.uk/ID0001" would be converted to "ID0001".
        }
        else
        {
            setResult("No Identifier");
        }
        return getResult();
    }

    public String getResult() {
        return result;
    }

    /**
     * Gets the code from the QR result string
      * @return result value or -1 in case of invalid QR result
     */
    public int getCode() {
        int code;

        try {
            code = Integer.parseInt(result); // get the value of result
            return code;
        } catch(NumberFormatException e) { // result contains non-numeric characters
            Log.e("riskybusinessmuseumapp", e.getMessage());
        }

        return -1; // Error in code or no content

    } // Gets the integer version of the code from the QR result string

    private void setResult(String result) {
        this.result = result;
    }
}
