package com.example.rosel.todolist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    private static LayoutInflater inflater = null;

    // Constructor
    public TaskAdapter (Context context, int textViewResourceId, ArrayList<Task> taskArray) {
        super(context, textViewResourceId, taskArray);
        try {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            Log.e("TaskAdapter", e.getMessage().toString());
        }
    }

    // View
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task tsk = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.todo_item, parent, false);
        }

        // Lookup view for data population
        TextView tskName = (TextView) convertView.findViewById(R.id.taskName);
        TextView tskStatus = (TextView) convertView.findViewById(R.id.status);
        TextView tskPriority = (TextView) convertView.findViewById(R.id.priority);
        TextView tskDeadline = (TextView) convertView.findViewById(R.id.deadline);

        // Populate the data into the template view using the data object
        tskName.setText(tsk.getName());
        tskStatus.setText(tsk.getStatus());
        tskPriority.setText(tsk.getPriority());
        tskDeadline.setText(tsk.getDeadline());

        // Return the completed view to render on screen
        return convertView;
    }
}