package com.example.labour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
    private ProgressBar pp;

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

        pp =findViewById(R.id.pp);

        pp.setVisibility(View.VISIBLE);
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
                pp.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //pp.setVisibility(View.GONE);
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

        String Assign =assign.getSelectedItem().toString().trim();

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

        getMob(Assign,JobName,JobDiscription,JobLocation,JobPay,JobRequirements);
    }

    private void getMob(String assign, String jobName, String jobDiscription, String jobLocation, String jobPay, String jobRequirements) {


        String[] str =assign.split(":",0);
       Toast.makeText(createJob.this,str[0],Toast.LENGTH_SHORT).show();

        database =FirebaseDatabase.getInstance();
        myRef =database.getReference().child("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap : snapshot.getChildren()){
                    Log.d("name", String.valueOf(snap.child("name")));
                    if(snap.child("name").getValue().equals(str[0].trim())){
                        Log.d("snap","Found");
                        String mob=snap.child("mobile").getValue().toString().trim();
                        //Toast.makeText(createJob.this,mob,Toast.LENGTH_SHORT).show();
                        sendMsg(mob,jobName,jobDiscription,jobLocation,jobPay,jobRequirements);
                    }else {
                        Log.d("snap","Not Found");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void sendMsg(String mob,String jobName, String jobDiscription, String jobLocation, String jobPay,String jobRequirements) {

        //Toast.makeText(createJob.this,String.valueOf(mob),Toast.LENGTH_SHORT).show();
        Log.d("details",jobName);
        Log.d("details",jobDiscription);
        Log.d("details",jobLocation);
        Log.d("details",jobPay);
        Log.d("details",jobRequirements);
        Log.d("details",String.valueOf(mob));



    }
}
