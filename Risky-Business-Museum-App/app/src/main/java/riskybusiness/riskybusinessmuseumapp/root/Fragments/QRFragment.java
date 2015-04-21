package riskybusiness.riskybusinessmuseumapp.root.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.lang.reflect.Method;
import java.util.Random;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;
import riskybusiness.riskybusinessmuseumapp.root.classes.ArtefactImage;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.ArtefactInfo;
import riskybusiness.riskybusinessmuseumapp.root.trailmanager.TrailManager;

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
    TrailManager trailManager;

    Method[] method;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qr, container, false);

        trailManager = TrailManager.getTrailManagerInstance((HomePageActivity) getActivity()); // Instantiate (If not already) Singleton TrailManager

        this.OurView = view;
        DisplayMetrics size = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(size);
        height = size.heightPixels;
        width = size.widthPixels;
        setIDs();
        setContent();
        setAllLayoutParams(height, width, OurView);

        //TESTING IMAGE - please try different images, all should be fitted into the fragment without overlapping onto any text.
        //updateImage(R.drawable.royal_blue_ipad_wallpaper);

        //updateImage("46_rocks");
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
     * Displaying a randomly selected hint from the array of hints
     */
    private void selectRandomHint(){
        Random random = new Random();
        Hint.setText(hints[random.nextInt(hints.length)]);
    }

    /**
     * Public setter method for the text displayed in the Title field of the fragment
     * @param text String containing the name of the item (Keep short!)
     */
    public void updateTitle(String text){
        Title.setText(text);
    }

    /**
     * Public setter method for the text displayed in the scrollable Description field of the fragment
     * @param text String containing the detailed description of the item (Length should not matter as the TextView scrolls)
     */
    public void updateDescription(String text){
        Description.setText(text);
    }

    /**
     * Public setter method for the image displayed on the fragment using a resource ID
     * @param ID Resource ID of the drawable image
     */
    public void updateImage(int ID){
        ItemImage.setImageResource(ID);
    }

    /**
     * Gets an image from the <b>assets/images</b> folder based on imageName
     * @param imageName the name of the image
     */
    public void updateImage(String imageName) {
        Drawable img = new ArtefactImage(getActivity(), imageName).getImage();
        ItemImage.setImageDrawable(img);
    }

    /**
     * Public setter method for the image displayed on the fragment using a drawable
     * @param drawable Drawable class for the image
     */
    public void updateImage(Drawable drawable){
        ItemImage.setImageDrawable(drawable);
    }


    /**
     * Setup method that defines a lot of the layout as well as colours, initial texts and scaling.
     * Also sets up the on click listener for the QR button
     */
    private void setContent(){

        hints = getResources().getStringArray(R.array.Hints); //array of hints from strings.xml
        Title.setText(R.string.QRFragmentTitle);
        Title.setTextColor(getResources().getColor(R.color.White)); //White font
        Hint.setText(R.string.DefaultHint);
        Hint.setGravity(Gravity.CENTER_HORIZONTAL);
        Hint.setTextColor(getResources().getColor(R.color.White)); //White font

        QRButton.setBackgroundResource(R.drawable.transparent__icon_qr);
        QRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrButtonClicked(v);
//                HomePageActivity hp = (HomePageActivity) getActivity();
//                hp.CallQRScannerActivity();
//
//                getArtefactDetails(hp); // Get the details from the scanned artefact
//
//                 selectRandomHint();
            }
        });

        QRButton.setScaleX(2.0f);
        QRButton.setScaleY(2.0f); //doubling size of qr button


        ItemImage.setMaxHeight((int) (height * 0.37)); //setting maximum height of the picture to a third of the screen
        //ItemImage.setMaxWidth((int) (height * (1/3)));

        //Description.setText(R.string.LoremIpsum);
        Description.setGravity(Gravity.LEFT);

        Description.setMovementMethod(new ScrollingMovementMethod()); //taken from http://stackoverflow.com/questions/1748977/making-textview-scrollable-in-android
        Description.setMaxLines(6);
        Description.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    private void qrButtonClicked(View v) {
        ArtefactInfo artefactInfo;

        HomePageActivity hpa = (HomePageActivity) getActivity(); // Get the real HomePageActivity instance

        hpa.CallQRScannerActivity();

        artefactInfo = hpa.getArtefact(); // Populate the artefact with the corresponding details based on scanned QR.
        //populateArtefactViews(artefactInfo);

        selectRandomHint();

    }

    /**
     * Populate the scanned artefact details within the fragment
     * @param artefactInfo Scanned artefact
     */
    private void populateArtefactViews(ArtefactInfo artefactInfo) {
        Title.setText((CharSequence)artefactInfo.name);
        Description.setText((CharSequence) artefactInfo.description);
        ItemImage.setImageDrawable(new ArtefactImage(getActivity(), artefactInfo.imageName));
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