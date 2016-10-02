package com.hackumbc.carl.draw;

/**
 * Created by carl on 10/1/16.
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;


public class DrawView extends View {
    Paint paint = new Paint();
    LinkedList<Integer> points = new LinkedList<Integer>();
    private int sum = 0;
    private int max = 0;
    Rect bounds = new Rect();
    Matrix matrix = new Matrix();
    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
    }

    public void addPoint(int data){
        points.add(data);
        sum += data;
        if(data > max){
            max = data;
        }
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        int range = points.size();
        int px = -1, py = -1;
        int xi = 0;
        for(Integer yi : points) {
            int x = (int) ((float) canvas.getHeight() / range)*xi;
            int y = 0;
            if(max != 0) {
                y = (int) (((float) canvas.getWidth() / (float) max) * yi);

            }
            if(px != -1){
                canvas.drawLine(py,px,y,x,paint);
            }
            px = x;
            py = y;
            xi++;
        }
        String text = "";
        if(points.size() != 0) {
            text = "Mean: " + sum / points.size();
        }else{
            text = "No Data";
        }
        paint.setColor(Color.GREEN);
        float testTextSize = 10f;
        float desiredWidth = canvas.getWidth()/7;
        paint.setTextSize(testTextSize);
        paint.getTextBounds(text.toCharArray(),0,text.length(),bounds);
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();
        paint.setTextSize(desiredTextSize);
        canvas.drawText(text.toCharArray(),0,text.length(),(int) (.20*canvas.getWidth()),(int)(.20*canvas.getHeight()),paint);


        text = "Max: " + (max);
        desiredWidth = canvas.getWidth()/7;
        paint.getTextBounds(text.toCharArray(),0,text.length(),bounds);
        desiredTextSize = testTextSize * desiredWidth / bounds.width();
        canvas.drawText(text.toCharArray(),0,text.length(),(int) (.20*canvas.getWidth()),(int)(.45*canvas.getHeight()),paint);
        paint.setColor(Color.BLUE);
        text = "Y axis";
        canvas.drawText(text.toCharArray(),0,text.length(),(int)(canvas.getWidth()*.5),(int)(canvas.getHeight()*.92),paint);

        text = "X axis";
        Path p = new Path();
        p.addRect((int) (canvas.getWidth() * .2),0,(int) (canvas.getHeight()),0, Path.Direction.CCW);
        int tx = (int)(canvas.getWidth()*.05);
        int ty = (int)(canvas.getHeight()*.5);

        canvas.drawLine(0,(int) (canvas.getHeight()*.95),canvas.getWidth(),(int) (canvas.getHeight()*.95),paint);
        canvas.rotate(90,tx,ty);
        //canvas.drawTextOnPath(text.toCharArray(),0,text.length(),p,tx,ty,paint);
        canvas.drawText(text.toCharArray(),0,text.length(),tx,ty,paint);
       // canvas.restore();
        canvas.rotate(-90,tx,ty);
        canvas.drawLine(0,0,0,(int) (canvas.getHeight()*.95),paint);


       // canvas.restore();
    }

    public void clear(){
        points.clear();
        sum = 0;
        max = 0;
        invalidate();
    }
}
