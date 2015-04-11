// DatabaseConnector.java
// Provides easy connection and creation of UserContacts database.
package com.example.sam.d_food.integration.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnector
{
    // database name
    private static final String DATABASE_NAME = "UserHistory";
    private SQLiteDatabase database; // database object
    private DatabaseOpenHelper databaseOpenHelper; // database helper

    // public constructor for DatabaseConnector
    public DatabaseConnector(Context context)
    {
        // create a new DatabaseOpenHelper
        databaseOpenHelper =
                new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    } // end DatabaseConnector constructor

    // open the database connection
    public void open() throws SQLException
    {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
    } // end method open

    // close the database connection
    public void close()
    {
        if (database != null)
            database.close(); // close the database connection
    } // end method close

    // inserts a new contact in the database
    public void insertContact(String price, String downpay, String term,
                              String rate, String money, String total, String startdate, String enddate)
    {
        ContentValues newContact = new ContentValues();
        newContact.put("price", price);
        newContact.put("downpay", downpay);
        newContact.put("term", term);
        newContact.put("rate", rate);
        newContact.put("money", money);
        newContact.put("total", total);
        newContact.put("startdate", startdate);
        newContact.put("enddate", enddate);

        open(); // open the database
        database.insert("history", null, newContact);
        close(); // close the database
    } // end method insertContact

    // inserts a new contact in the database
    public void updateContact(long id, String price, String downpay,
                              String term, String rate, String money, String total, String startdate, String enddate)
    {
        ContentValues editContact = new ContentValues();
        editContact.put("price", price);
        editContact.put("downpay", downpay);
        editContact.put("term", term);
        editContact.put("rate", rate);
        editContact.put("money", money);
        editContact.put("total", total);
        editContact.put("startdate", startdate);
        editContact.put("enddate", enddate);

        open(); // open the database
        database.update("history", editContact, "_id=" + id, null);
        close(); // close the database
    } // end method updateContact

    // return a Cursor with all contact information in the database
    public Cursor getAllHistory()
    {
        return database.query("history", new String[] {"_id", "price", "downpay","term", "rate",
                        "money","total", "startdate", "enddate"},
                null, null, null, null, "_id");
    } // end method getAllhistory

    // get a Cursor containing all information about the contact specified
    // by the given id
    public Cursor getOneContact(long id)
    {
        return database.query(
                "history", null, "_id=" + id, null, null, null, null);
    } // end method getOnContact

    // delete the contact specified by the given String price
    public void deleteContact(long id)
    {
        open(); // open the database
        database.delete("history", "_id=" + id, null);
        close(); // close the database
    } // end method deleteContact

    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        // public constructor
        public DatabaseOpenHelper(Context context, String name,
                                  CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        } // end DatabaseOpenHelper constructor

        // creates the history table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // query to create a new table named history
            String createQuery = "CREATE TABLE history" +
                    "(_id integer primary key autoincrement," +
                    "price TEXT, downpay TEXT, term TEXT," +
                    "rate TEXT, money TEXT, total TEXT, " +
                    "startdate TEXT, enddate TEXT);";

            db.execSQL(createQuery); // execute the query
        } // end method onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
        } // end method onUpgrade
    } // end class DatabaseOpenHelper
} // end class DatabaseConnector
