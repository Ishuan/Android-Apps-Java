package com.example.android.taskmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditTask extends AppCompatActivity {

    private EditText editTaskTitle;
    private EditText editTaskDate;
    private EditText editTaskTime;
    private RadioGroup editPriorityGroup;
    private RadioButton editPriorityBtn;
    private Button editSaveBtn;
    int btnID;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        editTaskTitle = findViewById(R.id.editTitleText);
        editTaskDate = findViewById(R.id.editDatePicker);
        editTaskTime = findViewById(R.id.editTimePicker);
        editPriorityGroup = findViewById(R.id.editPriorityGroup);
        editSaveBtn = findViewById(R.id.editSaveBtn);

        final Calendar cal = Calendar.getInstance();

        Task taskToBeEdit = (Task) getIntent().getExtras().getSerializable(MainActivity.EDIT_REQ);

        if (getIntent().getExtras().containsKey(MainActivity.EDIT_REQ)) {

            editTaskTitle.setText(taskToBeEdit.getTaskName());
            editTaskDate.setText(taskToBeEdit.getTaskDate());
            editTaskTime.setText(taskToBeEdit.getTaskTime());

            for (int i = 0; i < editPriorityGroup.getChildCount(); i++) {
                editPriorityBtn = (RadioButton) editPriorityGroup.getChildAt(i);
                if ((editPriorityBtn.getText().toString().equalsIgnoreCase(taskToBeEdit.getTaskPriority()))) {
                    btnID = editPriorityGroup.getChildAt(i).getId();
                    editPriorityGroup.check(btnID);
                }
            }

            editSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editedIntent = new Intent();
                    String editTitle = editTaskTitle.getText().toString();
                    int editPriotity = editPriorityGroup.getCheckedRadioButtonId();
                    editPriorityBtn = editPriorityGroup.findViewById(editPriotity);
                    String editPriorityText = editPriorityBtn.getText().toString();
                    String editDate = editTaskDate.getText().toString();
                    String editTime = editTaskTime.getText().toString();
                    Task editedTask = new Task(editTitle,editDate,editTime, editPriorityText);
                    editedIntent.putExtra(MainActivity.EDIT_REQ, editedTask);
                    setResult(RESULT_OK,editedIntent);
                    finish();
                }
            });

           editTaskDate.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   int year = cal.get(Calendar.YEAR);
                   int month = cal.get(Calendar.MONTH);
                   int day = cal.get(Calendar.DAY_OF_MONTH);

                   DatePickerDialog dialog = new DatePickerDialog(EditTask.this,
                           android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,
                           year,month,day);

                   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                   dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                   dialog.show();
               }
           });

           editTaskTime.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int hour = cal.get(Calendar.HOUR);
                   int minute = cal.get(Calendar.MINUTE);

                   TimePickerDialog timeDialog = new TimePickerDialog(EditTask.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,timeSetListener,
                           hour,minute,false);
                   timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                   timeDialog.show();
               }
           });

           dateSetListener = new DatePickerDialog.OnDateSetListener() {
               @Override
               public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                   month += 1;
                   String m = String.format("%02d", month);
                   String d = String.format("%02d", dayOfMonth);
                   String dat = m + "/" + d + "/" + year;
                   editTaskDate.setText(dat);
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
                   editTaskTime.setText(h + ":" + m + " " + timeOfDay);
               }
           };
        }
    }
}