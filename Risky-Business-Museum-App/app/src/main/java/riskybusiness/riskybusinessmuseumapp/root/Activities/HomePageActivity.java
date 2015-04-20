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


import java.util.ArrayList;
import java.util.List;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments.ExploreAncientWorldFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments.ExploreAquariumFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments.ExploreBugsFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments.ExploreDinosaursFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments.ExploreSpaceAndTimeFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments.ExploreWorldCulturesFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments.WelcomeExplorerTrailFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.informationFragments.InformationWebView;
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
import riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments.SecondFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.SpaceAndTimeFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments.ThirdFloorFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.WelcomeTrailFragment;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.trailFragments.WorldCulturesFragment;
import riskybusiness.riskybusinessmuseumapp.root.classes.ButtonCreator;
import riskybusiness.riskybusinessmuseumapp.root.classes.QRResultHandler;
import riskybusiness.riskybusinessmuseumapp.root.questionmanager.*;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.ArtefactInfo;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailInfo;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailManager;

public class HomePageActivity extends FragmentActivity implements AppConstants {
    int toptable;
    int bottomtable;
    Fragment[] fragments;
    Fragment[] Mapfragments;
    Fragment[] BottomFragments;
    Fragment[] InfoFragments;
    Fragment[] ExplorerTrailFragments;
    InformationWebView infoWebView;
    String Content;
    String Format;
    int currentTrailScore;
    ArrayList<Integer> questionScores;
    TrailManager trailManager;
    QuestionManager qm;
    ArtefactInfo artefact;  // Stores artefact information from the database
    List<TrailInfo> trails; // List of trails
    private ButtonCreator btncreate;
    QRFragment qrFragment = new QRFragment();


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
        ExplorerTrailFragments = CreateExplorerTrailFragments();

        Log.e("R id", String.valueOf(R.drawable.blue___icon_museuminfo));
        infoWebView = new InformationWebView();
        btncreate = new ButtonCreator(this,toptable,bottomtable,R.drawable.class.getFields(),fragments, Mapfragments, BottomFragments,infoWebView, ExplorerTrailFragments);
        getBtncreate().populateTopButtons();
        getBtncreate().populateBottomButtons();
        //btncreate.populateMapButtons();
        //setContentView(R.layout.fragment_bugs);

        trailManager = TrailManager.getTrailManagerInstance(this); // Instantiate (If not already) Singleton TrailManager

