package riskybusiness.riskybusinessmuseumapp.root.Fragments.informationFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.AppConstants;
import riskybusiness.riskybusinessmuseumapp.root.classes.CustomWebViewClient;

/**
 * Created by Alex on 10/04/2015.
 */
public class InformationWebView extends Fragment implements AppConstants{
    View view;
    WebView browser;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_information_webview, container, false);
        initialiseResources();
        setUpWebView(browser);
        browser.setBackgroundColor(getResources().getColor((R.color.transparent))); //make background transparent
        loadPage(DEFAULT_PAGE);
        return view;
    }

    /**
     * Loads a specific url
     * @param url String for specific url
     */
    public void loadPage(String url){
        browser.loadUrl(url);
        Log.e("url",browser.getUrl());
    }

    /**
     * Initialise view on the fragment
     */
    private void initialiseResources(){
        browser = (WebView) view.findViewById(R.id.InfoWebView);
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
