package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import riskybusiness.riskybusinessmuseumapp.R;
//import riskybusiness.riskybusinessmuseumapp.root.Database.DataBaseHelper;
//import riskybusiness.riskybusinessmuseumapp.root.Database.DatabaseAccessWrapper;
import riskybusiness.riskybusinessmuseumapp.root.Database.DatabaseHelper;
import riskybusiness.riskybusinessmuseumapp.root.classes.SharedPreferencesHandler;
import riskybusiness.riskybusinessmuseumapp.root.classes.TouchImageView;
import riskybusiness.riskybusinessmuseumapp.root.questionmanager.QuestionManager;


public class MainActivity extends FragmentActivity {
    private final String PREF_NAMES = "myAppPrefs";

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHelper db = null;
        SQLiteDatabase myDataBase;

        int status = 0; // Status value returned from createDatabase 0 == OK

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CheckForLanguageChoice(); // Checks to see if the language has been chosen

        db = new DatabaseHelper(this);


        System.out.println("Hello World");

        System.out.println("Database name: " + db.getDatabaseName());

        try {
            status = db.createDataBase(); // Checks if database already exists - if not tries to create it
        } catch(Exception e) {
            System.out.println("Database not created!");
        }

        if(status != 0) {
            System.out.println("Error initialising database, status value = " + status);
        }

        myDataBase = db.getReadableDatabase();

        db.openDataBase();

        try {
            Cursor cursor;

            System.out.println("Database is open: " + db.getDatabaseName());

            System.out.println("Ready to query database");

            cursor = myDataBase.rawQuery("SELECT trail._id as t_ID, trail.name as trailName, trailStep.* FROM trail join trailStep on trailStep.trailID = t_ID", new String[]{});

            System.out.println("Query executed, results:");

            if(cursor != null) {
                cursor.moveToFirst();

                while(!cursor.isAfterLast()) {
                    System.out.println("Trail = " + cursor.getString(cursor.getColumnIndex("trailName")) + ", Record = " + cursor.getString(cursor.getColumnIndex("question")));

                    cursor.moveToNext();
                }

            }

//            populateListViewFromDatabase(); // Query the database and populate list view with records

            db.close(); // Close the database

        } catch (Exception e) {
            System.out.println("Unable to create or open database");
        }

        System.out.println("Finished messing about, ");

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

        //Intent i = new Intent(getBaseContext(),HomePageActivity.class);
        Intent i = new Intent(getBaseContext(),DataBaseImageTestActivity.class);
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
