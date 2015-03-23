package riskybusiness.riskybusinessmuseumapp.root.Fragments.mapFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import riskybusiness.riskybusinessmuseumapp.R;

/**
 * Created by Tom on 04/02/2015.
 */
public class ThirdFloorFragment extends Fragment{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third, container, false);
        DisplayMetrics size = new DisplayMetrics() ;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(size);
        int height = size.heightPixels;
        int width = size.widthPixels;

        CreateLayoutParams(height,width,view);
        return view;
    }

    private void CreateLayoutParams(int screenHeight, int screenWidth, View v) {
        TextView TopText = (TextView) v.findViewById(R.id.TopText);
        TopText.setLayoutParams(TopTextLayoutParams(screenHeight,screenWidth));
        TopText.setGravity(Gravity.CENTER);
        ImageView Map = (ImageView) v.findViewById(R.id.Map);
        Map.setImageResource(R.drawable.floor_3);
        Map.setLayoutParams(mapLayoutParams(screenHeight,screenWidth));

    }

    private FrameLayout.LayoutParams TopTextLayoutParams(int screenHeight, int screenWidth) {
        int TopTextWidth,TopTextHeight;
        TopTextWidth = screenWidth - 20;
        TopTextHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(TopTextWidth, TopTextHeight); //setting gravity to center horizontal
        params.topMargin = (int) (screenHeight * 0.05); //!!!!
        params.gravity = Gravity.CENTER_HORIZONTAL;
        return params;
    }

    private FrameLayout.LayoutParams mapLayoutParams(int screenHeight, int screenWidth){
        int mapWidth, mapHeight;
        mapWidth = FrameLayout.LayoutParams.WRAP_CONTENT;
        mapHeight = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mapWidth, mapHeight);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (screenHeight * 0.13);
        return params;
    }
}
