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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.BackToMainMenuDialogFragment;
import riskybusiness.riskybusinessmuseumapp.root.Dialogs.IConfirmDialogCompliant;
import riskybusiness.riskybusinessmuseumapp.root.classes.QRResultHandler;

public class SingleAnswerActivity extends FragmentActivity implements IConfirmDialogCompliant{

    private final int MAX_SCORE = 10;
    private int score;
    private int screenHeight, screenWidth;
    private ImageButton SingleAnswerQRButton;
    private Button btnNextQuestionSA;
    private TextView SingleAnswerQuestion;
    private String ValidatedContent;
    private String Format;
    private boolean endtrail = false;

    private String CorrectAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_answer);
        score = MAX_SCORE;
        SingleAnswerQRButton = (ImageButton) findViewById(R.id.SingleAnswerQRButton);
        btnNextQuestionSA = (Button) findViewById(R.id.btnNextQuestionSA);
        setButtonListener();


        SingleAnswerQuestion = (TextView) findViewById(R.id.SingleAnswerQuestion);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;


        SingleAnswerQRButton.setLayoutParams(QRButtonLayout(screenHeight, screenWidth));
        SingleAnswerQuestion.setLayoutParams(SingleAnswerQuestionLayout(screenHeight, screenWidth));
        btnNextQuestionSA.setLayoutParams(btnNextQuestionLayout(screenHeight, screenWidth));


        Bundle bundle = getIntent().getExtras();
        SingleAnswerQuestion.setText(bundle.getString("QUESTION"));
        CorrectAnswer = bundle.getString("ANSWER");
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
        BackToMainMenuDialogFragment dialog = new BackToMainMenuDialogFragment("Do you want to leave this trail? Your score will be lost and you will return to the main menu.", this);
        dialog.show(this.getFragmentManager(), null);
    }

    private FrameLayout.LayoutParams SingleAnswerQuestionLayout(int screenHeight, int screenWidth){
        int textHeight, textWidth;
        textHeight = (int) (screenHeight * 0.30); //
        textWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(textWidth, textHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        return params;
    }

    private FrameLayout.LayoutParams QRButtonLayout(int screenHeight, int screenWidth){
        int btnHeight, btnWidth;
        btnHeight = (int) (screenHeight * 0.2);
        btnWidth = (int) (screenHeight * 0.2);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnWidth, btnHeight);
        params.topMargin = (int) (screenHeight * 0.55);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        return params;
    }

    private FrameLayout.LayoutParams btnNextQuestionLayout(int screenHeight, int screenWidth) {
        int btnDHeight, btnDWidth;
        btnDHeight = (int) (screenHeight * 0.10);
        btnDWidth = FrameLayout.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(btnDWidth, btnDHeight); //setting gravity to center horizontal
        params.gravity = Gravity.CENTER_HORIZONTAL;
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

        btnNextQuestionSA.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passData();
            }
        }));

        btnNextQuestionSA.setClickable(false);
        btnNextQuestionSA.setVisibility(View.INVISIBLE);
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
                    Toast.makeText(getBaseContext(),"Returned without content",Toast.LENGTH_LONG).show();
                    return;
                } else if(ValidatedContent.equals("No Identifier"))
                {
                    Toast.makeText(getBaseContext(),"This is not one of our QR-codes!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(ValidatedContent.equals(CorrectAnswer) || score <= 0){
                    //making next question button visible and clickable, disabling QR scanner button and making it green
                    SingleAnswerQRButton.setClickable(false);
                    SingleAnswerQRButton.setBackgroundResource(R.drawable.green__icon_qr);
                    btnNextQuestionSA.setClickable(true);
                    btnNextQuestionSA.setVisibility(View.VISIBLE);
                }
                else
                {
                    score -= 5;
                    //You've done it wrong pop-up
                    //CallQRScannerActivity();
                    Toast.makeText(getBaseContext(), "Wrong answer.", Toast.LENGTH_SHORT).show();
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
        bundle.putInt("Score", score);
        bundle.putString("FROM", "SingleAnswerActivity");
        bundle.putString("QRANSWER", ValidatedContent);
        bundle.putBoolean("EXIT",endtrail);
        it.putExtras(bundle);
        setIntent(it);
        setResult(RESULT_OK, it);
        finish();
        return bundle;
    }

    @Override
    public void doYesConfirmClick() {
        endtrail = true;
        passData();
    }

    @Override
    public void doNoConfirmClick() {
        //Do nothing
    }
}
