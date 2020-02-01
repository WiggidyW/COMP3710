package edu.auburn.comp3710.tempconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.Gravity;
import android.widget.Toast;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    EditText Fahrenheit, Celsius;
    Button Convert, Clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fahrenheit = findViewById(R.id.Fahrenheit);
        Celsius = findViewById(R.id.Celsius);

        Convert = findViewById(R.id.Convert);
        Clear = findViewById(R.id.Clear);

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fahrenheit.setText("");
                Celsius.setText("");
            }
        });

        Convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strF = Fahrenheit.getText().toString();
                String strC = Celsius.getText().toString();

                if (strF.length() == 0 && strC.length() == 0) {
                    Toast toast = Toast.makeText(v.getContext(),
                            "You have to input a temperature!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 350);
                    toast.show();
                }

                else if (strF.length() != 0 && strC.length() != 0) {
                    Toast toast = Toast.makeText(v.getContext(),
                            "You can only input one temperature!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 350);
                    toast.show();
                }

                else {
                    if (strF.length() == 0) {
                        Double dblC = Double.parseDouble(strC);
                        if (dblC > 537 || dblC < -537) {
                            Toast toast = Toast.makeText(v.getContext(),
                                    "Celsius can only be from -537 to 537!",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 350);
                            toast.show();
                        }
                        else {
                            Double dblF = (dblC*9/5) + 32;
                            String val = String.valueOf(Math.round(dblF));
                            Fahrenheit.setText(val);
                        }
                    }
                    else if (strC.length() == 0) {
                        Double dblF = Double.parseDouble(strF);
                        if (dblF > 999 || dblF < -999) {
                            Toast toast = Toast.makeText(v.getContext(),
                                    "Fahrenheit can only be from -999 to 999!",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 350);
                            toast.show();
                        }
                        else {
                            Double dblC = (dblF - 32)*5/9;
                            String val = String.valueOf(Math.round(dblC));
                            Celsius.setText(val);
                        }
                    }
                }
            }
        });
    }
}
