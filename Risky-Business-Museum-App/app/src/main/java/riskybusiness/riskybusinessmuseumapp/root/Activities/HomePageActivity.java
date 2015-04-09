package riskybusiness.riskybusinessmuseumapp.root.Activities;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.List;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.AquariumFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.BugsFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.AncientWorldFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments.FirstFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.QRFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.WelcomeFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.DinosaursFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments.FifthFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments.ForthFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments.GroundFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.informationFragments.InformationFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments.SecondFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.SpaceAndTimeFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments.ThirdFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.WorldCulturesFragment;
import riskybusiness.riskybusinessmuseumapp.root.classes.ButtonCreator;
import riskybusiness.riskybusinessmuseumapp.root.classes.QRResultHandler;
import riskybusiness.riskybusinessmuseumapp.root.questionmanager.*;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.ArtefactInfo;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailInfo;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailManager;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailStepInfo;

public class HomePageActivity extends FragmentActivity implements AppConstants {
    int toptable;
    int bottomtable;
    Fragment[] fragments;
    Fragment[] Mapfragments;
    Fragment[] BottomFragments;
    Fragment[] InfoFragments;
    String Content;
    String Format;
    int currentTrailScore;
    TrailManager trailManager;
    QuestionManager qm;
    ArtefactInfo artefact;  // Stores artefact information from the database
    List<TrailInfo> trails; // List of trails

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toptable = R.id.topTableForButtons;
        bottomtable = R.id.bottomTableForButtons;
        fragments = CreateFragments();
        Mapfragments = CreateMapFragments();
        BottomFragments = CreateBottomFragments();
        InfoFragments = CreateInfoFragments();
        Log.e("R id", String.valueOf(R.drawable.blue___icon_museuminfo));
        ButtonCreator btncreate = new ButtonCreator(this,toptable,bottomtable,R.drawable.class.getFields(),fragments, Mapfragments, BottomFragments,InfoFragments);
        btncreate.populateTopButtons();
        btncreate.populateBottomButtons();
        //btncreate.populateMapButtons();
        //setContentView(R.layout.fragment_bugs);

        trailManager = TrailManager.getTrailManagerInstance(this); // Instantiate (If not already) Singleton TrailManager

