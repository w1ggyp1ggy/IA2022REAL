package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class homePage extends AppCompatActivity {
    Button buttonToCoachPage;
    private FirebaseFirestore firebaseFirestore;

    private FirebaseAuth mAuth;
    private RecyclerView recView;
   private  String userType;
   private Button Refresh;
   private EditText searchKey;
   private String searchKeyStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        searchKeyStr="";
        Refresh= findViewById(R.id.Refresh);

        searchKey=findViewById(R.id.searchKey);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        buttonToCoachPage= findViewById(R.id.buttonToActivity);

        firebaseFirestore= FirebaseFirestore.getInstance();

        recView= findViewById(R.id.recView);

        getAndPopulateData();
    }
    public void toCoachPage(View v)
    {
        Intent intent= new Intent(this, CoachHome.class);

        startActivity(intent);
    }

    public void LogOut(View v)
    {
        mAuth.signOut();
        Intent intent= new Intent(this,LoginActivity.class);

        startActivity(intent);
    }

    public void getAndPopulateData()
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
                            if(task.isSuccessful()) {
                                if (searchKeyStr == "") {
                                    DocumentSnapshot clubSnapshot = task.getResult();
                                    Club currClub = clubSnapshot.toObject(Club.class);

                                    ArrayList currPlays = currClub.getPlays();

                                    updateAgain(currPlays);

                                    System.out.println(currPlays.size());
                                }
                            else
                                {
                                    System.out.println("I am trimming");
                                    DocumentSnapshot clubSnapshot = task.getResult();
                                    Club currClub = clubSnapshot.toObject(Club.class);

                                    ArrayList<Play> currPlays = currClub.getPlays();

                                    ArrayList trimmedPlays= new ArrayList();

                                    for (int i=0;i<currPlays.size();i++)
                                    {
                                        if(currPlays.get(i).getPlayName().toLowerCase()==searchKeyStr.toLowerCase()||currPlays.get(i).getPlayType().contains(searchKeyStr))
                                        {
                                            trimmedPlays.add(currPlays.get(i));
                                        }
                                    }
                                    if(trimmedPlays.size()>0) {
                                        System.out.println("plays does not need to suggest");

                                        updateAgain(trimmedPlays);
                                    }

                                    else if(trimmedPlays.size()==0)
                                    {
                                        System.out.println("Word check is being executed");
                                        for (int z=0;z<currPlays.size();z++)
                                        {
                                            if(wordSimilar(searchKeyStr,currPlays.get(z).getPlayName()))
                                            {
                                                Toast.makeText(getBaseContext(),"We could not find the play you are looking for these are the closest results",Toast.LENGTH_LONG).show();
                                                trimmedPlays.add(currPlays.get(z));
                                                System.out.println(currPlays.get(z).getPlayName());
                                            }
                                            else
                                                {
                                                    Toast.makeText(getBaseContext(),"We could not find the play you are looking think harder",Toast.LENGTH_LONG).show();
                                                }
                                        }
                                        updateAgain(trimmedPlays);
                                    }

                                    System.out.println(trimmedPlays.size());

                                }
                        }
                    }
                });
                }
            }
        });
        }
    public void updateAgain(ArrayList<Play> plays)
    {
        PlayAdapter myAdapter= new PlayAdapter(getBaseContext(),userType,plays);

        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    public void Refresh(View v)
    {


        searchKey=findViewById(R.id.searchKey);

        searchKeyStr=searchKey.getText().toString();

        System.out.println(searchKeyStr);
        getAndPopulateData();
    }

    public boolean wordSimilar(String original,String checkedAgainst)
    {
        int counter =0;

        System.out.println("I am being executed word similar");
        original= original.toLowerCase();
        checkedAgainst=checkedAgainst.toLowerCase();
        //check whether the checked against word has any consecutive 3 letters that are the same in the original
        //check whether the first letter is the same.
        if(original.length()<3)
        {
            return false;
        }
        for (int i=0;i<original.length()-2;i++)
        {
            String subString=original.substring(i,i+3);
            if (checkedAgainst.contains(subString)) {
                Log.d("Word Check", "3 letters are the same " + subString);
                counter += 1;
            }
        }
        //check if 80% of the letters are the same.

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (char character : original.toCharArray()) {
            Integer count = map.get(character);

            if (count == null) {
                count = 0;
            }
            map.put(character, count);
        }

        Map<Character, Integer> mapCheck = new HashMap<Character, Integer>();
        for (char character : checkedAgainst.toCharArray()) {
            Integer count2 = mapCheck.get(character);

            if (count2 == null) {
                count2 = 0;
            }
            map.put(character, count2);
        }
        if(original.charAt(0)==checkedAgainst.charAt(0))
        {
            counter+=1;
            Log.d("Word Check","The first letter is the same");
        }
        int newCounter=0;
        for(int k=0;k<mapCheck.size();k++)
        {
            if(mapCheck.get(original.charAt(k))==null)
            {
                continue;
            }
            else if(map.get(original.charAt(k))==mapCheck.get(original.charAt(k)))
            {
                newCounter+=1;
            }
        }
        int percentageNeeded=(int) Math.round(mapCheck.size()*0.8);
        if(newCounter>=percentageNeeded)
        {
            counter+=1;
            Log.d("Word Check","80% of the letters are correct");
        }

        if(original.length()<5&&counter>=2)
        {
            return true;
        }
        else if(counter>=3)
            {
                return true;
            }
        else
            {
                return false;
            }

    }
}