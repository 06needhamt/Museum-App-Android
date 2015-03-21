package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.AreYouSureToSkipDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.BackToMainMenuDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.IConfirmDialogCompliant;


public class MultiChoiceActivity extends FragmentActivity implements IConfirmDialogCompliant {
    private int screenHeight;
    private int screenWidth;
    private Button btnMcA, btnMcB, btnMcC, btnMcD, btnSkipOrNext;
    private Button[] answerButtons;
    private TextView multiChoiceQuestion, ScoreField, TrailPositionField;
    private final int MAX_SCORE = 100;
    private boolean endtrail = false; //set this to true for the trail to end after this question and false for it not to end after this question
    private int score;
    private String question;
    private List<String> answers;
    private String correctAnswer;
    private boolean hasBeenSkipped = false;
    private int currentPosition;
    private int trailLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choice);
        score = MAX_SCORE;
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

    }

    private void unpackBundle(Bundle bundle){ //unpacking bundle and assigning all relevant data
        question = bundle.getString("QUESTION");
        String temp = bundle.getString("ANSWER");
        applyAnswers(temp);
        correctAnswer = answers.get(0);
        currentPosition = bundle.getInt("TRAIL_POSITION", -1);
        trailLength = bundle.getInt("TRAIL_LENGTH", -1);
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

    private void populateButtons(List<String> list){
        btnMcA.setText(list.get(0));
        btnMcB.setText(list.get(1));
        btnMcC.setText(list.get(2));
        btnMcD.setText(list.get(3));
        btnSkipOrNext.setText(R.string.Skip);
        multiChoiceQuestion.setText(question);
        updateScore(score); //updating score field with the default score
        updateTrailPosition(currentPosition, trailLength); //setting the current trail position, i.e. Question 1 of 10
    }

    private void updateScore(int score){
        ScoreField.setText("Score: " + score);
    }

    private void updateTrailPosition(int currpos, int max){
        TrailPositionField.setText("Question " + (currpos + 1) + " of " + (max + 1)); //add 1 to them since these values come from a 0 indexed list
    }

    /**
     * Splitting the passed String at commas and converting it to a List<String>.
     * @param temp String containing comma separated answers to the question, first one being the correct answer
     */
    private void applyAnswers(String temp){
        answers = Arrays.asList(temp.split("\\s*,\\s*"));
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
        // Hightlite button to indicate used

        String answer = (String)btn.getText(); // Get button text

        if(answer.equals(correctAnswer)) {
            btn.setBackgroundColor(getResources().getColor(R.color.Green));
            Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_LONG).show();

        }
        else {
            btn.setBackgroundColor(getResources().getColor(R.color.Red));
            Toast.makeText(getBaseContext(), "Wrong answer " + score / 50 + " Guesses Left", Toast.LENGTH_LONG).show();
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
        score -= 25; // Reduce the score
        if(score <= 0) { // Check if any guesses left
            // no guesses exit activity returning data
        }
        //setting the button that has been clicked to un-clickable state
        btn.setClickable(false);
        updateScore(score); //update the score field with the new score
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
        params.topMargin = (int) (screenHeight * 0.43); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams btnMcCLayout (int screenHeight, int screenWidth) {
        int btnCHeight, btnCWidth;
        btnCHeight = (int) (screenHeight * 0.10);
        btnCWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnCWidth, btnCHeight); //setting gravity to center horizontal
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.53); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams btnMcDLayout (int screenHeight, int screenWidth) {
        int btnDHeight, btnDWidth;
        btnDHeight = (int) (screenHeight * 0.10);
        btnDWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnDWidth, btnDHeight); //setting gravity to center horizontal
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.63); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams btnNextOrSkipLayout(int screenHeight, int screenWidth) {
        int btnDHeight, btnDWidth;
        btnDHeight = (int) (screenHeight * 0.10);
        btnDWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnDWidth, btnDHeight); //setting gravity to center horizontal
        params.gravity = Gravity.RIGHT;
        params.topMargin = (int) (screenHeight * 0.73); //!!!!
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
        bundle.putInt("Score", score);
        bundle.putString("FROM", "MultiChoiceActivity");
        bundle.putBoolean("EXIT", endtrail);
        bundle.putBoolean("SKIPPED", hasBeenSkipped);
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
            score = 0;
            hasBeenSkipped = true;
            passData();
        }
    }

    @Override
    public void doNoConfirmClick(final int from) {
        //Do nothing
    }
}
