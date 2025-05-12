package edu.utsa.cs3443.silvesbro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.utsa.cs3443.silvesbro.models.TaskList;

public class TaskListActivity extends AppCompatActivity {

    private TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Button addTaskButton = findViewById(R.id.addTaskButton);
        Button exitButton = findViewById(R.id.exitButton);
        taskList = new TaskList();
        taskList.loadTasks(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(this, taskList.getTaskList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // remove/complete task
        // finds task based on position in recycler view by using the adapter
        adapter.setOnItemClickListener(new TaskRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                taskList.getTaskList().remove(position);
                adapter.notifyItemRemoved(position);
                //this removal is only temporary so we need to rewrite the csv in internal memory
                taskList.saveTasks(TaskListActivity.this);
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity();
            }
        });

    }

    public void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}