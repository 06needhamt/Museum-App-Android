package riskybusiness.riskybusinessmuseumapp.root.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Chris on 07/02/2015.
 * From: http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private String DB_PATH;
    private static String DB_NAME = "MuseumDB";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);

        this.myContext = context;

        DB_PATH = myContext.getDatabasePath(DB_NAME).getAbsolutePath();

        DB_PATH = DB_PATH.substring(0, DB_PATH.length() - DB_NAME.length());

        Log.e(null, "DB_PATH = " + DB_PATH + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     *
     * @return returns an integer indicating 0 == OK or position of error 1+
     * */
    public int createDataBase() throws IOException {
        boolean dbExist; // Used to check if the database exists
        int status = 0; // Status indicates 0 ==OK or position of error

        Log.i("log message", "In createDataBase");

        dbExist = checkDataBase(); // Does the database already exist?

        if(dbExist){ // Database exists - nothing to do
            System.out.println("Database exists!");
            return status; // Database ok
        }

        // Check the folder structure is in place
        File dir = new File(DB_PATH);

        // Create folder - if necessary
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // If the folder still does not exist there has been a problem creating it
        if(!dir.exists()) {

            System.out.println("Folder still does not exist  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            status = 1;    // Error creating folder structure
            return status; // Failed to create database
        }

        // Folder structure in place
        System.out.println("Folder Exists/Created: " + dir.getName() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        //By calling this method an empty database will be created into the default system path
        //of your application so we now overwrite that database with our database.
        this.getReadableDatabase();

        // Copy the assets database over the newly created empty one
        try {
            copyDataBase();
        } catch (IOException e) {

            status = 2; // Error copying database
            throw new Error("Error copying database");

        }


        return status; // Database successfully created
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;

        try{

            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            Log.i("Check Database", "Failed to open database " + myPath);
            Log.i("Check Database", "Need to create database " + myPath);

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;

        //myContext.getDatabasePath(DB_NAME).getAbsolutePath()
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    Cursor getTrailSteps() {
        Cursor cur = null;

        cur = myDataBase.rawQuery("SELECT trail._id as t_ID, trail.name as trailName, trailStep.* FROM trail join trailStep on trailStep.trailID = t_ID", new String[]{});

        //cur = myDataBase.rawQuery("SELECT * FROM trailStep", new String[]{});

        if(cur == null) {
            System.out.println("Query failed <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
        else {
            System.out.println("Database Queried <<<<<<<<<<<<<<<<<<<<<<<<<");
        }

        return cur;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}
