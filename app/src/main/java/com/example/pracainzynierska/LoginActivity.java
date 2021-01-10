package com.example.pracainzynierska;

/*import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private TextView _textZarejestruj;
    private ImageView mImageView, signIn;
    private static String URL_LOGIN = "http://192.168.1.37/PracaDyplomowa/login.php";
    SessionManager sessionManager;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.editLogin);
        mPassword = findViewById(R.id.editPassword);
        mImageView = findViewById(R.id.textZaloguj);
        _textZarejestruj = findViewById(R.id.textZarejestruj);
        signIn = findViewById(R.id.sign_in_button);


        _textZarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                closeKeyboard();


                if(!email.isEmpty() || !password.isEmpty()){
                    Login(email, password);

                } else{
                    mEmail.setError("Prosze podać login");
                    mPassword.setError("Prosze podac haslo");
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void Login(final String email, final String password){

        //_progressLogin.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String email = object.getString("email").trim();
                                    String name = object.getString("name").trim();

                                    sessionManager.createSession(name,email);

                                    Toast.makeText(LoginActivity.this, "Logowanie powiodło się \nName: " + name + " Email: " + email, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    finish();
                                }

                            }

                            else if(success.equals(null)){
                                Toast.makeText(LoginActivity.this, " ", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(LoginActivity.this, "Niepoprawne hasło", Toast.LENGTH_SHORT).show();
                                //_progressLogin.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Niepoprawny login" + e.toString(), Toast.LENGTH_SHORT).show(); // Wprowadzić kontrolę istniejącego emaila przy rejestracji
                            //_progressLogin.setVisibility(View.INVISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Nie można się zalogować " + error.toString(), Toast.LENGTH_SHORT).show();
                        //_progressLogin.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intentMain);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }


    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            Intent signInToMain = new Intent("name.student.MainActivity");
            startActivity(signInToMain);
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

        }
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

} =================================================== */



import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pracainzynierska.model.LoginRequest;
import com.example.pracainzynierska.model.LoginResponse;
import com.example.pracainzynierska.retrofit.RetrofitClient;
import com.example.pracainzynierska.ui.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private TextView _textZarejestruj;
    private ImageView _textZaloguj, signIn;
    private static String URL_LOGIN = "https://habt-tracker.herokuapp.com/api/auth/signup";
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ACCESS_TOKEN = "accesstoken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.editLogin);
        mPassword = findViewById(R.id.editPassword);
        _textZaloguj = findViewById(R.id.textZaloguj);
        _textZarejestruj = findViewById(R.id.textZarejestruj);

        signIn = findViewById(R.id.sign_in_button);



        _textZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameOrEmail = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                closeKeyboard();

                if(!usernameOrEmail.isEmpty() || !password.isEmpty()){
                    LoginRequest loginRequest = new LoginRequest(usernameOrEmail,password);
                    Login(loginRequest);


                } else{
                    mEmail.setError("Prosze podać login");
                    mPassword.setError("Prosze podac haslo");
                }

            }
        });

        _textZarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    /*@Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    } */

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            Intent signInToMain = new Intent("name.student.MainActivity");
            startActivity(signInToMain);
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

        }
    }


    private void Login(LoginRequest loginRequest){

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.code() == 401){
                    Toast.makeText(LoginActivity.this, "Niepoprawne dane logowania", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(response.isSuccessful()){
                        response.body();
                        String accessToken = response.body().getAccessToken();
                        saveAccessToken(accessToken);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveAccessToken(String accessToken){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.commit();
    }


    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}