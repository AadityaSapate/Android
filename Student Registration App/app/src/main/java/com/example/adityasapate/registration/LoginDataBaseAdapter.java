package com.example.adityasapate.registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter 
{
		static final String DATABASE_NAME = "Registration.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		// TODO: Create public field for each column in your table.
		// SQL Statement to create a new database.
		static final String DATABASE_CREATE = "create table "+"REGISTRATION"+
		                             "( " +"ID"+" integer primary key autoincrement,"+ "NAME  text ,REGISTRATIONID number ,ADDRESS text ,DOB DATE ,YEAR number ,PASSWORD text); ";
		// Variable to hold the database instance
		public  SQLiteDatabase db;
		// Context of the application using the database.
		private final Context context;
		// Database open/upgrade helper

		private DataBaseHelper dbHelper;
		public  LoginDataBaseAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public  LoginDataBaseAdapter open() throws SQLException 
		{
			db = dbHelper.getWritableDatabase();
			return this;
		}
		public void close() 
		{
			db.close();
		}

		/* public  SQLiteDatabase getDatabaseInstance()
		{
			return db;
		} */

		public void insertEntry(String Name,String regId, String Address,String dob,String Year,String password )
		{
	       ContentValues newValues = new ContentValues();

			newValues.put("NAME", Name);
			newValues.put("REGISTRATIONID",regId);
			newValues.put("ADDRESS",Address);
			newValues.put("DOB",dob);
            newValues.put("YEAR",Year);
			newValues.put("PASSWORD",password);
			

			db.insert("REGISTRATION", null, newValues);
			///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
		}
		public int deleteEntry(String Name)
		{

		    String where="NAME=?";
		    int numberOFEntriesDeleted= db.delete("REGISTRATION", where, new String[]{Name}) ;
	       // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
	        return numberOFEntriesDeleted;
		}	
		public String getSinlgeEntry(String Name)
		{
			Cursor cursor=db.query("REGISTRATION", null, " NAME=?", new String[]{Name}, null, null, null);
	        if(cursor.getCount()<1)
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
			cursor.close();
			return password;				
		}
    public String get(String Name)
    {
        Cursor cursor=db.query("REGISTRATION", null, " NAME=?", new String[]{Name}, null, null, null);
        if(cursor.getCount()<1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String Id = cursor.getString(cursor.getColumnIndex("ID"));
        String name= cursor.getString(cursor.getColumnIndex("NAME"));
        String registrationid= cursor.getString(cursor.getColumnIndex("REGISTRATIONID"));
        String address= cursor.getString(cursor.getColumnIndex("ADDRESS"));
        String dob= cursor.getString(cursor.getColumnIndex("DOB"));
        String year = cursor.getString(cursor.getColumnIndex("YEAR"));
       String data = "Serial No = " + Id +"\nName = " + name +"\nRegistration Id = " + registrationid + "\nAddress = " + address + "\nDate Of Birth = " + dob + "\nCurrent Year =" + year ;

       // String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));

        cursor.close();
        return data;
    }


}

