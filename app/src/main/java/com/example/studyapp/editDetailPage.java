package com.example.studyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editDetailPage extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText phone;
    EditText sem;
    Button btnEdit;
    DatabaseReference ref;

    ImageButton userLock;
    ImageButton passLock;
    ImageButton phoneLock;
    ImageButton semLock;

    String user;
    String pass;
    String phoneNumber;
    String semester;

    Boolean userBoo = true;
    Boolean passBoo = true;
    Boolean phoneBoo = true;
    Boolean semBoo = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_page);

        username = findViewById(R.id.editTextUsernameDetail);
        password = findViewById(R.id.editTextPasswordDetail);
        phone = findViewById(R.id.editTextPhoneDetail);
        sem = findViewById(R.id.editTextSemDetail);
        btnEdit = findViewById(R.id.btnEdit);

        username.setEnabled(false);
        password.setEnabled(false);
        phone.setEnabled(false);
        sem.setEnabled(false);

        userLock = findViewById(R.id.usernameLock);
        passLock = findViewById(R.id.passwordLock);
        phoneLock = findViewById(R.id.phoneLock);
        semLock = findViewById(R.id.semLock);

        Intent intent = getIntent();
        user = intent.getStringExtra("username");

        ref = FirebaseDatabase.getInstance().getReference("Student").child(user);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pass = snapshot.child("password").getValue().toString();
                phoneNumber = snapshot.child("phone").getValue().toString();
                semester = snapshot.child("semester").getValue().toString();
                username.setText(user);
                password.setText(pass);
                phone.setText(phoneNumber);
                sem.setText(semester);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userBoo){
                    username.setEnabled(true);
                    userBoo = false;
                }else{
                    username.setEnabled(false);
                    userBoo = true;
                }
            }
        });
        passLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passBoo){
                    password.setEnabled(true);
                    passBoo = false;
                }else{
                    password.setEnabled(false);
                    passBoo = true;
                }

            }
        });
        phoneLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneBoo){
                    phone.setEnabled(true);
                    phoneBoo = false;
                }else{
                    phone.setEnabled(false);
                    phoneBoo = true;
                }

            }
        });
        semLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(semBoo){
                    sem.setEnabled(true);
                    semBoo = false;
                }else{
                    sem.setEnabled(false);
                    semBoo = true;
                }

            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub1 = null;
                String sub2 = null;
                String mySem = sem.getText().toString();
                switch (mySem){
                    case "1":
                        sub1 = "C++";
                        sub2 = "Math";
                        break;
                    case "2":
                        sub1 = "Java";
                        sub2 = "Interface Design";
                        break;
                    case "3":
                        sub1 = "Network Design";
                        sub2 = "Software Design";
                        break;
                }
                ref = FirebaseDatabase.getInstance().getReference("Student").child(user);
                ref.child("username").setValue(username.getText().toString());
                ref.child("password").setValue(password.getText().toString());
                ref.child("phone").setValue(phone.getText().toString());
                ref.child("semester").setValue(mySem);
                ref.child("subject1").setValue(sub1);
                ref.child("subject2").setValue(sub2);
                Toast.makeText(editDetailPage.this, "Successful edit", Toast.LENGTH_LONG).show();
            }
        });
    }
}