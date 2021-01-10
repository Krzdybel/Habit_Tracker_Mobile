package com.example.pracainzynierska.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.dialog.MainDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Random;

import okhttp3.internal.Util;


public class MainActivity extends AppCompatActivity{

    GoogleSignInClient mGoogleSignInClient;
    HorizontalBarChart barChart, mChart;
    private Spinner spinner;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        mInfoView = findViewById(R.id.infoView);
//        mTutorialTextView = findViewById(R.id.tutorialTextView);
//        mTutorialTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OpenDialog();
//            }
//        });

        barChart = findViewById(R.id.barChart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(3f,1));
        barEntries.add(new BarEntry(15f,0));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Akutalna seria");
        barDataSet.setBarSpacePercent(50f);

        ArrayList<String> dates = new ArrayList<>();
        dates.add("Najlepsza seria");
        dates.add("Aktualna seria");


       BarData barData = new BarData(dates, barDataSet);
       barChart.setData(barData);
       barChart.animateY(1000);
       barChart.setDescription(null);
       barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
       barChart.getAxisLeft().setAxisMinValue(0f);
       barChart.getAxisRight().setDrawLabels(false);
       barChart.getAxisRight().setDrawGridLines(false);
       barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

       spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.habits));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        break;
                    case R.id.action_events:
                        Intent intentEvents = new Intent(MainActivity.this, EventsActivity.class);
                        startActivity(intentEvents);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_tasks:
                        Intent intentTasks = new Intent(MainActivity.this, TasksActivity.class);
                        startActivity(intentTasks);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_habits:
                        Intent intentHabits = new Intent(MainActivity.this, HabitsActivity.class);
                        startActivity(intentHabits);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_about:
                        Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intentAbout);
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    public void OpenDialog(){
        MainDialog mainDialog = new MainDialog();
        mainDialog.show(getSupportFragmentManager(),"Image Dialog");
    }



}
