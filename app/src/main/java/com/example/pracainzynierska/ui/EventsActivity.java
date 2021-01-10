package com.example.pracainzynierska.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.util.EventLog;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracainzynierska.EventsDatabaseHelper;
import com.example.pracainzynierska.LoginActivity;
import com.example.pracainzynierska.adapter.TasksItem;
import com.example.pracainzynierska.dialog.EventDialog;
import com.example.pracainzynierska.R;
import com.example.pracainzynierska.adapter.EventsAdapter;
import com.example.pracainzynierska.adapter.EventsItem;
//import com.github.sundeepk.compactcalendarview.domain.Event;
import com.example.pracainzynierska.model.EventRequest;
import com.example.pracainzynierska.model.EventResponse;
import com.example.pracainzynierska.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EventsActivity extends AppCompatActivity implements EventDialog.EventDialogListener{

    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<EventsItem> mEventsList;
    List<EventResponse> events = new ArrayList<>();
    private CalendarView mCalendarView;
    SimpleDateFormat formatter = new SimpleDateFormat("MM:dd:yyyy");
    int mDefaultColor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mDefaultColor = ContextCompat.getColor(EventsActivity.this, R.color.colorPrimary);

        mEventsList = new ArrayList<>();
        buildRecyclerView();
        getEvents();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//               removeItem();
            }
        }).attachToRecyclerView(mRecycleView);

        mCalendarView = findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month+=1;
                String date = dayOfMonth +"."+month +"."+year;
                EventDialog eventDialog = new EventDialog();
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                eventDialog.setArguments(bundle);
                eventDialog.show(getSupportFragmentManager(),"Image Dialog");
                Toast.makeText(EventsActivity.this, "" + date, Toast.LENGTH_SHORT).show();
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_events);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intentMain = new Intent(EventsActivity.this, MainActivity.class);
                        startActivity(intentMain);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.action_events:
                        break;
                    case R.id.action_tasks:
                        Intent intentTasks = new Intent(EventsActivity.this, TasksActivity.class);
                        startActivity(intentTasks);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.action_habits:
                        Intent intentHabits = new Intent(EventsActivity.this, HabitsActivity.class);
                        startActivity(intentHabits);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.action_about:
                        Intent intentAbout = new Intent(EventsActivity.this, AboutActivity.class);
                        startActivity(intentAbout);
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });


    }

    public void removeItem(){
        mEventsList.remove(0);
        mAdapter.notifyDataSetChanged();
    }


    public void buildRecyclerView() {
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EventsAdapter(EventsActivity.this, mEventsList);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);
    }


    private void getEvents(){
        Call<List<EventResponse>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getEvents(1, getAccessToken());

        call.enqueue(new Callback<List<EventResponse>>() {
            @Override
            public void onResponse(Call<List<EventResponse>> call, Response<List<EventResponse>> response) {
                events = response.body();
                events.forEach(new Consumer<EventResponse>() {
                    @Override
                    public void accept(EventResponse event) {
                        String strDate = formatter.format(event.getDate());
                        mEventsList.add(new EventsItem(event.getId(), event.getEvent_name(), strDate, event.getColor()));
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<EventResponse>> call, Throwable t) {
                Toast.makeText(EventsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addEvent(final EventRequest eventRequest){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .addEvents(eventRequest, getAccessToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("Wydarzenie zostało dodane");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


    public String getAccessToken(){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String value = "Bearer " + sharedPreferences.getString(LoginActivity.ACCESS_TOKEN, "");
        return value;
    }

}