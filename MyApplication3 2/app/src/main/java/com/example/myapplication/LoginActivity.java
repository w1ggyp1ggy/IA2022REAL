package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {


    private FirebaseFirestore firestore;

    private FirebaseAuth mAuth;
    Spinner spinnerUserType;
    private EditText emailField;
    private EditText passwordField;

    String[] userTypes={"Player", "Coach"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();

        emailField=  findViewById(R.id.email);
        passwordField= findViewById(R.id.password);




        if(mAuth.getCurrentUser()!=null)
        {
            updateUI(mAuth.getCurrentUser());
        }

    }


    public void signIn(View v)
    {
        String emailString= emailField.getText().toString();
        String passwordString= passwordField.getText().toString();
        mAuth.signInWithEmailAndPassword(emailString,passwordString);

        FirebaseUser user=mAuth.getCurrentUser();

        updateUI(user);
    }

    public void signUp(View v)
    {
        Intent intent= new Intent(this,SignUp.class);

        startActivity(intent);
    }
    public void updateUI(FirebaseUser currentUser)
    {
            if(currentUser!=null)
            {
                Intent intent= new Intent(this, homePage.class);
                startActivity(intent);
            }
    }

}