package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import riskybusiness.riskybusinessmuseumapp.R;


/**
 * @author Alex created on 09/04/15
 */
public class TrailResultActivity extends FragmentActivity {

    TextView TrailName, Score, Statistics;
    Button OkayButton;
    String[] testStringArray = {"Question 1: 50/100", "2. Question: 0/100"};
    ArrayList<String> formattedScores;
    ArrayList<Integer> questionScores;
    int totalScore;
    final int MAX_SCORE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_result);
        setResources();
        setButtonListener();
        Bundle bundle = getIntent().getExtras();
        unpackBundle(bundle);
        setFormattedScores();
        updateTrailName();
        updateTotalScore();
        displayFormattedStatistics();
    }

    /**
     * Unpacks and sets all relevant data from the bundle
     * @param b Bundle containing relevant data
     */
    private void unpackBundle(Bundle b){
        questionScores =  b.getIntegerArrayList("QSCORES");
        totalScore = b.getInt("SCORE");
        //TODO Unpack current trail name
    }

    /**
     * Setting resources for the items / views displayed on this activity
     */
    private void setResources(){
        TrailName = (TextView) findViewById(R.id.ResultTrailName);
        Score = (TextView) findViewById(R.id.ResultScore);
        Statistics = (TextView) findViewById(R.id.ResultStatistics);
        OkayButton = (Button) findViewById(R.id.ResultOkButton);
    }

    /**
     * Setting button listener for the Okay button that will close the Activity
     */
    private void setButtonListener(){
        OkayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //close activity
            }
        });
    }

    /**
     * Setting the ArrayList of Strings formattedScores to the format required to be correctly displayed when {@link #displayFormattedStatistics} is called
     */
    private void setFormattedScores(){
        if(formattedScores == null){
            formattedScores = new ArrayList<>();
        }
        for(int i = 0; i < questionScores.size(); i++){

            if(questionScores.get(i) == 0){ //TODO use skipped flag instead of score of 0. This only works for demonstration purposes.
                formattedScores.add( (i+1) + ". " + getString(R.string.Question) + ": " + questionScores.get(i) + "/" + MAX_SCORE + " (" + getString(R.string.Skipped) + ")");
            } else {
                formattedScores.add((i + 1) + ". " + getString(R.string.Question) + ": " + questionScores.get(i) + "/" + MAX_SCORE);
            }
            //adding String in the format of e.g.:
            // 1. Question: 50/100
        }
    }

    /**
     * Displaying formatted statistics starting with "Statistics:" and a line break, followed by each question score and a line break between them
     */
    private void displayFormattedStatistics(){
        Statistics.setText(getString(R.string.Statistics) + ":\n"); //setting initial line of the TextView
        for(String r : formattedScores){
            Statistics.append(r + "\n");
        }
    }

    /**
     * Show the trail name on the activity
     */
    private void updateTrailName(){
        TrailName.setText(getString(R.string.TrailName) + " " + "Default Trail Name"); //TODO add the current trail name
    }

    /**
     * Show the total score on the activity
     */
    private void updateTotalScore(){
        Score.setText(getString(R.string.YourTotalScore) + " " + totalScore + "/" + MAX_SCORE * questionScores.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trail_result, menu);
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
}
