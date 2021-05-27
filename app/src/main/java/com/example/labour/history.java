package com.example.labour;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class history extends AppCompatActivity {

    private ListView list;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Button delete;
    Module module;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list=findViewById(R.id.list);
        delete=findViewById(R.id.delete);
        module=(Module)getApplicationContext();

        ArrayList<String> List = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,List);
        list.setAdapter(adapter);


        database =FirebaseDatabase.getInstance();
        myRef =database.getReference().child("Jobs").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {

                String value =snapshot.getValue(job2.class).toString();
                String assign = snapshot.child("Assign").getValue().toString().trim();
                StringBuilder sb = new StringBuilder();
                sb.append("Assign to:- "+ assign +"\n");
                sb.append(value);
                String ans =sb.toString();
                List.add(ans);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp =List.get(position);
                Log.d("skyrim",temp);
                module.setGvalue_assign(temp);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str =module.getGvalue_assign();
                if(str.equals(null)){
                    Toast.makeText(history.this,"Select An record to delete",Toast.LENGTH_SHORT).show();
                }else {
                    myRef.child("Jobs").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            myRef.removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Toast.makeText(history.this,"Record Deleted",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),history.class);
                    startActivity(intent);
                }
            }
        });
    }
}