package comp3710.notetaker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText noteText, titleText;
    private Button button;
    private LinearLayout noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final NoteTakerDBOperations db = new NoteTakerDBOperations(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteText = findViewById(R.id.noteText);
        titleText = findViewById(R.id.titleText);
        button = findViewById(R.id.button);
        noteList = findViewById(R.id.noteList);

        String[] titleList = db.loadTitles();
        for (int i = 0; i < titleList.length; i++) {
            addButton(noteList, titleList[i], noteText, titleText, db);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = titleText.getText().toString();
                final String note = noteText.getText().toString();
                if (title.length() == 0) {
                    Toast toast = Toast.makeText(v.getContext(),
                            "You cannot have an empty title!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (db.noteExists(title)) {
                    AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
                    b.setTitle("Note Already Exists");
                    b.setMessage("A note with that title already exists." +
                            " Are you sure you want to overwrite it?");
                    b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int i) {
                            db.addNote(title, note);
                        }
                    });
                    b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int i) {}
                    });
                    b.show();
                }
                else {
                    db.addNote(title, note);
                    addButton(noteList, title, noteText, titleText, db);
                }
            }
        });
    }
    private void addButton(LinearLayout v, String t, EditText n, EditText e, NoteTakerDBOperations d) {
        final String title = t;
        final EditText noteText = n;
        final EditText titleText = e;
        final NoteTakerDBOperations db = d;
        Button b = new Button(this);
        b.setText(title);
        b.setTag(title);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = db.loadNote(v.getTag().toString());
                titleText.setText(title);
                noteText.setText(content);
            }
        });
        v.addView(b);
    }
}