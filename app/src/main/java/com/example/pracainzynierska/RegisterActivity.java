package com.example.pracainzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pracainzynierska.model.User;
import com.example.pracainzynierska.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RegisterActivity extends AppCompatActivity {

    private ImageView mTextZarejestruj;
    private EditText mEditImie, mEditNazwisko, mEditLoginR, mEditPassowordR, mEditCnfPassowrdR, mEditEmailR;
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$");
    //min (1 cyfra, mala litera, wielka litera, specjalny znak, 0 spacji, 8 znakow)

    public static final Pattern LOGIN_PATTERN = Pattern.compile("[A-Z][a-zA-Z][^#&<>\"~;$^%{}?]{3,20}$");
    public static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][a-zA-Z]{3,45}$");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditImie = findViewById(R.id.editImie);
        mEditNazwisko = findViewById(R.id.editNazwisko);
        mEditLoginR = findViewById(R.id.editLoginR);
        mEditPassowordR = findViewById(R.id.editPasswordR);
        mEditCnfPassowrdR = findViewById(R.id.editCnfPasswordR);
        mEditEmailR = findViewById(R.id.editEmailR);


        mTextZarejestruj = findViewById(R.id.textZarejestruj);
        mTextZarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = mEditImie.getText().toString().trim();
                String last_name = mEditNazwisko.getText().toString().trim();
                String username = mEditLoginR.getText().toString().trim();
                String password = mEditPassowordR.getText().toString().trim();
                String email = mEditEmailR.getText().toString().trim();

                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);

                boolean validateN = validateName();
                boolean validateS = validateSurname();
                boolean validateE = validateEmail();
                boolean validateP = validatePassword();
                boolean validateCnfP = validateCnfPassword();
                boolean validateL = validateLogin();
                if(validateE && validateP && validateCnfP && validateL && validateN && validateS){

                    User user = new User(first_name, last_name, username, email, password);
                    register(user);
                    startActivity(intentLogin);

                }
            }
        });

    }

    private void register(User user) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(RegisterActivity.this, "Zarejestrowano pomyslnie", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    private boolean validateName(){
        String name = this.mEditImie.getText().toString().trim();

        if(name.isEmpty()){
            mEditImie.setError("Pole nie moze byc puste");
            return false;
        } else if(!NAME_PATTERN.matcher(name).matches()){
            mEditImie.setError("Niepoprawne imie");
            return false;
        } else{
            return true;
        }
    }

    private boolean validateSurname(){
        String surname = this.mEditNazwisko.getText().toString().trim();

        if(surname.isEmpty()){
            mEditNazwisko.setError("Pole nie może być puste");
            return false;
        } else if(!NAME_PATTERN.matcher(surname).matches()){
            mEditNazwisko.setError("Niepoprawne nazwisko");
            return false;
        }
        else{
            return true;
        }
    }

    private boolean validateLogin(){
        String login = this.mEditLoginR.getText().toString().trim();

        if(login.isEmpty()){
            mEditLoginR.setError("Pole nie może być puste");
            return false;
        } else if(!LOGIN_PATTERN.matcher(login).matches()){
            mEditLoginR.setError("Niepoprawna nazwa użytkownika");
            return false;
        }
        else{
            return true;
        }
    }

    private boolean validatePassword(){
        String password = this.mEditPassowordR.getText().toString().trim();
        String cnfpassword = this.mEditCnfPassowrdR.getText().toString().trim();

        if(password.isEmpty()){
            mEditPassowordR.setError("Pole nie moze być puste");
            return false;
        } else if(!cnfpassword.equals(password)){
            mEditPassowordR.setError("Hasła róznią się");
            mEditCnfPassowrdR.setError("Hasła różnią się");
            return false;
        } else if(!PASSWORD_PATTERN.matcher(password).matches()){
            mEditPassowordR.setError("Niepoprawne hasło");
            return false;
        } else{
            return true;
        }
    }

    private boolean validateCnfPassword(){
        String cnfpassword = this.mEditCnfPassowrdR.getText().toString().trim();

        if(cnfpassword.isEmpty()){
            mEditCnfPassowrdR.setError("Pole nie moze być puste");
            return false;
        } else{
            return true;
        }
    }

    private boolean validateEmail(){

        String email = this.mEditEmailR.getText().toString().trim();

        if(email.isEmpty()){
            mEditEmailR.setError("Pole nie może być puste");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEditEmailR.setError("Niepoprawny adres email");
            return false;
        } else{
            mEditEmailR.setError(null);
            return true;
        }
    }

}