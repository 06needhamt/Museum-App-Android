package riskybusiness.riskybusinessmuseumapp.root.questionmanager;

import java.util.LinkedList;
import java.util.List;

import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;
import riskybusiness.riskybusinessmuseumapp.root.Activities.MultiChoiceActivity;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailManager;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailStepInfo;

import static java.lang.Integer.parseInt;

/**
 *
 * Class manages questions and answers
 * Created by Chris on 05/02/2015.
 */
public class QuestionManager implements AppConstants {
    private List<TrailStepInfo> steps; // List for trail steps
    TrailStepInfo question; // The current trail step
    //PopulateQuestions qs;   // Will hold the trail steps for this trail
    private int questionNum = 0; // The number of the current question
    int answerNum = 0;
    private boolean trailEnded = false; // Trail has not ended

    MultiChoiceActivity mc;
    HomePageActivity hm;

    // Construct question manager passing it the value received from the QR code
    public QuestionManager(HomePageActivity hm, List<TrailStepInfo> trailSteps) {
        //this.mc = mc;
        this.hm = hm;
        this.steps = trailSteps; // Assign the trail steps

        /**
         // Next we would query the database to identify what trail(s) that code belongs to
         // Output to the screen the available trails and let the user select the required one
         // Then retrieve the selected trail questions from the database
         // and start the trail
         */
        //qs = new PopulateQuestions(); // Populate simulated trail
        //setSteps(qs.questionList);      // get the questions list
        setQuestionNum(0);              // Set the question counter to first question
        setTrailEnded(false);           // The trail has not ended
    }

    /**
     * nextQuestion
     * @return TRUE trail has ended, FALSE trail not ended
     * Runs the next question activity according to the question type
     */
    public boolean nextQuestion() { // Gets the next/first question from the list
        // Starting from the current question
        // According to the question type, display the required intent passing it the question details

        if (isTrailEnded()) { // Trail has already ended - ensure we don't outrun the array

            //DEBUG: trying out trail result screen
            if(getQuestionNum() == getSteps().size()) {
                hm.callTrailResultActivity();
            }
            //DEBUG END---
            return true;

        }

        question = steps.get(questionNum); // Get the current question from the trail steps List

        //question = getSteps().get(getQuestionNum()); // copy the current question

        // Open the intent and get the answer - in the case of multi choice the intent should
        // return 0 = correct answer, -1 wrong answer

        switch (question.getQuestionType()) {
            case QUESTION_SINGLE: // normal question
                //answerNum = ...; // Open intent and Get the answer
                hm.callSingleAnswerActivity(question.getQuestion(), question.getAnswer());
                break;
            case QUESTION_MULTI: // Multi choice question
                //answerNum = ...; // Open intent and Get the answer
                // call intent passing question
                hm.callMultiChoiceActivity(question.getQuestion(), question.getAnswer()); // Multi-choice answers in comma separated list
                break;
            case QUESTION_PICTURE: // Picture question
                //answerNum = ...; // Open intent and Get the answer
                hm.callPictureQRQuestionActivity(question.getQuestion(), question.getAnswer(), question.getImageName());
                break;
            case QUESTION_MULTI_PICTURE: // Multi-choice picture question
                //answerNum = ...; // Open intent and Get the answer
                hm.callPictureMultiChoiceActivity(question.getQuestion(), question.getAnswer(), question.getImageName());
                break;
            default:
                // Oh crap - error!
                throw new Error("This question type does not exist.");
                //break;
        }

        setQuestionNum(getQuestionNum() + 1); // increment question number

        if (getQuestionNum() >= getSteps().size()) { // Trail ended
            setTrailEnded(true);
        }

        return isTrailEnded();
    }

    public int receiveData(int score){
        return score;
    }

    public boolean isTrailEnded() {
        return trailEnded;
    }

    public void setTrailEnded(boolean trailEnded) {
        this.trailEnded = trailEnded;
    }

    public List<TrailStepInfo> getSteps() {
        return steps;
    }

    public void setSteps(List<TrailStepInfo> steps) {
        this.steps = steps;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }
}
