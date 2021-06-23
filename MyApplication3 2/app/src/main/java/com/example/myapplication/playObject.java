package com.example.myapplication;

import android.graphics.PointF;

import java.util.ArrayList;

public class playObject {
    private Boolean arrowDrawn;
    private PointF currPoint;
    private PointF finalPoint;

    private int playObjectID;
    private String Colour;
    private String playObjectType;

    public playObject()
    {

    }



    public PointF getCurrPoint() {
        return currPoint;
    }

    public void setCurrPoint(PointF currPoint) {
        this.currPoint = currPoint;
    }

    public PointF getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(PointF finalPoint) {
        this.finalPoint = finalPoint;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }




    public playObject(Boolean arrowDrawn,String playObjectType,int playObjectID)
    {
        this.arrowDrawn= arrowDrawn;
        this.playObjectType=playObjectType;
        this.playObjectID=playObjectID;
        this.Colour="RED";
    }

    public int getPlayObjectID() {
        return playObjectID;
    }

    public void setPlayObjectID(int playObjectID) {
        this.playObjectID = playObjectID;
    }

    public Boolean getArrowDrawn() {
        return arrowDrawn;
    }

    public void setArrowDrawn(Boolean arrowDrawn) {
        this.arrowDrawn = arrowDrawn;
    }



    public String getPlayObjectType() {
        return playObjectType;
    }

    public void setPlayObjectType(String playObjectType) {
        this.playObjectType = playObjectType;
    }
}
