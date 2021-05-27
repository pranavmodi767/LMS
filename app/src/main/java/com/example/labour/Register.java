package com.example.labour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private EditText name;
    private EditText mobile;
    private EditText aadhar;
    private EditText email;
    private EditText age;
    private EditText pass;

    private Spinner role,proffesion;


    ProgressBar progressBar;
    Button button;


/*
    public void submit(View v) {
        ans = registerUser();

        if (ans) {
            Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Register.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        button =findViewById(R.id.button);
        button.setOnClickListener((View.OnClickListener) this);
        name = findViewById(R.id.loginName);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        age =findViewById(R.id.age);
        pass=findViewById(R.id.loginPass);

        role =findViewById(R.id.role);
        String[] items = new String[]{"Employer", "Worker"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        role.setAdapter(adapter);


        proffesion =findViewById(R.id.proffesion);
        String[] proff = new String[]{"None", "Sweeper","Peon","Cook","Electrician","Painter","Constructer","Security Guard"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, proff);
        proffesion.setAdapter(adapter2);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:
                registerUser();
                break;
        }


    }

    private void registerUser() throws NullPointerException {


        String Name = name.getText().toString().trim();
        String Mobile = mobile.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Age = age.getText().toString().trim();
        String Pass =pass.getText().toString().trim();
        String Role =role.getSelectedItem().toString().trim();
        String Proffesion=proffesion.getSelectedItem().toString().trim();

        if(Name.isEmpty()){
            name.setError("invalid name");
            name.requestFocus();


        }
        if(Mobile.isEmpty()){
            mobile.setError("Invaild Number");
            mobile.requestFocus();


        }

        if(Email.isEmpty()){
            email.setError("Invalid Email");
            email.requestFocus();


        }

        if (Age.isEmpty() ){
            age.setError("Enter Valid Age");
            age.requestFocus();


        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email,Pass)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                           // Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                           Users abuser = new Users(Name,Mobile,Email,Age,Role,Proffesion);

                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            myRef.setValue(abuser).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(),login.class));
                                    }else{
                                        Toast.makeText(Register.this,"Registration Failed Again",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                            myRef =database.getReference().child("Workers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            if(Role.equals("Worker")){
                                myRef.setValue(Name+":"+"("+Proffesion+")");
                            }
                        }else {
                            Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });



    }

}