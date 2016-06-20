package com.psalata.ready4s.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 19.06.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper ourInstance = null;

    public static DBHelper getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new DBHelper(context.getApplicationContext());
        } return ourInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="Database";

    private static final String TABLE_CITIES = "CITIES";

    private static final String ID = "id";
    private static final String CITY_NAME = "name";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CITIES + "("
                + ID + " INTEGER_PRIMARY_KEY," + CITY_NAME + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_CITIES);
        onCreate(db);
    }

    public long insertCityName(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CITY_NAME, cityName);

        return db.insert(TABLE_CITIES, null, values);
    }

    public List<String> getCitiesNames() {
        List<String> citiesNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT " + CITY_NAME + " FROM " + TABLE_CITIES + " ORDER BY " + CITY_NAME +" ASC";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String cityName = cursor.getString(cursor.getColumnIndex(CITY_NAME));
                citiesNames.add(cityName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return citiesNames;
    }
}
