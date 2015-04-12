package com.example.vinnie.pixelpicker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
   // private static final String TABLE_COURSE = "images";
    private static final String TABLE_COLORS = "colors";
    //Common column names
    private static final String KEY_ID = "id";
    //Courses Table column names
  //  private static final String KEY_COURSE_NAME = "course_name";
    //Color Table col names
    private static final String RED_VALUE = "red";
    private static final String GREEN_VALUE = "green";
    private static final String BLUE_VALUE = "blue";
    //private static final String KEY_COURSE_NAME = "course_name";


    //table create
//    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
//            + TABLE_COLORS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_COURSE_NAME+ " TEXT" +  ")";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_COLORS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + RED_VALUE + " INTEGER," + GREEN_VALUE + " INTEGER," + BLUE_VALUE + " INTEGER"
            + ")";

//    private static final String CREATE_TABLE_COURSES = "CREATE TABLE "
//            + TABLE_COURSE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_COURSE_NAME + " TEXT" + ")";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL(CREATE_TABLE_COURSES);
        db.execSQL(CREATE_TABLE_STUDENTS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLORS);
        onCreate(db);
    }

    /*------------------------------------------------------------Course methods -------------------*/
//    public long createCourse(Course course) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_COURSE_NAME, course.getCourseName());
//
//        long course_ID = db.insert(TABLE_COURSE, null, values);
//
//       return course_ID;
//        return 0;
//    }
//
//    public ArrayList<Course> getCourses() {
//        ArrayList<Course> courseList = new ArrayList<Course>();
//
//        String selectCourse = "SELECT *fROM " + TABLE_COURSE;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectCourse, null);
//        if (c.moveToFirst()) {
//            do {
//                Course course = new Course();
//                course.setID(c.getInt((c.getColumnIndex(KEY_ID))));
//                course.setCourseName(c.getString(c.getColumnIndex(KEY_COURSE_NAME)));
//                courseList.add(course);
//            } while (c.moveToNext());
//        }
//        return courseList;
//
//    }

    /*------------------------------------------------------------Color methods -------------------*/
    public long createColor(Color color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RED_VALUE, color.getRed());
        values.put(GREEN_VALUE, color.getGreen());
        values.put(BLUE_VALUE, color.getBlue());
       // values.put(KEY_COURSE_NAME, color.getCourseName());


        long color_ID = db.insert(TABLE_COLORS, null, values);

        return color_ID;
    }

    public ArrayList<Color> getColors() {
        ArrayList<Color> colorList = new ArrayList<Color>();

        String selectColor = "SELECT *fROM " + TABLE_COLORS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectColor, null);
        if (c.moveToFirst()) {
            do {
                Color color = new Color();
                color.setID(c.getInt((c.getColumnIndex(KEY_ID))));
                color.setRed(c.getInt(c.getColumnIndex(RED_VALUE)));
                color.setBlue(c.getInt(c.getColumnIndex(BLUE_VALUE)));
              //  color.setCourseName(c.getString(c.getColumnIndex(KEY_COURSE_NAME)));
                color.setGreen(c.getInt(c.getColumnIndex(GREEN_VALUE)));

                colorList.add(color);
            } while (c.moveToNext());
        }
        return colorList;

    }

    public Color getColorByID(long studentID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_COLORS + " WHERE "
                + KEY_ID + " = " + studentID;

        Log.e("DataBaseHandler", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Color color = new Color();
        color.setID(c.getInt(c.getColumnIndex(KEY_ID)));
        color.setRed(c.getInt(c.getColumnIndex(RED_VALUE)));
        color.setBlue(c.getInt(c.getColumnIndex(BLUE_VALUE)));
       // color.setCourseName(c.getString(c.getColumnIndex(KEY_COURSE_NAME)));
        color.setGreen(c.getInt(c.getColumnIndex(GREEN_VALUE)));

        return color;
    }

//    public int updateStudentUri(Color color) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(BLUE_VALUE, color.getImageUri());
//
//
//        // updating row
//        return db.update(TABLE_COLORS, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(color.getID())});
//    }

    public int updateColorData(Color color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RED_VALUE, color.getRed());
        values.put(GREEN_VALUE, color.getGreen());
        values.put(BLUE_VALUE, color.getBlue());

        // values.put(KEY_COURSE_NAME, color.getCourseName());

        // updating row
        return db.update(TABLE_COLORS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(color.getID())});
    }


}
