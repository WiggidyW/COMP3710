package comp3710.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;

public class MainActivity extends AppCompatActivity {
    EditText tmpC, tmpF, dstKM, dstM, wgtKG, wgtLB, volL, volG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmpC = findViewById(R.id.tmpC);
        tmpF = findViewById(R.id.tmpF);
        dstKM = findViewById(R.id.dstKM);
        dstM = findViewById(R.id.dstM);
        wgtKG = findViewById(R.id.wgtKG);
        wgtLB = findViewById(R.id.wgtLB);
        volL = findViewById(R.id.volL);
        volG = findViewById(R.id.volG);

        tmpC.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                // We check focus so that we don't trigger an endless loop off of programmatic edits.
                if (tmpC.hasFocus()) {
                    if (s.length() > 0) {
                        // For SignedNumber fields, inputting a "-" to empty EditText will break.
                        if (s.length() != 1 || s.charAt(0) != '-') {
                            tmpF.setText(CtoF(s.toString()));
                        }
                        else {
                            tmpF.setText("");
                        }
                    }
                    // If the EditText was emptied, we must empty the EditText next to it.
                    else {
                        tmpF.setText("");
                    }
                }
            }
            // We must implement the interface.
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });
        tmpF.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                // We check focus so that we don't trigger an endless loop off of programmatic edits.
                if (tmpF.hasFocus()) {
                    // For SignedNumber fields, inputting a "-" to empty EditText will break.
                    if (s.length() > 0) {
                        // For SignedNumber fields, inputting a "-" to empty EditText will break.
                        if (s.length() != 1 || s.charAt(0) != '-') {
                            tmpC.setText(FtoC(s.toString()));
                        }
                        else {
                            tmpC.setText("");
                        }
                    }
                    // If the EditText was emptied, we must empty the EditText next to it.
                    else {
                        tmpC.setText("");
                    }
                }
            }
            // We must implement the interface.
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });
        dstKM.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                // We check focus so that we don't trigger an endless loop off of programmatic edits.
                if (dstKM.hasFocus()) {
                    if (s.length() > 0) {
                        dstM.setText(KMtoM(s.toString()));
                    }
                    // If the EditText was emptied, we must empty the EditText next to it.
                    else {
                        dstM.setText("");
                    }
                }
            }
            // We must implement the interface.
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });
        dstM.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                // We check focus so that we don't trigger an endless loop off of programmatic edits.
                if (dstM.hasFocus()) {
                    if (s.length() > 0) {
                        dstKM.setText(MtoKM(s.toString()));
                    }
                    // If the EditText was emptied, we must empty the EditText next to it.
                    else {
                        dstKM.setText("");
                    }
                }
            }
            // We must implement the interface.
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });
        wgtKG.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                // We check focus so that we don't trigger an endless loop off of programmatic edits.
                if (wgtKG.hasFocus()) {
                    if (s.length() > 0) {
                        wgtLB.setText(KGtoLB(s.toString()));
                    }
                    // If the EditText was emptied, we must empty the EditText next to it.
                    else {
                        wgtLB.setText("");
                    }
                }
            }
            // We must implement the interface.
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });
        wgtLB.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                // We check focus so that we don't trigger an endless loop off of programmatic edits.
                if (wgtLB.hasFocus()) {
                    if (s.length() > 0) {
                        wgtKG.setText(LBtoKG(s.toString()));
                    }
                    // If the EditText was emptied, we must empty the EditText next to it.
                    else {
                        wgtKG.setText("");
                    }
                }
            }
            // We must implement the interface.
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });
        volL.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                // We check focus so that we don't trigger an endless loop off of programmatic edits.
                if (volL.hasFocus()) {
                    if (s.length() > 0) {
                        volG.setText(LtoG(s.toString()));
                    }
                    // If the EditText was emptied, we must empty the EditText next to it.
                    else {
                        volG.setText("");
                    }
                }
            }
            // We must implement the interface.
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });
        volG.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                // We check focus so that we don't trigger an endless loop off of programmatic edits.
                if (volG.hasFocus()) {
                    if (s.length() > 0) {
                        volL.setText(GtoL(s.toString()));
                    }
                    // If the EditText was emptied, we must empty the EditText next to it.
                    else {
                        volL.setText("");
                    }
                }
            }
            // We must implement the interface.
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void afterTextChanged(Editable s){}
        });
    }

    // All conversion rates were retrieved from Google Unit Converter.
    private String CtoF(String C) {
        double dblC = Double.parseDouble(C);
        double dblF = (dblC*9/5) + 32;
        return String.valueOf(Math.round(dblF));
    }
    private String FtoC(String F) {
        double dblF = Double.parseDouble(F);
        double dblC = (dblF - 32)*5/9;
        return String.valueOf(Math.round(dblC));
    }
    private String KMtoM(String KM) {
        double dblKM = Double.parseDouble(KM);
        double dblM = dblKM/1.60934;
        return String.valueOf(Math.round(dblM));
    }
    private String MtoKM(String M) {
        double dblM = Double.parseDouble(M);
        double dblKM = dblM*0.621371;
        return String.valueOf(Math.round(dblKM));
    }
    private String KGtoLB(String KG) {
        double dblKG = Double.parseDouble(KG);
        double dblLB = dblKG*2.20462;
        return String.valueOf(Math.round(dblLB));
    }
    private String LBtoKG(String LB) {
        double dblLB = Double.parseDouble(LB);
        double dblKG = dblLB*0.453592;
        return String.valueOf(Math.round(dblKG));
    }
    private String LtoG(String L) {
        double dblL = Double.parseDouble(L);
        double dblG = dblL*0.264172;
        return String.valueOf(Math.round(dblG));
    }
    private String GtoL(String G) {
        double dblG = Double.parseDouble(G);
        double dblL = dblG*3.78541;
        return String.valueOf(Math.round(dblL));
    }
}
