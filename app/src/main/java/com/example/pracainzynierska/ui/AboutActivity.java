package com.example.pracainzynierska.ui;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.example.pracainzynierska.ChangeIntent;
import com.example.pracainzynierska.LoginActivity;
import com.example.pracainzynierska.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutActivity extends AppCompatActivity {


    GoogleSignInClient mGoogleSignInClient;
    private Button mLogout, mChange;
    private ImageView aboutImageView;
    private TextView aboutImieNazwisko, aboutEmail;

    private GoogleApiClient googleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mLogout = findViewById(R.id.logout);
        mChange = findViewById(R.id.change);
        aboutImageView = findViewById(R.id.about_imageView);
        aboutImieNazwisko = findViewById(R.id.about_imie_nazwisko);
        aboutEmail = findViewById(R.id.about_email);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "Wylogowanie", Toast.LENGTH_SHORT).show();
                signOut();

            }
        });

        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeIntent = new Intent(AboutActivity.this, ChangeIntent.class);
                startActivity(changeIntent);
            }
        });



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_about);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intentMain = new Intent(AboutActivity.this, MainActivity.class);
                        startActivity(intentMain);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_events:
                        Intent intentEvents = new Intent(AboutActivity.this, EventsActivity.class);
                        startActivity(intentEvents);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_tasks:
                        Intent intentTasks = new Intent(AboutActivity.this, TasksActivity.class);
                        startActivity(intentTasks);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_habits:
                        Intent intentHabits = new Intent(AboutActivity.this, HabitsActivity.class);
                        startActivity(intentHabits);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_about:
                        break;
                }
                return true;
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();

            aboutImieNazwisko.setText(personName);
            aboutEmail.setText(personEmail);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent mainIntent = new Intent(AboutActivity.this, LoginActivity.class);
                        startActivity(mainIntent);
                    }
                });
    }




}