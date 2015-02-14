package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.classes.TouchImageView;

public class MultiTouchActivity extends Activity {

    FrameLayout fl;
    TouchImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_touch);
        setContentView(SetupLayout());
    }

    private FrameLayout SetupLayout() {
        fl = (FrameLayout) findViewById(R.id.ImageLayout);
        img = new TouchImageView(this);
        img.passImageData(R.drawable.ice_age_2,4.0f,R.drawable.google_maps_hello_world,4.0f);
        img.setImageResource(R.drawable.ice_age_2);
        img.setMaxZoom(4.0f);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        img.setLayoutParams(SetupLayoutParams(dm.widthPixels,dm.heightPixels));
        fl.addView(img);
        setContentView(fl);
        return fl;
    }

    private FrameLayout.LayoutParams SetupLayoutParams(int width, int height)
    {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.height = height >> 1;
        params.gravity = Gravity.CENTER;
        return params;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    public void ResetButtonClicked(View v)
    {
        img.resetImage();
    }

}
