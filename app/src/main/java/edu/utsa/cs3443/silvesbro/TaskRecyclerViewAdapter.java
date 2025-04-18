package edu.utsa.cs3443.silvesbro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.utsa.cs3443.silvesbro.Task;

/**
 * This class connects our data from EnclosureActivity to the recycler XML file and formats it on the
 * screen neatly
 *
 * @author sgx453
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Task> taskList;

    /**
     * constructor for the adapter class
     * @param context, context for EnclosureActivity
     * @param taskList, task arraylist
     */
    public TaskRecyclerViewAdapter(Context context, ArrayList<Task> taskList){
        this.context = context;
        this.taskList = taskList;
    }

    /**
     * This function inflates the layout, giving a look to our rows
     *
     * @param parent, The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType, The view type of the new View.
     *
     * @return new Dino_RecyclerViewAdapter.MyViewHolder(view), the inflated layout
     */
    @NonNull
    @Override
    public TaskRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_task_recycler, parent, false);
        return new TaskRecyclerViewAdapter.MyViewHolder(view);
    }

    /**
     * this function assigns values to the views we created in our recycler layout file based on
     * the position of the recycler view
     *
     * @param holder, The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position, The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigning values to the views we created in the recycler_view_row layout file
        //based on the position of the recycler view

        String nameString = "" + taskList.get(position).getName();
        String dueDateString = "" + taskList.get(position).getDueDate();

        holder.name.setText(taskList.get(position).getName());
        holder.dueDate.setText(dueDateString);
        //get image
        //String image = dinos.get(position).getName();
        //image = image.toLowerCase();
        //int resourceID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
        //holder.imageView.setImageResource(resourceID);


    }

    /**
     * this function determines the number of items we want displayed on screen
     *
     * @return dinos.size(), size of dinosaurs arraylist
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /**
     * MyViewHolder grabs the views from our recycler layout file, similar to onCreate
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView name, dueDate;

        /**
         * constructor for MyViewHolder class
         * @param itemView, the display of an individual dinosaur
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.taskName);
            dueDate = itemView.findViewById(R.id.taskDueDate);
        }
    }
}
