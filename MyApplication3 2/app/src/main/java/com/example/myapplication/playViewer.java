package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.PointF;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;

@SuppressLint("ClickableViewAccessibility")
public class playViewer extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private String userType;
    private int position;
    private ArrayList<Play> currPlays;
    protected Play currPlay;
    private ConstraintLayout layoutAddPlayer;
    private ArrayList<playObject> playObjectsClone;
    protected ArrayList<playObject> playObjectsLearn;

    private Button addPlayer;

    private ConstraintLayout layoutAddNewPlayer;
    private int counterPlayer;
    private Boolean drawTrue=false;
    private FieldMap field;
    private ConstraintLayout layout;

    private Button animateTrueFalse;
    private Button learn;
    private Button test;

    private ArrayList<playObject> untouchedPure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_viewer);

        layoutAddNewPlayer= findViewById(R.id.layoutAddNewPlayer);

        mAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        userType=getIntent().getStringExtra("UserType");
        String  positionStr=getIntent().getStringExtra("Position");
        position= (Integer) Integer.parseInt(positionStr);

        getData();

        addPlayer= findViewById(R.id.addPlayerView);

        WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();
        Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        int Width= windowMetrics.getBounds().width() - insets.left - insets.right;
        int Height= windowMetrics.getBounds().height()- insets.top -insets.bottom;



        playObjectsLearn= new ArrayList<>();
        layoutAddPlayer= findViewById(R.id.layoutAddPlayer);
        layout = findViewById(R.id.layoutPlayViewer);

        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(Width   ,Height,300000);

        field= new FieldMap(this);
        field.setLayoutParams(params);

        layout.addView(field,0);

        animateTrueFalse= findViewById(R.id.animateTrueFalse);
        learn= findViewById(R.id.Check);
        test= findViewById(R.id.Test);
    }

    public void getData()
    {
        firebaseFirestore.collection("Users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot userSnapshot= task.getResult();
                    User currUser= userSnapshot.toObject(User.class);

                    userType= currUser.getType();
                    String club= currUser.getClub();

                    System.out.println("CLub"+ club);
                    System.out.println(mAuth.getUid());

                    firebaseFirestore.collection("Clubs").document(club).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot clubSnapshot= task.getResult();
                                Club currClub= clubSnapshot.toObject(Club.class);

                                currPlays= currClub.getPlays();

                                currPlay=currPlays.get(position);

                                System.out.println(currPlay.getPlayName());

                                System.out.println(currPlays.size());

                                playObjectsClone= currPlay.getPlayObjects();
                                System.out.println(playObjectsClone.size()+" CHECKING SIZE");
                                playObjectsLearn= currPlay.getPlayObjects();
                                System.out.println(playObjectsLearn.size());

                                untouchedPure=currClub.getPlays().get(position).getPlayObjects();
                                fillScreen();
                                field.setStorage(currPlay.getPlayObjects());
                            }
                        }
                    });
                }
            }
        });
    }

    public void addLearnedPlay()
    {
        firebaseFirestore.collection("Users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot userSnapshot= task.getResult();
                    User currUser= userSnapshot.toObject(User.class);

                    userType= currUser.getType();
                    String club= currUser.getClub();


                    firebaseFirestore.collection("Clubs").document(club).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot clubSnapshot= task.getResult();
                                Club currClub= clubSnapshot.toObject(Club.class);

                                currClub.getPlays().get(position).getLearnedPlay().add(currUser);

                                firebaseFirestore.collection("Clubs").document(club).set(currClub);
                            }
                        }
                    });
                }
            }
        });
    }


    public void fillScreen()
    {
        addPlayer();
        ArrayList filler= currPlay.getPlayObjects();
        field.setPlayObjects(filler);


        field.setStorage(filler);

        System.out.println(filler.size()+ "check tastic");
        field.invalidate();
    }
    @SuppressLint("ClickableViewAccessibility")
    public void addPlayer()
    {
        for (int i=0;i<currPlay.getPlayObjects().size();i++) {

            playObject currPlayObject= currPlay.getPlayObjects().get(i);

            ImageView template = new ImageView(playViewer.this);

            template.setImageResource(R.drawable.ic_player);

            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(75, 75);
            layoutParams.setMargins(0, 10, 0, 10);

            template.setLayoutParams(layoutParams);

            template.setX(currPlayObject.getCurrPoint().x);
            template.setY(currPlayObject.getCurrPoint().y);

            layoutAddPlayer.addView(template);


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

    public void learnCheck(View v)
    {
        getData();
        if(learnCheck2(playObjectsClone,field.getStorage()))
        {
            addLearnedPlay();
            Toast.makeText(getBaseContext(),"You learned it good job", Toast.LENGTH_LONG).show();
            learn.setBackgroundColor(Color.GREEN);

        }
        else
            {
                Toast.makeText(getBaseContext(),"You are trash", Toast.LENGTH_LONG).show();
                learn.setBackgroundColor(Color.RED);
            }
    }
    public Boolean learnCheck2(ArrayList<playObject> checking, ArrayList<playObject> original)
    {
        int counter=0;


        System.out.println(checking.size()+"FUck"+original.size());


        for(int i=0;i<checking.size();i++) {
            if(checking.get(i).getArrowDrawn())
            {
                for (int k=0;k<original.size();k++){
                    System.out.println("checking");
                if(distance(checking.get(i).getCurrPoint(),original.get(k).getCurrPoint())
                        &&distance(checking.get(i).getFinalPoint(),original.get(k).getFinalPoint()))
                {
                    System.out.println(checking.get(i));
                    System.out.println(original.get(i));
                    counter+=1;
                }
                }
            }
            else if(!checking.get(i).getArrowDrawn())
                {
                    for (int z=0;z<original.size();z++){
                        if(distance(checking.get(i).getCurrPoint(),original.get(z).getCurrPoint())&&!original.get(z).getArrowDrawn())
                        {
                            System.out.println(checking.get(i));
                            System.out.println(original.get(i));
                            counter+=1;
                        }
                    }
                }
        }
        if(original.size()==counter)
        {
            System.out.println(counter+"Does this really work?");
            return true;
        }
        else
            {
                System.out.println(counter+"Does this really work?+ False case");
                return false;
            }
    }

    public Boolean distance(PointF point1,PointF point2)
    {
        System.out.println("I have been called");

        float distance1=(point1.x-point2.x)*(point1.x-point2.x);
        float distance2=(point1.y-point2.y)*(point1.y-point2.y);

        double distance3= Math.sqrt(Math.round(distance1+distance2));

        if(distance3<50)
        {
            Log.d("distance","This is in range");
            System.out.println("This is in range");

            Log.d("distance","this is the distance "+distance3);
            return true;

        }
        Log.d("distance","This is out of range");
        System.out.println("This is out of range");
        Log.d("distance","this is the distance"+distance3);
        return false;
    }

    public void drawArrow(View v)
    {
        if(drawTrue)
        {
            drawTrue=false;
            animateTrueFalse.setBackgroundColor(Color.RED);
            animateTrueFalse.setText("Drag");

        }
        else
        {
            drawTrue=true;
            animateTrueFalse.setBackgroundColor(Color.GREEN);
            animateTrueFalse.setText("Arrow");
        }
    }

    public void Test(View v)
    {
        layoutAddPlayer.removeAllViews();

        layoutAddNewPlayer.removeAllViews();

        addPlayer.setVisibility(View.VISIBLE);

        counterPlayer=0;

        System.out.println(currPlay.getPlayObjects().size()+" rando garb1");
        playObjectsClone= new ArrayList<>();

        System.out.println(currPlay.getPlayObjects().size()+" rando garb2");

        field.reset();
        System.out.println(currPlay.getPlayObjects().size()+" rando garb3");

        System.out.println(playObjectsClone.size()+ " CHECKING SIZE V2");


        System.out.println(currPlay.getPlayObjects().size()+" rando garb");
    }


    public void addView(ImageView imageView,int width,int height)
    {
        counterPlayer+=1;
        ConstraintLayout.LayoutParams layoutParams= new ConstraintLayout.LayoutParams(width,height);
        layoutParams.setMargins(0,10,0,10);

        imageView.setLayoutParams(layoutParams);

        imageView.setId(counterPlayer);
        layoutAddNewPlayer.addView(imageView);

        playObject playObjectTemplate= new playObject(false,"ball",counterPlayer);

        PointF finalPoint= new PointF();

        finalPoint.set(30,30);

        playObjectTemplate.setFinalPoint(finalPoint);

        playObjectsClone.add(playObjectTemplate);
        System.out.println(playObjectsClone.size());
    }


    public void AddPlayer(View v)
    {
        ImageView template= new ImageView(playViewer.this);

        template.setImageResource(R.drawable.ic_player);

        addView(template,75,75);

        template.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float xDown = 0, yDown = 0;


                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        float movedX, movedY;
                        movedX = event.getX();
                        movedY = event.getY();

                        float distanceX = movedX - xDown;
                        float distanceY = movedY - yDown;


                        if (!drawTrue) {
                            System.out.println(template.getX() + distanceX);

                            template.setX(template.getX() + distanceX);
                            template.setY(template.getY() + distanceY);

                            PointF currpoint= new PointF();
                            currpoint.set((template.getX() + distanceX),(template.getY() + distanceY));

                            getCorrectID(playObjectsClone,template.getId(),currpoint);

                            field.setPlayObjects(playObjectsClone);
                            field.invalidate();

                        } else {
                            System.out.println("I AM DRAWING");

                            System.out.println(playObjectsClone.toString());



                            System.out.println(playObjectsClone.toString());


                            PointF currPoint = new PointF();
                            currPoint.set(template.getX(), template.getY());

                            PointF finalPoint = new PointF();
                            finalPoint.set(template.getX() + distanceX, template.getY() + distanceY);


                            getCorrectIDFinalPoint(playObjectsClone,template.getId(),currPoint,finalPoint);

                            field.setPlayObjects(playObjectsClone);


                            field.invalidate();
                        }
                        break;

                }


                return true;

            }
        });
    }

}