package riskybusiness.riskybusinessmuseumapp.root.classes;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Alex on 27/02/2015.
 */
public class CustomWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String Url){
//        if(!Url.contains("www.riskybusiness.co.uk")){
//            return false;
//        }
        view.loadUrl(Url);
        return true;
    }
}
