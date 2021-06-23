package com.example.myapplication;

import java.util.ArrayList;

public class Coach extends User{
    private String ageRange;
    private ArrayList<Play> coachPlayBook;
    private String clubCoached;



    public Coach(){
        System.out.println("Default called");
    }

    public Coach(String email, String type, String uid, String club, String ageRange) {
        super(email, type, uid, club);
        this.ageRange = ageRange;
    }


    public Coach(String email, String type, String uid, String club, String ageRange, ArrayList<Play> coachPlayBook, String clubCoached) {
        super(email, type, uid, club);
        this.ageRange = ageRange;
        this.coachPlayBook = coachPlayBook;
        this.clubCoached = clubCoached;
    }

    public Coach(String email, String type, String uid, String ageRange, ArrayList<Play> coachPlayBook, String clubCoached) {
        super(email, type, uid);
        this.ageRange = ageRange;
        this.coachPlayBook = coachPlayBook;
        this.clubCoached = clubCoached;
    }

    public Coach(String ageRange, ArrayList<Play> coachPlayBook, String clubCoached) {
        this.ageRange = ageRange;
        this.coachPlayBook = coachPlayBook;
        this.clubCoached = clubCoached;
    }


    public Coach(String email, String type, String ageRange, ArrayList<Play> coachPlayBook, String clubCoached) {
        super(email, type);
        this.ageRange = ageRange;
        this.coachPlayBook = coachPlayBook;
        this.clubCoached = clubCoached;
    }

    public Coach(String email, String type, String uid, String ageRange) {
        super(email, type, uid);
        this.ageRange = ageRange;
    }

    public Coach(String ageRange) {
        this.ageRange = ageRange;
    }

    public Coach(String email, String type) {
        super(email, type);
        ageRange=null;
        coachPlayBook=null;
    }

    public Coach(String name,String email,String club)
    {
        super(name,email);
        clubCoached=club;
        coachPlayBook=null;
        ageRange=null;
    }

    public String getClubCoached() {
        return clubCoached;
    }

    public void setClubCoached(String clubCoached) {
        this.clubCoached = clubCoached;
    }

    public Coach(String email, String type, String uid, String ageRange, ArrayList<Play> coachPlayBook) {
        super(email, type, uid);
        this.ageRange = ageRange;
        this.coachPlayBook = coachPlayBook;
    }

    public Coach(String ageRange, ArrayList<Play> coachPlayBook) {
        this.ageRange = ageRange;
        this.coachPlayBook = coachPlayBook;
    }

    public Coach(String email, String type, String ageRange, ArrayList<Play> coachPlayBook) {
        super(email, type);
        this.ageRange = ageRange;
        this.coachPlayBook = coachPlayBook;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public ArrayList<Play> getCoachPlayBook() {
        return coachPlayBook;
    }

    public void setCoachPlayBook(ArrayList<Play> coachPlayBook) {
        this.coachPlayBook = coachPlayBook;
    }
}
