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

public class addSubjectPage extends AppCompatActivity {
    EditText subject;
    EditText time;
    EditText date;
    Button add;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject_page);

        subject = findViewById(R.id.editTextSubject);
        time = findViewById(R.id.editTextTime);
        date = findViewById(R.id.editTextDate);
        add = findViewById(R.id.btnAdd);
        back = findViewById(R.id.btnBackAddSubject);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mySubject = subject.getText().toString();
                String myTime = time.getText().toString();
                String myDate = date.getText().toString();

                if(!mySubject.isEmpty() && !myTime.isEmpty() && !myDate.isEmpty()){
                    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                    DatabaseReference ref = firebase.getReference("Teacher");
                    newSubject s1 = new newSubject(mySubject, myTime, myDate);
                    ref.child(mySubject).setValue(s1);
                    Toast.makeText(addSubjectPage.this, "New subject added", Toast.LENGTH_LONG).show();
                    goBack(v);
                }else{
                    if(mySubject.isEmpty()){
                        subject.setError("Field shouldn't be empty");
                    }
                    if(myTime.isEmpty()){
                        time.setError("Field shouldn't be empty");
                    }
                    if(myDate.isEmpty()){
                        date.setError("Field shouldn't be empty");
                    }
                }
            }
        });
    }
    private void goBack(View v){
        Intent intent = new Intent(v.getContext(), teacherPage.class);
        startActivity(intent);
    }
}