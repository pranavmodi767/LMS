package com.example.labour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class createJob extends AppCompatActivity {

    private EditText jobName,jobDisription,jobLocation,jobPay;
    private EditText jobRequirements;
    private Button jobBtn;
    private Spinner assign;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        mAuth =FirebaseAuth.getInstance();

        assign=findViewById(R.id.assign);
        jobName = findViewById(R.id.jobName);
        jobDisription = findViewById(R.id.jobDiscription);
        jobLocation =findViewById(R.id.jobLocation);
        jobPay =findViewById(R.id.jobPay);
        jobRequirements = findViewById(R.id.jobRequirements);

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        assign.setAdapter(adapter);

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference().child("Workers");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snappy : snapshot.getChildren()){
                    //list.clear();
                    list.add(snappy.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        jobBtn =findViewById(R.id.jobBtn);
        jobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job();
            }


        });


    }


    public void job(){
        String JobName =jobName.getText().toString().trim();
        String JobDiscription=jobDisription.getText().toString().trim();
        String JobLocation = jobLocation.getText().toString().trim();
        String JobPay = jobPay.getText().toString().trim();
        String JobRequirements=jobRequirements.getText().toString().trim();

        database = FirebaseDatabase.getInstance();

        SharedPreferences countSettings = getSharedPreferences("count", 0);
        // get current counts
        int count = countSettings.getInt("counts",0);
        count++;
        final SharedPreferences.Editor edit = countSettings.edit();
        edit.putInt("counts",count);
        edit.commit();

        Job hob = new Job(JobName,JobDiscription,JobLocation,JobPay,JobRequirements);

        myRef =database.getReference().child("Jobs").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(count));
        myRef.setValue(hob).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(createJob.this, "Job Created Successfully!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(createJob.this,"Failed to Create Job!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}