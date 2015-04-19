package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;


/**
 * @author Alex created on 09/04/15
 */
public class TrailResultActivity extends FragmentActivity implements AppConstants{

    TextView TrailName, Score, Statistics, Rank;
    Button OkayButton;
    String[] testStringArray = {"Question 1: 50/100", "2. Question: 0/100"};
    ArrayList<String> formattedScores;
    ArrayList<Integer> questionScores;
    int totalScore;
    String trailName;
    final int MAX_SCORE = MAX_SCORE_FOR_ONE_QUESTION;
    String[] ranks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_result);
        setResources();
        formatTextViews();
        setButtonListener();
        Bundle bundle = getIntent().getExtras();
        unpackBundle(bundle);
        setFormattedScores();
        updateTrailName();
        updateTotalScore();
        updateRank(); //DO THIS AFTER TOTAL SCORE IS CALCULATED!
        displayFormattedStatistics();
    }

    /**
     * Unpacks and sets all relevant data from the bundle
     * @param b Bundle containing relevant data
     */
    private void unpackBundle(Bundle b){
        questionScores =  b.getIntegerArrayList("QSCORES");
        totalScore = b.getInt("SCORE");
        trailName = b.getString("TRAILNAME");

        //not to do with bundle but getting rank names from Strings.xml
        ranks = getResources().getStringArray(R.array.ExplorerRanking);
    }

    /**
     * Setting resources for the items / views displayed on this activity
     */
    private void setResources(){
        TrailName = (TextView) findViewById(R.id.ResultTrailName);
        Rank = (TextView) findViewById(R.id.ResultRank);
        Score = (TextView) findViewById(R.id.ResultScore);
        Statistics = (TextView) findViewById(R.id.ResultStatistics);
        OkayButton = (Button) findViewById(R.id.ResultOkButton);
    }

    /**
     * Formatting the given textviews to facilitate scrolling
     */
    private void formatTextViews(){
        Statistics.setMovementMethod(new ScrollingMovementMethod());
        Statistics.setMaxLines(11);
    }

    /**
     * Setting button listener for the Okay button that will close the Activity
     */
    private void setButtonListener(){
        OkayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passData(); //close activity
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
     * update the rank of the user
     */
    private void updateRank(){
        Rank.setText(getResources().getString(R.string.YourRankIs) + " " + determineRank());
    }

    /**
     * Show the trail name on the activity with trailname and the phrase completed, e.g.
     * Discover the World of Bugs completed!
     */
    private void updateTrailName(){
        TrailName.setText(trailName + " " + getResources().getString(R.string.completed) + "!");
    }

    /**
     * Show the total score on the activity
     */
    private void updateTotalScore(){
        Score.setText(getString(R.string.YourTotalScore) + " " + totalScore + "/" + MAX_SCORE * questionScores.size());
    }

    private String determineRank(){
        double percentage = (double) (totalScore) / ((double) (MAX_SCORE) * (double) (questionScores.size())); //getting the percentage of "correctness"
        String rank = getResources().getString(R.string.Dora); //default value
//DEBUGGING
//        System.out.println("percentage = " + percentage);
//        System.out.println("totalScore = " + totalScore);
//        System.out.println("MAX TOTAL SCORE = " + (MAX_SCORE * questionScores.size()));

        if(percentage == 1.0){ //100% correctness
            rank = ranks[ranks.length - 1]; //last item in the list
        } else if (percentage >= 0.8){ //80% correctness
            try {
                rank = ranks[ranks.length - 2];
            }
            catch(IndexOutOfBoundsException E){
                E.printStackTrace();
                System.out.println("Explorer ranks does not have enough items in it");
            }
        } else if(percentage >= 0.6){
            try {
                rank = ranks[ranks.length - 3];
            }
            catch(IndexOutOfBoundsException E){
                E.printStackTrace();
                System.out.println("Explorer ranks does not have enough items in it");
            }
        } else if(percentage >= 0.4){
            try {
                rank = ranks[ranks.length - 4];
            }
            catch(IndexOutOfBoundsException E){
                E.printStackTrace();
                System.out.println("Explorer ranks does not have enough items in it");
            }
        } else if(percentage >= 0.2){
            try {
                rank = ranks[ranks.length - 5];
            }
            catch(IndexOutOfBoundsException E){
                E.printStackTrace();
                System.out.println("Explorer ranks does not have enough items in it");
            }
        }
        return rank;
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

    /**
     * Create a bundle containing all the relevant information for the activity which as started
     * this activity for a result.
     * @return Bundle with all relevant data from this activity, including tags "FROM", "Score", "EXIT" and "SKIPPED"
     */
    private Bundle passData(){
        Bundle bundle = new Bundle();
        Intent it = getIntent();
        bundle.putString("FROM", "TrailResultActivity");
        it.putExtras(bundle);
        setIntent(it);
        setResult(RESULT_OK, it);
        finish();
        return bundle;
    }
}
