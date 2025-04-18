package edu.utsa.cs3443.silvesbro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListActivity extends AppCompatActivity {

    private TaskList taskList;
    Button addTaskButton;
    Button completeTaskButton;
    Button deleteTaskButton;

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

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });



    }
}