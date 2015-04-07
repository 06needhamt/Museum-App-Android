package riskybusiness.riskybusinessmuseumapp.root.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import riskybusiness.riskybusinessmuseumapp.R;
import riskybusiness.riskybusinessmuseumapp.root.Database.DatabaseHelper;

public class DataBaseImageTestActivity extends FragmentActivity {

    ImageView dbimage;
    int status;
    SQLiteDatabase myDataBase;
    DatabaseHelper db;
    final int IMAGE_COLUMN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_image_test);
        dbimage = (ImageView) findViewById(R.id.DBImageView);
        openDatabase();
        dbimage.setImageDrawable(getImageFromDB(0));
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_base_image_test, menu);
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

    public Drawable getImageFromDB(int id)
    {
        Cursor cursor;
        cursor = myDataBase.rawQuery("SELECT image FROM artefact",new String[]{});
        if(!cursor.moveToPosition(id))
        {
            Log.e("error","Invalid id");
        }
        byte[] imagebytes = cursor.getBlob(IMAGE_COLUMN);
        Bitmap b = BitmapFactory.decodeByteArray(imagebytes,0,imagebytes.length, new BitmapFactory.Options());
        Drawable D = new BitmapDrawable(getResources(),b);
        cursor.close();
        return D;
    }

    public void openDatabase()
    {
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

           // db.close(); // Close the database

        } catch (Exception e) {
            System.out.println("Unable to create or open database");
        }

        System.out.println("Finished messing about, ");

    }
}
