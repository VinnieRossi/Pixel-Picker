package com.example.vinnie.pixelpicker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Luis on 2/17/2015.
 */

//FOR THE LOVE OF GOD DON'T USE DB SHIT YET.
public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "colorManager";

    //TABLE NAMES
    private static final String TABLE_COLORS = "colors";
    //Common column names
    private static final String KEY_ID = "id";

    //Color Table col names
    private static final String HEX_VALUE = "hex_code";
    private static final String HEX_VALUE_INTEGER = "hex_value";

    private static final String CUSTOM_NAME = "customName";




    private static final String CREATE_TABLE_COLORS = "CREATE TABLE "
            + TABLE_COLORS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + HEX_VALUE + " TEXT,"+ HEX_VALUE_INTEGER + " INTEGER,"
            + CUSTOM_NAME + " TEXT"
            + ")";



    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COLORS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLORS);
        onCreate(db);
    }



    /*------------------------------------------------------------Color methods -------------------*/
    public long createColor(PickedColor color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HEX_VALUE, color.getHex());
        values.put(HEX_VALUE_INTEGER, Color.parseColor(color.getHex()));

        values.put(CUSTOM_NAME, color.getCustomName());
        // values.put(KEY_COURSE_NAME, color.getCourseName());


        long color_ID = db.insert(TABLE_COLORS, null, values);

        return color_ID;
    }

    public ArrayList<PickedColor> getColors() {
        ArrayList<PickedColor> colorList = new ArrayList<PickedColor>();

        String selectColor = "SELECT *fROM " + TABLE_COLORS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectColor, null);
        if (c.moveToFirst()) {
            do {
                PickedColor color = new PickedColor();
                color.setID(c.getInt((c.getColumnIndex(KEY_ID))));
                color.setHex(c.getString(c.getColumnIndex(HEX_VALUE)));
                color.setCustomName(c.getString(c.getColumnIndex(CUSTOM_NAME)));

                colorList.add(color);
            } while (c.moveToNext());
        }
        return colorList;

    }

    public ArrayList<PickedColor> getSortedColors() {
        ArrayList<PickedColor> colorList = new ArrayList<PickedColor>();

        String selectColor = "SELECT *fROM " + TABLE_COLORS + " ORDER BY "+HEX_VALUE_INTEGER
                +" ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectColor, null);
        if (c.moveToFirst()) {
            do {
                PickedColor color = new PickedColor();
                color.setID(c.getInt((c.getColumnIndex(KEY_ID))));
                color.setHex(c.getString(c.getColumnIndex(HEX_VALUE)));

                color.setCustomName(c.getString(c.getColumnIndex(CUSTOM_NAME)));

                colorList.add(color);
            } while (c.moveToNext());
        }
        return colorList;

    }

    public PickedColor getColorByID(long studentID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_COLORS + " WHERE "
                + KEY_ID + " = " + studentID;

        Log.e("DataBaseHandler", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        PickedColor color = new PickedColor();
        color.setID(c.getInt(c.getColumnIndex(KEY_ID)));
        color.setHex(c.getString(c.getColumnIndex(HEX_VALUE)));
        // color.setCourseName(c.getString(c.getColumnIndex(KEY_COURSE_NAME)));
        color.setCustomName(c.getString(c.getColumnIndex(CUSTOM_NAME)));

        return color;
    }


    public int updateColorData(PickedColor color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HEX_VALUE, color.getHex());
        values.put(CUSTOM_NAME, color.getCustomName());

        return db.update(TABLE_COLORS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(color.getID())});
    }


}
