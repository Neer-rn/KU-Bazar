package com.neer.ku_bazar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    Button login;
    TextView signup,forgotpass;
    EditText email,password;
    String userID;
   private UserSession session;
    FirebaseFirestore store;
    FirebaseUser user;
    FirebaseAuth auth;
    @Override

            protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        login = findViewById(R.id.login);
        signup = findViewById(R.id.text_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        session= new UserSession(getApplicationContext());


        Intent signupclick = new Intent(login.this,signup.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(signupclick);
                finish();
            }
        });

        forgotpass=findViewById(R.id.forget_pass);
        forgotpass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,Forget_password.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String semail = email.getText().toString().trim();
                String spass = password.getText().toString().trim();


                if (TextUtils.isEmpty(semail)){
                    Toast.makeText(getApplicationContext(),"Please enter your email adress",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(spass)){
                    Toast.makeText(getApplicationContext(),"Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.signInWithEmailAndPassword(semail,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            boolean isEmailVerified = checkIfEmailVerified();

                            if(!isEmailVerified){
                                Toast.makeText(getApplicationContext(),"E-mail not verified", Toast.LENGTH_SHORT).show();
                            } else {
                                userID = auth.getCurrentUser().getUid();
                                store.collection("user").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if (task.isSuccessful()) {

                                            if (task.getResult().exists()) {

                                                String sessionname = task.getResult().getString("name");
                                                String sessionemail = task.getResult().getString("email");

                                                session.createLoginSession(sessionname,sessionemail);


                                                Intent loginSuccess = new Intent(login.this, MainActivity.class);
                                                startActivity(loginSuccess);
                                                finish();


                                            }
                                        } else {

                                            new AlertDialog.Builder(login.this)
                                                    .setTitle("Login Error")
                                                    .setMessage(task.getException().getMessage())
                                                    .setPositiveButton("OK", null) // You can add buttons here
                                                    .show();

                                        }

                                    }
                                });
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"Couldn't log in. Please check email and password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private boolean checkIfEmailVerified() {

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user.isEmailVerified())
            return true;
        else
            return false;
    }



}

