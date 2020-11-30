package com.example.studyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    LinearLayout login;
    TextInputEditText user;
    TextInputEditText pass;
    Switch teaStu;
    TextView signUp;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Connect to Firebase", Toast.LENGTH_LONG).show();

        user = findViewById(R.id.textInputUsername);
        pass = findViewById(R.id.textInputPassword);
        login = findViewById(R.id.btnLogin);
        teaStu = findViewById(R.id.swtiching);
        signUp = findViewById(R.id.signUpText);

        teaStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teaStu.isChecked()){
                    teaStu.setText("Teacher");
                }else{
                    teaStu.setText("Student");
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = teaStu.isChecked();
                String myUser;
                String myPass;
                if(check){
                    myUser = user.getText().toString();
                    myPass = pass.getText().toString();
                    if(myUser.equals("admin") && myPass.equals("admin")){
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    }else{
                        user.setError("Wrong Username or Password");
                    }
                }else{

                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSign(v);
            }
        });
    }
    private void goSign(View view){
        Intent intent = new Intent(view.getContext(), SignUpPage.class);
        startActivity(intent);
    }
}