package riskybusiness.riskybusinessmuseumapp.root.trailmanager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Chris on 21/03/2015.
 * Holds the information about a question from the trailStep table
 */

// Question information, holds information regarding a single question
public class TrailStepInfo implements AppConstants, DatabaseConstants {
    int stepID;   // Question ID
    int trailID;      // trail ID
    int questionType; // Question type indicator 0 = normal, 1 = multi-choice, 2 = picture question
    String question;  // Question string
    String answer;    // Answer to question
    int qrCode;       // QR code with answer in it
    int imageID;      // Image ID reference to image table

    List<String> multiChoiceAnswers; // Answers to multi choice as List, first element = answer

    /**
     * Constructs a TrailStepInfo object based on the data supplied in trailStep
     * @param trailStep ParseRecord holding the name:value pairs of trail step information
     */
    public TrailStepInfo(ParseRecord trailStep) {

        setData(trailStep); // Populate member variables

        if(questionType == QUESTION_MULTI) { // If the question is multi-choice, split the answer string into a list
            multiChoiceAnswers = parseMultiChoice(answer);
        }

    }

    /**
     * Sets the trailStep data
     * @param trailStep ParseRecord holding the name:value pairs that will be used to populate the TrailStepInfo
     */
    private void setData(ParseRecord trailStep) {
        String temp;

        // Get the trail step ID
        temp = trailStep.getValue(STP_ID);
        stepID = temp.toUpperCase().equals("NULL") ? 0 : Integer.parseInt(temp); // Get value from temp or 0 if null

        // Get the trail ID that this step belongs to
        temp = trailStep.getValue(STP_TRAIL_ID);
        trailID = temp.toUpperCase().equals("NULL") ? 0 : Integer.parseInt(temp); // Get value from temp or 0 if null

        // Get question type
        temp = trailStep.getValue(STP_QUESTION_TYPE);
        questionType = temp.toUpperCase().equals("NULL") ? 0 : Integer.parseInt(temp); // Get value from temp or 0 if null

        // Get the question
        question = trailStep.getValue(STP_QUESTION);

        // Get the answer
        answer = trailStep.getValue(STP_ANSWER);

        // Get the QR code
        temp = trailStep.getValue(STP_QR_CODE);
        qrCode = temp.toUpperCase().equals("NULL") ? 0 : Integer.parseInt(temp); // Get value from temp or 0 if null

        // Get the image ID
        temp = trailStep.getValue(STP_IMAGE);
        imageID = temp.toUpperCase().equals("NULL") ? 0 : Integer.parseInt(temp); // Get value from temp or 0 if null
    }

    // Converts comma separated string into string array for multi-choice
    private List<String> parseMultiChoice(String answerString) {
        List<String> answerList;

        // Convert answer string int array list
        answerList = Arrays.asList(answerString.split("\\s*,\\s*"));

        return answerList; // Return the list of answer strings - in original order
    }



}
