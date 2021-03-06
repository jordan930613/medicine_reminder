package com.example.medicine_reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TimeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydata.db";
    public static final String TABLE_NAME = "time_table";

    int max_time = 0;
    int Get_datacount;

    public TimeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(id_time INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name_id VARCHAR(32), " +
                "datetime VARCHAR(32), " +
                "eat INTEGER NOT NULL, " +
                "dead INTEGER NOT NULL)";

        db.execSQL(CREATE_TABLE);

        System.out.println("create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 刪除原有的表格
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+SQLiteDAO.TABLE_NAME);

        // 呼叫onCreate建立新版的表格

        onCreate(db);
    }

    public Cursor getData(int get_name_id) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT datetime FROM " + TABLE_NAME + " WHERE name_id = '" + get_name_id + "'";
        Cursor data = db.rawQuery(query, null);
        if (data.getCount() >= 4) {
            max_time = 1;
        }

        return data;
    }

    public int get_time_id(int get_name_id, String get_time) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT id_time FROM " + TABLE_NAME + " WHERE name_id = '" + get_name_id + "'" + " AND datetime = '" + get_time + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();

        return Integer.valueOf(data.getString(0));
    }

    public int getMax_time() {

        return max_time;
    }

    public Cursor sort() {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT datetime,name_id,dead FROM " + TABLE_NAME + " ORDER BY datetime ASC ";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public Cursor checkEat(int nameId, String time) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT eat FROM " + TABLE_NAME + " WHERE datetime = '" + time + "'"+ " AND name_id = '" + nameId + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();

        return data;
    }

    public Cursor checkDead(int nameId, String time) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT dead FROM " + TABLE_NAME + " WHERE datetime = '" + time + "'"+ " AND name_id = '" + nameId + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();

        return data;
    }

    public void deleteTime(String get_datetime, int get_name_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE datetime = '" + get_datetime + "'" + " AND name_id = '" + get_name_id + "'");
    }


    //解決no such table，因為onCreate跑一次就不會再跑了
    @Override
    public void onOpen(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(id_time INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name_id VARCHAR(32), " +
                "datetime VARCHAR(32), " +
                "eat INTEGER NOT NULL, " +
                "dead INTEGER NOT NULL)";

        db.execSQL(CREATE_TABLE);
        super.onOpen(db);
    }

    public void deletezerobag(int name_id) {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE name_id = '" + name_id + "'";
        db.execSQL(query);
    }

    public Cursor datacount() {
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT id_time FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        Get_datacount = data.getCount();

        return data;
    }

    public int get_datacount() {

        datacount();
        System.out.println("getdatacount = " + Get_datacount);

        return Get_datacount;
    }
}
