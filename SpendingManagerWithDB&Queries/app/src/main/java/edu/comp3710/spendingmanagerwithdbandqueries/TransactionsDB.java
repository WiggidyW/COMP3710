package edu.comp3710.spendingmanagerwithdbandqueries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

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
    public List<String[]> loadReadableTransactions(long dateFrom, long dateTo, float amountFrom, float amountTo, int spendType) {
        List<String[]> list = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        Cursor c = db.rawQuery("SELECT date, amount, reason FROM transactions ORDER BY date DESC"
                , null);

        while (c.moveToNext()) {
            long date = c.getLong(0);
            float amount = c.getFloat(1);
            String reason = c.getString(2);

            if(dateFrom != -1) {
                if(date < dateFrom) {
                    continue;
                }
            }
            if(dateTo != -1) {
                if(date > dateTo) {
                    continue;
                }
            }

            if(spendType == 1) {
                if(amount < 0) {
                    continue;
                }
            }
            else if(spendType == 2) {
                if(amount > 0) {
                    continue;
                }
            }

            if(amountFrom != -1 || amountTo != -1) {
                float absAmount = abs(amount);
                if(amountFrom != -1) {
                    if(absAmount < amountFrom) {
                        continue;
                    }
                }
                if(amountTo != -1) {
                    if(absAmount > amountTo) {
                        continue;
                    }
                }
            }

            list.add(new String[]{ formatter.format(date).toString(), String.format("$%.2f", amount), reason });
        }

        return list;
    }
}
