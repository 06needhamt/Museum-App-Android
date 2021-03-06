package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.AreYouSureToSkipDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.BackToMainMenuDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.IConfirmDialogCompliant;


public class MultiChoiceActivity extends FragmentActivity implements IConfirmDialogCompliant, AppConstants {
    private int screenHeight;
    private int screenWidth;
    private Button btnMcA, btnMcB, btnMcC, btnMcD, btnSkipOrNext;
    private Button[] answerButtons = { btnMcA, btnMcB, btnMcC, btnMcD};
    private TextView multiChoiceQuestion, ScoreField, TrailPositionField;
    private final int MAX_SCORE = MAX_SCORE_FOR_ONE_QUESTION;
    private boolean endtrail = false; //set this to true for the trail to end after this question and false for it not to end after this question
    private int scoreForThisQuestion;
    private int totalScore;
    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;
    private boolean hasBeenSkipped = false;
    private int currentPosition;
    private int trailLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choice);
        scoreForThisQuestion = MAX_SCORE;
        multiChoiceQuestion = (TextView) findViewById(R.id.multiChoiceQuestion);
        ScoreField = (TextView) findViewById(R.id.McScore);
        TrailPositionField = (TextView) findViewById(R.id.McTrailPosition);
        setButtonListeners();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;

        createLayoutParams(screenHeight, screenWidth);
        Bundle bundle = getIntent().getExtras();
        unpackBundle(bundle); //unpacking bundle and assigning all relevant data

        //randomising the answers
        theOldSwitcheroo(); //this will call populateButtons()
        formatButtons(); // formatting button text color and text size

    }

    private void unpackBundle(Bundle bundle){ //unpacking bundle and assigning all relevant data
        question = bundle.getString(QUESTION_TAG);
        String temp = bundle.getString(ANSWER_TAG);
        totalScore = bundle.getInt(SCORE_TAG, 0); //getting total score with error handling for 0
        applyAnswers(temp);
        correctAnswer = answers.get(0);
        currentPosition = bundle.getInt(TRAIL_POSITION_TAG, -1);
        trailLength = bundle.getInt(TRAIL_LENGTH_TAG, -1);
    }

    /**
     * This method will randomise the order of the four questions and populate the buttons with the new answers.
     * The correct answer is still saved in the String correctAnswer and will be checked against when pressing one of the buttons
     */
    private void theOldSwitcheroo(){
        Random rd = new Random();
        LinkedList<String> tempy = new LinkedList<>();
        tempy.addAll(answers);
        List<String> newAnswers = new ArrayList<>();
        int currentRoll;
        for(int i = 0; i < 4; i++){
            currentRoll = rd.nextInt(4 - i); // returns pseudo random integer ranging from 0 (inclusive) to the given parameter (exclusive)
            newAnswers.add(tempy.get(currentRoll)); //add the selected answer to the new list of answers
            tempy.remove(currentRoll);
        }
        populateButtons(newAnswers);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            onBackPressed();
        }
        return super.onKeyDown(keycode, event);
    }

    @Override
    public void onBackPressed(){
        BackToMainMenuDialogFragment dialog = new BackToMainMenuDialogFragment(R.string.BackToMainFragmentText, this);
        dialog.show(this.getFragmentManager(), null);
    }

    private void formatButtons() {
        Button[] btns = new Button[4];
        btns[0] = btnMcA;
        btns[1] = btnMcB;
        btns[2] = btnMcC;
        btns[3] = btnMcD;

        //remember the buttons padding
        int pl, pr, pt, pb;
        pl = btnMcA.getTotalPaddingLeft();
        pt = btnMcA.getTotalPaddingTop();
        pr = btnMcA.getTotalPaddingRight();
        pb = btnMcA.getTotalPaddingBottom();
        for (Button b : btns) {
        //    b.setTextColor(getResources().getColor(R.color.RoyalBlue)); //setting text colour to white
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15); //setting text size to 15sp (scaled pixels)
            b.setPadding(pl, pt, pr, pb);
        }

        pl = btnSkipOrNext.getPaddingLeft();
        pt = btnSkipOrNext.getPaddingTop();
        pr = btnSkipOrNext.getPaddingRight();
        pb = btnSkipOrNext.getPaddingBottom();
        //btnSkipOrNext.setTextColor(getResources().getColor(R.color.Black));
        btnSkipOrNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        btnSkipOrNext.setPadding(pl, pt, pr, pb);
    }

    private void populateButtons(List<String> list){
        btnMcA.setText(list.get(0));
        btnMcB.setText(list.get(1));
        btnMcC.setText(list.get(2));
        btnMcD.setText(list.get(3));
        btnSkipOrNext.setText(R.string.Skip);
        multiChoiceQuestion.setText(question);
        updateScore(totalScore); //updating score field with the total score
        updateTrailPosition(currentPosition, trailLength); //setting the current trail position, i.e. Question 1 of 10
    }

    private void updateScore(int score){
        ScoreField.setText("Score: " + score);
    }

    private void updateTrailPosition(int currpos, int max){
        TrailPositionField.setText("Question " + (currpos + 1) + " of " + (max)); //add 1 to them since these values come from a 0 indexed list
    }

    /**
     * Splitting the passed String at commas and converting it to a List<String>.
     * @param temp String containing comma separated answers to the question, first one being the correct answer
     */
    private void applyAnswers(String temp){
        List<String> tempList;

        tempList = Arrays.asList(temp.split("\\s*,\\s*"));

        answers = new ArrayList<>(tempList);

        // In case there are not enough answers to the question - prevents null pointer and indicates incorrect answer list
        while(answers.size() < 4)
            answers.add("MISSING ANSWER");

    }

    /**
     * Converting the button from saying Skip to saying Next.
     * This should be called when the user gives the correct answer.
     */
    private void makeSkipToNextButton() {
        btnSkipOrNext.setText(R.string.Next);
    }

    /**
     * Sets the onClickListeners for all buttons on the screen.
     */
    private void setButtonListeners(){
        //BUTTON A
        btnMcA = (Button) findViewById(R.id.btnMcA);
        btnMcA.setClickable(true);
        btnMcA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(), "Button A clicked", Toast.LENGTH_LONG).show(); //////////////delete
                if(checkAnswer(btnMcA)) {
                    //passData(); // Correct answer
                    btnSkipOrNext.setVisibility(View.VISIBLE);
                    btnSkipOrNext.setClickable(true);
                    makeSkipToNextButton();
                    disableAllAnswerButtons();
                }
                else {
                    numGuesses(btnMcA); // Incorrect answer
                }
            }
        });

        //BUTTON B------------------------------------------
        btnMcB = (Button) findViewById(R.id.btnMcB);
        btnMcB.setClickable(true);
        btnMcB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(), "Button B clicked", Toast.LENGTH_LONG).show();//////////////////
                if(checkAnswer(btnMcB)) {
                    //passData(); // Correct answer
                    btnSkipOrNext.setVisibility(View.VISIBLE);
                    btnSkipOrNext.setClickable(true);
                    makeSkipToNextButton();
                    disableAllAnswerButtons();
                }
                else {
                    numGuesses(btnMcB); // Incorrect answer
                }
            }
        });

        //BUTTON C------------------------------------------
        btnMcC = (Button) findViewById(R.id.btnMcC);
        btnMcC.setClickable(true);
        btnMcC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(), "Button C clicked", Toast.LENGTH_LONG).show();///////////////
                if(checkAnswer(btnMcC)) {
                    //passData(); // Correct answer
                    btnSkipOrNext.setVisibility(View.VISIBLE);
                    btnSkipOrNext.setClickable(true);
                    makeSkipToNextButton();
                    disableAllAnswerButtons();
                }
                else {
                    numGuesses(btnMcC); // Incorrect answer
                }
            }
        });

        //BUTTON D------------------------------------------
        btnMcD = (Button) findViewById(R.id.btnMcD);
        btnMcD.setClickable(true);
        btnMcD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(), "Button D clicked", Toast.LENGTH_LONG).show();//////////////
                if(checkAnswer(btnMcD)) {
                    //passData(); // Correct answer
                    btnSkipOrNext.setVisibility(View.VISIBLE);
                    btnSkipOrNext.setClickable(true);
                    makeSkipToNextButton();
                    disableAllAnswerButtons();
                }
                else {
                    numGuesses(btnMcD); // Incorrect answer
                }
            }
        });

        //BUTTON SKIP OR NEXT-------------------------------
        btnSkipOrNext = (Button) findViewById(R.id.btnMcSkipOrNext);
        btnSkipOrNext.setVisibility(View.VISIBLE);
        btnSkipOrNext.setClickable(true);
        btnSkipOrNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSkipOrNext.getText().equals(getResources().getString(R.string.Skip))){ //if the button is still set to Skip, reduce the score to 0
                    applySkipDialog();
                }
                else if (btnSkipOrNext.getText().equals(getResources().getString(R.string.Next))){
                    passData();
                }
            }
        });
    }

    /**
     * Creates an AreYouSureToSkipDialogFragment
     */
    private void applySkipDialog(){
        AreYouSureToSkipDialogFragment dialog = new AreYouSureToSkipDialogFragment(R.string.SkipFragmentText, this);
        dialog.show(getFragmentManager(), null);
    }


    /**
     * Checks the content of the button to see if it is the right answer.
     * @param btn
     * @return
     */
    private boolean checkAnswer(Button btn) {
        // Highlight button to indicate used

        String answer = (String)btn.getText(); // Get button text
//        int pl, pr, pt, pb; //remembering padding
//        pl = btn.getTotalPaddingLeft();
//        pt = btn.getTotalPaddingTop();
//        pr = btn.getTotalPaddingRight();
//        pb = btn.getTotalPaddingBottom();
//
//        //remembering shadow
//        int sc = btn.getShadowColor();
//        float sdx = btn.getShadowDx();
//        float sdy = btn.getShadowDy();
//        float sradius = btn.getShadowRadius();

        if(answer.equals(correctAnswer)) {

            btn.setBackgroundColor(getResources().getColor(R.color.TransparentLightGreen));
//            btn.setPadding(pl, pt, pr, pb); //setting padding after changing colour
//            btn.setShadowLayer(sradius, sdx, sdy, sc); //setting shadow after changing colour
            //adding spaces to the toast as they get removed from the xml files
            Toast.makeText(getBaseContext(), getResources().getString(R.string.CorrectGuessPart1of2) + " " + scoreForThisQuestion + " " + getResources().getString(R.string.CorrectGuessPart2of2), Toast.LENGTH_LONG).show();
            updateScore(scoreForThisQuestion + totalScore);

        }
        else {
            btn.setBackgroundColor(getResources().getColor(R.color.TransparentLightRed));
//            btn.setPadding(pl, pt, pr, pb); //setting padding after changing colour
//            btn.setShadowLayer(sradius, sdx, sdy, sc); //setting shadow after changing colour
            //adding spaces to the toast as they get removed from the xml files
            Toast.makeText(getBaseContext(), getResources().getString(R.string.WrongGuessPart1of2) + " " + scoreForThisQuestion / 50 + " " + getResources().getString(R.string.WrongGuessPart2of2), Toast.LENGTH_LONG).show();
         }

//        CountDownTimer timer = new CountDownTimer(5000, 1) { // Pause the thread
//            @Override
//            public void onTick(long millisUntilFinished) { int y = 10; while()}
//
//            @Override
//            public void onFinish() {
//               int x = 0; // just something to give the timer to do!
//            }
//        };
//        timer.start();



        return answer.equals(correctAnswer) ? true : false;
    }

    private void numGuesses(Button btn) { // Check the guesses state and adjust score
        scoreForThisQuestion -= MAX_SCORE / 4; // Reduce the score
        if(scoreForThisQuestion <= 0) { // Check if any guesses left
            // no guesses exit activity returning data
        }
        //setting the button that has been clicked to un-clickable state
        btn.setClickable(false);
    }

    private void resetButtonsToClickable(){
        btnMcA.setClickable(true);
        btnMcB.setClickable(true);
        btnMcC.setClickable(true);
        btnMcD.setClickable(true);
        btnSkipOrNext.setClickable(true);
    }


    private void createLayoutParams(int screenHeight, int screenWidth){
        btnMcA.setLayoutParams(btnMcALayout(screenHeight,screenWidth));
        btnMcB.setLayoutParams(btnMcBLayout(screenHeight, screenWidth));
        btnMcC.setLayoutParams(btnMcCLayout(screenHeight, screenWidth));
        btnMcD.setLayoutParams(btnMcDLayout(screenHeight, screenWidth));
        btnSkipOrNext.setLayoutParams(btnNextOrSkipLayout(screenHeight, screenWidth));
        multiChoiceQuestion.setLayoutParams(multiChoiceQuestionLayout(screenHeight, screenWidth));
    }

    private FrameLayout.LayoutParams multiChoiceQuestionLayout(int screenHeight, int screenWidth){
        int textHeight, textWidth;
        textHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        textWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(textWidth, textHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.05); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams btnMcALayout (int screenHeight, int screenWidth) {
        int btnAHeight, btnAWidth;
        btnAHeight = (int) (screenHeight * 0.10);
        btnAWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnAWidth, btnAHeight); //setting gravity to center horizontal
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.33); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams btnMcBLayout (int screenHeight, int screenWidth) {
        int btnBHeight, btnBWidth;
        btnBHeight = (int) (screenHeight * 0.10);
        btnBWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnBWidth, btnBHeight); //setting gravity to center horizontal
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.44); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams btnMcCLayout (int screenHeight, int screenWidth) {
        int btnCHeight, btnCWidth;
        btnCHeight = (int) (screenHeight * 0.10);
        btnCWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnCWidth, btnCHeight); //setting gravity to center horizontal
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.55); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams btnMcDLayout (int screenHeight, int screenWidth) {
        int btnDHeight, btnDWidth;
        btnDHeight = (int) (screenHeight * 0.10);
        btnDWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnDWidth, btnDHeight); //setting gravity to center horizontal
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.66); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams btnNextOrSkipLayout(int screenHeight, int screenWidth) {
        int btnDHeight, btnDWidth;
        btnDHeight = (int) (screenHeight * 0.10);
        btnDWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnDWidth, btnDHeight); //setting gravity to center horizontal
        params.gravity = Gravity.RIGHT;
        params.topMargin = (int) (screenHeight * 0.80); //!!!!
        return params;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_choice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void disableAllAnswerButtons(){
        btnMcA.setClickable(false);
        btnMcB.setClickable(false);
        btnMcC.setClickable(false);
        btnMcD.setClickable(false);
    }

    /**
     * Create a bundle containing all the relevant information for the activity which as started
     * this activity for a result.
     * @return Bundle with all relevant data from this activity, including tags "FROM", "Score", "EXIT" and "SKIPPED"
     */
    private Bundle passData(){
        Bundle bundle = new Bundle();
        Intent it = getIntent();
        bundle.putInt(SCORE_TAG, scoreForThisQuestion);
        bundle.putString(FROM_TAG, FROM_MULTI_CHOICE);
        bundle.putBoolean(EXIT_TAG, endtrail);
        bundle.putBoolean(SKIPPED_TAG, hasBeenSkipped);
        it.putExtras(bundle);
        setIntent(it);
        setResult(RESULT_OK, it);
        finish();
        return bundle;
    }

    @Override
    public void doYesConfirmClick(final int from) { //this is called in the dialog fragment when the user presses the yes button.
        if(from == IConfirmDialogCompliant.FROM_BACK_TO_MAIN_DIALOG) {
            endtrail = true;
            passData();
        }
        else if (from == IConfirmDialogCompliant.FROM_SKIP_DIALOG){
            scoreForThisQuestion = 0;
            hasBeenSkipped = true;
            passData();
        }
    }

    @Override
    public void doNoConfirmClick(final int from) {
        //Do nothing
    }
}
