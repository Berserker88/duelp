package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Lernplan_DatabaseHandler extends SQLiteOpenHelper 
{
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "duelp_learnPlan_db";
 
    // Learnplan table name
    private static final String TABLE_ENTRY = "lernplan";
 
    // LearnPlan Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATUM = "datum";
    private static final String KEY_FACH = "fach";
    private static final String KEY_START = "start";
    private static final String KEY_ENDE = "ende";
    private static final String KEY_ORT = "ort";
    private static final String KEY_FRUEHSTUECK = "fruehstuck";
	  
    public Lernplan_DatabaseHandler(Context context) 
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
    	//DropTable();
    	
        String CREATE_LEARNPLAN_TABLE = "CREATE TABLE " + TABLE_ENTRY +"("+ 
        		KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE ," + 
        		KEY_DATUM + " VARCHAR NOT NULL," + 
        		KEY_FACH + " VARCHAR NOT NULL," + 
        		KEY_START + " VARCHAR NOT NULL,"+ 
        		KEY_ENDE + " VARCHAR NOT NULL," + 
        		KEY_ORT + " VARCHAR,"+ 
        		KEY_FRUEHSTUECK + " VARCHAR NOT NULL"+")";
        db.execSQL(CREATE_LEARNPLAN_TABLE);
    }
 
    
    public void DropTable()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	String drop = "DROP TABLE "+ TABLE_ENTRY;
    	
    	db.execSQL(drop);
    	db.close();
    }
    
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
    	// Drop older table if existed
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);
 
    	// Create tables again
    	onCreate(db);
    }
    
     
    
    /**
      *All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new entry
    void addLearnEntry(LearnEntry learnEntry) 
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(KEY_DATUM, learnEntry.getDate()); // Eintrag Datum
    	values.put(KEY_FACH, learnEntry.getFach()); // Eintrag Fach
    	values.put(KEY_START, learnEntry.getStart()); // Eintrag Start
    	values.put(KEY_ENDE, learnEntry.getEnde()); // Eintrag Ende
    	values.put(KEY_ORT, learnEntry.getOrt()); // Eintrag Ort
        values.put(KEY_FRUEHSTUECK, learnEntry.getFruehstueck()); // Eintrag Frühstück
 
        // Inserting Row
        db.insert(TABLE_ENTRY, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single entry
    LearnEntry getLearnEnry(int id) 
    {
    	SQLiteDatabase db = this.getReadableDatabase();
 
    	Cursor cursor = db.query(TABLE_ENTRY, new String[] { KEY_ID,
    			KEY_DATUM, KEY_FACH, KEY_START, KEY_ENDE, KEY_ORT, KEY_FRUEHSTUECK }, KEY_ID + "=?",
    			new String[] { String.valueOf(id) }, null, null, null, null);
    	if (cursor != null)
    		cursor.moveToFirst();
 
    	LearnEntry learnEntry = new LearnEntry(Integer.parseInt(cursor.getString(0)), //<---Geändert
    			cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        // return contact
    	return learnEntry;
    }
 
    // Getting All entry
    public List<LearnEntry> getAllLearnEntrys() 
    {
        List<LearnEntry> learnEntryList = new ArrayList<LearnEntry>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) 
        {
            do 
            {
                LearnEntry learnEntry = new LearnEntry(Integer.parseInt(cursor.getString(0)), 
                		cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                /*learnEntry.setId(cursor.getString(0));  		//<----geändert
                learnEntry.setDatum(cursor.getString(1));
                learnEntry.setFach(cursor.getString(2));
                learnEntry.setStart(cursor.getString(3));
                learnEntry.setEnde(cursor.getString(4));
                learnEntry.setOrt(cursor.getString(5));
                learnEntry.setFruehstueck(cursor.getString(6));*/
                // Adding learnEntry to list
                learnEntryList.add(learnEntry);
            } while (cursor.moveToNext());
        }
 
        // return entry list
        return learnEntryList;
    }
 

	// Updating single entry
    public int updateLearnEntry(LearnEntry learnEntry) 
    {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_DATUM, learnEntry.getDate());
        values.put(KEY_START, learnEntry.getStart());
        values.put(KEY_FACH, learnEntry.getFach());
        values.put(KEY_ENDE, learnEntry.getEnde());
        values.put(KEY_ORT, learnEntry.getOrt());
        values.put(KEY_FRUEHSTUECK, learnEntry.getFruehstueck());
 
        // updating row
        return db.update(TABLE_ENTRY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(learnEntry.getId()) });
    }
 
    // Deleting single entry
    public void deleteLearnEntry(LearnEntry learnEntry) 
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ENTRY, KEY_ID + " = ?",
                new String[] { String.valueOf(learnEntry.getId()) });
        db.close();
    }
 
    // Getting entrys Count
    public int getLearnEntryCount() 
    {
        String countQuery = "SELECT  * FROM " + TABLE_ENTRY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
	        return cursor.getCount();
    }
	 
}

