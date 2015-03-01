package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import riskybusiness.riskybusinessmuseumapp.R;

public class LanguageActivity extends ActionBarActivity {

    TextView Title;
    ImageView England, Germany, France, Spain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Title = (TextView) findViewById(R.id.ChooseLanguageText);
        England = (ImageView) findViewById(R.id.FlagEngland);
        Germany = (ImageView) findViewById(R.id.FlagGermany);
        France = (ImageView) findViewById(R.id.FlagFrance);
        Spain = (ImageView) findViewById(R.id.FlagSpain);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        SetAllLayoutParams(height, width);
        SetImages();
        SetOnClickListeners();
    }

    private void SetOnClickListeners()
    {
        England.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetLanguageEnglish();
            }
        });
        Germany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetLanguageGerman();
            }
        });
        France.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetLanguageFrench();
            }
        });
        Spain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetLanguageSpanish();
            }
        });
    }

    private void SetLanguageSpanish() {
        Toast.makeText(getBaseContext(),"Button Clicked",Toast.LENGTH_LONG).show();
        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void SetLanguageFrench() {
        Toast.makeText(getBaseContext(),"Button Clicked",Toast.LENGTH_LONG).show();
        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void SetLanguageGerman() {
        Toast.makeText(getBaseContext(),"Button Clicked",Toast.LENGTH_LONG).show();
        Locale locale = new Locale("de");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void SetLanguageEnglish() {
        Toast.makeText(getBaseContext(),"Button Clicked",Toast.LENGTH_LONG).show();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void SetAllLayoutParams(int height, int width)
    {
        Title.setLayoutParams(CreateTitleLayoutParams(height, width));
        England.setLayoutParams(CreateEnglandLayoutParams(height, width));
        Germany.setLayoutParams(CreateGermanyLayoutParams(height, width));
        France.setLayoutParams(CreateFranceLayoutParams(height, width));
        Spain.setLayoutParams(CreateSpainLayoutParams(height, width));
    }
    private void SetImages()
    {
        England.setImageResource(R.drawable.flag_england);
        Germany.setImageResource(R.drawable.flag_germany);
        France.setImageResource(R.drawable.flag_france);
        Spain.setImageResource(R.drawable.flag_spain);
    }
    private FrameLayout.LayoutParams CreateEnglandLayoutParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.topMargin = (int) (height * 0.15);
        return params;
    }
    private FrameLayout.LayoutParams CreateGermanyLayoutParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.topMargin = (int) (height * 0.35);
        return params;
    }
    private FrameLayout.LayoutParams CreateFranceLayoutParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.topMargin = (int) (height * 0.55);
        return params;
    }
    private FrameLayout.LayoutParams CreateSpainLayoutParams(int height, int width) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.topMargin = (int) (height * 0.75);
        return params;
    }

    private FrameLayout.LayoutParams CreateTitleLayoutParams(int height, int width)
    {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.topMargin = (int) (height * 0.05);
        return params;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
