package com.example.android.taskmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;


public class CreateTask extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    private Calendar cal = Calendar.getInstance();
    private EditText datePicker;
    private EditText timePicker;
    private EditText titleText;
    private RadioGroup priorityGroup;
    private RadioButton selectedPriority;
    private int priorityID;
    private String priorityText = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        titleText = findViewById(R.id.titleText);
        priorityGroup = findViewById(R.id.priorityGroup);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTask.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTask.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListener, hour, minute, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String m = String.format("%02d", month);
                String d = String.format("%02d", dayOfMonth);
                String dat = m + "/" + d + "/" + year;
                datePicker.setText(dat);
            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String timeOfDay = "AM";
                if (hourOfDay > 12)
                    timeOfDay = "PM";
                int updatedHour;
                if (hourOfDay > 12)
                    updatedHour = hourOfDay - 12;
                else
                    updatedHour = hourOfDay;
                String h = String.format("%02d", updatedHour);
                String m = String.format("%02d", minute);
                timePicker.setText(h + ":" + m + " " + timeOfDay);
            }
        };

        priorityID = priorityGroup.getCheckedRadioButtonId();
        priorityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                priorityID = checkedId;
            }
        });

        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = titleText.getText().toString();
                String taskDate = datePicker.getText().toString();
                String taskTime = timePicker.getText().toString();
                if (taskTitle.length() == 0) {
                    titleText.setError("Enter the Title of the Task");
                } else if (taskDate.length() == 0) {
                    datePicker.setError("Enter the Date of the Task");
                } else if (taskTime.length() == 0) {
                    timePicker.setError("Enter the Time of the Task");
                } else {
                    selectedPriority = priorityGroup.findViewById(priorityID);
                    priorityText = selectedPriority.getText().toString();
                    Intent intent = new Intent();
                    Task task = new Task(taskTitle, taskDate, taskTime, priorityText);
                    intent.putExtra(MainActivity.TASK_NAME, task);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}