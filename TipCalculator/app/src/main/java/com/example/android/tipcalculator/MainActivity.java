package com.example.android.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    String billText;
    double billNumber;
    RadioButton tipBtn;
    int selectBtnID;
    double totalBill = 0;
    double totalTip = 0;
    float tipPercent = 0.0f;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.tip);
        menu.setDisplayUseLogoEnabled(true);

        final EditText billVal = findViewById(R.id.editText);
        final RadioGroup tipGroup = findViewById(R.id.radioGroup);
        final SeekBar tipSeek = findViewById(R.id.seekBar);
        final TextView seekText = findViewById(R.id.textView2);
        final TextView tipText = findViewById(R.id.textView_tip);
        final TextView totalText = findViewById(R.id.textView_total);


        seekText.setText(String.valueOf(tipSeek.getProgress()));

        billVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                billText = billVal.getText().toString();
                if (billText.length() == 0) {
                    billNumber = 0.0d;
                    billVal.setError("Enter Bill Total");
                    tipText.setText(getResources().getString(R.string.tip_int));
                    totalText.setText(getResources().getString(R.string.tip_int));
                } else {
                    billNumber = Double.valueOf(decimalFormat.format(Double.parseDouble(billText)));
                    selectBtnID = tipGroup.getCheckedRadioButtonId();
                    tipBtn = tipGroup.findViewById(selectBtnID);
                    if (tipBtn.getText().toString().contains("Custom"))
                        tipPercent = Float.parseFloat(seekText.getText().toString());
                    else
                        tipPercent = Float.parseFloat(tipBtn.getText().toString().split("%")[0]);
                    totalBill = setTotal(tipPercent);
                    tipText.setText(String.valueOf(totalTip));
                    totalText.setText(String.valueOf(totalBill));
                }
            }
        });

        tipGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                billText = billVal.getText().toString();
                if (billText.length() == 0) {
                    billNumber = 0.0d;
                    billVal.setError("Enter Bill Total");
                    tipText.setText(getResources().getString(R.string.tip_int));
                    totalText.setText(getResources().getString(R.string.tip_int));
                }
                selectBtnID = checkedId;
                tipBtn = tipGroup.findViewById(selectBtnID);
                if (tipBtn.getText().toString().contains("Custom"))
                    tipPercent = Float.parseFloat(seekText.getText().toString());
                else
                    tipPercent = Float.parseFloat(tipBtn.getText().toString().split("%")[0]);
                totalBill = setTotal(tipPercent);
                tipText.setText(String.valueOf(totalTip));
                totalText.setText(String.valueOf(totalBill));
            }
        });

        tipSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekText.setText(String.valueOf(progress));
                tipPercent = Float.parseFloat(seekText.getText().toString());
                totalBill = setTotal(tipPercent);
                tipText.setText(String.valueOf(totalTip));
                totalText.setText(String.valueOf(totalBill));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                billText = billVal.getText().toString();
                if (billText.length() == 0) {
                    billNumber = 0.0d;
                    billVal.setError("Enter Bill Total");
                    tipText.setText(getResources().getString(R.string.tip_int));
                    totalText.setText(getResources().getString(R.string.tip_int));
                }
                tipGroup.check(tipGroup.getChildAt(3).getId());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private double setTotal(float tipPercent) {
        totalTip = Double.valueOf(decimalFormat.format(Float.valueOf(
                decimalFormat.format(tipPercent / 100)) * billNumber));
        totalBill = Double.valueOf(decimalFormat.format(billNumber + totalTip));
        return totalBill;
    }
}
