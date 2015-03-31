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
import java.util.ArrayList;

import riskybusiness.riskybusinessmuseumapp.R;

/**
 * Created by Chris on 07/02/2015.
 * From: http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/riskybusiness.databasetest/databases/";
    private static String DB_NAME = "museumDB";
    private SQLiteDatabase myDataBase;
    private final Context context;
    //private DatabaseConstants dc = new DatabaseConstants(); // Holds constants for database

    DatabaseHelper db = null;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context Context
     */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 1);

        this.context = context;
    }

    /**
     * Queries the database using given query string
     * @param queryString The required query string
     * @return Cursor holding the query results or null inc ase of error or no results
     */
    public Cursor queryDatabase(String queryString) {
        Cursor cur;

        // Query the database
        try {
            cur = myDataBase.rawQuery(queryString, new String[]{});
        } catch(SQLException e) {
            Log.d(context.getResources().getString(R.string.app_name), ">>>> SQL Exception in queryDatabase: " + e.getMessage());
            return null;
        }

        if(cur.getCount() <= 0) { // No results returned from query
            Log.d(context.getResources().getString(R.string.app_name), "No records returned from query using: " + queryString + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            return null;
        }

        return cur; // Return cursor containing artefact
    }

    /**
     * Initialises the database by checking if it exists. If it doesn't the file is copied from Assets
     */
    public void initialiseDatabase() {
        int status = 0; // Status value returned from createDatabase 0 == OK

        Log.d(context.getResources().getString(R.string.app_name), "Database name: " + db.getDatabaseName());

        try {
            status = db.createDataBase(); // Checks if database already exists - if not tries to create it
        } catch(Exception e) {
            Log.d(context.getResources().getString(R.string.app_name), "Database not created!");
        }

        if(status != 0) {
            Log.d(context.getResources().getString(R.string.app_name),"Error initialising database, status value = " + status );
        }
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     *
     * @return returns an integer indicating 0 == OK or position of error 1+
     * */
    public int createDataBase() throws IOException {
        boolean dbExist; // Used to check if the database exists
        int status = 0; // Status indicates 0 ==OK or position of error

        Log.d(context.getResources().getString(R.string.app_name), "In createDataBase");

        dbExist = checkDataBase(); // Does the database already exist?

        if(dbExist){ // Database exists - nothing to do
            Log.d(context.getResources().getString(R.string.app_name), "Database exists!");
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

            Log.d(context.getResources().getString(R.string.app_name), "Folder still does not exist  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            status = 1;    // Error creating folder structure
            return status; // Failed to create database
        }

        // Folder structure in place
        Log.d(context.getResources().getString(R.string.app_name), "Folder Exists/Created: " + dir.getName() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

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

            //database does't exist yet.
        }

        if(checkDB != null){

            checkDB.close(); // Database exists, close it before leaving method
        }

        return checkDB != null;
    }


    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

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

    /**
     * Opens the database
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }


    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {     }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}
