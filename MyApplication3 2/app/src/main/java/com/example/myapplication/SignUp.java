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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    private String userType;
    private EditText name;
    private EditText email;
    private EditText age;
    private EditText club;
    private FirebaseAuth mAuth;
    private EditText password;

    private FirebaseFirestore firestore;
    Spinner spinnerUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();


        name= findViewById(R.id.nameSignUp);
        email=findViewById(R.id.emailSignUp);
        age=findViewById(R.id.ageSignUp);
        club=findViewById(R.id.clubSignUp);
        password=findViewById(R.id.passwordSignUp);

        spinnerUserType=(Spinner) findViewById(R.id.spinner);

        String emailStr=email.toString();

        populateUserTypeSpinner();




    }
    private void populateUserTypeSpinner() {
        ArrayAdapter<String> userAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.userTypes));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(userAdapter);
    }

    public void signUp (View v)
    {
        String userType=spinnerUserType.getSelectedItem().toString();

        final String emailString= email.getText().toString();
        String passwordString= password.getText().toString();
        String nameString=name.getText().toString();
        String clubString=club.getText().toString();
        String ageSTring= age.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    if (userType.equals("Coach")) {
                        Coach newCoach = new Coach();
                        Club newClub= new Club();

                        ArrayList<Coach> listOfCoach=new ArrayList<>();
                        ArrayList random= new ArrayList();
                        ArrayList random2= new ArrayList();
                        newClub.setPlayers(random);
                        newClub.setPlays(random2);



                        ArrayList emptyArraylist= new ArrayList();


                        newCoach.setCoachPlayBook(emptyArraylist);

                        newCoach.setUid(mAuth.getUid());
                        newCoach.setClubCoached(clubString);
                        newCoach.setEmail(emailString);
                        newCoach.setAgeRange(ageSTring);
                        newCoach.setType("Coach");

                        User newUser= new User();

                        newUser.setUid(mAuth.getUid());
                        newUser.setClub(clubString);
                        newUser.setEmail(emailString);
                        newUser.setType("Coach");

                        listOfCoach.add(newCoach);
                        newClub.setClubCoachs(listOfCoach);

                        newCoach.setClub(clubString);
                        firestore.collection("Clubs").document(clubString).set(newClub);

                        firestore.collection("coaches").document(mAuth.getUid()).set(newCoach);

                        firestore.collection("Users").document(mAuth.getUid()).set(newUser);
                        updateUI(mAuth.getCurrentUser());

                    }
                    if (userType.equals("Player")) {
                        Player newPlayer = new Player(nameString,emailString,clubString);
                        newPlayer.setUid(mAuth.getUid());
                        newPlayer.setType(userType);
                        firestore.collection("players").document(mAuth.getUid()).set(newPlayer);

                        User newUser= new User();

                        newUser.setUid(mAuth.getUid());
                        newUser.setClub(clubString);
                        newUser.setEmail(emailString);
                        newUser.setType("Player");

                        firestore.collection("Users").document(mAuth.getUid()).set(newUser);

                        updateUI(mAuth.getCurrentUser());
                    }
                }
                else
                    {
                        Log.d("Fail",task.getException().toString());
                        Toast.makeText(getBaseContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }

            }
        });


    }
    public void updateUI(FirebaseUser currentUser)
    {
        if(currentUser!=null)
        {
            Intent intent= new Intent(this, homePage.class);
            Log.d("I am here",mAuth.getUid());
            startActivity(intent);

        }
    }
}