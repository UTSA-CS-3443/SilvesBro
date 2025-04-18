package edu.utsa.cs3443.silvesbro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import edu.utsa.cs3443.silvesbro.model.TaskList;
import edu.utsa.cs3443.silvesbro.model.StudyTask;
public class Task_RecyclerViewAdapter extends RecyclerView.Adapter<Task_RecyclerViewAdapter.TaskViewHolder> {
    private Context context;
    private ArrayList<StudyTask> taskList;

    public Task_RecyclerViewAdapter(Context context, ArrayList<StudyTask> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       LayoutInflater inflater = LayoutInflater.from(context);
       View view = inflater.inflate(R.layout.task_view, parent, false);
       return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int pos) {
        StudyTask task = taskList.get(pos);

        holder.nameView.setText(task.getTaskName());
        holder.dateView.setText(task.getDueDateStr());
    }

    @Override
    public int getItemCount() {
        return taskList.size();

    }
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, dateView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.taskName);
            dateView = itemView.findViewById(R.id.dueDate);
        }


    }
}
