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
import android.util.Log;

import com.example.sam.d_food.business.deliveryman.DeliverymanInfo;
import com.example.sam.d_food.business.user.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public void check() throws SQLException
    {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getReadableDatabase();
    }
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

    public boolean checkUser(String userName, String password) {
        boolean check=false;
        check();

        Cursor cursor=database.rawQuery("SELECT userName, password FROM Users WHERE userName = '"+ userName + "' AND password ='"+ password + "'", null);
        if(!cursor.moveToFirst()){
            check=false;
        }
        else{
            check=true;
        }
        return check;
    }
    public boolean checkDeliveryman(String userName, String password) {
        boolean check=false;
        check();
        Cursor cursor=database.rawQuery("SELECT userName, password FROM Deliveryman WHERE userName = '"+ userName + "' AND password ='"+ password + "'", null);
        if(!cursor.moveToFirst()){
            check=false;
        }
        else{
            check=true;
        }
        return check;
    }

    public void insertUser(String userName, String password, String address) {

        ContentValues values = new ContentValues();
        values.put("userName",userName);
        values.put("password",password);
        values.put("address",address);
        open();
        database.insert("Users",null,values);
        close();
    }
    public void insertDeliveryman(String userName, String password, String address) {

        ContentValues values = new ContentValues();
        values.put("userName",userName);
        values.put("password",password);
        values.put("address",address);
        open();
        database.insert("Deliveryman",null,values);
        close();
    }
    public UserInfo getUser(String userName){
        String selectQuery = "SELECT  * FROM " + "Users" + " WHERE "
                + "userName" + " = " + userName;
        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        UserInfo user = new UserInfo();
        user.setUserName(c.getString(c.getColumnIndex("userName")));
        user.setPassword((c.getString(c.getColumnIndex("password"))));
        user.setAddress(c.getString(c.getColumnIndex("address")));

        return user;
    }
    public DeliverymanInfo getDeliveryman(String userName){
        String selectQuery = "SELECT  * FROM " + "Deliveryman" + " WHERE "
                + "userName" + " = " + userName;
        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        DeliverymanInfo deliveryMan = new DeliverymanInfo();
        deliveryMan.setUserName(c.getString(c.getColumnIndex("userName")));
        deliveryMan.setPassword((c.getString(c.getColumnIndex("password"))));
        deliveryMan.setAddress(c.getString(c.getColumnIndex("address")));

        return deliveryMan;
    }
    public List<String> getAllUsers(){
        List<String> Users = new ArrayList<String>();

        String selectQuery = "SELECT  userName FROM " + "Users";
        Cursor c = database.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to list
                Users.add(c.getString(c.getColumnIndex("userName")));
            } while (c.moveToNext());
        }
        return Users;
    }
    public List<String> getAllDeliveryman(){
        List<String> Deliverymen = new ArrayList<String>();

        String selectQuery = "SELECT  userName FROM " + "Deliveryman";
        Cursor c = database.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to list
                Deliverymen.add(c.getString(c.getColumnIndex("userName")));
            } while (c.moveToNext());
        }

        return Deliverymen;
    }
    public void updateUser(UserInfo user){
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("password", user.getPassword());
        values.put("address", user.getAddress());

        // updating row
        database.update("Deliveryman", values, "userName" + " = ?",new String[] { String.valueOf(user.getUserName())});
    }
    public void updateDeliveryman(DeliverymanInfo deliveryMan){
        ContentValues values = new ContentValues();
        values.put("userName", deliveryMan.getUserName());
        values.put("password", deliveryMan.getPassword());
        values.put("address", deliveryMan.getAddress());

        // updating row
        database.update("Deliveryman", values, "userName" + " = ?",new String[] { String.valueOf(deliveryMan.getUserName())});
    }
    public void deleteUser(String userName){
        open();
        database.delete("Users", "userName" + " = ?",new String[] { String.valueOf(userName) });
        close();
    }
    public void deleteDeliveryman(String userName){
        open();
        database.delete("Deliveryman", "userName" + " = ?",new String[] { String.valueOf(userName) });
        close();
    }
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

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            String dateString="";

            // query to create a new table named history
            String createQuery = "CREATE TABLE history" +
                    "(_id integer primary key autoincrement," +
                    "price TEXT, downpay TEXT, term TEXT," +
                    "rate TEXT, money TEXT, total TEXT, " +
                    "startdate TEXT, enddate TEXT);";

            String createUsersTable= "CREATE TABLE "
                    + "Users" + "(" + "id" + " INTEGER PRIMARY KEY autoincrement," + "userName"
                    + " TEXT," + "password" + " TEXT,"+ "address" + " TEXT,"+""+dateString
                    + " TEXT" + ")";

            String createDeliverymanTable= "CREATE TABLE "
                    + "Deliveryman" + "(" + "id" + " INTEGER PRIMARY KEY autoincrement," + "userName"
                    + " TEXT," + "password" + " TEXT,"+ "address" + " TEXT,"+""+dateString
                    + " TEXT" + ")";
            db.execSQL(createQuery); // execute the query
            db.execSQL(createUsersTable); // execute the query
            db.execSQL(createDeliverymanTable); // execute the query
        } // end method onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
        } // end method onUpgrade
    } // end class DatabaseOpenHelper
} // end class DatabaseConnector
