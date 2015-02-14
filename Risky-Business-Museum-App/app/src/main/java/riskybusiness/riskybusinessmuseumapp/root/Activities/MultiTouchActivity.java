package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.classes.TouchImageView;

public class MultiTouchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        TouchImageView img = new TouchImageView(this);
        img.passImageData(R.drawable.ice_age_2,4.0f,R.drawable.google_maps_hello_world,4.0f);
        img.setImageResource(R.drawable.ice_age_2);
        img.setMaxZoom(4.0f);
        setContentView(img);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

}
