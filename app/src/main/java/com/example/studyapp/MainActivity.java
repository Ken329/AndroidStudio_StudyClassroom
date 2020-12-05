package com.example.studyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
                String myUser = user.getText().toString();
                String myPass = pass.getText().toString();
                if(check){
                    if(myUser.equals("admin") && myPass.equals("admin")){
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), teacherPage.class);
                        startActivity(intent);
                    }else{
                        user.setError("Wrong Username or Password");
                    }
                }else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Student");
                    Query query = ref.orderByChild("username").equalTo(myUser);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String passFromDb = snapshot.child(myUser).child("password").getValue(String.class);
                                if(passFromDb.equals(myPass)){
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(v.getContext(), StudentPage.class);
                                    intent.putExtra("username", myUser);
                                    startActivity(intent);
                                }else{
                                    pass.setError("Password not correct");
                                }
                            }else{
                                user.setError("Username not available");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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