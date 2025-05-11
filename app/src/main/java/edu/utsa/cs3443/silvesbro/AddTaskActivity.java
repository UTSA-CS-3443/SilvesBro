package edu.utsa.cs3443.silvesbro;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class AddTaskActivity extends AppCompatActivity {

    EditText inputName;
    EditText inputDate;
    private Task task;
    private TaskList taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskList = new TaskList();
        inputName = findViewById(R.id.nameInput);
        Button createTaskButton = findViewById(R.id.createTaskButton);
        Button dateButton = findViewById(R.id.taskDate);
        Button timeButton = findViewById(R.id.taskTime);

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // making sure task name isnt empty
                if (inputName.getText().toString().isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Invalid input, try again.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // random filler ID, useless
                    task = new Task("9999", inputName.getText().toString(), "", false);
                    TaskList.addTaskToCSV(AddTaskActivity.this, task);
                    Intent intent = new Intent(AddTaskActivity.this, TaskListActivity.class);
                    startActivity(intent);
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDate();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogTime();
            }
        });

    }

    // date picker
    public void openDialogDate(){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String testString = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(dayOfMonth);
                Toast.makeText(AddTaskActivity.this, testString, Toast.LENGTH_SHORT).show();
                //THIS IS WHERE YOU STORE THE DATE VALUES AND MAKE DATE WORK
            }
        }, 2025, 0, 0);

        dialog.show();
    }

    // time picker
    public void openDialogTime() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String testString = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                Toast.makeText(AddTaskActivity.this, testString, Toast.LENGTH_SHORT).show();
                //THIS IS WHERE YOU STORE THE TIME VALUES AND MAKE TIME WORK
            }
        }, 15, 0, true);

        dialog.show();

    }

}