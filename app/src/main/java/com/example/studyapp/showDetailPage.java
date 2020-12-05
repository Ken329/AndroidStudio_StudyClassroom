package com.example.studyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class showDetailPage extends AppCompatActivity {
    TextView username;
    TextView password;
    TextView phone;
    TextView sem;

    String user;
    String pass;
    String phn;
    String sems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_page);

        username = findViewById(R.id.detailUsername);
        password = findViewById(R.id.detailPassword);
        phone = findViewById(R.id.detailPhone);
        sem = findViewById(R.id.detailSemester);

        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        String pass = intent.getStringExtra("password");
        String phn = intent.getStringExtra("phone");
        String sems = intent.getStringExtra("sem");
        username.setText(user);
        password.setText(pass);
        phone.setText(phn);
        sem.setText(sems);
    }
}