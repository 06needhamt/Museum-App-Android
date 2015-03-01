package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.content.Intent;
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

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Database.DataBaseHelper;
import riskybusiness.riskybusinessmuseumapp.root.Database.DatabaseAccessWrapper;
import riskybusiness.riskybusinessmuseumapp.root.classes.TouchImageView;
import riskybusiness.riskybusinessmuseumapp.root.questionmanager.QuestionManager;


public class MainActivity extends FragmentActivity {

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DatabaseAccessWrapper db = new DatabaseAccessWrapper(getBaseContext());
        DataBaseHelper dbh = new DataBaseHelper(getBaseContext());
        //database = db.OpenDatabase();
        try {
            database = dbh.createDataBase(getBaseContext());
            //database = SQLiteDatabase.openOrCreateDatabase(dbh.dbfile, null);
        }
        catch(Exception E){
            E.printStackTrace();
        }

        System.out.println("Are we here yet?");
        database = SQLiteDatabase.openDatabase(getBaseContext().getDatabasePath("MuseumDB").getAbsolutePath(),null,0);

//        if(database != null) {
//            Toast.makeText(getBaseContext(), "DB Okay", Toast.LENGTH_LONG).show();
//            System.out.println(getBaseContext().getDatabasePath("MuseumDB").getAbsolutePath());
//            database.beginTransaction();
//            Cursor c = database.rawQuery("SELECT * FROM TrailStep",null);
//            if(c != null)
//            {
//                Toast.makeText(getBaseContext(), "Query Okay", Toast.LENGTH_LONG).show();
//                c.moveToFirst();
//                while (!c.isLast())
//                {
//                    c.moveToNext();
//                    Toast.makeText(getBaseContext(),(CharSequence)c.getString(0),Toast.LENGTH_SHORT).show();
//                }
//            }
//            database.endTransaction();
//            return;
//        }
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
        //Intent i = new Intent(getBaseContext(),HomePageActivity.class);
        Intent i = new Intent(getBaseContext(),LanguageActivity.class);
        //Intent i = new Intent(getBaseContext(), MultiTouchActivity.class);
        startActivity(i);
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }
}
