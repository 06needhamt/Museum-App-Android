package riskybusiness.riskybusinessmuseumapp.root.questionmanager;

import java.util.LinkedList;

/**
 *
 * Used to simulate getting data back from database query
 * Created by Chris on 05/02/2015.
 */
public class PopulateQuestions {
    public LinkedList<TrailStep> questionList = new LinkedList<TrailStep>(); // Instantiate empty question list

    // Data to be used when initialising simulated questions
    private int[] questionType = {1, 0, 0, 0, 0, 1, 1, 0, 0}; // Question type array

    private String[] questions = { // Questions
            "Is there a 'for some reason dialog fragment' in this app?",
            "This brass staff was probably kept on a royal alter",
            "This brass figure depicts 'Cat's whisker' scars",
            "A costume ornament made from a wild cat",
            "The Oba used this to make contact with the spirits of his ancestors",
            "Ahianmwen utoye, the?",
            "The Oba is the:",
            "This realistic head dates from the earliest period of Edo court sculpture",
            "Worn on the left hip of the Chief at palace ceremonies"
    };

    private String[] answers = { // Either the artifact number to be retrieved from the QR or multi-choice options
            "Yes, No, Maybe, What?",
            "1",
            "2",
            "6",
            "8",
            "Bird of disaster, Queen Mother, Son of the leopard home, Palace of the King",
            "Head of the Kingdom, Queen Mother, War Chief, High Priest",
            "16",
            "24"
    };

    public PopulateQuestions() { // constructor

        for (int t = 0; t < questionType.length; t++) { // Populate the questions list
            TrailStep q = new TrailStep();

            q.questionType = questionType[t];
            q.question = questions[t];
            q.answer = answers[t];

            questionList.add(q); // Add the question to the list

        }
    }
//
//
//    public List<TrailStep> getQuestions() { // Returns the populated list of questions;
//
//    }
}

