package riskybusiness.riskybusinessmuseumapp.root.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Activities.HomePageActivity;
import riskybusiness.riskybusinessmuseumapp.root.Activities.QRScannerActivity;

/**
 * Created by Alex and Chris on 01/03/2015
 */
public class QRFragment extends Fragment {
    View OurView;
    TextView Title, Hint;
    ImageButton QRButton;

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
        int height = size.heightPixels;
        int width = size.widthPixels;
        setIDs();
        setContent();
        setAllLayoutParams(height, width, OurView);
        return OurView;

    }

    private void setIDs(){
        Title = (TextView) OurView.findViewById(R.id.QRFragmentTitle);
        Hint = (TextView) OurView.findViewById(R.id.QRFragmentHint);
        QRButton = (ImageButton) OurView.findViewById(R.id.QRFragmentQRButton);
    }

    private void setContent(){
        Title.setText(R.string.QRFragmentTitle);
        Title.setTextColor(getResources().getColor(R.color.White)); //White font
        Hint.setText(R.string.QRFragmentHint);
        Hint.setTextColor(getResources().getColor(R.color.White)); //White font

        QRButton.setBackgroundResource(R.drawable.transparent__icon_qr);
        QRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageActivity hp = (HomePageActivity) getActivity();
                hp.CallQRScannerActivity();
            }
        });
        QRButton.setScaleX(2.0f);
        QRButton.setScaleY(2.0f); //doubling size of qr button
    }

    private void setAllLayoutParams(int height, int width, View view){
        Title.setLayoutParams(createTitleParams(height, width));
        Hint.setLayoutParams(createHintParams(height, width));
        QRButton.setLayoutParams(createQRButtonParams(height, width));
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

    private FrameLayout.LayoutParams createHintParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.70); //!!!
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.leftMargin = (int) (width * 0.01);
        return params;
    }

    private FrameLayout.LayoutParams createQRButtonParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (height * 0.55); //!!

        return params;
    }
}