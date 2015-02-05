package riskybusiness.riskybusinessmuseumapp.root.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.provider.BaseColumns;


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
    //private static String DB_PATH = "data\\data\\riskybusiness.riskybusinessmuseumapp.root\\databases\\";
    //private static String DB_PATH ="app\\src\\main\\assets\\";
    private static String DB_PATH = "No Value";
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
        //DB_PATH = context.getFilesDir().toString();
        DB_PATH = "/app/src/main/assets/";
        System.out.println("DB_PATH = " + DB_PATH);
    }
	
	public SQLiteDatabase OpenDatabase()
	{
		//mDbHelper = new FeedReaderDbHelper(context);
		//System.out.println("The Database Name Is" + mDbHelper.getDatabaseName());
		//mDbHelper.onCreate(db)
        SQLiteDatabase db = null;
        String path = DB_PATH + DB_NAME;
        Boolean databaseOk;
        String copyLocation = "/data/data/riskybusiness.riskybusinessmuseumapp.root/databases/";


        //if(!databaseOk) {
            //try {
                db = copyDataBase(DB_NAME, copyLocation);
//            } catch (IOException e) {
//                System.out.println("Problem with database");
//                e.printStackTrace();
//                return null;
//            }
        //}
        /*
        databaseOk = checkDataBase();

        try {
             db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);;
        } catch (NullPointerException e) {
            e.printStackTrace();

        }

        if(db != null)
            Toast.makeText(context, "Database Opened", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Database NOT Opened :(", Toast.LENGTH_LONG).show();
        */
        return db;
	}

    private SQLiteDatabase copyDataBase(String DBname, String copyLocation){
        File f = new File(copyLocation + "/" + DBname);
        SQLiteDatabase db = null;
        if(!f.exists()){
            try{
                InputStream is = context.getAssets().open(DBname);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(buffer);
                fos.close();
            }
            catch (Exception E){
                E.printStackTrace();
                return null;
            }

                db.openOrCreateDatabase(f, null);

        }
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
            return true;

        }

        return checkDB != null ? true : false;
    }




    ////////////////////////////////////////////////
    // copy your assets db to the new system DB
    /* OLD copyDataBase function
    private void copyDataBase() throws IOException {

        String outFileName = DB_PATH + DB_NAME;
        File testFile = new File(context.getFilesDir(), DB_NAME);
        List<String> results = new ArrayList<String>();
        File[] files = new File(context.getFilesDir().toString()).listFiles();
        for(File file : files){
            if(file.isFile()){
                results.add(file.getName());
                System.out.println("file: " + file.getName());
            }
        }
        //Open your local db as the input stream
        //InputStream myInput = context.getAssets().open(DB_NAME);
        FileInputStream myInput = new FileInputStream(testFile) {};

        // Path to the just created empty db
        File newFile = new File("/data/data/riskybusiness.riskybusinessmuseumapp.root/databases/");
        //if(!newFile.exists()) newFile.createNewFile();
        //Open the empty db as the output stream
        FileOutputStream myFileOutput = new FileOutputStream(newFile);
        System.out.println("newFile = " + newFile.toString());
        //myFileOutput = context.openFileOutput((outFileName), context.MODE_PRIVATE);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myFileOutput.write(buffer, 0, length);
        }

        //Close the streams
        myFileOutput.flush();
        myFileOutput.close();
        myInput.close();
        System.out.println("Copied db okay");
    }*/
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
