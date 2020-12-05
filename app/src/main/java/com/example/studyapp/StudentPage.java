package com.example.studyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class StudentPage extends AppCompatActivity {
    TextView welcometext;
    ImageButton showDetail;
    ImageButton editDetail;
    ImageButton addSubject;
    ImageButton logout;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    DatabaseReference ref;

    EditText sub;
    Button add;
    String subject;
    Dialog dialog;

    String spinnerSub1;
    String spinnerSub2;
    String spinnerAdd;

    LinearLayout sub1, sub2, additional;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        welcometext = findViewById(R.id.welcomeText);
        Intent intent = getIntent();
        String myUsername = intent.getStringExtra("username");
        String editUsername = myUsername.substring(0, 1).toUpperCase() + myUsername.substring(1);
        welcometext.setText("Welcome back " + editUsername);

        showDetail = findViewById(R.id.userBtn);
        editDetail = findViewById(R.id.editDetailbtn);
        addSubject = findViewById(R.id.studentAddBtn);
        logout = findViewById(R.id.logoutBtn);
        tv1 = findViewById(R.id.tvSub1);
        tv2 = findViewById(R.id.tvSub2);
        tv3 = findViewById(R.id.tvSub3);
        sub1 = findViewById(R.id.linearSub1);
        sub2 = findViewById(R.id.linearSub2);
        additional = findViewById(R.id.linerAdd);

        ListView list = new ListView(this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(list);
        final Dialog dialog = builder.create();

        ref = FirebaseDatabase.getInstance().getReference("Student").child(myUsername);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinnerSub1 = snapshot.child("subject1").getValue().toString();
                spinnerSub2 = snapshot.child("subject2").getValue().toString();
                if(snapshot.child("additional").exists()){
                    spinnerAdd = snapshot.child("additional").getValue().toString();
                    tv3.setText(spinnerAdd);
                }else{
                    tv3.setText("empty");
                }
                tv1.setText(spinnerSub1);
                tv2.setText(spinnerSub2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetail(spinnerSub1);
                dialog.show();
                arrayList.clear();
            }
        });
        sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetail(spinnerSub2);
                dialog.show();
                arrayList.clear();
            }
        });
        additional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv3.getText().toString() == "empty"){
                    Toast.makeText(StudentPage.this, "No additional subject found", Toast.LENGTH_LONG).show();
                }else{
                    openDetail(spinnerAdd);
                    dialog.show();
                    arrayList.clear();
                }
            }
        });
        editDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), editDetailPage.class);
                intent.putExtra("username", myUsername);
                startActivity(intent);
            }
        });
        showDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference("Student").child(myUsername);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String mypass = snapshot.child("password").getValue().toString();
                        String myphone = snapshot.child("phone").getValue().toString();
                        String mysem = snapshot.child("semester").getValue().toString();

                        Intent intent = new Intent(v.getContext(), showDetailPage.class);
                        intent.putExtra("username", myUsername);
                        intent.putExtra("password", mypass);
                        intent.putExtra("phone", myphone);
                        intent.putExtra("sem", mysem);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentPage.this, "Successful Log Out", Toast.LENGTH_LONG).show();
                goBack(v);
            }
        });
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference("Student").child(myUsername);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("additional").exists()){
                            Toast.makeText(StudentPage.this, "Already consists one additional subject", Toast.LENGTH_LONG).show();
                        }else{
                            openDialog();
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    subject = sub.getText().toString();
                                    ref = FirebaseDatabase.getInstance().getReference("Teacher");
                                    Query q = ref.orderByChild("subject").equalTo(subject);
                                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                ref = FirebaseDatabase.getInstance().getReference("Student").child(myUsername);
                                                ref.child("additional").setValue(subject);
                                                dialog.dismiss();
                                                Toast.makeText(StudentPage.this, "New subject added", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(StudentPage.this, "No subject Found, try again n see", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    public void goBack(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }
    public void openDetail(String data){
        ref = FirebaseDatabase.getInstance().getReference("Teacher").child(data);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.add("Time: " + snapshot.child("time").getValue().toString());
                arrayList.add("Date: " + snapshot.child("date").getValue().toString());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflate = this.getLayoutInflater();
        final View view = inflate.inflate(R.layout.add_subject_student, null);
        sub = view.findViewById(R.id.addSubjectText);
        add = view.findViewById(R.id.addSubjeectBtn);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
}