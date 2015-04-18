package riskybusiness.riskybusinessmuseumapp.root.Fragments;

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
 * Created by Tom on 03/02/2015.
 * Edited by Tom and Alex on 01/03/2015
 */
public class WelcomeFragment extends Fragment{
    View OurView;
    TextView Title, Trail,      ExTrail,    QRScanner,    OurMaps,    Info;
    ImageView       TrailImg,   ExTrailImg, QRScannerImg, OurMapsImg, InfoImg;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        this.OurView = view;
        DisplayMetrics size = new DisplayMetrics() ;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(size);
        int height = size.heightPixels;
        int width = size.widthPixels;
        setTextIDs();
        setContent();
        setTextAlignmentToLeft();
        setImageIDs();
        setDrawables();
        setImageScaling();

        setAllLayoutParams(height, width, OurView);

        return OurView;

    }

    private void setTextIDs(){
        Title = (TextView) OurView.findViewById(R.id.WelcomeFragmentTitle);
        Trail = (TextView) OurView.findViewById(R.id.WelcomeTrailInfo);
        ExTrail = (TextView) OurView.findViewById(R.id.WelcomeExplorerTrailInfo);
        QRScanner = (TextView) OurView.findViewById(R.id.WelcomeQRScannerInfo);
        OurMaps = (TextView) OurView.findViewById(R.id.WelcomeMapInfo);
        Info = (TextView) OurView.findViewById(R.id.WelcomeMoreInfo);
    }

    private void setTextAlignmentToLeft(){
        Title.setGravity(Gravity.LEFT);
        Trail.setGravity(Gravity.LEFT);
        ExTrail.setGravity(Gravity.LEFT);
        QRScanner.setGravity(Gravity.LEFT);
        OurMaps.setGravity(Gravity.LEFT);
        Info.setGravity(Gravity.LEFT);
    }

    private void setContent(){
        Title.setText(R.string.WelcomeTitle);
        Trail.setText(R.string.WelcomeTrailInfo);
        ExTrail.setText(R.string.WelcomeExplorerTrailInfo);
        QRScanner.setText(R.string.WelcomeQRScannerInfo);
        OurMaps.setText(R.string.WelcomeMapInfo);
        Info.setText(R.string.WelcomeMoreInfo);
    }

    private void setImageIDs(){
        TrailImg = (ImageView) OurView.findViewById(R.id.WelcomeTrailImage);
        ExTrailImg = (ImageView) OurView.findViewById(R.id.WelcomeExplorerImage);
        QRScannerImg = (ImageView) OurView.findViewById(R.id.WelcomeQRImage);
        OurMapsImg = (ImageView) OurView.findViewById(R.id.WelcomeMapImage);
        InfoImg = (ImageView) OurView.findViewById(R.id.WelcomeInfoImage);
    }

    private void setDrawables(){
        TrailImg.setImageResource(R.drawable.transparent__icon_question);
        ExTrailImg.setImageResource(R.drawable.transparent__icon_trail);
        QRScannerImg.setImageResource(R.drawable.transparent__icon_qr);
        OurMapsImg.setImageResource(R.drawable.transparent__icon_map);
        InfoImg.setImageResource(R.drawable.transparent__icon_information);
    }

    private void setAllLayoutParams(int height, int width, View view){
        setImageParams(height, width, view); // KEEP THIS ORDER - text params depend on image params
        setTextParams(height, width, view);
    }

    private void setTextParams(int height, int width, View view){
        Title.setLayoutParams(createTitleParams(height, width));
        Trail.setLayoutParams(createTrailParams(height, width));
        ExTrail.setLayoutParams(createExTrailParams(height, width));
        QRScanner.setLayoutParams(createQRScannerParams(height, width));
        OurMaps.setLayoutParams(createOurMapsParams(height, width));
        Info.setLayoutParams(createInfoParams(height, width));
    }


    private void setImageParams(int height, int width, View view){
        TrailImg.setLayoutParams(createTrailImgParams(height, width));
        ExTrailImg.setLayoutParams(createExTrailImgParams(height, width));
        QRScannerImg.setLayoutParams(createQRScannerImgParams(height, width));
        OurMapsImg.setLayoutParams(createOurMapsImgParams(height, width));
        InfoImg.setLayoutParams(createInfolImgParams(height, width));
    }

    private void setImageScaling(){
        final float scale = 1.75f; //2.0f;
        final int padding = 0;
        TrailImg.setPadding(padding,padding,padding,padding);
        ExTrailImg.setPadding(padding,padding,padding,padding);
        QRScannerImg.setPadding(padding,padding,padding,padding);
        OurMapsImg.setPadding(padding,padding,padding,padding);
        InfoImg.setPadding(padding,padding,padding,padding);

        TrailImg.setScaleX(scale);
        TrailImg.setScaleY(scale);

        ExTrailImg.setScaleX(scale);
        ExTrailImg.setScaleY(scale);

        QRScannerImg.setScaleX(scale);
        QRScannerImg.setScaleY(scale);

        OurMapsImg.setScaleX(scale);
        OurMapsImg.setScaleY(scale);

        InfoImg.setScaleX(scale);
        InfoImg.setScaleY(scale);
    }


    private FrameLayout.LayoutParams createTrailImgParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.15);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (int) (width * 0.05);
        return params;
    }

    private FrameLayout.LayoutParams createExTrailImgParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.28);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (int) (width * 0.05);
        return params;
    }

    private FrameLayout.LayoutParams createQRScannerImgParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.41);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (int) (width * 0.05);
        return params;
    }

    private FrameLayout.LayoutParams createOurMapsImgParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.53);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (int) (width * 0.05);
        return params;
    }

    private FrameLayout.LayoutParams createInfolImgParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.65);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (int) (width * 0.05);
        return params;
    }

    private FrameLayout.LayoutParams createTitleParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = 2;
        params.gravity = Gravity.LEFT;
        return params;
    }

    private FrameLayout.LayoutParams createTrailParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.15);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (TrailImg.getDrawable().getIntrinsicWidth() * 2) + 3;
        return params;
    }

    private FrameLayout.LayoutParams createExTrailParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.25);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (ExTrailImg.getDrawable().getIntrinsicWidth() * 2) + 3;
        return params;
    }

    private FrameLayout.LayoutParams createQRScannerParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.41);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (QRScannerImg.getDrawable().getIntrinsicWidth() * 2) + 3;
        return params;
    }

    private FrameLayout.LayoutParams createOurMapsParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.53);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (OurMapsImg.getDrawable().getIntrinsicWidth() * 2) + 3;
        return params;
    }

    private FrameLayout.LayoutParams createInfoParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.topMargin = (int) (height * 0.65);
        params.gravity = Gravity.LEFT;
        params.leftMargin = (InfoImg.getDrawable().getIntrinsicWidth() * 2) + 3;
        return params;
    }

}

