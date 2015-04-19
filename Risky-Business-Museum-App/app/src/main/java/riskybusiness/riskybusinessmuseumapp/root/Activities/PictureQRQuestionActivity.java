package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Scanner;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.AreYouSureToSkipDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.BackToMainMenuDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.IConfirmDialogCompliant;
import riskybusiness.riskybusinessmuseumapp.root.classes.ArtefactImage;
import riskybusiness.riskybusinessmuseumapp.root.classes.QRResultHandler;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailManager;

/**
 * @author Alex 16/04/2015
 */
public class PictureQRQuestionActivity extends FragmentActivity implements IConfirmDialogCompliant, AppConstants{
    TextView Question, TrailPosition, Score;
    ImageButton ScannerButton;
    ImageView Image;
    Button SkipOrNextButton;

    private boolean endtrail = false;
    private boolean hasBeenSkipped = false;
    private int totalScore;
    private int scoreForThisQuestion = MAX_SCORE_FOR_ONE_QUESTION; //defined in AppConstants
    private String ValidatedContent;
    private String CorrectAnswer;
    private TrailManager trailManager;
    private Drawable imageDrawable;
    private String imageName;
    private int screenheight, screenwidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_qrquestion);
        setupResources(); //DO THIS FIRST
        setupDisplaymetrics(); //setting up display metrics and getting devices screen resolution
        setupButtonListeners(); //this has to be done after setting up the resources!
        formatQuestionField(); //formatting text area that displays the question on the activity
        formatImageField(); //formatting image area (setting max height)
        //formatScannerButton();
        Bundle b = getIntent().getExtras();
        unpackAndApplyBundle(b); //unpacking bundle and setting data
    }

    /**
     * Getting the devices screen resolution and saving it in {@link #screenwidth} and {@link #screenheight}
     */
    private void setupDisplaymetrics(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenheight = metrics.heightPixels;
        screenwidth = metrics.widthPixels;
    }

    /**
     * Linking all relevant views to their respective ID
     */
    private void setupResources(){
        Question = (TextView) findViewById(R.id.PicQRQuestion);
        TrailPosition = (TextView) findViewById(R.id.PicQRTrailPosition);
        Score = (TextView) findViewById(R.id.PicQRScore);
        ScannerButton = (ImageButton) findViewById(R.id.PicQRScannerButton);
        Image = (ImageView) findViewById(R.id.PicQRImage);
        SkipOrNextButton = (Button) findViewById(R.id.PicQRNextOrSkipButton);

        trailManager = TrailManager.getTrailManagerInstance(getBaseContext()); // Get TrailManager
    }

    /**
     * Getting relevant items from the bundle passed to this activity, such as the score, trail position, answer and question
     * @param bundle Bundle containing relevant data
     */
    private void unpackAndApplyBundle(Bundle bundle){
        updateQuestion(bundle.getString(QUESTION_TAG));
        CorrectAnswer = bundle.getString(ANSWER_TAG);
        totalScore = bundle.getInt(SCORE_TAG);
        imageName = bundle.getString(IMAGE_TAG, "rolay_blue_ipad_wallpaper");
        updateScore(totalScore);
        updateTrailPosition(bundle.getInt(TRAIL_POSITION_TAG), bundle.getInt(TRAIL_LENGTH_TAG));
        updateImage(imageName);
    }

    /**
     * Setting up the button listeners for the SkipOrNextButton as well as the ScannerButton
     */
    private void setupButtonListeners(){
        SkipOrNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SkipOrNextButton.getText().equals(getString(R.string.Skip))){ //if the text is "Skip" or translated equivalent
                    applySkipDialog();
                    }
                else if (SkipOrNextButton.getText().equals(getString(R.string.Next))){ //if text is "Next" or translated equivalent
                    passData();
                }
            }
        });

        ScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callQRScannerActivity();
            }
        });
    }

    /**
     * Making Question TextView scrollable and defines how many lines will be displayed before scrolling
     */
    private void formatQuestionField(){
        Question.setMovementMethod(new ScrollingMovementMethod()); //making TextView scrollable
        Question.setMaxLines(4); //defining amount of lines to be displayed before the scrolling begins
    }

    /**
     * Setting the maximum height of the image displayed on the activity.
     * This makes sure that the image does not overlap with any of text or buttons on the activity.
     */
    private void formatImageField(){
        Image.setMaxHeight((int) (screenheight * 0.5));
    }

    /**
     * Formatting the ScannerButton, i.e. doubling it in size.
     */

    @Deprecated
    private void formatScannerButton(){
        //tripling size of the image button
        ScannerButton.setMaxHeight((int) (screenwidth * (1/3)));
        ScannerButton.setMaxWidth((int) (screenwidth * (1/3)));
    }

    /**
     * Update the displayed score on the activity
     * @param score Integer of the current trail score
     */
    private void updateScore(int score){
        Score.setText("Score: " + score);
    }

    /**
     * Update the current trail position to be displayed on the activity
     * @param currpos Integer of the current position (0 indexed, please)
     * @param max Integer of the amount of trail steps left (Does not need adding 1, as the size does not care if a list is 0 indexed)
     */
    private void updateTrailPosition(int currpos, int max){
        TrailPosition.setText("Question " + (currpos + 1) + " of " + (max)); //add 1 to currpos since these values come from a 0 indexed list
    }

    private void updateQuestion(String question){
        Question.setText(question);
    }

    /**
     * Update the image with a given string name of the file
     * @param name file name of the image
     */
    private void updateImage(String name){
        ArtefactImage ae = new ArtefactImage(getBaseContext(), name);
        Image.setImageDrawable(ae.getImage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture_qrquestion, menu);
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

    private void makeSkipToNextButton() {
        SkipOrNextButton.setText(R.string.Next);
    }

    /**
     * This method is a call-bridge used by the @link QuestionManager class as you can not start activities that return a value from inside an intent.
     * This is because an onActivityResult() method is required in order to return a value from one intent into another.
     * This method may only be implemented in a class that extends @link Activity or one of its subclasses.
     */
    public void callQRScannerActivity()
    {
        Intent i = new Intent(getBaseContext(),QRScannerActivity.class);
        startActivityForResult(i, 0, null);
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bundle tempBundle = data.getExtras();
        String from = tempBundle.getString(FROM_TAG, "");
        int artefactID;

        if(from.equals(FROM_QR_SCANNER)) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();
                QRResultHandler qrrh = new QRResultHandler(b.getString(CONTENT_TAG, "No Value"));
                ValidatedContent = qrrh.getResult();

                if(ValidatedContent.equals("No Content"))
                {
                    Toast.makeText(getBaseContext(), "Returned without content", Toast.LENGTH_LONG).show(); //THIS WILL REMOVED IN THE FINAL VERSION - USED FOR DEBUGGING
                    return;
                } else if(ValidatedContent.equals("No Identifier"))
                {
                    Toast.makeText(getBaseContext(),R.string.NotOneOfOurQRCodesMessage,Toast.LENGTH_LONG).show();
                    return;
                }

                // TODO: Need to check if the code scanned is part of this trail, if not the user may have moved on. This also needs to be done anywhere else necessary
                // Not sure if this is the right place to put the code or not
                // Use call to TrailManager.checkArtefact(artefact number), to check if the scanned artefact is on the current trail

                artefactID = Integer.parseInt(ValidatedContent);

                if(trailManager.checkArtefact(artefactID) == -1) {       // Artefact  not on trail

                    if(trailManager.isArtefactInExhibit(artefactID)) {// Is the artefact part of the current trails exhibit?
                        // Artefact is in the exhibit - so wrong QR code scanned (wrong answer)
                    }
                    else { // Not part of the current exhibit so user has moved on
                        // display message asking the user if they want to return to the trail,
                        // join the new trail belonging to the scanned artefact or leave the trail altogether and just browse
                    }
                }



                if(ValidatedContent.equals(CorrectAnswer) || scoreForThisQuestion <= 0){ //this is the correct answer
                    //making next question button visible and clickable, disabling QR scanner button
                    ScannerButton.setClickable(false);
                    makeSkipToNextButton();
                    //adding spaces to the toast as they get removed from the xml files
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.CorrectGuessPart1of2) + " " + scoreForThisQuestion + " " + getResources().getString(R.string.CorrectGuessPart2of2), Toast.LENGTH_LONG).show();
                    updateScore(scoreForThisQuestion + totalScore);
                }
                else
                {
                    scoreForThisQuestion -= 25;
                    updateScore(scoreForThisQuestion);
                    //You've done it wrong pop-up
                    //CallQRScannerActivity();
                    Toast.makeText(getBaseContext(), R.string.WrongAnswer, Toast.LENGTH_SHORT).show();
                }

                // Logic for qr codes from either other exibits or not containing our identifier
//                else if()
//                {
//
//                }
                //Content = Content.substring(9, Content.length());
                //Toast.makeText(getBaseContext(), (CharSequence) Content, Toast.LENGTH_SHORT).show();
                //Format = b.getString("Format", "No Format");
                //Format = Format.substring(7, Format.length());
                //Toast.makeText(getBaseContext(), (CharSequence) Format, Toast.LENGTH_SHORT).show();
                // data.getStringArrayExtra("content");
            }
        }
    }

    /**
     * This method returns a bundle containing all the relevant data to the intent calling it via a
     * call-bridge @link CallQRScannerActivity() the use of @link startActivityForResult()
     * @return Bundle containing all relevant data
     */
    private Bundle passData(){
        Bundle bundle = new Bundle();
        Intent it = getIntent();
        bundle.putInt(SCORE_TAG, scoreForThisQuestion);
        bundle.putString(FROM_TAG, FROM_PICTURE_QR_QUESTION);
        bundle.putString(QR_ANSWER_TAG, ValidatedContent);
        bundle.putBoolean(EXIT_TAG, endtrail);
        bundle.putBoolean(SKIPPED_TAG, hasBeenSkipped);
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
