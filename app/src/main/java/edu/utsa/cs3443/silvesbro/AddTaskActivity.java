package edu.utsa.cs3443.silvesbro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import edu.utsa.cs3443.silvesbro.model.StudyTask;
import edu.utsa.cs3443.silvesbro.model.TaskList;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, dueDateEditText;
    private Button saveTaskButton;
    private TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        taskNameEditText = findViewById(R.id.taskNameEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        saveTaskButton = findViewById(R.id.saveTaskButton);


        taskList = new TaskList();


        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get task name and due date
                String taskName = taskNameEditText.getText().toString();
                String dueDateString = dueDateEditText.getText().toString();


                if (taskName.isEmpty() || dueDateString.isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Please fill out both fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date dueDate = null;
                try {
                    dueDate = sdf.parse(dueDateString);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddTaskActivity.this, "Invalid date format. Please use yyyy-MM-dd", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new task with default priority "Normal"
                String taskId = String.valueOf(System.currentTimeMillis()); // Using current time as a unique ID
                StudyTask newTask = new StudyTask(taskId, taskName, dueDateString, false, "Normal");


                try {
                    taskList.addTask(newTask, AddTaskActivity.this);
                    Toast.makeText(AddTaskActivity.this, "Task Added!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddTaskActivity.this, "Error saving task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}