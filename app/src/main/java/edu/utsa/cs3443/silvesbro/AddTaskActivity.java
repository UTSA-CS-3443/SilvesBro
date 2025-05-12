package edu.utsa.cs3443.silvesbro;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.silvesbro.models.Task;
import edu.utsa.cs3443.silvesbro.models.TaskList;

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

    }

}