package com.example.android.taskmanager;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE = 101;
    private final int EDIT_REQ_CODE = 102;
    private LinkedList<Task> taskLinkedList = new LinkedList<>();
    public static final String TASK_NAME = "taskName";
    public static final String EDIT_REQ = "editTask";

    private TextView taskTitle;
    private TextView taskDateText;
    private TextView taskTimeText;
    private TextView footerMsg;
    private TextView taskPriority;
    private ImageButton goLeftBtn;
    private ImageButton goRightBtn;
    private ImageButton goFirstBtn;
    private ImageButton goLastBtn;
    private ImageButton deleteTask;
    private ImageButton editTask;

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy", Locale.US);
    final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aaa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        footerMsg = findViewById(R.id.taskCount);
        taskTitle = findViewById(R.id.taskTitle);
        taskPriority = findViewById(R.id.taskPriority);
        goLeftBtn = findViewById(R.id.goLeftBtn);
        goRightBtn = findViewById(R.id.goRightBtn);
        goLastBtn = findViewById(R.id.goLastBtn);
        goFirstBtn = findViewById(R.id.goFirstBtn);
        deleteTask = findViewById(R.id.deleteBtn);
        editTask = findViewById(R.id.editBtn);
        taskTimeText = findViewById(R.id.taskTime);
        taskDateText = findViewById(R.id.taskDate);

        findViewById(R.id.addTaskBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTask.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });

        goLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = Integer.parseInt(footerMsg.getText().toString().split(" ")[1]);
                if (counter == 1)
                    Toast.makeText(MainActivity.this, "You are on the First Task",
                            Toast.LENGTH_SHORT).show();
                else {
                    counter--;
                    getTaskDetails(counter - 1);
                    footerMsg.setText("Task " + counter + " of " + taskLinkedList.size());
                }
            }
        });

        goRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = Integer.parseInt(footerMsg.getText().toString().split(" ")[1]);
                if (counter == taskLinkedList.size())
                    Toast.makeText(MainActivity.this, "You are on the Last Task",
                            Toast.LENGTH_SHORT).show();
                else {
                    counter++;
                    getTaskDetails(counter - 1);
                    footerMsg.setText("Task " + counter + " of " + taskLinkedList.size());
                }
            }
        });

        goFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTaskDetails(0);
                footerMsg.setText("Task 1 of " + taskLinkedList.size());
            }
        });

        goLastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTaskDetails(taskLinkedList.size() - 1);
                footerMsg.setText("Task " + taskLinkedList.size() + " of " + taskLinkedList.size());
            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int taskToBeDeleted = Integer.parseInt(footerMsg.getText().toString().
                        split(" ")[1]);
                if (taskLinkedList.size() == 1) {
                    setControlsInvisible();
                    taskLinkedList.remove(taskLinkedList.getFirst());
                    footerMsg.setText(R.string.numberTask);
                } else {
                    taskLinkedList.remove(taskToBeDeleted - 1);
                    footerMsg.setText("Task 1 of " + taskLinkedList.size());
                    getTaskDetails(0);
                }
            }
        });

        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int taskToEdit = Integer.parseInt(footerMsg.getText().toString().split(" ")[1]);
                Intent editIntent = new Intent(MainActivity.this,EditTask.class);
                editIntent.putExtra(EDIT_REQ,taskLinkedList.get(taskToEdit-1));
                taskLinkedList.remove(taskToEdit-1);
                startActivityForResult(editIntent,EDIT_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Task task = null;
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                setControlsVisible();
                if (data.getExtras().containsKey(TASK_NAME)) {
                    task = (Task) data.getExtras().get(TASK_NAME);
                    taskLinkedList.add(task);
                    Collections.sort(taskLinkedList, new Comparator<Task>() {
                        @Override
                        public int compare(Task o1, Task o2) {
                            Date date1 = null;
                            Date date2 = null;
                            Date time1 = null;
                            Date time2 = null;
                            try {
                                date1 = simpleDateFormat.parse(o1.getTaskDate());
                                date2 = simpleDateFormat.parse(o2.getTaskDate());
                                time1 = simpleTimeFormat.parse(o1.getTaskTime());
                                time2 = simpleTimeFormat.parse(o2.getTaskTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return (date1.compareTo(date2) == 0 ? time1.compareTo(time2) : date1.compareTo(date2));
                        }
                    });

                    int indexOfNewTask = taskLinkedList.indexOf(task);
                    getTaskDetails(indexOfNewTask);
                    footerMsg.setText("Task "+(indexOfNewTask+1)+" of "+taskLinkedList.size());
                }
            }
        } else if(requestCode == EDIT_REQ_CODE){
            if(resultCode == RESULT_OK){
                if(data.getExtras().containsKey(EDIT_REQ)){
                    Task newEditedTask = (Task) data.getExtras().get(EDIT_REQ);
                    taskLinkedList.add(newEditedTask);
                    Collections.sort(taskLinkedList, new Comparator<Task>() {
                        @Override
                        public int compare(Task o1, Task o2) {
                            Date date1 = null;
                            Date date2 = null;
                            Date time1 = null;
                            Date time2 = null;
                            try {
                                date1 = simpleDateFormat.parse(o1.getTaskDate());
                                date2 = simpleDateFormat.parse(o2.getTaskDate());
                                time1 = simpleTimeFormat.parse(o1.getTaskTime());
                                time2 = simpleTimeFormat.parse(o2.getTaskTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return (date1.compareTo(date2) == 0 ? time1.compareTo(time2) : date1.compareTo(date2));
                        }
                    });

                    int indexOfNewTask = taskLinkedList.indexOf(newEditedTask);
                    getTaskDetails(indexOfNewTask);
                    footerMsg.setText("Task "+(indexOfNewTask+1)+" of "+taskLinkedList.size());
                }
            }
        }
    }

    public void setControlsVisible() {
        taskTitle.setVisibility(View.VISIBLE);
        taskPriority.setVisibility(View.VISIBLE);
        taskDateText.setVisibility(View.VISIBLE);
        taskTimeText.setVisibility(View.VISIBLE);
        goLastBtn.setVisibility(View.VISIBLE);
        goLeftBtn.setVisibility(View.VISIBLE);
        goRightBtn.setVisibility(View.VISIBLE);
        goFirstBtn.setVisibility(View.VISIBLE);
        deleteTask.setVisibility(View.VISIBLE);
        editTask.setVisibility(View.VISIBLE);
    }

    public void setControlsInvisible() {
        taskTitle.setVisibility(View.INVISIBLE);
        taskPriority.setVisibility(View.INVISIBLE);
        taskDateText.setVisibility(View.INVISIBLE);
        taskTimeText.setVisibility(View.INVISIBLE);
        goLastBtn.setVisibility(View.INVISIBLE);
        goLeftBtn.setVisibility(View.INVISIBLE);
        goRightBtn.setVisibility(View.INVISIBLE);
        goFirstBtn.setVisibility(View.INVISIBLE);
        deleteTask.setVisibility(View.INVISIBLE);
        editTask.setVisibility(View.INVISIBLE);
    }

    public void getTaskDetails(int index) {
        taskTitle.setText(taskLinkedList.get(index).getTaskName());
        taskPriority.setText(taskLinkedList.get(index).getTaskPriority());
        taskDateText.setText(taskLinkedList.get(index).getTaskDate());
        taskTimeText.setText(taskLinkedList.get(index).getTaskTime());
    }
}