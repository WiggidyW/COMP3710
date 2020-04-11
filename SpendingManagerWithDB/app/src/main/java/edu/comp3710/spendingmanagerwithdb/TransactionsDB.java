package edu.comp3710.spendingmanagerwithdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsDB {
    private TransactionsDBHelper dbHelper;
    private SQLiteDatabase db;

    public TransactionsDB(Context context) {
        dbHelper = new TransactionsDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void addTransaction(long date, float amount, String reason) {
        ContentValues v = new ContentValues();
        v.put("date", date);
        v.put("amount", amount);
        v.put("reason", reason);
        db.insertOrThrow("transactions", null, v);
    }

    public String loadReadableBalance() {
        Cursor c = db.rawQuery("SELECT SUM(amount) FROM transactions", null);
        float balance;
        if(c.moveToFirst()) {
            balance = c.getFloat(0);
        }
        else {
            balance = 0;
        }
        return String.format("$%.2f", balance);
    }

    // Returns list of (String, String, String)
    // Wanted to use a Tuple but struggled to import a new lib into android studio.
    public List<String[]> loadReadableTransactions() {
        List<String[]> list = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        Cursor c = db.rawQuery("SELECT date, amount, reason FROM transactions ORDER BY date DESC"
                , null);

        while (c.moveToNext()) {
            String date = formatter.format(new Date(c.getLong(0))).toString();
            String amount = String.format("$%.2f", c.getFloat(1));
            String reason = c.getString(2);
            list.add(new String[]{ date, amount, reason });
        }

        return list;
    }
}
