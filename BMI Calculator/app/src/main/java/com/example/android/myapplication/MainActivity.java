package com.example.android.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private double bmi;
    private double normalWt;

    private EditText ageText;
    private EditText poundsText;
    private EditText inchText;
    private EditText ftText;
    private Button calBtn;
    private TextView bmiRange;
    private TextView colorText;
    private TextView bmiText;
    private TextView lastText;
    private DecimalFormat df = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ageText = findViewById(R.id.editText_years);
        poundsText = findViewById(R.id.editText_pounds);
        inchText = findViewById(R.id.editText_inches);
        ftText = findViewById(R.id.editText_feet);
        calBtn = findViewById(R.id.button_calculate);
        bmiRange = findViewById(R.id.BMI);
        colorText = findViewById(R.id.color);
        bmiText = findViewById(R.id.NormalBMI);
        lastText = findViewById(R.id.lastLine);

        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int ageInYears = Integer.parseInt(ageText.getText().toString());
                    int wtInPounds = Integer.parseInt(poundsText.getText().toString());
                    int htInInch = Integer.parseInt(inchText.getText().toString());
                    int htinFt = Integer.parseInt(ftText.getText().toString()) * 12;

                    if (ageInYears >= 18)
                        bmi = 703 * (wtInPounds / Math.pow(htinFt + htInInch, 2.0));
                    else
                        Toast.makeText(getApplicationContext(), "Age should be more than 18 years",
                                Toast.LENGTH_SHORT).show();

                    if (bmi < 18.5) {
                        normalWt = ((18.5 - bmi) * Math.pow((htinFt + htInInch), 2)) / 703;
                        bmiRange.setText("BMI = " + String.valueOf(df.format(bmi)));
                        colorText.setTextColor(getResources().getColor(R.color.underweightColor));
                        colorText.setText(R.string.underweight);
                        lastText.setText("You will need to gain " + String.valueOf(df.format(normalWt)) +" lbs to\n" +
                                "reach a BMI of 18.5");
                    } else if (bmi >= 18.5 && bmi < 25) {
                        bmiRange.setText("BMI = " + String.valueOf(df.format(bmi)));
                        colorText.setTextColor(getResources().getColor(R.color.normalColor));
                        colorText.setText(R.string.normal);
                        bmiText.setText(R.string.bmiRange);
                        lastText.setText(R.string.goodWork);
                    } else if (bmi >= 25 && bmi < 30) {
                        normalWt = ((bmi - 25) * Math.pow((htinFt + htInInch), 2)) / 703;
                        bmiRange.setText("BMI = " + String.valueOf(df.format(bmi)));
                        colorText.setTextColor(getResources().getColor(R.color.overweightColor));
                        colorText.setText(R.string.overweight);
                        lastText.setText("You will need to lose "+String.valueOf(df.format(normalWt))
                                + " lbs to reach a BMI of 25 ");
                    } else if (bmi >= 30) {
                        normalWt = ((bmi - 25) * Math.pow((htinFt + htInInch), 2)) / 703;
                        bmiRange.setText("BMI = " + String.valueOf(df.format(bmi)));
                        colorText.setTextColor(getResources().getColor(R.color.obeseColor));
                        colorText.setText(R.string.obese);
                        lastText.setText("You will need to lose "+String.valueOf(df.format(normalWt))+ " lbs to\n" +
                                "reach a BMI of 25 ");
                    }

                    TextView resText = findViewById(R.id.Result);
                    resText.setText(R.string.Result);

                } catch (NumberFormatException e) {
                    if (ageText.getText().toString().length() == 0)
                        ageText.setError("Age is required.");
                    else if (poundsText.getText().toString().length() == 0)
                        poundsText.setError("Weight is required.");
                    else if (inchText.getText().toString().length() == 0)
                        inchText.setError("Height (inches) is required.");
                    else if (ftText.getText().toString().length() == 0)
                        ftText.setError("Height (feet) is required.");
                }
            }

        });
    }
}

