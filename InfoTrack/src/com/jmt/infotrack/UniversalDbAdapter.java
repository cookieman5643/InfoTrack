package com.jmt.infotrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UniversalDbAdapter {

	/*
	 * IF there exists two different paths that eventually have the same
	 * category name then they would need further specification to
	 * differentiate. So I can't have two 'folders' with the same name, unless I
	 * have a third category possibly.
	 * 
	 * Instead of using 'base,' use 'root' and then have path names, eventually
	 * we'll 'cut' off the extra
	 */

	// Class Vars.

	static String TAG = new String("UniversalDbAdapter");

	// Fields in Database
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATECREATED = "datecreated";
	public static final String KEY_DATEEDITTED = "dateeditted";
	public static final String KEY_DATEVIEWED = "dateviewed";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_SUBCATEGORY = "subcategory";
	public static final String KEY_DATA1 = "data1";
	public static final String KEY_DATA2 = "data2";
	public static final String KEY_DATA3 = "data3";
	public static final String KEY_DATA4 = "data4";
	public static final String KEY_DATA5 = "data5";
	public static final String KEY_EXTRA1 = "extra1";
	public static final String KEY_EXTRA2 = "extra2";

	String[] columnOrder = new String[] { "_id", "datecreated", "dateeditted",
			"dateviewed", "category", "subcategory", "data1", "data2", "data3",
			"data4", "data5", "extra1", "extra2" };

	// DB OBJECTS
	private DatabaseHelper gDbHelper;
	private SQLiteDatabase gDb;
	private final Context gCtx;

	// DB CREATION STATEMENTS
	private static final String DATABASE_CREATE = "create table universaltable (_id integer primary key autoincrement, "
			+ "datecreated text not null, "
			+ "dateeditted text not null, "
			+ "dateviewed text not null, "
			+ "category text not null, "
			+ "subcategory text not null, "
			+ "data1 text not null, "
			+ "data2 text not null, "
			+ "data3 text not null, "
			+ "data4 text not null, "
			+ "data5 text not null, "
			+ "extra1 text not null, " + "extra2 text not null); ";

	// DB INFO CONSTS
	private static final String DATABASE_NAME = "universal_db";
	private static final String DATABASE_UNIVERSAL_TABLE = "universaltable";
	private static final int DATABASE_VERSION = 3;

	// DB CLASS HELP
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
			// TODO: add here other tables
			// TODO: add base entries here
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS universaltable");
			onCreate(db);
		}
	}// end class DatabaseHelper

	// ########################
	// #***** COSTRUCTOR *****#
	// ########################
	public UniversalDbAdapter(Context ctx) {
		Log.i(TAG, "UniversalDbAdapter Constructor");
		this.gCtx = ctx;
	}

	// OPEN/CLOSE DATABASE
	public UniversalDbAdapter open() throws SQLException {
		gDbHelper = new DatabaseHelper(gCtx);
		gDb = gDbHelper.getWritableDatabase();
		return this;
	}

	/*
	 * This method could vary for other uses of this database class, but
	 * specifically for InfoTrack This sets up the initial cat=root,
	 * subCat=heading which is essential for the first opening
	 */
	public void fillInitialData() {
		DataObj init = new DataObj();
		init.setAllCategories("root", "heading");
		init.setData("Lists", "", "", "", "");
		this.createEntry(init);
	}// end fillInitialData()

	public void close() {
		gDbHelper.close();
	}

	// ############################
	// ##### CREATION METHODS #####
	// ############################

	/*
	 * @param dataobj This is a helper object that has all the fields of one row
	 * Method adds a row to table
	 */
	public long createEntry(DataObj dataobj) {
		ContentValues args = new ContentValues();
		args.put(KEY_DATECREATED, dataobj.getDCreated());
		args.put(KEY_DATEEDITTED, dataobj.getDModified());
		args.put(KEY_DATEVIEWED, dataobj.getDViewed());
		args.put(KEY_CATEGORY, dataobj.getCategory());
		args.put(KEY_SUBCATEGORY, dataobj.getSubCategory());
		args.put(KEY_DATA1, dataobj.getDataAt(1));
		args.put(KEY_DATA2, dataobj.getDataAt(2));
		args.put(KEY_DATA3, dataobj.getDataAt(3));
		args.put(KEY_DATA4, dataobj.getDataAt(4));
		args.put(KEY_DATA5, dataobj.getDataAt(5));
		args.put(KEY_EXTRA1, dataobj.getExtrasAt(1));
		args.put(KEY_EXTRA2, dataobj.getExtrasAt(2));
		return gDb.insert(DATABASE_UNIVERSAL_TABLE, null, args);
	}// end createEntry

	/*
	 * This is the alternate way to add an entry, but is seems very cumbersome.
	 * That is why we use the DataObj as a mediating object.
	 */
	public long createEntryA(String datecreated, String dateeditted,
			String dateviewed, String category, String subcategory,
			String data1, String data2, String data3, String data4,
			String data5, String extra1, String extra2) {
		ContentValues args = new ContentValues();
		args.put(KEY_DATECREATED, datecreated);
		args.put(KEY_DATEEDITTED, dateeditted);
		args.put(KEY_DATEVIEWED, dateviewed);
		args.put(KEY_CATEGORY, category);
		args.put(KEY_SUBCATEGORY, subcategory);
		args.put(KEY_DATA1, data1);
		args.put(KEY_DATA2, data2);
		args.put(KEY_DATA3, data3);
		args.put(KEY_DATA4, data4);
		args.put(KEY_DATA5, data5);
		args.put(KEY_EXTRA1, extra1);
		args.put(KEY_EXTRA2, extra2);
		return gDb.insert(DATABASE_UNIVERSAL_TABLE, null, args);
	}// end createEntry

	/*
	 * Method deletes row from the database's table.
	 * 
	 * @param rowId : gives a particular row of the table
	 */
	public boolean deleteEntry(long rowId) {
		return gDb.delete(DATABASE_UNIVERSAL_TABLE, KEY_ROWID + "=" + rowId,
				null) > 0;
	}// end deleteEntry
	
