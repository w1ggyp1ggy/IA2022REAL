package com.example.myapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Play {
    ArrayList<playObject> playObjects;

    ArrayList<User> learnedPlay;

    public ArrayList<User> getLearnedPlay() {
        return learnedPlay;
    }

    public void setLearnedPlay(ArrayList<User> learnedPlay) {
        this.learnedPlay = learnedPlay;
    }

    public Play(ArrayList<playObject> playObjects, ArrayList<User> learnedPlay, Boolean viewable, String playType, String playName) {
        this.playObjects = playObjects;
        this.learnedPlay = learnedPlay;
        this.viewable = viewable;
        this.playType = playType;
        this.playName = playName;
    }

    Boolean viewable;

    String playType;
    String playName;

    public Play(ArrayList<playObject> playObjects, Boolean viewable, String playType, String playName) {
        this.playObjects = playObjects;
        this.viewable = viewable;
        this.playType = playType;
        this.playName = playName;
        this.learnedPlay= new ArrayList<>();
    }

    public Play()
    {

    }
    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public Play(ArrayList<playObject> playObjects, Boolean viewable, String playType) {
        this.playObjects = playObjects;
        this.viewable = viewable;
        this.playType = playType;
    }

    public Play(ArrayList<playObject> playObjects, Boolean viewable) {
        this.playObjects = playObjects;
        this.viewable = viewable;
        this.playType = "Empty";
    }


    public ArrayList<playObject> getPlayObjects() {
        return playObjects;
    }

    public void setPlayObjects(ArrayList<playObject> playObjects) {
        this.playObjects = playObjects;
    }

    public Boolean getViewable() {
        return viewable;
    }

    public void setViewable(Boolean viewable) {
        this.viewable = viewable;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }
}
