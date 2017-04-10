package com.example.rosel.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ADD = 1;
    private ArrayList<Task> myTasks = new ArrayList<Task>();
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // Init
        init();

        // binding buttons
        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener( new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

        // list view
        ListView mainList = (ListView)findViewById(R.id.mainList);
        taskAdapter = new TaskAdapter(this, R.layout.todo_item, myTasks);
        mainList.setAdapter(taskAdapter);
        mainList.setTextFilterEnabled(true);
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data){
        if (requestCode == REQUEST_ADD) {
            if (resultCode == RESULT_OK) {
                // Create Task
                Task task = new Task();
                task.setName(data.getStringExtra("name"));
                task.setStatus(data.getBooleanExtra("status", false));
                task.setPriority(data.getStringExtra("priority"));
                task.setDeadline(data.getStringExtra("deadline"));
                myTasks.add(task);
                // Toast
                Toast.makeText(this, "Task added:\n" + task.toString(), Toast.LENGTH_LONG).show();
                // Update listview
                taskAdapter.notifyDataSetChanged();
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.d("Main", "canceled");
            }
        }
    }

    private void init()
    {
        myTasks.add(new Task("réviser IA04", "14/04/17", false, "Medium"));
        myTasks.add(new Task("réviser NF28", "14/04/17", true, "High"));
        myTasks.add(new Task("Lingrie", "13/04/17", false, "Medium"));
    }
}