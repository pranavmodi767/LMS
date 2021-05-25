package com.example.labour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    FirebaseAuth mAuth;

    private EditText userName;
    private EditText userPass;
    private Spinner loginRole;
    Button button;

    public void register(View v){
        Intent i = new Intent(this,Register.class);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.loginName);
        userPass = findViewById(R.id.loginPass);

        loginRole =findViewById(R.id.loginRole);
        String[] items = new String[]{"Worker", "Employer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,items);
        loginRole.setAdapter(adapter);
        String Roole =loginRole.getSelectedItem().toString().trim();

        button =findViewById(R.id.butt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name =userName.getText().toString().trim();
                String pass =userPass.getText().toString().trim();
                //Toast.makeText(login.this,"Works",Toast.LENGTH_LONG).show();
                if(name.isEmpty()){
                    userName.setError("Enter Email id");
                    userName.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    userPass.setError("Please enter Password ");
                    userPass.requestFocus();
                    return;
                }
                String Roole =loginRole.getSelectedItem().toString().trim();
                mAuth.signInWithEmailAndPassword(name,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(login.this,"Login Successful",Toast.LENGTH_LONG).show();
                        if(Roole.equals("Worker")) {
                            startActivity(new Intent(getApplicationContext(),worker.class));
                            finish();
                        }else if (Roole.equals("Employer")) {
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(login.this,"Select A role",Toast.LENGTH_SHORT).show();
                        }
                        }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(login.this,"Login Failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}