        AddFragment();
        currentTrailScore = 0;

    }

    private Fragment[] CreateFragments() {
        Fragment[] fragments = new Fragment[6];
        fragments[0] = new AncientWorldFragment();
        fragments[1] = new AquariumFragment();
        fragments[2] = new BugsFragment();
        fragments[3] = new WorldCulturesFragment();
        fragments[4] = new DinosaursFragment();
        fragments[5] = new SpaceAndTimeFragment();
        return fragments;
    }

    private Fragment[] CreateMapFragments()
    {
        Fragment[] fragments = new Fragment[6];
        fragments[0] = new GroundFloorFragment();
        fragments[1] = new FirstFloorFragment();
        fragments[2] = new SecondFloorFragment();
        fragments[3] = new ThirdFloorFragment();
        fragments[4] = new ForthFloorFragment();
        fragments[5] = new FifthFloorFragment();
        return fragments;
    }

    private Fragment[] CreateBottomFragments(){
        Fragment[] fragments = new Fragment[5];
        fragments[0] = null;
        fragments[1] = null;
        fragments[2] = new QRFragment();
        fragments[3] = new GroundFloorFragment();
        fragments[4] = new InformationFragment();
        return fragments;
    }
    private Fragment[] CreateInfoFragments()
    {
        Fragment[] fragments = new Fragment[6];
        fragments[0] = new Fragment();
        fragments[1] = new Fragment();
        fragments[2] = new Fragment();
        fragments[3] = new Fragment();
        fragments[4] = new Fragment();
        fragments[5] = new Fragment();
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
        if(data.getExtras() == Bundle.EMPTY)
        {
            Toast.makeText(getBaseContext(),"Not Ok",Toast.LENGTH_LONG).show();
            return;
        }
        Bundle tempBundle = data.getExtras();
        String from = tempBundle.getString("FROM", "");
        boolean exit = false;

        if(from.equals("MultiChoiceActivity")) {

            if(resultCode == RESULT_OK){
                Bundle b = data.getExtras();
                exit = b.getBoolean("EXIT", false);
                if(exit){
                    currentTrailScore = 0;
                    qm.setTrailEnded(true);
                    qm.nextQuestion();
                    return;
                }
                currentTrailScore += b.getInt("Score", -1);
                qm.nextQuestion();
            }
        }

        else if(from.equals("QRScannerActivity")) { // Browsing QR codes
            if (resultCode == RESULT_OK) {
                int qrCode;
//                if(!data.hasExtra("B"))
//                {
//                    Toast.makeText(getBaseContext(),"Error scanning QR Code",Toast.LENGTH_LONG).show();
//                    return;
//                }
                Bundle b = data.getExtras();

                Content = b.getString("Content", "No Value");
                QRResultHandler qrh = new QRResultHandler(Content);


                // Get the result from the scanned code
                qrCode = qrh.getCode(); // Get the code from the QR code

                //  Retrieve the artefact information from the TrailManager, this includes associated trail types
                artefact = trailManager.browseArtefactID(qrCode);

                System.out.println("Browsed Artefact = " + artefact.artefactID + " " + artefact.name);

                // Get the TrailManager to pre-load any associated trails information
                // trailManager.getArtefactTrails(artefact.artefactID);

                // TODO: Display the artefact information on screen



                // TODO: Indicate types of trails for the browsed artefact on bottom fragment using:
                // artefact.trailStatus 0 = No trail, 1 = Normal, 2 = Explorer, 3 = Normal and explorer


//                if(qrh.getResult().equals(DATABASE EXHIBIT ITEM)){
//                    Show information on the item the person scanned
//                    Or ask user if they want to start a trail from this exhibit
//                }
            }
            else
            {
                Toast.makeText(getBaseContext(),"Not Ok",Toast.LENGTH_LONG).show();
                return;
            }
        }
        else if(from.equals("SingleAnswerActivity")) {
            if(resultCode == RESULT_OK){
                Bundle b = data.getExtras();
                currentTrailScore += b.getInt("Score", -1);
                exit = b.getBoolean("EXIT", false);
                if(exit){
                    currentTrailScore = 0;
                    qm.setTrailEnded(true);
                    qm.nextQuestion();
                    return;
                }
                qm.nextQuestion();
            }

    //            qm.nextQuestion();
        }
//        else if(data.getClass().getSimpleName().equals(SingleAnswerActivity.class)) {
//            if(resultCode == RESULT_OK){
//                Bundle b = data.getExtras();
//                currentTrailScore = b.getInt("Score", -1);
//            }
//              Toast.makeText(getBaseContext(), "Score:" + currentTrailScore,Toast.LENGTH_LONG ).show();
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
        bundle.putInt("TRAIL_POSITION", qm.getQuestionNum());
        bundle.putInt("TRAIL_LENGTH", qm.getSteps().size());
        bundle.putInt("SCORE", currentTrailScore);
        i.putExtras(bundle);
        setIntent(i);
        startActivityForResult(i, 0, null);
    }

    public void callSingleAnswerActivity(String question, String answer){
        Intent i = new Intent(getBaseContext(), SingleAnswerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("QUESTION", question);
        bundle.putString("ANSWER", answer);
        bundle.putInt("TRAIL_POSITION", qm.getQuestionNum());
        bundle.putInt("TRAIL_LENGTH", qm.getSteps().size());
        bundle.putInt("SCORE", currentTrailScore);
        i.putExtras(bundle);
        setIntent(i);
        startActivityForResult(i, 0, null);
    }

//    public void callImageQuestionActivity(){
//        Intent i = new Intent(getBaseContext(), ImageQuestionActivity.class);
//
//        startActivityForResult(i, 0, null);
//    }

    /**
     * Instantiate a QuestionManager
     * Before calling this the TrailManager must be set to the appropriate trail using a call to:
     * TrailManager.setTrail(trailID)
     */
    public void callQuestionManager(int trailID){

        trailManager.setTrail(trailID); // Set the trail and loads all associated trail steps

        // Check that a trail has been set using TrailManager.setTrail for the QuestionManager to use
        if(trailManager.getMode() == MODE_ON_TRAIL) {

            // Instantiate new QuestionManager passing it the trail steps
            qm = new QuestionManager(this, trailManager.getCurrentTrailSteps());

            qm.nextQuestion();
        }
        else {
            Log.d("RiskyBusinessMuseumApp", "HomePageActivity, callQuestionManager(): No Trails found for Trail ID " + trailID);
        }
    }
}