/*	
Method tries to delete the table from existing.	
*/
	public void deleteAll() {
		// TODO: fix broken code
		gDb.execSQL("DROP TABLE IF EXISTS " + DATABASE_UNIVERSAL_TABLE);
		gDb.execSQL(DATABASE_CREATE);
	}

	// NOTE: there is no date created update since that never will change.
	public boolean updateEntry(long rowId, String dateeditted,
			String dateviewed, String category, String subcategory,
			String data1, String data2, String data3, String data4,
			String data5, String extra1, String extra2) {
		ContentValues args = new ContentValues();
		args.put(KEY_DATEEDITTED, dateeditted);
		args.put(KEY_DATEVIEWED, dateviewed);
		args.put(KEY_CATEGORY, category);
		args.put(KEY_SUBCATEGORY, subcategory);
		args.put(KEY_DATA1, data1);
		args.put(KEY_DATA2, data2);
		args.put(KEY_DATA3, data3);
		args.put(KEY_DATA4, data4);
		args.put(KEY_DATA5, data5);
		args.put(KEY_EXTRA1, extra1);
		args.put(KEY_EXTRA2, extra2);
		return gDb.update(DATABASE_UNIVERSAL_TABLE, args, KEY_ROWID + "="
				+ rowId, null) > 0;
	}// end updateEntry

	// ###########################
	// ##### ACCESS METHODS ######
	// ###########################

	/*
	 * @returns all entries with all columns.
	 */
	public Cursor fetchAllEntry() {
		return gDb.query(DATABASE_UNIVERSAL_TABLE, new String[] { KEY_ROWID,
				KEY_DATECREATED, KEY_DATEEDITTED, KEY_DATEVIEWED, KEY_CATEGORY,
				KEY_SUBCATEGORY, KEY_DATA1, KEY_DATA2, KEY_DATA3, KEY_DATA4,
				KEY_DATA5, KEY_EXTRA1, KEY_EXTRA2 }, null, null, null, null,
				null);
	}// end fetchAllEntry

	/*
	 * @param cat = category
	 * 
	 * @param subCat = sub category
	 * 
	 * @returns Cursor : Filtered list of Data1
	 */
	public Cursor fetchAllCategoryEntry(String cat, String subcat) {
		return gDb.query(DATABASE_UNIVERSAL_TABLE, new String[] { KEY_ROWID,
				KEY_CATEGORY, KEY_SUBCATEGORY, KEY_DATA1 }, KEY_CATEGORY
				+ " = '" + cat + "' AND " + KEY_SUBCATEGORY + " = '" + subcat
				+ "'", null, null, null, null, null);
	}// end fetchAllEntry

	/*
	 * @param rowId : the given row of table
	 * 
	 * @return given row with all of its columns
	 */
	public Cursor fetchEntry(long rowId) throws SQLException {
		Cursor mCursor = gDb.query(DATABASE_UNIVERSAL_TABLE, new String[] {
				KEY_ROWID, KEY_DATECREATED, KEY_DATEEDITTED, KEY_DATEVIEWED,
				KEY_CATEGORY, KEY_SUBCATEGORY, KEY_DATA1, KEY_DATA2, KEY_DATA3,
				KEY_DATA4, KEY_DATA5, KEY_EXTRA1, KEY_EXTRA2 }, KEY_ROWID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}// end fetchEntry

	/*
	 * @param rowId = given row or entry
	 * 
	 * @param column = column name
	 * 
	 * @returns Text of specified row and column
	 */
	public String getEntryText(long rowId, String column) {
		//Log.i(TAG, "about to get cursor"); //Clutters Logs for long tables
		Cursor mCursor = gDb.query(DATABASE_UNIVERSAL_TABLE, new String[] {
				KEY_ROWID, column }, KEY_ROWID + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return "" + mCursor.getString(mCursor.getColumnIndex(column));

	}

}// end class InfoDbAdapter

