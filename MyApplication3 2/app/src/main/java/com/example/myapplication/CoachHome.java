package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CoachHome extends AppCompatActivity {

    Button goToCreate;
    private RecyclerView recView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_home);

        goToCreate= (Button) findViewById(R.id.buttonToCreate);
    }

    public void goToCreatePage(View v)
    {
        Intent intent= new Intent(this, MainActivity.class);

        startActivity(intent);
    }
    public void goBack(View v)
    {
        Intent intent= new Intent(this,homePage.class);

        startActivity(intent);
    }
}