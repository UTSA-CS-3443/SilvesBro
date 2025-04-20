package edu.utsa.cs3443.silvesbro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    Button button;
    Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        inputName = findViewById(R.id.nameInput);
        button = findViewById(R.id.createTaskButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // random filler ID
                task = new Task("9999", inputName.getText().toString(), "", false);
                writeTaskToCSV(AddTaskActivity.this, task);
                Intent intent = new Intent(AddTaskActivity.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

    }

    public void writeTaskToCSV(Context context, Task task) throws RuntimeException  {
        try { // MODE_APPEND TO APPEND OR MODE_PRIVATE TO WRITE ?
            OutputStream outputStream = context.openFileOutput("tasks.csv", Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            //writer.write("name,total_study_time_minutes,mountain_dew_count,happiness_level,is_music_on,is_sound_on,is_swagger_mode\n");
            writer.write(String.format("%s,%s,%s,%b\n",
                    task.getId(), task.getName(), task.getDueDate(), task.getIsCompleted()));

            writer.close();
            outputStreamWriter.close();
            outputStream.close();
        } catch (IOException e) {
            Log.e("AddTaskActivity", "Failed to save profile: " + e.getMessage());
        }
    }

}