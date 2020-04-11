package edu.comp3710.spendingmanagerwithdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionsDBHelper extends SQLiteOpenHelper {
    public TransactionsDBHelper(Context context) {
        super(context, "SpendingManager.db", null, 1);
    }

    private static final String SQL_CREATE =
            "CREATE TABLE transactions(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "date INTEGER NOT NULL," +
                    "amount REAL NOT NULL," +
                    "reason TEXT NOT NULL" +
                    ")";

    private static final String SQL_DELETE =
            "DROP TABLE IF EXISTS transactions";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}