        AddFragment();
        currentTrailScore = 0;
        questionScores = new ArrayList<Integer>();

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
        fragments[0] = new WelcomeTrailFragment();
        fragments[1] = new WelcomeExplorerTrailFragment();
        fragments[2] = qrFragment; // this fragment is pre initialised so that we can dynamically populate information
        fragments[3] = new GroundFloorFragment();
        fragments[4] = new InformationWebView();
        return fragments;
    }

    @Deprecated
    private Fragment[] CreateInfoFragments()
    {
        Fragment[] fragments = new Fragment[6];
        fragments[0] = new InformationWebView();
        fragments[1] = new InformationWebView();
        fragments[2] = new InformationWebView();
        fragments[3] = new InformationWebView();
        fragments[4] = new InformationWebView();
        fragments[5] = new InformationWebView();
        return fragments;
    }

    private Fragment[] CreateExplorerTrailFragments() {
        Fragment[] fragments = new Fragment[6];
        fragments[0] = new ExploreAncientWorldFragment();
        fragments[1] = new ExploreAquariumFragment();
        fragments[2] = new ExploreBugsFragment();
        fragments[3] = new ExploreWorldCulturesFragment();
        fragments[4] = new ExploreDinosaursFragment();
        fragments[5] = new ExploreSpaceAndTimeFragment();
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
        String from = tempBundle.getString(FROM_TAG, "");
        boolean exit = false;

        if(from.equals(FROM_MULTI_CHOICE)) {
            if(resultCode == RESULT_OK){
                Bundle b = data.getExtras();
                appendQuestionScores(b.getInt(SCORE_TAG, -1));
                exit = b.getBoolean(EXIT_TAG, false);
                if(exit){
                    currentTrailScore = 0;
                    questionScores.clear();
                    qm.setTrailEnded(true);
                    qm.nextQuestion();
                    return;
                }
                currentTrailScore += b.getInt(SCORE_TAG, -1);
                System.out.println("Current trail score = " + currentTrailScore);
                qm.nextQuestion();
            }
        } else if(from.equals(FROM_PICTURE_MULTI_CHOICE)) {
            if(resultCode == RESULT_OK){
                Bundle b = data.getExtras();
                appendQuestionScores(b.getInt(SCORE_TAG, -1));
                exit = b.getBoolean(EXIT_TAG, false);
                if(exit){
                    currentTrailScore = 0;
                    questionScores.clear();
                    qm.setTrailEnded(true);
                    qm.nextQuestion();
                    return;
                }
                currentTrailScore += b.getInt(SCORE_TAG, -1);
                qm.nextQuestion();
            }
        }

        else if(from.equals(FROM_QR_SCANNER)) { // Browsing QR codes
            if (resultCode == RESULT_OK) {
                int qrCode;
                int trailCount = 0;
                int explorerCount = 0;

                List<TrailInfo> trailList;  // List of trails associated with the artefact

                Bundle b = data.getExtras();

                Content = b.getString(CONTENT_TAG, "No Value");
                QRResultHandler qrh = new QRResultHandler(Content);


                // Get the result from the scanned code
                qrCode = qrh.getCode(); // Get the code from the QR code

                //CATCHING A BUG


                //  Retrieve the artefact information from the TrailManager, this includes associated trail types
                try {
                    artefact = trailManager.browseArtefactID(qrCode);
                }
                catch(NullPointerException E){ //catching nullpointer generated by QRResultHandler.getCode() returning -1
                    E.printStackTrace();
                    System.out.println("CQ Code number = " + qrh.getCode());
                    return;
                }

                System.out.println("Browsed Artefact = " + artefact.artefactID + " " + artefact.name);

                // btncreate.lightUpButtons(TRAIL_AND_EXPLORER);
                // Get the TrailManager to pre-load any associated trails information
                trailList = trailManager.getArtefactTrails(artefact.artefactID, TRAIL_AND_EXPLORER);

                if(trailList != null) { // Are there any trails associated with the artefact?
                    for (TrailInfo t : trailList) { // find trail types
                        if (t.trailType == PURPLE_TRAIL)
                            trailCount = 1;
                        else if (t.trailType == PURPLE_EXPLORER)
                            explorerCount = 1;
                    }

                    if(trailCount > 0) // If ordinary trail light trail button
                        getBtncreate().lightUpButtons(TRAIL);

                    if(explorerCount > 0) // If explorer trail light up explorer trail button
                        getBtncreate().lightUpButtons(EXPLORER);
                }


                // TODO: Display the artefact information on screen
                qrFragment.updateTitle(artefact.name);
                qrFragment.updateDescription(artefact.description);
                qrFragment.updateImage(artefact.imageName);


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
        else if(from.equals(FROM_SINGLE_ANSWER)) {
            if(resultCode == RESULT_OK){
                Bundle b = data.getExtras();
                appendQuestionScores(b.getInt(SCORE_TAG, -1));
                currentTrailScore += b.getInt(SCORE_TAG, -1);
                exit = b.getBoolean(EXIT_TAG, false);
                if(exit){
                    currentTrailScore = 0;
                    questionScores.clear();
                    qm.setTrailEnded(true);
                    qm.nextQuestion();
                    return;
                }
                qm.nextQuestion();
            }
        }
        else if(from.equals(FROM_PICTURE_QR_QUESTION)) {
            if(resultCode == RESULT_OK){
                Bundle b = data.getExtras();
                appendQuestionScores(b.getInt(SCORE_TAG, -1));
                currentTrailScore += b.getInt(SCORE_TAG, -1);
                exit = b.getBoolean(EXIT_TAG, false);
                if(exit){
                    currentTrailScore = 0;
                    questionScores.clear();
                    qm.setTrailEnded(true);
                    qm.nextQuestion();
                    return;
                }
                qm.nextQuestion();
            }
        }

        else if(from.equals(FROM_TRAIL_RESULT_SCREEN)){
            if(resultCode == RESULT_OK){
                //resetting trail values
                currentTrailScore = 0;
                questionScores.clear();
            }
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

    /**
     * Returns the current artefact information (if any)
     * @return artefact
     */
    public ArtefactInfo getArtefact() { return artefact; }


    private void appendQuestionScores(int score){
        questionScores.add(score);
    }

    public void CallQRScannerActivity()
    {
        Intent i = new Intent(getBaseContext(),QRScannerActivity.class);
        startActivityForResult(i,0,null);
    }

    public void callPictureQRQuestionActivity(String question, String answer, String image){
        Intent i = new Intent(getBaseContext(), PictureQRQuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(QUESTION_TAG, question);
        bundle.putString(ANSWER_TAG, answer);
        bundle.putInt(TRAIL_POSITION_TAG, qm.getQuestionNum());
        bundle.putInt(TRAIL_LENGTH_TAG, qm.getSteps().size());
        bundle.putInt(SCORE_TAG, currentTrailScore);
        bundle.putString(IMAGE_TAG, image);
        i.putExtras(bundle);
        setIntent(i);
        startActivityForResult(i, 0, null);
    }

    public void callPictureMultiChoiceActivity(String question, String answer, String image){
        Intent i = new Intent(getBaseContext(), PictureMultiChoiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(QUESTION_TAG, question);
        bundle.putString(ANSWER_TAG, answer);
        bundle.putInt(TRAIL_POSITION_TAG, qm.getQuestionNum());
        bundle.putInt(TRAIL_LENGTH_TAG, qm.getSteps().size());
        bundle.putInt(SCORE_TAG, currentTrailScore);
        bundle.putString(IMAGE_TAG, image);
        i.putExtras(bundle);
        setIntent(i);
        startActivityForResult(i, 0, null);
    }

    public void callMultiChoiceActivity(String question, String answer){
        Intent i = new Intent(getBaseContext(), MultiChoiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(QUESTION_TAG, question);
        bundle.putString(ANSWER_TAG, answer);
        bundle.putInt(TRAIL_POSITION_TAG, qm.getQuestionNum());
        bundle.putInt(TRAIL_LENGTH_TAG, qm.getSteps().size());
        bundle.putInt(SCORE_TAG, currentTrailScore);
        i.putExtras(bundle);
        setIntent(i);
        startActivityForResult(i, 0, null);
    }

    public void callTrailResultActivity(){
        Intent i = new Intent(getBaseContext(), TrailResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(SCORE_TAG, currentTrailScore);
        bundle.putIntegerArrayList(QUESTION_SCORES_TAG, questionScores);
        bundle.putString(TRAIL_NAME_TAG, trailManager.currentTrail.name);
        i.putExtras(bundle);
        setIntent(i);
        startActivityForResult(i, 0, null);
    }

    public void callSingleAnswerActivity(String question, String answer){
        Intent i = new Intent(getBaseContext(), SingleAnswerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(QUESTION_TAG, question);
        bundle.putString(ANSWER_TAG, answer);
        bundle.putInt(TRAIL_POSITION_TAG, qm.getQuestionNum());
        bundle.putInt(TRAIL_LENGTH_TAG, qm.getSteps().size());
        bundle.putInt(SCORE_TAG, currentTrailScore);
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

    public ButtonCreator getBtncreate() {
        return btncreate;
    }

}
