package com.example.cs160cashew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        mRegisterBtn.setOnClickListener (new View.OnClickListener() {
           @Override
            public void onClick(View v){

              String email = mEmail.getText().toString().trim();
              String password = mPassword.getText().toString().trim();

              if(TextUtils.isEmpty(email)){
                  mEmail.setError("Email is Required");
                  return;
              }
               if(TextUtils.isEmpty(password)){
                   mPassword.setError("Password is Required");
                   return;
               }

               if(password.length() < 6){
                   mPassword.setError("Password must be more than 6 letters");
                   return;
               }

               String name = mFullName.getText().toString().trim();
               String phone = mPhone.getText().toString().trim();

               HashMap<String, Object> map = new HashMap<>();
               map.put("Name", name);
               map.put("Email", email);
               map.put("Phone", phone);

               FirebaseDatabase.getInstance().getReference().child("User Info").push().updateChildren(map);

               //register the user to firebase
               fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(Register.this,"User created",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       }
                       else{
                           Toast.makeText(Register.this,"Error occur" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}