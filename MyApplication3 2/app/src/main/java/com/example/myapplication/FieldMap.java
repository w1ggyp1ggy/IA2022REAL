package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class FieldMap extends View {
    private Bitmap mapImage;
    private ArrayList<playObject> playObjects;

    private ArrayList<playObject> storage;

    public Bitmap getMapImage() {
        return mapImage;
    }

    public void setMapImage(Bitmap mapImage) {
        this.mapImage = mapImage;
    }

    public ArrayList<playObject> getPlayObjects() {
        return playObjects;
    }

    public void setPlayObjects(ArrayList<playObject> playObjects) {
        this.playObjects = playObjects;
    }

    public FieldMap(Context context)
    {
        super(context);
        mapImage = BitmapFactory.decodeResource(getResources(),R.drawable.symm_rugby);
        playObjects= new ArrayList<>();
        System.out.println(playObjects.toString()+" is this null");
        storage=new ArrayList<>();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(mapImage, 0, 0, null);
        Paint linePaint = new Paint();
        linePaint.setColor(Color.RED);


        System.out.println("I have been invalidated");
        if (playObjects != null) {
            for (int i = 0; i < playObjects.size(); i++) {
                if (playObjects.get(i).getArrowDrawn()==true) {
                    playObject currObject = playObjects.get(i);

                    System.out.println("I should be drawing an arrow");
                    System.out.println("This object"+currObject.getPlayObjectID());
                    System.out.println("this object"+currObject.getArrowDrawn().toString());
                    canvas.drawLine(currObject.getCurrPoint().x, currObject.getCurrPoint().y, currObject.getFinalPoint().x, currObject.getFinalPoint().y, linePaint);
                }
            }
        }
        else if(playObjects== null)
        {
            System.out.println("I am doing nothing");
        }
    }
    public void reset(){
        storage= new ArrayList<>();
        if(playObjects==null){
            System.out.println("This is null");
        }
        for (int i=0;i<playObjects.size();i++)
        {
            storage.add(playObjects.get(i));
        }
        playObjects= new ArrayList<>();

        System.out.println(storage.size()+ "rando garb 5");
        System.out.println(storage.size()+ "rando garb 4");

        invalidate();
    }

    public ArrayList<playObject> getStorage() {
        return storage;
    }

    public void setStorage(ArrayList<playObject> storage) {
        this.storage = storage;
    }
}
