package riskybusiness.riskybusinessmuseumapp.root.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.classes.ArtefactImage;
import riskybusiness.riskybusinessmuseumapp.root.classes.ButtonCreator;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.ArtefactInfo;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailInfo;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailManager;

/**
 * Ancient World Screen dynamically creating itself using FrameLayout
 * Created by Tom on 03/02/2015.
 * @author Tom
 * @author Chris
 * @author Alex
 */
public class ArtefactTrailSelectorFragment extends Fragment implements AppConstants {
    View view;
    TextView artefactTitle, artefactSubTitle, artefactDescription, artefactSpinnerIstruction;
    ImageView artefactImage;
    ImageButton artefactGoButton;
    Spinner artefactTrailSpinner;
    int artefactNumber;
    int trailType;

    ArtefactInfo currentArtefact;
    TrailManager trailManager;

    Bundle bundle;

    /**
     * List<TrailInfo> trails:
     * Used to store a list of trail info from database
     * This is initialised in getSpinnerWorking
     * Reference this for trail information when the user selects a trail
     */
    List<TrailInfo> trails;
    int trailID = 0; // The ID of the chosen trail

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_artefact_trail_selector, container, false);
        view.setPadding(0,0,0,0);

        trailManager = TrailManager.getTrailManagerInstance(getActivity());

        DisplayMetrics size = new DisplayMetrics() ;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(size);
        int height = size.heightPixels;
        int width = size.widthPixels;
        //Toast.makeText(getActivity().getBaseContext(), (CharSequence) String.valueOf(height),Toast.LENGTH_SHORT).show();
        allocateViews(view);

        artefactImage.setAdjustViewBounds(true);
        artefactImage.setMaxHeight((int) (height * 0.3));

        //setSpinnerContentSylte();
        createLayoutParams(height, width, view);

        populateFragment(getActivity());

        getSpinnerWorking(view, artefactNumber, getActivity());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(this.getClass().getName(), "In onAttach");
    }

    public void getBundle(Bundle bundle) {
        artefactNumber = bundle.getInt("ARTEFACT_NUMBER", -1);
        trailType = bundle.getInt("TRAIL_TYPE", -1);
    }

    public void populateFragment(Context context) {
        trailManager = TrailManager.getTrailManagerInstance(context);

        if(artefactNumber == -1 || trailType == -1) {
            Log.e("ArtefactTrailSelector", "Artefact number not received from bundle");

            return;
        }

        currentArtefact = trailManager.currentArtefact;

        artefactTitle.setText(R.string.Associated);
        artefactSubTitle.setText(currentArtefact.name);
        artefactImage.setImageDrawable(new ArtefactImage(getActivity(), currentArtefact.imageName).getImage());
        artefactDescription.setText(currentArtefact.description);
    }


    private void getSpinnerWorking(View view, int artefactNumber, Context context ){ //view http://examples.javacodegeeks.com/android/core/ui/spinner/android-spinner-drop-down-list-example/ and http://www.mkyong.com/android/android-spinner-drop-down-list-example/
        //String[] trails = getActivity().getResources().getStringArray(R.array.BugTrails);
        trails = new ArrayList<>();
        String[] trailNames;

        //Context context = getActivity(); // Need the context for the TrailManager

        if(context == null) { // Context not initialised
            System.out.println("Error in ArtefactTrailSelectorFragment initialisation");
        }
        else {

            trails = trailManager.getArtefactTrails(artefactNumber, trailType); // Search for 2 == Ancient World Explorer trails

            if(trails != null) { // Trail names to polpulate spinner
                TextView[] texts = new TextView[trails.size() + 1];
                trailNames = new String[trails.size() + 1];

                trailNames[0] = "Please select a trail";

                for (int i = 0; i < trails.size(); i++) {
                    trailNames[i + 1] = trails.get(i).name;
                }
            }
            else { // No trail names to populate spinner
                trailNames = new String[1];

                trailNames[0] = getActivity().getResources().getString(R.string.NoTrails);
            }

            // Crashing on view.getContext() == null
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_body, trailNames);
            dataAdapter.setDropDownViewResource(R.layout.spinner_rows);
            artefactTrailSpinner.setAdapter(dataAdapter);
        }
    }

    private void setSpinnerContentSylte(){
        //setting text styles for inside the Spinner (as they are taken from strings.xml which does not include any style
        int counter = 0;
        while(artefactTrailSpinner.getItemAtPosition(counter) != null){
            System.out.println("Inside Spinner Loop");
            TextView t = (TextView) artefactTrailSpinner.getItemAtPosition(counter);
            t.setTextColor(getResources().getColor(R.color.White));
            t.setTextSize(20);
            counter++;
        }
    }
    private void allocateViews(View view){
        artefactTitle = (TextView) view.findViewById(R.id.ArtefactTitle);

        artefactSubTitle = (TextView) view.findViewById(R.id.ArtefactSubTitle);


        artefactImage = (ImageView) view.findViewById(R.id.ArtefactImage);
//        artefactImage.setImageDrawable(new ArtefactImage(getActivity(), artefact.));


        artefactDescription = (TextView) view.findViewById(R.id.ArtefactDescription);
        artefactDescription.setGravity(Gravity.CENTER);

        artefactTrailSpinner = (Spinner) view.findViewById(R.id.ArtefactTrailSpinner);

        artefactGoButton = (ImageButton) view.findViewById(R.id.ArtefactTrailGoButton);
        artefactGoButton.setBackgroundResource(R.drawable.transparent__icon_trail_ancientworld); //Placeholder image for "GO!" Button
        artefactGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity hp = (HomePageActivity) getActivity();
                int choice;

                // Get the chosen trail id from the spinner before setting trailID
                choice = artefactTrailSpinner.getSelectedItemPosition();

                if(choice == 0) return; // User did not select a trail so ignore trail button

                trailID = trails.get(choice - 1).trailID; // Get choice - ignoring instruction string at element 0

                hp.callQuestionManager(trailID); // Call the questionManager with chosen trail
            }
        });
    }

    /**
     * Setting the parameter for all the fragments contained within the Ancient World screen
     * @param screenHeight screen height as integer
     * @param screenWidth screen width as integer
     * @param view View Object
     */
    private void createLayoutParams(int screenHeight, int screenWidth, View view){
        artefactTitle.setLayoutParams(artefactTitleLayoutParams(screenHeight, screenWidth));
        artefactSubTitle.setLayoutParams(artefactSubTitleLayoutParams(screenHeight, screenWidth));
        artefactImage.setLayoutParams(artefactImageLayoutParams(screenHeight, screenWidth));
        artefactDescription.setLayoutParams(descriptionLayoutParams(screenHeight, screenWidth));
        artefactTrailSpinner.setLayoutParams(AncientWorldTrailSpinnerParams(screenHeight,screenWidth)); //Keep this before AncientWorldGoButton as it relies upon it.
        artefactGoButton.setLayoutParams(AncientWorldGoButtonParams(screenHeight, screenWidth));
        //spinnerInstruction.setLayoutParams(spinnerInstructionLayoutParams(screenHeight, screenWidth));

    }

    private FrameLayout.LayoutParams artefactTitleLayoutParams(int screenHeight, int screenWidth){
        int titleWidth, titleHeight;
        titleWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
        titleHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(titleWidth, titleHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.0); //!!!

        return params;
    }

    private FrameLayout.LayoutParams artefactSubTitleLayoutParams(int screenHeight, int screenWidth){
        int subTitleWidth, subTitleHeight;
        subTitleWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
        subTitleHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(subTitleWidth, subTitleHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.10); //!!!

        return params;
    }

    private FrameLayout.LayoutParams artefactImageLayoutParams(int screenHeight, int screenWidth){
        int mapWidth, mapHeight;
        mapWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
        mapHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mapWidth, mapHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.20); //!!!

        return params;
    }

    private FrameLayout.LayoutParams descriptionLayoutParams(int screenHeight, int screenWidth) {
        int descWidth, descHeight;
        descWidth = screenWidth - 10;
        descHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(descWidth, descHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.50); //!!!
        return params;
    }

    private FrameLayout.LayoutParams spinnerInstructionLayoutParams(int screenHeight, int screenWidth) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenWidth, screenHeight);
        params.gravity = Gravity.LEFT;
        params.topMargin = (int) (screenHeight * 0.62); //!!!
        params.leftMargin= (int) (screenWidth * 0.05);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }

    private FrameLayout.LayoutParams AncientWorldTrailSpinnerParams(int screenHeight, int screenWidth) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenHeight, screenWidth);
        params.gravity = Gravity.LEFT;
        params.topMargin = (int) (screenHeight * 0.65); //!!!
        params.leftMargin= (int) (screenWidth * 0.05);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }

    private FrameLayout.LayoutParams AncientWorldGoButtonParams(int screenHeight, int screenWidth) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenHeight, screenWidth);
        params.gravity = Gravity.RIGHT;
        params.topMargin = (int) (screenHeight * 0.66); //!!!
        params.rightMargin = (int) (screenWidth * 0.05);
        //params.leftMargin= BugsTrailSpinner.getMeasuredWidth() + (int) (screenWidth * 0.02) + 10; //moving this depending on the width of the BugsTrailSpinner width + it's left margin + 10 pixels for good measure
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }
}


