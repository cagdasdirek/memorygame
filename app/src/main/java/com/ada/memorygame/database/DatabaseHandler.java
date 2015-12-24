package com.ada.memorygame.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ada.memorygame.modals.Score;

/**
 * SQL Lite Database implementation.
 * Created by Cagdas Direk on 23/12/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    /**
     * Database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Database name.
     */
    public static final String DATABASE_NAME = "highScore.db";

    /**
     * Table name.
     */
    public static final String TABLE_SCORE = "scores";

    /**
     * Constructor of the class.
     *
     * @param context of the activity.
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create the table in the exact db.
     *
     * @param db that we are going to use.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
                + Score._ID.toString() + " INTEGER PRIMARY KEY," + Score.NAME.toString() + " TEXT,"
                + Score.SCORE.toString() + " INTEGER" + ")";
        db.execSQL(CREATE_SCORE_TABLE);
    }

    /**
     * Upgrade the db.
     *
     * @param db         that we are going to use.
     * @param oldVersion old version number
     * @param newVersion new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }
}