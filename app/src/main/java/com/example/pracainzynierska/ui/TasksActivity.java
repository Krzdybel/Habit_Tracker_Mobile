package com.example.pracainzynierska.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracainzynierska.LoginActivity;
import com.example.pracainzynierska.R;
import com.example.pracainzynierska.dialog.TasksDialog;
import com.example.pracainzynierska.adapter.TasksAdapter;
import com.example.pracainzynierska.adapter.TasksItem;
import com.example.pracainzynierska.model.CurrentUserResponse;
import com.example.pracainzynierska.model.TaskRequest;
import com.example.pracainzynierska.model.TaskResponse;
import com.example.pracainzynierska.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TasksActivity extends AppCompatActivity implements TasksDialog.TasksDialogListener{

    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TasksItem> mTasksList;
    List<TaskResponse> tasks = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy");
    CurrentUserResponse currentUserResponse = new CurrentUserResponse();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mTasksList = new ArrayList<>();
        buildRecyclerView();
        getTasks();



        FloatingActionButton floatingMainButton = (FloatingActionButton) findViewById(R.id.floatingTasksButton);
        floatingMainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenDialog();
                final Date currentTime = Calendar.getInstance().getTime();
                Toast.makeText(TasksActivity.this, "To jest ta pora! "+ currentTime, Toast.LENGTH_SHORT).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int i=viewHolder.getAdapterPosition();
                Toast.makeText(TasksActivity.this, "Przesunięto element: "+ i, Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(mRecycleView);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_tasks);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intentMain = new Intent(TasksActivity.this, MainActivity.class);
                        startActivity(intentMain);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_events:
                        Intent intentEvents = new Intent(TasksActivity.this, EventsActivity.class);
                        startActivity(intentEvents);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_tasks:
                        break;
                    case R.id.action_habits:
                        Intent intentHabits = new Intent(TasksActivity.this, HabitsActivity.class);
                        startActivity(intentHabits);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_about:
                        Intent intentAbout = new Intent(TasksActivity.this, AboutActivity.class);
                        startActivity(intentAbout);
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
    }


    public void OpenDialog() {
        TasksDialog tasksDialog = new TasksDialog();
        tasksDialog.show(getSupportFragmentManager(), "Tasks Dialog");
    }

    public void buildRecyclerView() {
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TasksAdapter(TasksActivity.this, mTasksList);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    private void getTasks(){
            Call<List<TaskResponse>> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getTasks(1, getAccessToken());

            call.enqueue(new Callback<List<TaskResponse>>() {
                @Override
                public void onResponse(Call<List<TaskResponse>> call, Response<List<TaskResponse>> response) {
                    tasks = response.body();
                    tasks.forEach(new Consumer<TaskResponse>() {
                        @Override
                        public void accept(TaskResponse task) {
                            String strDate = formatter.format(task.getDate());
                            mTasksList.add(new TasksItem(task.getId(),task.getTask_text(),task.getColor(), strDate, task.isDone()));
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
                @Override
                public void onFailure(Call<List<TaskResponse>> call, Throwable t) {
                    Toast.makeText(TasksActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }


    @Override
    public void addTask(final TaskRequest taskRequest){
            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .addTasks(taskRequest, getAccessToken());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println("Zadanie zostało dodane");
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
    }

    private void getCurrentUser(){
        Call<CurrentUserResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCurrentUser(getAccessToken());

        call.enqueue(new Callback<CurrentUserResponse>() {
            @Override
            public void onResponse(Call<CurrentUserResponse> call, Response<CurrentUserResponse> response) {
                currentUserResponse = response.body();
                System.out.println("WORKS FINE "+ currentUserResponse.getUsername());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CurrentUserResponse> call, Throwable t) {
                System.out.println("DZIALA ZLE");
            }
        });
    }


    public String getAccessToken(){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String value = "Bearer " + sharedPreferences.getString(LoginActivity.ACCESS_TOKEN, "");
        return value;
    }

}