package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class PlayAdapter extends RecyclerView.Adapter<PlayHolder> {
    private Context context;
    private String currUserType;
    ArrayList<Play> plays;

    private FirebaseAuth mAuth;



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getCurrUserType() {
        return currUserType;
    }

    public void setCurrUserType(String currUserType) {
        this.currUserType = currUserType;
    }

    public ArrayList<Play> getPlays() {
        return plays;
    }

    public void setPlays(ArrayList<Play> plays) {
        this.plays = plays;
    }

    public PlayAdapter(Context context, String currUserType, ArrayList<Play> plays) {
        this.context = context;
        this.currUserType = currUserType;
        this.plays = plays;
    }

    @NonNull
    @Override
    public PlayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowview, parent, false);

        PlayHolder holder = new PlayHolder(myView);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull PlayHolder holder, int position) {
        mAuth= FirebaseAuth.getInstance();


        System.out.println(plays.size());
        Log.d("RecyclerView",plays.size()+"");

        holder.getPlayName().setText(plays.get(position).getPlayName());
        holder.getPlayType().setText(plays.get(position).getPlayType());

        holder.getLearnedStatus().setText("NOT LEARNED");
        holder.getLearnedStatus().setBackgroundColor(Color.RED);

        for (int i=0;i<plays.get(position).getLearnedPlay().size();i++) {
            System.out.println(plays.get(position).getLearnedPlay().get(i).getUid() + " CHECK ME");

            System.out.println(mAuth.getUid() + " CHECK ME2");
            if (plays.get(position).getLearnedPlay().get(i).getUid().equals(mAuth.getUid())) {
                System.out.println("Am i ever here");
                holder.getLearnedStatus().setText("LEARNED");
                holder.getLearnedStatus().setBackgroundColor(Color.GREEN);
            }
        }


        holder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context=v.getContext();
                Intent intent= new Intent(context,playViewer.class);
                intent.putExtra("UserType",currUserType);




                intent.putExtra("Position",position+"");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plays.size();
    }

}
