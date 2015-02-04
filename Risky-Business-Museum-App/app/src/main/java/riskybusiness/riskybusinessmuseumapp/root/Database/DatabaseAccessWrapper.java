package riskybusiness.riskybusinessmuseumapp.root.Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.provider.BaseColumns;
import android.widget.Toast;

import riskybusiness.riskybusinessmuseumapp.root.Dialogs.DatabaseAccessErrorDialogFragment;


public class DatabaseAccessWrapper implements IFeedEntry, BaseColumns{

	private URI DatabaseLocation;
	private String Password;
	private String DatabaseName;
	private String UserName;
	private SQLiteDatabase Database;
	private Context context;
	private final String SELECT_ALL = "SELECT * FROM test";
	private String[] projection = { IFeedEntry.COLUMN_NAME_ID,IFeedEntry.COLUMN_NAME_FIRST_NAME,IFeedEntry.COLUMN_NAME_LAST_NAME };
	FeedReaderDbHelper mDbHelper;

    ///////////////////////////////
    //The Android's default system path of your application database.
    private static String DB_PATH = "data/data/riskybusiness.riskybusinessmuseumapp.root/databases/";
    private static String DB_NAME = "MuseumDB";
    private static String TABLE_LOCATION = "trailStep";
    /////////////////////////////
    /////////////////////////////

//	public DatabaseAccessWrapper(Context context, String Database_name, String User_Name, String Location, String Password) {
//		this.context = context;
//		this.DatabaseLocation = URI.create(Location);
//		this.DatabaseName = Database_name;
//		this.UserName = User_Name;
//		this.Password = Password;
//	}

    public DatabaseAccessWrapper(Context context) {
        this.context = context;
    }
	
	public SQLiteDatabase OpenDatabase()
	{
		//mDbHelper = new FeedReaderDbHelper(context);
		//System.out.println("The Database Name Is" + mDbHelper.getDatabaseName());
		//mDbHelper.onCreate(db)
        SQLiteDatabase db = null;
        String path = DB_PATH + DB_NAME;
        Boolean databaseOk;

        databaseOk = checkDataBase();

        if(!databaseOk) {
            try {
                copyDataBase();
            } catch (IOException e) {
                System.out.println("Problem with database");
                e.printStackTrace();
                return null;
            }
        }

        try {
             db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);;
        } catch (NullPointerException e) {
            e.printStackTrace();

        }

        if(db != null)
            Toast.makeText(context, "Database Opened", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Database NOT Opened :(", Toast.LENGTH_LONG).show();

        return db;
	}

    // Check if the database exist to avoid re-copy the data
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            // database don't exist yet.
            e.printStackTrace();

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }




    ////////////////////////////////////////////////
    // copy your assets db to the new system DB
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        //InputStream myInput = context.getAssets().open(DB_NAME);
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
    ////////////////////////////////////////////////////


    // OLD select data - don't use
    public Cursor SelectData(SQLiteDatabase db, String[] projection)
	{
		if(!db.isOpen() || db.equals(null))
		{
//            try
//            {
//                Database = this.OpenDatabase();
//            }
//            catch (Exception e)
//            {
//                try {
//
//                } catch (IOException) {
//                    System.out.println("Problem with database");
//                }
//
//                DatabaseAccessErrorDialogFragment f = new DatabaseAccessErrorDialogFragment();
//                //f.show(null, null);
//                e.printStackTrace();
//                return null;
//            }

        }
		
		db.beginTransaction();
		Cursor Result = db.query(false, TABLE_NAME, projection, null, null, null, null, COLUMN_NAME_FIRST_NAME, String.valueOf(100),null);
		db.endTransaction();
		return Result;
		
	}
	
	public long AddData(SQLiteDatabase db, String[] data )
	{
		ContentValues contentValues = new ContentValues();
		//contentValues.put("_Id", data[0]);
		contentValues.put("FirstName", data[0]);
		contentValues.put("LastName", data[1]);
		db.beginTransaction();
		long rowid = db.insert(TABLE_NAME, null, contentValues);
		db.endTransaction();
		return rowid;
	}


}
