package com.neer.ku_bazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.internal.zzx;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    public static final String TAG = "TAG";
    String newuser;

    Button btnsignup;
    EditText email, pass, name;
    TextView login;

    FirebaseFirestore store;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        btnsignup = findViewById(R.id.signup_button);
        email = findViewById(R.id.signup_email);
        pass = findViewById(R.id.signup_password);
        name = findViewById(R.id.fullname);
        login = findViewById(R.id.text_login);

        Intent nlogin = new Intent(signup.this,login.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(nlogin);
            }
        });

        store = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semail = email.getText().toString().trim();
                String spass = pass.getText().toString().trim();
                String sname = name.getText().toString().trim();

                if (TextUtils.isEmpty(semail)){
                    Toast.makeText(getApplicationContext(),"Please enter your email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(spass)){
                    Toast.makeText(getApplicationContext(),"Please Enter a password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sname)){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Fullname",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spass.length()<6){
                    Toast.makeText(getApplicationContext(),"Please Enter a minimum of 6 characters",Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(semail,spass).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Register Sucesful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"Verification Failed: Email not Sent"+ e.getMessage());

                                }
                            });
                            Toast.makeText(getApplicationContext(),"Welcome!!!",Toast.LENGTH_SHORT).show();
                            newuser = auth.getCurrentUser().getUid();
                            DocumentReference documentReference = store.collection("user").document(newuser);
                            Map<String,Object> nuser = new HashMap<>();
                            nuser.put("Name",sname);
                            nuser.put("Email",semail);
                            documentReference.set(nuser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"User Profile Created" + newuser);

                                }
                            });

                            Intent naya = new Intent(signup.this,login.class);
                            startActivity(naya);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error!!!"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }

                    }
                });






            }
        });


    }
}