package riskybusiness.riskybusinessmuseumapp.root.Activities;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.BugsFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.AncientWorldFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.WelcomeFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.DinosaursFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.FifthFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.ForthFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.GroundFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.InformationFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.SecondFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.SpaceAndTimeFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.ThirdFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.WorldCulturesFragment;
import riskybusiness.riskybusinessmuseumapp.root.classes.ButtonCreator;
import riskybusiness.riskybusinessmuseumapp.root.classes.QRResultHandler;
import riskybusiness.riskybusinessmuseumapp.root.questionmanager.*;

public class HomePageActivity extends FragmentActivity {

    int toptable;
    int bottomtable;
    Fragment[] fragments;
    Fragment[] Mapfragments;
    Fragment[] BottomFragments;
    String Content;
    String Format;
    int score;
    QuestionManager qm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toptable = R.id.topTableForButtons;
        bottomtable = R.id.bottomTableForButtons;
        fragments = CreateFragments();
        Mapfragments = CreateMapFragments();
        BottomFragments = CreateBottomFragments();
        ButtonCreator btncreate = new ButtonCreator(this,toptable,bottomtable,R.drawable.class.getFields(),fragments, Mapfragments, BottomFragments);
        btncreate.populateTopButtons();
        btncreate.populateBottomButtons();
        //btncreate.populateMapButtons();
        //setContentView(R.layout.fragment_bugs);
        AddFragment();
        score = 0;

    }

    private Fragment[] CreateFragments() {
        Fragment[] fragments = new Fragment[5];
        fragments[0] = new AncientWorldFragment();
        fragments[1] = new BugsFragment();
        fragments[2] = new WorldCulturesFragment();
        fragments[3] = new DinosaursFragment();
        fragments[4] = new SpaceAndTimeFragment();
        return fragments;
    }

    private Fragment[] CreateMapFragments()
    {
        Fragment[] fragments = new Fragment[5];
        fragments[0] = new GroundFloorFragment();
        fragments[1] = new SecondFloorFragment();
        fragments[2] = new ThirdFloorFragment();
        fragments[3] = new ForthFloorFragment();
        fragments[4] = new FifthFloorFragment();
        return fragments;
    }

    private Fragment[] CreateBottomFragments(){
        Fragment[] fragments = new Fragment[5];
        fragments[0] = null;
        fragments[1] = null;
        fragments[2] = null;
        fragments[3] = null;
        fragments[4] = new InformationFragment();
        return fragments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void AddFragment() {
        WelcomeFragment fragment = new WelcomeFragment();
    getFragmentManager().beginTransaction().add(R.id.frame, fragment).commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        //Toast.makeText(getBaseContext(),"In Return function",Toast.LENGTH_SHORT).show();

        Bundle tempBundle = data.getExtras();
        String from = tempBundle.getString("FROM", "");
        boolean exit = false;

        if(from.equals("MultiChoiceActivity")) {

            if(resultCode == RESULT_OK){
                Bundle b = data.getExtras();
                exit = b.getBoolean("EXIT", false);
                if(exit){
                    score = 0;
                    qm.setTrailEnded(true);
                    qm.nextQuestion();
                    return;
                }
                score += b.getInt("Score", -1);
                Toast.makeText(getBaseContext(), "Score:" + score,Toast.LENGTH_LONG ).show();
                qm.nextQuestion();
            }
        }

        else if(from.equals("QRScannerActivity")) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();

                Content = b.getString("Content", "No Value");
                QRResultHandler qrh = new QRResultHandler(Content);
//                if(qrh.getResult().equals(DATABASE EXHIBIT ITEM)){
//                    Show information on the item the person scanned
//                    Or ask user if they want to start a trail from this exhibit
//                }
            }
        }
        else if(from.equals("SingleAnswerActivity")) {
            if(resultCode == RESULT_OK){
                Bundle b = data.getExtras();
                score += b.getInt("Score", -1);
                exit = b.getBoolean("EXIT", false);
                if(exit){
                    score = 0;
                    qm.setTrailEnded(true);
                    qm.nextQuestion();
                    return;
                }
                Toast.makeText(getBaseContext(), "Score:" + score,Toast.LENGTH_LONG ).show();
                qm.nextQuestion();
            }

    //            qm.nextQuestion();
        }
//        else if(data.getClass().getSimpleName().equals(SingleAnswerActivity.class)) {
//            if(resultCode == RESULT_OK){
//                Bundle b = data.getExtras();
//                score = b.getInt("Score", -1);
//            }
//              Toast.makeText(getBaseContext(), "Score:" + score,Toast.LENGTH_LONG ).show();
//        }
        else {
            // Error invalid something or other
            //Toast.makeText(getBaseContext(), "In Else :(", Toast.LENGTH_LONG ).show();
        }

    }

    public void CallQRScannerActivity()
    {
        Intent i = new Intent(getBaseContext(),QRScannerActivity.class);
        startActivityForResult(i,0,null);
    }

    public void callMultiChoiceActivity(String question, String answer){
        Intent i = new Intent(getBaseContext(), MultiChoiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("QUESTION", question);
        bundle.putString("ANSWER", answer);
        i.putExtras(bundle);
        setIntent(i);
        startActivityForResult(i, 0, null);
    }

    public void callSingleAnswerActivity(String question, String answer){
        Intent i = new Intent(getBaseContext(), SingleAnswerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("QUESTION", question);
        bundle.putString("ANSWER", answer);
        i.putExtras(bundle);
        setIntent(i);
        startActivityForResult(i, 0, null);
    }

//    public void callImagequestionActivity(){
//        Intent i = new Intent(getBaseContext(), ImageQuestionActivity.class);
//
//        startActivityForResult(i, 0, null);
//    }

    public void callQuestionManager(){
        qm = new QuestionManager(this);
        qm.nextQuestion();
    }



}
