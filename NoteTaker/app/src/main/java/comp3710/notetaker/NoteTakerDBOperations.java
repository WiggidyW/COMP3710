package comp3710.notetaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NoteTakerDBOperations {
    private NoteTakerDBHelper dbHelper;
    private SQLiteDatabase db;

    public NoteTakerDBOperations(Context context) {
        dbHelper = new NoteTakerDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean noteExists(String title) {
        Cursor c = db.rawQuery("SELECT title FROM notes WHERE title = ?", new String[] {title});
        if (c.getCount() == 0) {
            return false;
        }
        return true;
    }

    public void addNote(String title, String content) {
        ContentValues v = new ContentValues();
        v.put("title", title);
        v.put("content", content);
        db.replaceOrThrow("notes", null, v);
    }

    public String loadNote(String title) {
        Cursor c = db.rawQuery("SELECT content FROM notes WHERE title = ?", new String[] {title});
        c.moveToFirst();
        return c.getString(0);
    }

    public String[] loadTitles() {
        Cursor c = db.rawQuery("SELECT title FROM notes", null);
        String[] titleList = new String[c.getCount()];
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToNext();
            titleList[i] = c.getString(0);
        }
        return titleList;
    }

    public void closeDB() {
        db.close();
        dbHelper.close();
    }
}
