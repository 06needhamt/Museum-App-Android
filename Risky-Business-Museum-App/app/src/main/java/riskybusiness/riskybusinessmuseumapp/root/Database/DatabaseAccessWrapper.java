package riskybusiness.riskybusinessmuseumapp.root.Database;

import java.net.URI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.provider.BaseColumns;

import riskybusiness.riskybusinessmuseumapp.root.Ddalogs.DatabaseAccessErrorDialogFragment;


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
	
	public DatabaseAccessWrapper(Context context, String Database_name, String User_Name, String Location, String Password) {
		this.context = context;
		this.DatabaseLocation = URI.create(Location);
		this.DatabaseName = Database_name;
		this.UserName = User_Name;
		this.Password = Password;
	}
	
	public SQLiteDatabase OpenDatabase()
	{
		mDbHelper = new FeedReaderDbHelper(context);
		System.out.println("The Database Name Is" + mDbHelper.getDatabaseName());
		//mDbHelper.onCreate(db)
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		return db;
	}
	
	public Cursor SelectData(SQLiteDatabase db, String[] projection)
	{
		if(!db.isOpen() || db.equals(null))
		{
			try
			{
				this.OpenDatabase();
			}
			catch (Exception e)
			{
				DatabaseAccessErrorDialogFragment f = new DatabaseAccessErrorDialogFragment();
				//f.show(null, null);
				e.printStackTrace();
				return null;
			}
			
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
