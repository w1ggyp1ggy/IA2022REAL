package com.example.myapplication;

import java.util.ArrayList;

public class Player extends User{

    private ArrayList<Play> UnlearnedPlays;
    private String club;


    public Player(String email, String type, String uid, ArrayList<Play> unlearnedPlays) {
        super(email, type, uid);
        UnlearnedPlays = unlearnedPlays;
    }

    public Player(String name,String email,String Club)
    {
        super(name,email);
        club=Club;
    }

    public Player(ArrayList<Play> unlearnedPlays) {
        UnlearnedPlays = unlearnedPlays;
    }

    public Player(String email, String type, ArrayList<Play> unlearnedPlays) {
        super(email, type);
        UnlearnedPlays = unlearnedPlays;
    }

    public ArrayList<Play> getUnlearnedPlays() {
        return UnlearnedPlays;
    }

    public void setUnlearnedPlays(ArrayList<Play> unlearnedPlays) {
        UnlearnedPlays = unlearnedPlays;
    }
}
