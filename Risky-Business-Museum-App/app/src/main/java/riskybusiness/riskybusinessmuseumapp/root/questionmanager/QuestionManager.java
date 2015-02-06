package riskybusiness.riskybusinessmuseumapp.root.questionmanager;

import android.content.Intent;

import java.util.LinkedList;

import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;
import riskybusiness.riskybusinessmuseumapp.root.Activities.MultiChoiceActivity;

import static java.lang.Integer.parseInt;

/**
 *
 * Class manages questions and answers
 * Created by Chris on 05/02/2015.
 */
public class QuestionManager {
    LinkedList<TrailStep> steps; // = new LinkedList<TrailStep>(); // List for trail steps
    TrailStep question;    // The current trail step
    PopulateQuestions qs;  // Will hold the trail steps for this trail
    int questionNum = 0; // The number of the current question
    int answerNum = 0;
    boolean trailEnded = false; // Trail has not ended
    MultiChoiceActivity mc;
    HomePageActivity hm;

    // Construct question manager passing it the value received from the QR code
    public QuestionManager(HomePageActivity hm) {
        //this.mc = mc;
        this.hm = hm;

        /**
         // Next we would query the database to identify what trail(s) that code belongs to
         // Output to the screen the available trails and let the user select the required one
         // Then retrieve the selected trail questions from the database
         // and start the trail
         */
        qs = new PopulateQuestions(); // Populate simulated trail
        steps = qs.questionList;      // get the questions list
        questionNum = 0;              // Set the question counter
        trailEnded = false;           // The trail has not ended
    }

    /**
     * nextQuestion
     * @return TRUE trail has ended, FALSE trail not ended
     * Runs the next question activity according to the question type
     */
    public boolean nextQuestion() { // Gets the next/first question from the list
        // Starting from the current question
        // According to the question type, display the required intent passing it the question details

        if (trailEnded) { // Trail has already ended - ensure we don't outrun the array
            return true;
        }

        question = steps.get(questionNum); // copy the current question

        // Open the intent and get the answer - in the case of multi choice the intent should
        // reutrn 0 = correct answer, -1 wrong answer

        switch (question.questionType) {
            case 0: // normal question
                //answerNum = ...; // Open intent and Get the answer
                hm.callSingleAnswerActivity(question.question, question.answer);
                break;
            case 1: // Multi choice question
                //answerNum = ...; // Open intent and Get the answer
                // call intent passing question
                hm.callMultiChoiceActivity(question.question, question.answer);
                break;
            case 2: // Picture question
                //answerNum = ...; // Open intent and Get the answer
                break;
            default:
                // Oh crap - error!
                break;
        }

        questionNum++; // increment question number

        if (questionNum >= steps.size()) { // Trail ended
            trailEnded = true;
        }

        return trailEnded;
    }

    public int receiveData(int score){
        return score;
    }

}
