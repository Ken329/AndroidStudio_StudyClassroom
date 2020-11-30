package com.example.studyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPage extends AppCompatActivity {
    EditText user;
    EditText pass;
    EditText conPass;
    EditText phone;
    Button register;
    Button back;

    FirebaseDatabase firebase;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        user = findViewById(R.id.editTextUsername);
        pass = findViewById(R.id.editTextPassword);
        conPass = findViewById(R.id.editTextConPass);
        phone = findViewById(R.id.editTextPhone);
        register = findViewById(R.id.btnRegister);
        back = findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myUser = user.getText().toString();
                String myPass = pass.getText().toString();
                String myConPass = conPass.getText().toString();
                String myPhone = phone.getText().toString();

                if(myPass.equals(myConPass)){
                    firebase = FirebaseDatabase.getInstance();
                    ref = firebase.getReference("Student");

                    newStudent student = new newStudent(myUser, myPass, myPhone);
                    ref.child(myUser).setValue(student);
                    Toast.makeText(SignUpPage.this, "New Student added", Toast.LENGTH_LONG).show();
                    goBack(v);
                }else{
                    conPass.setError("Password not equal, Please check!!!");
                }

            }
        });
    }
    private void goBack(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }
}