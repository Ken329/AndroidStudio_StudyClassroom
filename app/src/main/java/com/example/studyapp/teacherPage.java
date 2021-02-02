package com.example.studyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class teacherPage extends AppCompatActivity {
    ListView mListView;
    TextView btnPlus;
    ImageView btnOut;
    ArrayList<String> item = new ArrayList<>();
    ArrayList<String> detail = new ArrayList<>();
    DatabaseReference ref;
    String searching;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page);
        btnOut = findViewById(R.id.imageBtnOut);
        btnPlus = findViewById(R.id.textViewPlus);

        mListView = findViewById(R.id.listViewTeacher);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        mListView.setAdapter(arrayAdapter);

        ListView list = new ListView(this);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, detail);
        list.setAdapter(adapter);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setView(list);
        final AlertDialog dialog1 = dialog.create();

        ref = FirebaseDatabase.getInstance().getReference("Teacher");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    item.add(snapshot1.child("subject").getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searching = item.get(position);
                search(searching);
                dialog1.show();
                detail.clear();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                searching = item.get(position);
                ref = FirebaseDatabase.getInstance().getReference("Teacher").child(searching);
                ref.removeValue();
                Toast.makeText(teacherPage.this, searching + " has been removed", Toast.LENGTH_LONG).show();
                item.remove(position);
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), addSubjectPage.class);
                startActivity(intent);
            }
        });
    }
    public void search(String search){
        ref = FirebaseDatabase.getInstance().getReference("Teacher").child(search);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    detail.add(snapshot1.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
