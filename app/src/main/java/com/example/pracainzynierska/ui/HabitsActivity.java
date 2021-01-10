package com.example.pracainzynierska.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracainzynierska.LoginActivity;
import com.example.pracainzynierska.R;
import com.example.pracainzynierska.adapter.HabitsAdapter;
import com.example.pracainzynierska.adapter.HabitsItem;
import com.example.pracainzynierska.dialog.HabitsDialog;
import com.example.pracainzynierska.model.HabitRequest;
import com.example.pracainzynierska.model.HabitResponse;
import com.example.pracainzynierska.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitsActivity extends AppCompatActivity implements HabitsDialog.HabitsDialogListener {

    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<HabitsItem> mHabitsList;
    List<HabitResponse> habits = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        mHabitsList = new ArrayList<>();
        buildRecyclerView();
        getHabits();


        FloatingActionButton floatingMainButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingMainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenDialog();
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_habits);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intentMain = new Intent(HabitsActivity.this, MainActivity.class);
                        startActivity(intentMain);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_events:
                        Intent intentEvents = new Intent(HabitsActivity.this, EventsActivity.class);
                        startActivity(intentEvents);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_tasks:
                        Intent intentTasks = new Intent(HabitsActivity.this, TasksActivity.class);
                        startActivity(intentTasks);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_habits:
                        break;
                    case R.id.action_about:
                        Intent intentAbout = new Intent(HabitsActivity.this, AboutActivity.class);
                        startActivity(intentAbout);
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });

    }

    public void OpenDialog() {
        HabitsDialog habitsDialog = new HabitsDialog();
        habitsDialog.show(getSupportFragmentManager(), "Habits Dialog");
    }

    public void buildRecyclerView() {
        mRecycleView = findViewById(R.id.recyclerViewHabits);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HabitsAdapter(HabitsActivity.this, mHabitsList);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    private void getHabits(){
        Call<List<HabitResponse>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getHabits(1, getAccessToken());

        call.enqueue(new Callback<List<HabitResponse>>() {
            @Override
            public void onResponse(Call<List<HabitResponse>> call, Response<List<HabitResponse>> response) {
                habits = response.body();
                habits.forEach(new Consumer<HabitResponse>() {
                    @Override
                    public void accept(HabitResponse habit) {
                        System.out.println("HABIT ZROBIONY????"+habit.isDone());
                        mHabitsList.add(new HabitsItem(habit.getId(),habit.getHabit_text(),habit.getColor(), habit.getIcon(),habit.isDone()));
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<HabitResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void addHabit(HabitRequest habitRequest) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .addHabits(habitRequest, getAccessToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("Nawyk został dodany");
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
