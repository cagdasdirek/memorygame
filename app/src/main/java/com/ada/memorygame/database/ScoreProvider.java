package com.ada.memorygame.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.ada.memorygame.modals.Score;

/**
 * Content provider class to create bridge between Loader and Database.
 * Created by Cagdas Direk on 24/12/15.
 */
public class ScoreProvider extends ContentProvider {
    private DatabaseHandler dbHandler;
    private static final UriMatcher uriMatcher;
    private static final SparseArray<String> sMimeTypes;

    /**
     * The authority for the contacts provider
     */
    public static final String AUTHORITY = "com.ada.memorygame";

    /**
     * The content:// style URI for this table
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * URI and Mime-type matcher
     */
    static {
        uriMatcher = new UriMatcher(0);
        sMimeTypes = new SparseArray<>();
        uriMatcher.addURI(AUTHORITY, DatabaseHandler.TABLE_SCORE, 1);
        sMimeTypes.put(1, "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + DatabaseHandler.TABLE_SCORE);
    }

    @Override
    public boolean onCreate() {
        dbHandler = new DatabaseHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor returnCursor = db.query(DatabaseHandler.TABLE_SCORE, projection, null, null, null, null, Score.SCORE.toString() + " DESC");
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return sMimeTypes.get(uriMatcher.match(uri));
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        db.beginTransaction();
        db.insert(DatabaseHandler.TABLE_SCORE, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
