package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import riskybusiness.riskybusinessmuseumapp.root.classes.SharedPreferencesHandler;

public class LanguageActivity extends FragmentActivity {
    private final String PREF_NAMES = "myAppPrefs";
    final String[] langs = {"en", "de", "fr", "es"}; // Language specifiers

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
                setLanguage("en");
            }
        });
        Germany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("de");
            }
        });
        France.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("fr");
            }
        });
        Spain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("es");
            }
        });
    }

    private void startHomePageActivity() {
        Intent i = new Intent(getBaseContext(),HomePageActivity.class);
        startActivity(i);
        finish();
    }

    private void setLanguage(String lang) {
        String chosenLang = "en";

        //Toast.makeText(getBaseContext(),"Button Clicked",Toast.LENGTH_LONG).show();

        for(String l : langs) { // Make sure that a valid language is chosen or default to English
            if(l.equals(lang))
                chosenLang = l;
        }

        Locale locale = new Locale(chosenLang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        saveLanguageChoice(lang);
        startHomePageActivity();
    }

    private void saveLanguageChoice(String lang) {
        SharedPreferences prefs = getBaseContext().getSharedPreferences(PREF_NAMES, Context.MODE_PRIVATE);
        SharedPreferencesHandler sph = new SharedPreferencesHandler(prefs);
        sph.setLanguage(lang);
        Log.e("Prefs", "Language = " + sph.getLanguage());
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
