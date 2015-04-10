package riskybusiness.riskybusinessmuseumapp.root.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;
import riskybusiness.riskybusinessmuseumapp.root.Activities.QRScannerActivity;

/**
 * Created by Alex and Chris on 01/03/2015
 * Database repo test kjhg
 */
public class QRFragment extends Fragment {
    View OurView;
    TextView Title, Hint, Description;
    ImageButton QRButton;
    ImageView ItemImage;
    String[] hints;
    int height;
    int width;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qr, container, false);
        this.OurView = view;
        DisplayMetrics size = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(size);
        height = size.heightPixels;
        width = size.widthPixels;
        setIDs();
        setContent();
        setAllLayoutParams(height, width, OurView);

        //TESTING
        ItemImage.setImageResource(R.drawable.royal_blue_ipad_wallpaper);
        //TESTING END
        return OurView;
    }


    /**
     * Setting resources displayed on this fragment
     */
    private void setIDs(){
        Title = (TextView) OurView.findViewById(R.id.QRFragmentTitle);
        Hint = (TextView) OurView.findViewById(R.id.QRFragmentHint);
        Description = (TextView) OurView.findViewById(R.id.QRFragmentDescription);
        QRButton = (ImageButton) OurView.findViewById(R.id.QRFragmentQRButton);
        ItemImage = (ImageView) OurView.findViewById(R.id.QRFragmentItemImage);
    }

    /**
     * selecting and siplaying a randomly selected hint from the array of hints
     */
    private void selectRandomHint(){
        Random random = new Random();
        Hint.setText(hints[random.nextInt(hints.length)]);
    }

    private void setContent(){
        hints = getResources().getStringArray(R.array.Hints); //array of hints from strings.xml
        Title.setText(R.string.QRFragmentTitle);
        Title.setTextColor(getResources().getColor(R.color.White)); //White font
        Hint.setText(R.string.DefaultHint);
        Hint.setTextColor(getResources().getColor(R.color.White)); //White font

        QRButton.setBackgroundResource(R.drawable.transparent__icon_qr);
        QRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity hp = (HomePageActivity) getActivity();
                hp.CallQRScannerActivity();
                selectRandomHint();
            }
        });
        QRButton.setScaleX(2.0f);
        QRButton.setScaleY(2.0f); //doubling size of qr button


        ItemImage.setMaxHeight((int) (height * 0.37)); //setting maximum height of the picture to a third of the screen
        //ItemImage.setMaxWidth((int) (height * (1/3)));

        Description.setText(R.string.LoremIpsum);

        Description.setMovementMethod(new ScrollingMovementMethod()); //taken from http://stackoverflow.com/questions/1748977/making-textview-scrollable-in-android
        Description.setMaxLines(6);
    }

    private void setAllLayoutParams(int height, int width, View view){
        Title.setLayoutParams(createTitleParams(height, width));
        Hint.setLayoutParams(createHintParams(height, width));
        QRButton.setLayoutParams(createQRButtonParams(height, width));
        ItemImage.setLayoutParams(createItemImageParams(height,width));
        Description.setLayoutParams(createDescriptionParams(height,width));
    }


    private FrameLayout.LayoutParams createTitleParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.03); //!!!
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.leftMargin = (int) (width * 0.01);
        return params;
    }

    private FrameLayout.LayoutParams createDescriptionParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.50); //!!!
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.leftMargin = (int) (width * 0.01);
        return params;
    }

    private FrameLayout.LayoutParams createItemImageParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (height * 0.12); //!!!
        params.leftMargin = (int) (width * 0.01);
        params.rightMargin = (int) (width * 0.01);
        return params;
    }

    private FrameLayout.LayoutParams createHintParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.82); //!!!
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.leftMargin = (int) (width * 0.01);
        return params;
    }

    private FrameLayout.LayoutParams createQRButtonParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (height * 0.70); //!!!

        return params;
    }
}