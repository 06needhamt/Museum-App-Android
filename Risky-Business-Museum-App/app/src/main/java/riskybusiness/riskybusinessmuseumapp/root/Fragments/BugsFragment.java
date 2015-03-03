package riskybusiness.riskybusinessmuseumapp.root.Fragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;

/**
 * Created by Tom on 03/02/2015.
 */

public class BugsFragment extends Fragment {
    TextView Title, SubTitle, Description;
    ImageView Map;
    ImageButton BugsGoButton;
    Spinner BugsTrailSpinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bugs, container, false);
        view.setPadding(0,0,0,0);
        /*Button btnEnter = (Button) OurView.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Ancient World Button Clicked");
            }
        });*/
        DisplayMetrics size = new DisplayMetrics() ;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(size);
        int height = size.heightPixels;
        int width = size.widthPixels;
        //Toast.makeText(getActivity().getBaseContext(), (CharSequence) String.valueOf(height),Toast.LENGTH_SHORT).show();
        allocateViews(view);
        getSpinnerWorking(view);
        //setSpinnerContentSylte();
        createLayoutParams(height, width, view);

        return view;
    }

    private void getSpinnerWorking(View view){ //view http://examples.javacodegeeks.com/android/core/ui/spinner/android-spinner-drop-down-list-example/ and http://www.mkyong.com/android/android-spinner-drop-down-list-example/
        String[] trails = getActivity().getResources().getStringArray(R.array.BugTrails);
        TextView[] texts = new TextView[trails.length];
        for(int i = 0; i > trails.length; i++){
            texts[i].setText(trails[i]);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_rows, trails);
        dataAdapter.setDropDownViewResource(R.layout.spinner_rows);
        BugsTrailSpinner.setAdapter(dataAdapter);
    }

    private void setSpinnerContentSylte(){
        //setting text styles for inside the Spinner (as they are taken from strings.xml which does not include any style
        int counter = 0;
        while(BugsTrailSpinner.getItemAtPosition(counter) != null){
            System.out.println("Inside Spinner Loop");
            TextView t = (TextView) BugsTrailSpinner.getItemAtPosition(counter);
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
        Map.setImageResource(R.drawable.floor_2);
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

        BugsTrailSpinner = (Spinner) view.findViewById(R.id.BugsTrailSpinner);

        BugsGoButton = (ImageButton) view.findViewById(R.id.BugsGoButton);
        BugsGoButton.setImageResource(R.drawable.transparent__icon_trail_bugs); //Placeholder image for "GO!" Button
        BugsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity hp = (HomePageActivity) getActivity();
                hp.callQuestionManager();
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
        BugsTrailSpinner.setLayoutParams(BugsTrailSpinnerParams(screenHeight,screenWidth)); //Keep this before BugsGoButton as it relies upon it.
        BugsGoButton.setLayoutParams(BugsGoButtonParams(screenHeight, screenWidth));
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
        params.topMargin = (int) (screenHeight * 0.00); //!!!
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

    private FrameLayout.LayoutParams BugsTrailSpinnerParams(int screenHeight, int screenWidth) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenHeight, screenWidth);
        params.gravity = Gravity.LEFT;
        params.topMargin = (int) (screenHeight * 0.65); //!!!
        params.leftMargin= (int) (screenWidth * 0.05);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }

    private FrameLayout.LayoutParams BugsGoButtonParams(int screenHeight, int screenWidth) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenHeight, screenWidth);
        params.gravity = Gravity.RIGHT;
        params.topMargin = (int) (screenHeight * 0.62); //!!!
        params.rightMargin = (int) (screenWidth * 0.1);
        //params.leftMargin= BugsTrailSpinner.getMeasuredWidth() + (int) (screenWidth * 0.02) + 10; //moving this depending on the width of the BugsTrailSpinner width + it's left margin + 10 pixels for good measure
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }
}
