package com.example.pracainzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pracainzynierska.adapter.TasksAdapter;
import com.example.pracainzynierska.ui.TasksActivity;
import com.google.android.gms.tasks.Tasks;

public class UpdateActivity extends AppCompatActivity {

    public EditText name_input, desc_input, date_input;
    public Button mButtonUpdate, mButtonDelete;
    public String id,name,desc,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        name_input = findViewById(R.id.task_name2);
        desc_input = findViewById(R.id.task_desc2);
        date_input = findViewById(R.id.task_date2);
        mButtonUpdate = findViewById(R.id.button_update);
        mButtonDelete = findViewById(R.id.button_delete);
        getAndSetIntentData();

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateActivity.this, "UPDATE TO POS.: " + id, Toast.LENGTH_SHORT).show();
                TasksDatabaseHelper taskDB = new TasksDatabaseHelper(UpdateActivity.this);
                name=name_input.getText().toString().trim();
                desc=desc_input.getText().toString().trim();
                date=date_input.getText().toString().trim();
                taskDB.updateData(id,name,desc,date);
                Intent back = new Intent(UpdateActivity.this, TasksActivity.class);
                startActivity(back);
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksDatabaseHelper taskDB = new TasksDatabaseHelper(UpdateActivity.this);
                taskDB.deleteOneRow(id);
                Intent back = new Intent(UpdateActivity.this, TasksActivity.class);
                startActivity(back);
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("desc") && getIntent().hasExtra("date")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            desc = getIntent().getStringExtra("desc");
            date = getIntent().getStringExtra("date");

            name_input.setText(name);
            desc_input.setText(desc);
            date_input.setText(date);
        }
        else{
            Toast.makeText(this, "No data. ", Toast.LENGTH_SHORT).show();
        }
    }
}
