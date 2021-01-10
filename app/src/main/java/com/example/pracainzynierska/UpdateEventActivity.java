package com.example.pracainzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pracainzynierska.ui.EventsActivity;
import com.example.pracainzynierska.ui.TasksActivity;

public class UpdateEventActivity extends AppCompatActivity {

    public EditText name_input, desc_input, date_input;
    public Button mButtonUpdate, mButtonDelete;
    public String id,name,desc,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        name_input = findViewById(R.id.event_name_update);
        desc_input = findViewById(R.id.event_desc_update);
        date_input = findViewById(R.id.event_date_update);
        mButtonUpdate = findViewById(R.id.button_update);
        mButtonDelete = findViewById(R.id.button_delete_event);
        getAndSetIntentData();

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateEventActivity.this, "UPDATE TO POS.: " + id, Toast.LENGTH_SHORT).show();
                EventsDatabaseHelper eventDB = new EventsDatabaseHelper(UpdateEventActivity.this);
                name=name_input.getText().toString().trim();
                desc=desc_input.getText().toString().trim();
                date=date_input.getText().toString().trim();
                eventDB.updateData(id,name,desc,date);
                Intent back = new Intent(UpdateEventActivity.this, EventsActivity.class);
                startActivity(back);
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsDatabaseHelper eventDB = new EventsDatabaseHelper(UpdateEventActivity.this);
                eventDB.deleteOneRow(id);
                Intent back = new Intent(UpdateEventActivity.this, EventsActivity.class);
                startActivity(back);
            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("desc") && getIntent().hasExtra("date")) {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            desc = getIntent().getStringExtra("desc");
            date = getIntent().getStringExtra("date");

            name_input.setText(name);
            desc_input.setText(desc);
            date_input.setText(date);
        } else {
            Toast.makeText(this, "No data. ", Toast.LENGTH_SHORT).show();
        }
    }
}
