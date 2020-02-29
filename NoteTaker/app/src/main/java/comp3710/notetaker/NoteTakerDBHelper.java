package comp3710.notetaker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteTakerDBHelper extends SQLiteOpenHelper {
    public NoteTakerDBHelper(Context context) {
        super(context, "NoteTaker.db", null, 1);
    }

    private static final String SQL_CREATE =
                    "CREATE TABLE notes(" +
                    "title TEXT PRIMARY KEY," +
                    "content TEXT" +
                    ")";

    private static final String SQL_DELETE =
                    "DROP TABLE IF EXISTS notes";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}