package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.internal.$Gson$Preconditions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView drawingImageView;
    Canvas canvas;


    private FirebaseFirestore firestore;

    private FirebaseAuth mAuth;
    private EditText playNameType;

    Button addPlayer;
    Boolean drawTrue;
    private FieldMap field;

    Button animateSwitch;
    Play currPlay;
    ArrayList<playObject> playObjects;

    private ConstraintLayout constraintLayout;


    Integer counterPlayer=0;


    PointF pointA;
    PointF pointB;


    ConstraintLayout layout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playNameType=(EditText) findViewById(R.id.playNameType);

        animateSwitch= (Button) findViewById(R.id.animateSwitch);
        mAuth=FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();

        Context thisContext= getBaseContext();
        DisplayMetrics displaymetrics = new DisplayMetrics();

        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;



        playObjects= new ArrayList<playObject>();

        constraintLayout= findViewById(R.id.ConstraintLayout);

        WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();
        Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        int Width= windowMetrics.getBounds().width() - insets.left - insets.right;
        int Height= windowMetrics.getBounds().height()- insets.top -insets.bottom;


        layout = findViewById(R.id.Layout);


        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(Width   ,Height,300000);



//TEsting area
        field= new FieldMap(this);
        field.setLayoutParams(params);

        field.setPlayObjects(playObjects);
        System.out.println(field.getPlayObjects());
        System.out.println(playObjects+" null");
        System.out.println(playObjects.size()+" size");




        layout.addView(field,0);



        //Testing area end
        currPlay= new Play();
        currPlay.setPlayObjects(playObjects);
        currPlay.setPlayType("Empty");
        currPlay.setViewable(false);



        System.out.println(field);
        drawTrue=false;

        addPlayer= findViewById(R.id.playerAdd);

        //How to move each object.

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //generate a imageview

                ImageView template= new ImageView(MainActivity.this);



                template.setImageResource(R.drawable.ic_player);

                addView(template,75,75);

                System.out.println(template.getId());


                template.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch (View v, MotionEvent motionEvent) {

                            System.out.println("I have been clicked"+ template.getId());

                            System.out.println("ObjectID"+ template.getId());


                            float xDown = 0, yDown = 0;


                            switch (motionEvent.getActionMasked()) {
                                case MotionEvent.ACTION_DOWN:
                                    xDown = motionEvent.getX();
                                    yDown = motionEvent.getY();

                                    break;

                                case MotionEvent.ACTION_UP:
                                    float movedX, movedY;
                                    movedX = motionEvent.getX();
                                    movedY = motionEvent.getY();

                                    float distanceX = movedX - xDown;
                                    float distanceY = movedY - yDown;


                                    if (!drawTrue) {
                                        System.out.println(template.getX() + distanceX);

                                        template.setX(template.getX() + distanceX);
                                        template.setY(template.getY() + distanceY);

                                        PointF currpoint= new PointF();
                                        currpoint.set((template.getX() + distanceX),(template.getY() + distanceY));

                                        getCorrectID(playObjects,template.getId(),currpoint);

                                        field.setPlayObjects(playObjects);
                                        field.invalidate();

                                    } else {
                                        System.out.println("I AM DRAWING");

                                        System.out.println(playObjects.toString());




                                        int currID = template.getId();

                                        System.out.println(playObjects.toString());


                                        PointF currPoint = new PointF();
                                        currPoint.set(template.getX(), template.getY());

                                        PointF finalPoint = new PointF();
                                        finalPoint.set(template.getX() + distanceX, template.getY() + distanceY);


                                        getCorrectIDFinalPoint(playObjects,template.getId(),currPoint,finalPoint);
                                        
                                        field.setPlayObjects(playObjects);


                                        field.invalidate();
                                    }
                                    break;

                            }


                            return true;

                        }

                });
            }
        });


    }

    public void addView(ImageView imageView,int width,int height)
    {
        counterPlayer+=1;
        ConstraintLayout.LayoutParams layoutParams= new ConstraintLayout.LayoutParams(width,height);
        layoutParams.setMargins(0,10,0,10);

        imageView.setLayoutParams(layoutParams);

        imageView.setId(counterPlayer);
        constraintLayout.addView(imageView);


       playObject playObjectTemplate= new playObject(false,"ball",counterPlayer);

       PointF finalPoint= new PointF();

       finalPoint.set(30,30);

       playObjectTemplate.setFinalPoint(finalPoint);

       playObjects.add(playObjectTemplate);


       System.out.println(playObjects.toString()+" Testing this thing");

    }





    public void drawArrow(View v)
    {
        if(drawTrue)
        {
            drawTrue=false;
            animateSwitch.setBackgroundColor(Color.RED);
            animateSwitch.setText("Drag");

        }
        else
            {
                drawTrue=true;
                animateSwitch.setBackgroundColor(Color.GREEN);
                animateSwitch.setText("Arrow");
            }


    }
    public void printAll(){
        if(playObjects.size()!=0)
        {

        }
    }

    public void getCorrectID(ArrayList<playObject> playObjects2,int thing,PointF currpoint)
    {
        for (int i=0;i<playObjects2.size();i++)
        {
            if(playObjects2.get(i).getPlayObjectID()==thing)
            {
                playObjects2.get(i).setCurrPoint(currpoint);
            }

        }

    }
    public void getCorrectIDFinalPoint(ArrayList<playObject> playObjects2,int thing,PointF currpoint,PointF finalPoint)
    {
        for (int i=0;i<playObjects2.size();i++)
        {
            if(playObjects2.get(i).getPlayObjectID()==thing)
            {
               playObjects2.get(i).setArrowDrawn(true);
                playObjects2.get(i).setCurrPoint(currpoint);
                playObjects2.get(i).setFinalPoint(finalPoint);
            }
        }
    }

    public void reset(View v)
    {


       constraintLayout.removeAllViews();
        counterPlayer=0;
        playObjects= new ArrayList<>();
        field.reset();
    }




    public void publishPlay(View v)
    {
        if(playNameType.getText()==null)
        {
            Toast.makeText(this, "Please input playname", Toast.LENGTH_LONG).show();
        }
        else {
            String playName = playNameType.getText().toString();
            String[] playNames = playName.split(", ");

            Play thisPlay = new Play(playObjects, true, playNames[1], playNames[0]);

            //GIVE A POPUP SO THAT THEY CAN ENTER THE PLAY NAME AND TYPE
            firestore.collection("coaches").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot coachSnapshot = task.getResult();
                    Coach currCoach = coachSnapshot.toObject(Coach.class);


                    String clubCoached = currCoach.getClubCoached();

                    currCoach.getCoachPlayBook().add(thisPlay);

                    firestore.collection("coaches").document(mAuth.getUid()).set(currCoach);
                    firestore.collection("Clubs").document(clubCoached).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot clubSnapshot = task.getResult();
                            Club currClub = clubSnapshot.toObject(Club.class);

                            System.out.println("Size" + currClub.getPlays().size());

                            currClub.getPlays().add(thisPlay);

                            firestore.collection("Clubs").document(clubCoached).set(currClub);
                        }
                    });
                }
            });

            Intent newIntent = new Intent(this, CoachHome.class);
            startActivity(newIntent);
        }


    }



}