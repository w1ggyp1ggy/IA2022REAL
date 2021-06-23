package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PlayHolder extends RecyclerView.ViewHolder{
    private TextView playName;
    private TextView learnedStatus;
    private TextView playType;
    private ConstraintLayout layout;

    public TextView getPlayName() {
        return playName;
    }

    public void setPlayName(TextView playName) {
        this.playName = playName;
    }

    public TextView getLearnedStatus() {
        return learnedStatus;
    }

    public void setLearnedStatus(TextView learnedStatus) {
        this.learnedStatus = learnedStatus;
    }

    public TextView getPlayType() {
        return playType;
    }

    public void setPlayType(TextView playType) {
        this.playType = playType;
    }

    public PlayHolder(@NonNull View itemView) {
        super(itemView);
        this.layout= itemView.findViewById(R.id.parentLayout);
        playName= itemView.findViewById(R.id.playName);
        playType= itemView.findViewById(R.id.playType);
        learnedStatus= itemView.findViewById(R.id.learnStatus);
    }

    public ConstraintLayout getLayout() {
        return layout;
    }

    public void setLayout(ConstraintLayout layout) {
        this.layout = layout;
    }
}
