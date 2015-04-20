package riskybusiness.riskybusinessmuseumapp.root.Fragments.explorerTrailFragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailInfo;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailManager;

/**
 * Created by Chris on 14/04/2015.
 */
public class ExploreAquariumFragment extends Fragment implements AppConstants{
    TextView Title, SubTitle, Description, spinnerIstruction;
    ImageView Map;
    ImageButton AquariumGoButton;
    Spinner AquariumTrailSpinner;

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

        View view = inflater.inflate(R.layout.fragment_aquarium, container, false);
        view.setPadding(0,0,0,0);

        DisplayMetrics size = new DisplayMetrics() ;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(size);
        int height = size.heightPixels;
        int width = size.widthPixels;

        allocateViews(view);
        getSpinnerWorking(view);
        //setSpinnerContentSylte();
        createLayoutParams(height, width, view);

        return view;
    }

    private void getSpinnerWorking(View view){ //view http://examples.javacodegeeks.com/android/core/ui/spinner/android-spinner-drop-down-list-example/ and http://www.mkyong.com/android/android-spinner-drop-down-list-example/
        //String[] trails = getActivity().getResources().getStringArray(R.array.BugTrails);
        trails = new ArrayList<>();
        String trailNames[];

        Context context = getActivity(); // Need the context for the TrailManager

        if(context == null) { // Context not initialised
            System.out.println("Error in Ancient World trail spinner initialisation");
        }
        else {
            TrailManager tm = TrailManager.getTrailManagerInstance(getActivity());

            // TODO: set correct trail NO AQUARIUM TRAIL SET UP
            // trails = tm.getExhibitTrails(2, TRAIL); // Search for 2 == Ancient World
            trails = null;

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
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_body, trailNames);
            dataAdapter.setDropDownViewResource(R.layout.spinner_rows);
            AquariumTrailSpinner.setAdapter(dataAdapter);
        }
    }

    private void setSpinnerContentSylte(){
        //setting text styles for inside the Spinner (as they are taken from strings.xml which does not include any style
        int counter = 0;
        while(AquariumTrailSpinner.getItemAtPosition(counter) != null){
            System.out.println("Inside Spinner Loop");
            TextView t = (TextView) AquariumTrailSpinner.getItemAtPosition(counter);
            t.setTextColor(getResources().getColor(R.color.White));
            t.setTextSize(20);
            counter++;
        }
    }
    private void allocateViews(View view){
        Title = (TextView) view.findViewById(R.id.title);

        SubTitle = (TextView) view.findViewById(R.id.subTitle);
        SubTitle.setGravity(Gravity.CENTER);

        Map = (ImageView) view.findViewById(R.id.Map);
        Map.setImageResource(R.drawable.floor_1);

        /**
         Map.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        //Toast.makeText(getActivity().getBaseContext(), "Map Clicked", Toast.LENGTH_SHORT).show();
        HomePageActivity hp = (HomePageActivity) getActivity();
        hp.callQuestionManager();
        }
        });
         */

        Description = (TextView) view.findViewById(R.id.description);
        Description.setGravity(Gravity.CENTER);

        AquariumTrailSpinner = (Spinner) view.findViewById(R.id.TrailSpinner);

        AquariumGoButton = (ImageButton) view.findViewById(R.id.TrailGoButton);
        AquariumGoButton.setBackgroundResource(R.drawable.transparent__icon_trail_aquarium); //Placeholder image for "GO!" Button
        AquariumGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity hp = (HomePageActivity) getActivity();
                int choice;

                // Get the chosen trail id from the spinner before setting trailID
                choice = AquariumTrailSpinner.getSelectedItemPosition();

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
        SubTitle.setLayoutParams(subTitleLayoutParams(screenHeight, screenWidth));
        Map.setLayoutParams(mapLayoutParams(screenHeight, screenWidth));
        Description.setLayoutParams(descriptionLayoutParams(screenHeight, screenWidth));
        AquariumTrailSpinner.setLayoutParams(AquariumTrailSpinnerParams(screenHeight,screenWidth)); //Keep this before AquariumGoButton as it relies upon it.
        AquariumGoButton.setLayoutParams(AquariumGoButtonParams(screenHeight, screenWidth));
        //spinnerInstruction.setLayoutParams(spinnerInstructionLayoutParams(screenHeight, screenWidth));

    }

    /**
     * Create specified FrameLayout.LayoutParams parameters to be applied to the SubTitle text field
     * in the Ancient World screen.
     * @param screenHeight screen height as integer
     * @param screenWidth screen width as integer
     * @return FrameLayout.LayoutParams Object
     */
    private FrameLayout.LayoutParams subTitleLayoutParams(int screenHeight, int screenWidth) {
        int subTitleWidth, subTitleHeight;
        subTitleWidth = screenWidth - 20;
        subTitleHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(subTitleWidth, subTitleHeight); //setting gravity to center horizontal
        params.topMargin = (int) (screenHeight * 0.08); //!!!!
        params.gravity = Gravity.CENTER_HORIZONTAL;
        return params;
    }

    private FrameLayout.LayoutParams mapLayoutParams(int screenHeight, int screenWidth){
        int mapWidth, mapHeight;
        mapWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
        mapHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mapWidth, mapHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.13); //!!!
        return params;
    }

    private FrameLayout.LayoutParams descriptionLayoutParams(int screenHeight, int screenWidth) {
        int descWidth, descHeight;
        descWidth = screenWidth - 10;
        descHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(descWidth, descHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.70); //!!!
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

    private FrameLayout.LayoutParams AquariumTrailSpinnerParams(int screenHeight, int screenWidth) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenHeight, screenWidth);
        params.gravity = Gravity.LEFT;
        params.topMargin = (int) (screenHeight * 0.65); //!!!
        params.leftMargin= (int) (screenWidth * 0.05);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }

    private FrameLayout.LayoutParams AquariumGoButtonParams(int screenHeight, int screenWidth) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenHeight, screenWidth);
        params.gravity = Gravity.RIGHT;
        params.topMargin = (int) (screenHeight * 0.66); //!!!
        params.rightMargin = (int) (screenWidth * 0.05);
        //params.leftMargin= BugsTrailSpinner.getMeasuredWidth() + (int) (screenWidth * 0.02) + 10; //moving this depending on the width of the AquariumTrailSpinner width + it's left margin + 10 pixels for good measure
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }
}

