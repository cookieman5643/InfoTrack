package com.jmt.infotrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class InfoDbAdapter {
	
//Class Vars.

	static String TAG = new String ("InfoTrackApp");	
	
	
//Fields in my database
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATECREATED = "datecreated";
	public static final String KEY_DATEEDITTED = "dateeditted";
	public static final String KEY_DATEVIEWED = "dateviewed";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_DATA1 = "data1";
	public static final String KEY_DATA2 = "data2";
	
//DB OBJECTS	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;	
	
	
//DB CREATION STATEMENTS
	private static final String DATABASE_CREATE =
			"create table sleeptable (_id integer primary key autoincrement, "
			+ "datecreated text not null, "
			+ "dateeditted text not null, "
			+ "dateviewed text not null, "
			+ "category text not null, "
			+ "data1 text not null, "
			+ "data2 text not null);";
	
//DB INFO CONSTS
	private static final String DATABASE_NAME = "info_db";
	private static final String DATABASE_SLEEP_TABLE = "sleeptable";
	private static final int DATABASE_VERSION = 1;
	
	
//DB CLASS HELP
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(DATABASE_CREATE);
//TODO: add here other tables
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS sleeptable");
			onCreate(db);		
		}
	}//end class DatabaseHelper
	

//########################
//#***** COSTRUCTOR	*****#
//########################
	public InfoDbAdapter(Context ctx){
		Log.i(TAG,"**InfoDbAdapter Constructor");
		this.mCtx = ctx;
	}	
	
//OPEN/CLOSE DATABASE
	public InfoDbAdapter open() throws SQLException{
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	public void close(){
		mDbHelper.close();
	}

//########################
//#****ACCESS METHODS****#
//########################	
public long createSleep(
		String datecreated,
		String dateeditted,
		String dateviewed,
		String category,
		String data1,
		String data2){
	ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DATECREATED, datecreated);
		initialValues.put(KEY_DATEEDITTED, dateeditted);
		initialValues.put(KEY_DATEVIEWED, dateviewed);
		initialValues.put(KEY_CATEGORY, category);
		initialValues.put(KEY_DATA1, data1);
		initialValues.put(KEY_DATA2, data2);
	return mDb.insert(DATABASE_SLEEP_TABLE,  null, initialValues);
}//end createSleepEntry
	
public boolean deleteSleep(long rowId){
	return mDb.delete(DATABASE_SLEEP_TABLE,  KEY_ROWID + "=" + rowId, null) > 0;
}//end deleteSleep
	
public Cursor fetchAllSleep() {
	return mDb.query(DATABASE_SLEEP_TABLE,  new String[] {
			KEY_ROWID, KEY_DATECREATED,
			KEY_DATEEDITTED, KEY_DATEVIEWED,
			KEY_CATEGORY, KEY_DATA1,
			KEY_DATA2}, null, null, null, null, null);
}//end fetchAllSleep

public Cursor fetchSleep(long rowId) throws SQLException {
	Cursor mCursor = 
		mDb.query(DATABASE_SLEEP_TABLE, new String[] {
			KEY_ROWID, KEY_DATECREATED,
			KEY_DATEEDITTED, KEY_DATEVIEWED,
			KEY_CATEGORY, KEY_DATA1,
			KEY_DATA2}, 
			KEY_ROWID + "=" + rowId, null,null, null, null, null);
		if (mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
}//end fetchSleep	
	
public boolean updateSleep(long rowId, 
		String dateeditted,
		String dateviewed,
		String category,
		String data1,
		String data2){
	ContentValues args = new ContentValues();
	args.put(KEY_DATEEDITTED, dateeditted);
	args.put(KEY_DATEVIEWED, dateviewed);
	args.put(KEY_CATEGORY, category);
	args.put(KEY_DATA1, data1);
	args.put(KEY_DATA2, data2);
	return mDb.update(DATABASE_SLEEP_TABLE, args, 
			KEY_ROWID + "=" + rowId, null) > 0;
}//end updateSleep
	
	
}//end class InfoDbAdapter
