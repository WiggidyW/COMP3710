package edu.comp3710.spendingmanagerwithdb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // inputs
    private EditText inputDate, inputAmount, inputPurpose;
    private Button inputAdd, inputSpend;
    private DatePickerDialog inputDateDialog;
    private AlertDialog.Builder inputAmountDialog, inputPurposeDialog;

    // outputs
    private TextView balance;
    private TableLayout transactions;
    private TransactionsDB transactionsDB;
    private long insertDate;
    private float insertAmount;
    private String insertReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        transactionsDB = new TransactionsDB(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balance = findViewById(R.id.balance);
        setBalance();

        transactions = findViewById(R.id.transactions);
        setTransactions();

        inputDate = findViewById(R.id.inputDate);
        inputDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputDateDialog = new DatePickerDialog(v.getContext());
                inputDateDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker d, int year, int month, int dayOfMonth) {
                        inputDate.setText(String.format("%02d/%02d/%04d",
                                month+1,
                                dayOfMonth,
                                year));
                        insertDate = LocalDate.of(year, month+1, dayOfMonth+1)
                            .atStartOfDay(ZoneOffset.UTC)
                            .toInstant()
                            .toEpochMilli();
                    }
                });
                inputDateDialog.show();
            }
        });

        inputAmount = findViewById(R.id.inputAmount);
        inputAmount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputAmountDialog = new AlertDialog.Builder(v.getContext());
                final EditText input = new EditText(inputAmountDialog.getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setHint("Amount");
                inputAmountDialog.setView(input);

                inputAmountDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {
                        String input_str = input.getText().toString();
                        Float input_f;
                        if(input_str.length() == 0) {input_f = 0.00f;}
                        else {input_f = Float.parseFloat(input_str);}
                        inputAmount.setText(String.format("$%.2f", input_f));
                        insertAmount = input_f;
                    }
                });
                inputAmountDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {
                        d.cancel();
                    }
                });

                inputAmountDialog.show();
            }
        });

        inputPurpose = findViewById(R.id.inputPurpose);
        inputPurpose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputPurposeDialog = new AlertDialog.Builder(v.getContext());
                final EditText input = new EditText(inputPurposeDialog.getContext());
                input.setHint("Purpose");
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                inputPurposeDialog.setView(input);

                inputPurposeDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {
                        String input_str = input.getText().toString();
                        if(input_str.length() == 0) {input_str = "No Reason";}
                        inputPurpose.setText(input_str);
                        insertReason = input_str;
                    }
                });
                inputPurposeDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {
                        d.cancel();
                    }
                });

                inputPurposeDialog.show();
            }
        });

        inputAdd = findViewById(R.id.inputAdd);
        inputAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(inputDate.getText().toString().length() == 0) {
                    Toast.makeText(v.getContext(), "Your date is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(inputAmount.getText().toString().length() == 0) {
                    Toast.makeText(v.getContext(), "Your amount is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(inputPurpose.getText().toString().length() == 0) {
                    Toast.makeText(v.getContext(), "Your purpose is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    transactionsDB.addTransaction(insertDate, insertAmount, insertReason);
                    setBalance();
                    setTransactions();
                }
            }
        });

        inputSpend = findViewById(R.id.inputSpend);
        inputSpend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(inputDate.getText().toString().length() == 0) {
                    Toast.makeText(v.getContext(), "Your date is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(inputAmount.getText().toString().length() == 0) {
                    Toast.makeText(v.getContext(), "Your amount is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(inputPurpose.getText().toString().length() == 0) {
                    Toast.makeText(v.getContext(), "Your purpose is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    transactionsDB.addTransaction(insertDate, -insertAmount, insertReason);
                    setBalance();
                    setTransactions();
                }
            }
        });
    }

    private void setBalance() {
        balance.setText(String.format("Current Balance: %s",
                transactionsDB.loadReadableBalance()));
    }

    private void setTransactions() {
        transactions.removeAllViews();
        List<String[]> list = transactionsDB.loadReadableTransactions();
        for (int i = 0; i < list.size(); i++) {
            String[] arr = list.get(i);
            addRow(arr[0], arr[1], arr[2]);
        }
    }

    private void addRow(String date, String amount, String reason) {
        TableRow row = new TableRow(getApplicationContext());
        row.addView(getColumn(date));
        row.addView(getColumn(amount));
        row.addView(getColumn(reason));
        transactions.addView(row);
    }

    private TextView getColumn(String s) {
        TextView v = new TextView(getApplicationContext());
        v.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1));
        v.setText(s);
        return v;
    }
}
