package riskybusiness.riskybusinessmuseumapp.root.Fragments.informationFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Fragments.WelcomeFragment;
import riskybusiness.riskybusinessmuseumapp.root.classes.CustomWebViewClient;

/**
 * @author created by Alex and Tom on 27/02/2015
 */
public class InformationFragment extends Fragment {
    WebView browser;
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_information, container, false);
        DisplayMetrics size = new DisplayMetrics() ;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(size);
        int height = size.heightPixels;
        int width = size.widthPixels;
        browser = (WebView) (view.findViewById(R.id.webViewInfo));
        setUpWebView(browser);
        CreateLayoutParams(height,width,view);
        return view;
    }

    /**
     * Loading the embedded HTML page containing museum information
     * @param v View used by the intent (currently unused)
     */
    public void loadMuseumInfoPage(View v){
        browser.setVisibility(View.VISIBLE);
        browser.setBackgroundColor(getResources().getColor((R.color.transparent))); //make background transparent
        // browser.setAlpha(1.0f);
        browser.loadUrl("file:///android_asset/webpages/index.html"); //using assets folder for resource
    }

    /**
     * Loading the embedded HTML page containing app information
     * @param v View used by the intent (currently unused)
     */
    public void loadAppInfoPage(View v){
        //browser.setVisibility(View.VISIBLE);
        //browser.loadUrl("http://google.co.uk");
        this.getActivity().getFragmentManager().beginTransaction().replace(R.id.frame, new WelcomeFragment()).commit();
    }

    /**
     * Method to give the single fragments used on the intent their location. This is
     * dynamically sized depending on screen resolution of the device.
     * @param height Integer representing height of the devices screen in pixels
     * @param width Integer representing the width of the devices screen in pixels
     * @param view View used by the intent
     */
    private void CreateLayoutParams(int height, int width, View view){
        //Creating all relevant layout parameters
        TextView HelperField = (TextView) (view.findViewById(R.id.HelpCategory));
        HelperField.setLayoutParams(CreateHelpCategoryTextFieldParams(height, width));
        HelperField.setTextColor(getResources().getColor(R.color.White));

        Button AppInfoBtn = (Button) (view.findViewById(R.id.AppInfoButton));
        AppInfoBtn.setLayoutParams(CreateAppInfoButtonParams(height,width));
        AppInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAppInfoPage(v);
            }
        });
        AppInfoBtn.setTextColor(getResources().getColor(R.color.White));

        Button MuseumInfoBtn = (Button) (view.findViewById(R.id.MuseumInfoButton));
        MuseumInfoBtn.setLayoutParams(CreateMuseumInfoButtonParams(height, width));
        MuseumInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMuseumInfoPage(v);
            }
        });
        MuseumInfoBtn.setTextColor(getResources().getColor(R.color.White));

        browser.setLayoutParams(CreateBrowserParams(height,width));
    }

    /**
     * Method used to create the layout parameters for the TextView used in the @link InformationFragment
     * @param height Integer representing height of the devices screen in pixels
     * @param width Integer representing the width of the devices screen in pixels
     * @return FrameLayout.LayoutParams containing the parameters for the TextView used in @link InformationFragment
     */
    private FrameLayout.LayoutParams CreateHelpCategoryTextFieldParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (height*0.05);
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }

    /**
     * Method used to create the layout parameters for the AppButton used in the @link InformationFragment
     * @param height Integer representing height of the devices screen in pixels
     * @param width Integer representing the width of the devices screen in pixels
     * @return FrameLayout.LayoutParams containing the parameters for the AppButton used in @link InformationFragment
     */
    private FrameLayout.LayoutParams CreateAppInfoButtonParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (height*0.12); //make sure this is identical to the other button!
        params.width = (width / 2) - 10;
        params.leftMargin = (params.width / 2) + 2;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }

    /**
     * Method used to create the layout parameters for the MuseumButton used in the @link InformationFragment
     * @param height Integer representing height of the devices screen in pixels
     * @param width Integer representing the width of the devices screen in pixels
     * @return FrameLayout.LayoutParams containing the parameters for the MuseumButton used in @link InformationFragment
     */
    private FrameLayout.LayoutParams CreateMuseumInfoButtonParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (height*0.12); //make sure this is identical to the other button!
        params.width = (width / 2) - 10;
        params.rightMargin = (params.width / 2) + 2;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        return params;
    }

    /**
     * Method used to create the layout parameters for the WebView used in the @link InformationFragment
     * @param height Integer representing height of the devices screen in pixels
     * @param width Integer representing the width of the devices screen in pixels
     * @return FrameLayout.LayoutParams containing the parameters for the WebView used in @link InformationFragment
     */
    private FrameLayout.LayoutParams CreateBrowserParams(int height, int width){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (height*0.22);
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.height = (int) (height - (height*0.25));
        return params;
    }

    /**
     * Method used to set the settings of the browser. It will disable javascript, load with overview mode,
     * use wide OurView port, set the text zoom to 100% and disable support zoom.
     * @param view
     */
    private void setUpWebView(WebView view){
        view.setWebViewClient(new CustomWebViewClient());
        view.getSettings().setJavaScriptEnabled(false);
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setTextZoom(100);
        view.getSettings().setSupportZoom(false);
    }

    @Override
    public void onDetach() {
        browser.destroy();
        browser = null;
        super.onDetach();
    }
}
