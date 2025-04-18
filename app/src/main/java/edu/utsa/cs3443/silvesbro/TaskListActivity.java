package edu.utsa.cs3443.silvesbro;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import edu.utsa.cs3443.silvesbro.model.StudyTask;
import edu.utsa.cs3443.silvesbro.model.TaskList;


public class TaskListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Task_RecyclerViewAdapter adapter;
    private ArrayList<StudyTask> taskList = new ArrayList<>();
    private TaskList taskListManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        taskListManager = new TaskList();

        recyclerView = findViewById(R.id.TaskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Task_RecyclerViewAdapter(this, taskList);
        recyclerView.setAdapter(adapter);

        try {
            loadTasks();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading tasks.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTasks() throws IOException {
        taskListManager.loadFromCSV(this);

        taskList.clear();
        taskList.addAll(taskListManager.getTasks());

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload tasks when coming back to the activity
        try {
            loadTasks();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading tasks.", Toast.LENGTH_SHORT).show();
        }
    }

}
