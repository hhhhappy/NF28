package com.example.rosel.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;
import java.util.Locale;

/**
 * Created by rosel on 2017/4/10.
 */

public class AddTaskActivity extends AppCompatActivity{
    Calendar myCalendar = Calendar.getInstance();
    EditText deadline;
    EditText taskName;
    CheckBox status;
    Button buttonOk;
    Button buttonCancel;
    Button buttonReset;
    RadioGroup proprity;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        /*Find View*/
        taskName = (EditText) findViewById(R.id.taskName);
        status = (CheckBox) findViewById(R.id.status);
        deadline = (EditText) findViewById(R.id.deadline);
        buttonOk = (Button)findViewById(R.id.buttonOk);
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        buttonReset = (Button)findViewById(R.id.buttonReset);
        proprity = (RadioGroup)findViewById(R.id.priority);

        /*Value default*/
        reset();

        /*AddListner*/
        buttonReset.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                reset();
            }
        });

        buttonCancel.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                Intent result = new Intent();
                setResult(RESULT_CANCELED, result);
                finish();
            }
        });

        buttonOk.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                Intent result = new Intent();
                setResult(RESULT_OK, result);
                result.putExtra("name", taskName.getText().toString());
                result.putExtra("status", status.isChecked());
                result.putExtra("priority", ((RadioButton)findViewById(proprity.getCheckedRadioButtonId())).getText());
                result.putExtra("deadline", deadline.getText().toString());
                finish();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDeadline(myCalendar.getTime());
            }
        };

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTaskActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void setDeadline(Date newDate) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        deadline.setText(sdf.format(newDate));
    }

    private void reset(){
        taskName.setText("New task");
        setDeadline(new Date());
        status.setChecked(false);
        proprity.check(R.id.radioMedium);
    }
}
