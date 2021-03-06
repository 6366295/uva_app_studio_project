/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * HighScoreDatabase.java
 *
 * SQLite helper class for a high score database
 * Provides access to Highscore.db database
 * */

package nl.uva.projecttds;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HighScoreDatabase extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Highscore.db";
    public static final String TABLE_NAME = "gamescores";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_SCORE = "score";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_NAME + " TEXT" + "," +
                    COLUMN_NAME_SCORE + " TEXT" +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public HighScoreDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Necessary function for SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // Necessary function for SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // Necessary function for SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
