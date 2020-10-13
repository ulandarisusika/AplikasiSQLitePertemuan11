package com.example.aplikasisqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {
    private static final int Database_version=2;
    static final String Database_name="digitaltalent.db";
    public static final String Table_sqlite="sqlite";
    public static final String column_id="id";
    public static final String column_name="name";
    public static final String column_address="address";

    public DbHelper(Context context){
        super(context, Database_name,null,Database_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String Sql_create="CREATE TABLE "+Table_sqlite+" ("+column_id+" INTEGER PRIMARY KEY autoincrement, "+
                column_name+" TEXT NOT NULL, "+column_address+" TEXT NOT NULL )";
        db.execSQL(Sql_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_sqlite);
        onCreate(db);
    }
    public ArrayList<HashMap<String, String>> getAllData(){
        ArrayList<HashMap<String, String>> wordList;
        wordList=new ArrayList<HashMap<String,String>>();
        String select_query="SELECT * FROM "+Table_sqlite;
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery(select_query, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(column_id,cursor.getString(0));
                map.put(column_name,cursor.getString(1));
                map.put(column_address,cursor.getString(2));
            } while (cursor.moveToNext());
        }
        Log.e("select sqlite ",""+wordList);
        database.close();
        return  wordList;
    }
    public void insert(String name, String address){
        SQLiteDatabase database=this.getWritableDatabase();
        String queryValues="INSERT INTO "+Table_sqlite+" (name,address) "+" VALUES " +
                "('"+name+"','"+address+"')";
        Log.e("insert sqlite",""+queryValues);
        database.execSQL(queryValues);
        database.close();
    }
    public void update(int id, String name, String address){
        SQLiteDatabase database=this.getWritableDatabase();
        String updateQuery = "UPDATE "+Table_sqlite+" SET "
                +column_name+"='"+name+"',"
                +column_address+"='"+address+"'"
                +" WHERE "+column_id+"="+"'"+id+"'";
        Log.e("update sqlite ",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
    public void delete(int id){
        SQLiteDatabase database=this.getWritableDatabase();
        String deleteQuery="DELETE FROM "+Table_sqlite+" WHERE "+column_id+"="+"'"+id+"'";
        Log.e("delete query ",deleteQuery);
        database.execSQL(deleteQuery);
        database.close();
    }
}
