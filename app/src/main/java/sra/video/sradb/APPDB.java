package sra.video.sradb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

@SuppressLint("NewApi")
public class APPDB extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/sra.videos.india.homeveda/";
	private static String DB_NAME = "sravan.sqlite";
	private static SQLiteDatabase db;
	private static Context dbContext;
	
	public APPDB(Context context) {
		super(context, DB_NAME, null, 1);
		APPDB.dbContext = context;
        db=getWritableDatabase();
	}

	public static void CreateDataBase() throws IOException {

		try {
			boolean dbExist = checkDataBase();
			Log.w("dbExist : " + dbExist, "=");
			if (dbExist) {

			} else {

			
				try {
					copyDataBase();
				} catch (IOException e) {
					Log.w(" copy ERROR ", e.toString());
					throw new Error("Error copying database");

				}
			}
		} catch (Exception e) {
			Log.w(" create db  ", e.toString());
		}

	}

	private static boolean checkDataBase() {

		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			Log.w(" ope or create ", e.toString());
		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	private static void copyDataBase() throws IOException {

		InputStream myInput = dbContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	/*public static void OpenDataBase() throws SQLException {
		try {
			String myPath = DB_PATH + DB_NAME;
			db = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (Exception e) {
			Log.w(" open db ", " ope db ");
		}
	} */

	public static Cursor ExecuteRawQuerry(String querry, String[] selectionArgs)
			throws SQLException {
		return db.rawQuery(querry, selectionArgs);

	}


	public Cursor getResult(String query) {
		return db.rawQuery(query, null);
	}

	public int getCount(String tabName) {
		try {
			Cursor c = db.rawQuery("select * from " + tabName, null);
			int count = c.getCount();
			c.close();
			return count;
		} catch (Exception e) {
			Log.w(" ERROR " + e.toString(), "=======>>>>>");
			return 0;
		}
	}

	public synchronized long inSert(String tabName, String colName,
			ContentValues cv) {
		try {
			return db.insertOrThrow(tabName, colName, cv);
		} catch (SQLiteConstraintException e) {
			Log.w("constartint ", e.toString());
			return 0;
		}
	}

	public int update(String tabName, ContentValues cv, String where) {
		return db.update(tabName, cv, where, null);
	}
	
	public int delete(String tabName, String args, String where[]) {
		return db.delete(tabName, args,where);
	}

	public int getLast(String qur) {

		Cursor c = db.rawQuery(qur, null);
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			int cc = c.getInt(0);
			c.close();
			return cc;
		} else {
			if (c != null)
				c.close();
			return 0;
		}

	}

	public static void CloseDataBase() {
		try {
			if (db != null)
				db.close();
		} catch (Exception e) {
		}
	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		CREATE TABLE "sravan" ("videoid" TEXT ,"title" TEXT,"url" TEXT ,
		"rtspurl" TEXT ,"thumb" TEXT,"duration"
		 TEXT,"isfav" integer DEFAULT (null) ,"sra2" TEXT,"sra3" TEXT)
		 */

		db.execSQL("create TABLE IF NOT EXISTS sravan ( videoid text ,title text" +
				" , url text ,rtspurl  text , thumb text ,duration text ,isfav integer" +
				"  , sra2 text ,sra3 text )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
