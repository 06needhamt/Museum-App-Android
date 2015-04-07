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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.AreYouSureToSkipDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.BackToMainMenuDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.IConfirmDialogCompliant;
import riskybusiness.riskybusinessmuseumapp.root.classes.QRResultHandler;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailManager;

public class SingleAnswerActivity extends FragmentActivity implements IConfirmDialogCompliant{

    private final int MAX_SCORE = 100;
    private int scoreForThisQuestion;
    private int totalScore;
    private int screenHeight, screenWidth;
    private ImageButton SingleAnswerQRButton;
    private Button btnSkipOrNext;
    private TextView SingleAnswerQuestion, ScoreField, TrailPositionField;
    private String ValidatedContent;
    private String Format;
    private boolean endtrail = false;
    private boolean hasBeenSkipped = false;

    private String CorrectAnswer;

    private TrailManager trailManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_answer);
        scoreForThisQuestion = MAX_SCORE;
        SingleAnswerQRButton = (ImageButton) findViewById(R.id.SingleAnswerQRButton);
        SingleAnswerQRButton.setScaleX(3.0f); //trippling size
        SingleAnswerQRButton.setScaleY(3.0f);
        btnSkipOrNext = (Button) findViewById(R.id.bntSaSkipOrNext);
        btnSkipOrNext.setText(R.string.Skip);
        btnSkipOrNext.setTextColor(getResources().getColor(R.color.White)); //setting text colour to white
        btnSkipOrNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15); //setting text size to 15sp (scaled pixels)
        setButtonListener();


        SingleAnswerQuestion = (TextView) findViewById(R.id.SingleAnswerQuestion);
        ScoreField = (TextView) findViewById(R.id.SaScore);
        TrailPositionField = (TextView) findViewById(R.id.SaTrailPosition);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;


        SingleAnswerQRButton.setLayoutParams(QRButtonLayout(screenHeight, screenWidth));
        SingleAnswerQuestion.setLayoutParams(SingleAnswerQuestionLayout(screenHeight, screenWidth));
        btnSkipOrNext.setLayoutParams(btnSkipOrNextLayout(screenHeight, screenWidth));

        trailManager = TrailManager.getTrailManagerInstance(getBaseContext()); // Get TrailManager

        Bundle bundle = getIntent().getExtras();
        unpackBundle(bundle);

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

    private void unpackBundle(Bundle bundle){
        SingleAnswerQuestion.setText(bundle.getString("QUESTION"));
        CorrectAnswer = bundle.getString("ANSWER");
        totalScore = bundle.getInt("SCORE");
        updateScore(totalScore);
        updateTrailPosition(bundle.getInt("TRAIL_POSITION"), bundle.getInt("TRAIL_LENGTH"));
    }

    private void updateScore(int score){
        ScoreField.setText("Score: " + score);
    }

    private void updateTrailPosition(int currpos, int max){
        TrailPositionField.setText("Question " + (currpos + 1) + " of " + (max + 1)); //add 1 to them since these values come from a 0 indexed list
    }

    private FrameLayout.LayoutParams SingleAnswerQuestionLayout(int screenHeight, int screenWidth){
        int textHeight, textWidth;
        textHeight = (int) (screenHeight * 0.30); //
        textWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(textWidth, textHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.05); //!!!!
        return params;
    }

    private FrameLayout.LayoutParams QRButtonLayout(int screenHeight, int screenWidth){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenWidth, screenHeight);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (screenHeight * 0.55); //!!!!
        params.gravity = Gravity.CENTER_HORIZONTAL;

        return params;
    }

    private FrameLayout.LayoutParams btnSkipOrNextLayout(int screenHeight, int screenWidth) {
        int btnDHeight, btnDWidth;
        btnDHeight = (int) (screenHeight * 0.10);
        btnDWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnDWidth, btnDHeight); //setting gravity to center horizontal
        params.gravity = Gravity.RIGHT;
        params.topMargin = (int) (screenHeight * 0.78); //!!!!
        return params;
    }

    private void setButtonListener(){
        SingleAnswerQRButton.setClickable(true);
        SingleAnswerQRButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallQRScannerActivity();
            }
        }));

        btnSkipOrNext.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSkipOrNext.getText().equals(getResources().getString(R.string.Skip))){ //if the button is still set to Skip, reduce the scoreForThisQuestion to 0
                    applySkipDialog();
                }
                else if (btnSkipOrNext.getText().equals(getResources().getString(R.string.Next))){
                    passData();
                }
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_single_answer, menu);
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


    private void applySkipDialog(){
        AreYouSureToSkipDialogFragment dialog = new AreYouSureToSkipDialogFragment(R.string.SkipFragmentText, this);
        dialog.show(getFragmentManager(), null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bundle tempBundle = data.getExtras();
        String from = tempBundle.getString("FROM", "");
        if(from.equals("QRScannerActivity")) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();
                QRResultHandler qrrh = new QRResultHandler(b.getString("Content", "No Value"));
                ValidatedContent = qrrh.getResult();

                if(ValidatedContent.equals("No Content"))
                {
                    Toast.makeText(getBaseContext(),"Returned without content",Toast.LENGTH_LONG).show(); //THIS WILL REMOVED IN THE FINAL VERSION - USED FOR DEBUGGING
                    return;
                } else if(ValidatedContent.equals("No Identifier"))
                {
                    Toast.makeText(getBaseContext(),R.string.NotOneOfOurQRCodesMessage,Toast.LENGTH_LONG).show();
                    return;
                }

                // TODO: Need to check if the code scanned is part of this trail, if not the user may have moved on. This also needs to be done anywhere else necessary
                // Not sure if this is the right place to put the code or not
                // Use call to TrailManager.checkArtefact(artefact number), to check if the scanned artefact is on the current trail
                if(trailManager.checkArtefact(3) == -1) { // Artefact  not on trail
                    // display message asking the user if they want to return to the trail,
                    // join the new trail belonging to the scanned artefact or leave the trail altogether and just browse


                }



                if(ValidatedContent.equals(CorrectAnswer) || scoreForThisQuestion <= 0){ //this is the correct answer
                    //making next question button visible and clickable, disabling QR scanner button
                    SingleAnswerQRButton.setClickable(false);
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

    private void makeSkipToNextButton() {
        btnSkipOrNext.setText(R.string.Next);
    }

    /**
     * This method is a call-bridge used by the @link QuestionManager class as you can not start activities that return a value from inside an intent.
     * This is because an onActivityResult() method is required in order to return a value from one intent into another.
     * This method may only be implemented in a class that extends @link Activity or one of its subclasses.
     */
    public void CallQRScannerActivity()
    {
        Intent i = new Intent(getBaseContext(),QRScannerActivity.class);
        startActivityForResult(i, 0, null);
    }

    /**
     * This method returns a bundle containing all the relevant data to the intent calling it via a
     * call-bridge @link CallQRScannerActivity() the use of @link startActivityForResult()
     * @return Bundle containing all relevant data
     */
    private Bundle passData(){
        Bundle bundle = new Bundle();
        Intent it = getIntent();
        bundle.putInt("Score", scoreForThisQuestion);
        bundle.putString("FROM", "SingleAnswerActivity");
        bundle.putString("QRANSWER", ValidatedContent);
        bundle.putBoolean("EXIT",endtrail);
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
