package com.example.myapplication;

import java.util.ArrayList;

public class Club {
    private ArrayList<Play> Plays;
    private ArrayList<Player> players;
    private ArrayList<Coach> clubCoachs;

    public Club(){}

    public ArrayList<Play> getPlays() {
        return Plays;
    }

    public void setPlays(ArrayList<Play> plays) {
        Plays = plays;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Coach> getClubCoachs() {
        return clubCoachs;
    }

    public void setClubCoachs(ArrayList<Coach> clubCoachs) {
        this.clubCoachs = clubCoachs;
    }

    public Club(ArrayList<Play> plays, ArrayList<Player> players, ArrayList<Coach> clubCoachs) {
        Plays = plays;
        this.players = players;
        this.clubCoachs = clubCoachs;
    }
}
