package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import riskybusiness.riskybusinessmuseumapp.root.classes.ArtefactImage;

public class PictureMultiChoiceActivity extends FragmentActivity implements IConfirmDialogCompliant, AppConstants {

    TextView questionField, trailPositonField, scoreField;
    ImageView imageField;
    Button buttonA, buttonB, buttonC, buttonD, skipOrNextButton;
    private boolean endtrail = false;
    private boolean hasBeenSkipped = false;
    private int scoreForThisQuestion = MAX_SCORE_FOR_ONE_QUESTION;
    private int totalScore;
    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;
    private int currentPosition;
    private int trailLength;
    private int screenheight, screenwidth;
    private int imageResourceID;
    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_multi_choice);
        setupDisplayMetrics();
        setupResources();
        formatImageField();
        formatQuestionField();
        setupButtonListeners();

        //TESTING
//        Bundle testBundle = new Bundle();
//        testBundle.putString("QUESTION", "This is a testing text. Testing scrollable text view: yadda yadda yadda yadda yadda yadda yadda yadda yadda yadda yadda yadda yadda yadda yadda yadda yadda.");
//        testBundle.putString("ANSWER", "first answer,second answer,third answer,fourth answer");
//        testBundle.putInt("SCORE", 1234);
//        testBundle.putInt("TRAIL_POSITION", 13);
//        testBundle.putInt("TRAIL_LENGTH", 37);
//        testBundle.putInt("IMAGE", R.drawable.gaia);
//
//        unpackAndApplyBundle(testBundle);
        //Uncomment the following 2 lines when implementing this activity!
        Bundle bundle = getIntent().getExtras();
        unpackAndApplyBundle(bundle); //unpacking bundle and assigning all relevant data
        //END OF TESTING

        setupViewsWithInformation();
        theOldSwitcheroo(); //randomly mixing up the answers on the buttons.

    }

    /**
     * Getting the screen resolution of the used device and saving it in {@link #screenwidth} and {@link #screenheight}.
     */
    private void setupDisplayMetrics(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); //DO NOT FORGET THIS LINE OR ELSE IT WILL BE 0
        screenheight = dm.heightPixels;
        screenwidth = dm.widthPixels;
    }

    /**
     * Setting up the relevant resources to this activity, like buttons, imageViews and textViews.
     * This is required to be done first as it allows the change of the resources, such as
     * changing the displayed text and images.
     */
    private void setupResources(){
        questionField = (TextView) findViewById(R.id.PicMCQuestion);
        trailPositonField = (TextView) findViewById(R.id.PicMCTrailPosition);
        scoreField = (TextView) findViewById(R.id.PicMCScore);
        imageField = (ImageView) findViewById(R.id.PicMCImage);
        buttonA = (Button) findViewById(R.id.PicMCButtonA);
        buttonB = (Button) findViewById(R.id.PicMCButtonB);
        buttonC = (Button) findViewById(R.id.PicMCButtonC);
        buttonD = (Button) findViewById(R.id.PicMCButtonD);
        skipOrNextButton = (Button) findViewById(R.id.PicMCNextOrSkipButton);
    }

    /**
     * Setting up {@link #imageField} so that it fits with the rest of the screen by setting its maximum height.
     */
    private void formatImageField(){
        imageField.setAdjustViewBounds(true);
        imageField.setMaxHeight((int) (screenheight * 0.33)); //setting max height to a percentage of the devices screenheight
    }

    /**
     * Formatting the {@link #questionField} so that it scrolls.
     */
    private void formatQuestionField(){
        questionField.setMovementMethod(new ScrollingMovementMethod()); //making textView scrollable
        questionField.setMaxLines(4); //maximum amount of lines before the view starts scrolling
    }

    /**
     * Setting up button listeners for skip button as well as the answer buttons.
     */
    private void setupButtonListeners(){
        skipOrNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(skipOrNextButton.getText().equals(getString(R.string.Skip))){
                    applySkipDialog();
                } else if(skipOrNextButton.getText().equals(getString(R.string.Next))){
                    passData();
                }
            }
        });

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(buttonA);
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(buttonB);
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(buttonC);
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(buttonD);
            }
        });
    }

    /**
     * Checking the answer of the given button.
     * @param button Button to be checked
     */
    private void checkAnswer(Button button){
        String answer = (String) button.getText();

        //right answer
        if(answer.equals(correctAnswer)){
            button.setBackgroundColor(getResources().getColor(R.color.TransparentLightGreen));
            updateScore(scoreForThisQuestion + totalScore); //update the score with the new high-score
            disableAllAnswerButtons();
            makeSkipToNextButton();
        } else {
            //wrong answer
            button.setBackgroundColor(getResources().getColor(R.color.TransparentLightRed));
            scoreForThisQuestion -= MAX_SCORE_FOR_ONE_QUESTION / 4;
            button.setClickable(false);
        }
    }

    /**
     * Disabling all buttons so they may not be clicked.
     */
    private void disableAllAnswerButtons(){
        buttonA.setClickable(false);
        buttonB.setClickable(false);
        buttonC.setClickable(false);
        buttonD.setClickable(false);
    }

    /**
     * Converting the skip button to next button.
     * Only done when the correct answer has been given insidle {@link #checkAnswer(android.widget.Button)}
     */
    private void makeSkipToNextButton(){
        skipOrNextButton.setText(R.string.Next);
    }

    /**
     * Unpacking and applying all relevant data from the bundle into the views on the activity.
     * @param bundle Bundle containing all relevant data
     */
    private void unpackAndApplyBundle(Bundle bundle){
        question = bundle.getString("QUESTION");
        String temp = bundle.getString("ANSWER");
        totalScore = bundle.getInt("SCORE", 0); //getting total score with error handling for 0
        //TODO add image to question via either string or resource id.
        imageName = bundle.getString("IMAGE", "royal_blue_ipad_wallpaper");
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
        populateButtonsWithAnswers(newAnswers);
    }

    /**
     * Updating the arrayList by splitting the given string at commas.
     * @param temp comma separated String of answers
     */
    private void applyAnswers(String temp){
        List<String> tempList;

        tempList = Arrays.asList(temp.split("\\s*,\\s*"));

        answers = new ArrayList<>(tempList);

        // In case there are not enough answers to the question - prevents null pointer and indicates incorrect answer list
        while(answers.size() < 4)
            answers.add("MISSING ANSWER");
    }

    private void setupViewsWithInformation(){
        questionField.setText(question);
        updateScore(totalScore); //updating score field with the total score
        updateTrailPosition(currentPosition, trailLength); //setting the current trail position, i.e. Question 1 of 10

        //TODO update the picture with the given file.
        //updateImageField(imageResourceID);
        updateImageField(imageName);
    }

    /**
     * Populates the four buttons with the given answers from a list.
     * Make sure this list is at least the size of 4 and contains String values!!!
     * @param list List of answer Strings
     */
    private void populateButtonsWithAnswers(List<String> list){
        buttonA.setText(list.get(0));
        buttonB.setText(list.get(1));
        buttonC.setText(list.get(2));
        buttonD.setText(list.get(3));
    }

    private void updateScore(int score){
        scoreField.setText("Score: " + score);
    }

    private void updateTrailPosition(int currpos, int max){
        trailPositonField.setText("Question " + (currpos + 1) + " of " + (max)); //add 1 to the current position since these values come from a 0 indexed list but max is the length of the list, so I doesn't need adding 1
    }

    /**
     * Update the image via a resource id
     * @param ID resource ID of the file
     */
    private void updateImageField(int ID){
        imageField.setImageResource(ID);
    }

    /**
     * Update the image via a String
     * @param name String name of the file
     */
    private void updateImageField(String name){
        ArtefactImage ae = new ArtefactImage(getBaseContext(), name);
        imageField.setImageDrawable(ae.getImage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture_multi_choice, menu);
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

    /**
     * Create a bundle containing all the relevant information for the activity which as started
     * this activity for a result.
     * @return Bundle with all relevant data from this activity, including tags "FROM", "Score", "EXIT" and "SKIPPED"
     */
    private Bundle passData(){
        Bundle bundle = new Bundle();
        Intent it = getIntent();
        bundle.putInt("Score", scoreForThisQuestion);
        bundle.putString("FROM", "PictureMultiChoiceActivity");
        bundle.putBoolean("EXIT", endtrail);
        bundle.putBoolean("SKIPPED", hasBeenSkipped);
        it.putExtras(bundle);
        setIntent(it);
        setResult(RESULT_OK, it);
        finish();
        return bundle;
    }

    /**
     * Calling the skip dialog that will ask the user of they want to skip the currently displayed question.
     */
    private void applySkipDialog(){
        AreYouSureToSkipDialogFragment dialog = new AreYouSureToSkipDialogFragment(R.string.SkipFragmentText, this);
        dialog.show(getFragmentManager(), null);
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

    @Override
    public void doYesConfirmClick(final int from) { //this is called in the dialog fragment when the user presses the yes button.
        if (from == IConfirmDialogCompliant.FROM_BACK_TO_MAIN_DIALOG) {
            endtrail = true;
            passData();
        } else if (from == IConfirmDialogCompliant.FROM_SKIP_DIALOG) {
            scoreForThisQuestion = 0;
            hasBeenSkipped = true;
            passData();
        }
    }

    @Override
    public void doNoConfirmClick(int from) {
        //Do nothing
    }
}
