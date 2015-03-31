package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
//import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Database.DatabaseHelper;
import riskybusiness.riskybusinessmuseumapp.root.classes.SharedPreferencesHandler;


public class MainActivity extends FragmentActivity {
    private final String PREF_NAMES = "myAppPrefs";
    DatabaseHelper db = null;
    //SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this); // Prepare database
        db.initialiseDatabase(); // Initialise the database. In the case of first run , this will copy database from assets
    }


    public void CheckForLanguageChoice(View v) {
        ///////////////////////// Debug Start DBImage Activity Remove Later  ///////////////////////////////
        //Intent dbi = new Intent(getBaseContext(),DataBaseImageTestActivity.class);
        //startActivity(dbi);
        //////////////////////////////////// End of Debug Calls ////////////////////////////////////////////
        SharedPreferences prefs = getBaseContext().getSharedPreferences(PREF_NAMES, Context.MODE_PRIVATE);
        SharedPreferencesHandler prh = new SharedPreferencesHandler(prefs);

        System.out.println("Chosen Language = " + prh.getLanguage());

        if(prh.getLanguage().equals("Not Chosen"))
        {
            Intent i = new Intent(getBaseContext(), LanguageActivity.class);
            startActivity(i);
        }
        else
        {
            System.out.println("Chosen Language - in ELSE");

            SetLanguage(prh.getLanguage());
            LoadMainPage(v);
        }
    }

    private void SetLanguage(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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

    public void LoadMainPage(View v)
    {
        System.out.println("Load Main Page" );

        //CheckForLanguageChoice(new View(getBaseContext());  // Checks to see if the language has been chosen

        Intent i = new Intent(getBaseContext(),HomePageActivity.class);
        //Intent i = new Intent(getBaseContext(),DataBaseImageTestActivity.class);
        //Intent i = new Intent(getBaseContext(),LanguageActivity.class);
        //Intent i = new Intent(getBaseContext(), MultiTouchActivity.class);



        startActivity(i);
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }


}
