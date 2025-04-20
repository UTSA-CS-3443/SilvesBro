package edu.utsa.cs3443.silvesbro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListActivity extends AppCompatActivity {

    private TaskList taskList;
    Button addTaskButton;
    Button completeTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        addTaskButton = findViewById(R.id.addTaskButton);
        //completeTaskButton = findViewById(R.id.)
        taskList = new TaskList();
        taskList.loadTasks(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(this, taskList.getTaskList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new TaskRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                taskList.getTaskList().remove(position);
                adapter.notifyItemRemoved(position);
                //Toast.makeText(TaskListActivity.this, "Good job student!", Toast.LENGTH_SHORT).show();
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



    }
}