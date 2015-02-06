package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;

import riskybusiness.riskybusinessmuseumapp.R;

public class SingleAnswerActivity extends ActionBarActivity {

    private final int MAX_SCORE = 10;
    private int score;
    private int screenHeight, screenWidth;
    private ImageButton SingleAnswerQRButton;
    private TextView SingleAnswerQuestion;
    private String Content;
    private String Format;

    private String CorrectAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_answer);
        score = MAX_SCORE;
        SingleAnswerQRButton = (ImageButton) findViewById(R.id.SingleAnswerQRButton);
        setButtonListener();
        SingleAnswerQuestion = (TextView) findViewById(R.id.SingleAnswerQuestion);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;

        SingleAnswerQRButton.setLayoutParams(QRButtonLayout(screenHeight, screenWidth));
        SingleAnswerQuestion.setLayoutParams(SingleAnswerQuestionLayout(screenHeight, screenWidth));

        Bundle bundle = getIntent().getExtras();
        SingleAnswerQuestion.setText(bundle.getString("QUESTION"));
        CorrectAnswer = bundle.getString("ANSWER");


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
        params.topMargin = (int) (screenHeight * 0.60);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        return params;
    }

    private void setButtonListener(){
        SingleAnswerQRButton.setClickable(true);
        SingleAnswerQRButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(), "QR Button clicked", Toast.LENGTH_LONG).show();
                //Start scanning QR code
                //if it is the right answer: score = 10;
                CallQRScannerActivity();
                //passData();
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_answer, menu);
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

                Content = b.getString("Content", "No Value");
                Content = Content.substring(9, Content.length());
                //Toast.makeText(getBaseContext(), (CharSequence) Content, Toast.LENGTH_SHORT).show();
                Format = b.getString("Format", "No Format");
                Format = Format.substring(7, Format.length());
                //Toast.makeText(getBaseContext(), (CharSequence) Format, Toast.LENGTH_SHORT).show();
                // data.getStringArrayExtra("content");


                if(Content.equals(CorrectAnswer) || score <= 0){
                    passData();
                }
                else {
                    score -= 5;
                    //You've done it wrong pop-up
                    //CallQRScannerActivity();
                    Toast.makeText(getBaseContext(), (CharSequence) "Wrong answer.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void CallQRScannerActivity()
    {
        Intent i = new Intent(getBaseContext(),QRScannerActivity.class);
        startActivityForResult(i, 0, null);
    }

    private Bundle passData(){
        Bundle bundle = new Bundle();
        Intent it = getIntent();
        bundle.putInt("Score", score);
        bundle.putString("FROM", "SingleAnswerActivity");
        bundle.putString("QRANSWER", Content);
        it.putExtras(bundle);
        setIntent(it);
        setResult(RESULT_OK, it);
        finish();
        return bundle;
    }
}
