package com.hackumbc.carl.draw;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.getpebble.android.kit.PebbleKit;

import java.util.UUID;


public class DrawActivity extends AppCompatActivity {
    //private UUID APP_UUID = UUID.fromString("42902e12-ec30-41dd-9994-4a722b34db4d");
    private UUID APP_UUID = UUID.fromString("584577fd-63ea-4040-b7bb-bea0425938ca");;
    private static final String TAG = "graph";
    PebbleKit.PebbleDataLogReceiver pdlr;

    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.BLACK);
        setContentView(drawView);
        Button reset = new Button(getApplicationContext());
        reset.setScaleX(.5f);
        reset.setScaleY(.1f);
        reset.setText("Reset Graph");
        reset.setPivotX(drawView.getWidth());
        reset.setPivotY(drawView.getHeight());
        reset.setTextSize(200);
        reset.setTextScaleX(.2f);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.clear();
            }
        });
        addContentView(reset, drawView.getLayoutParams());
        pdlr = new PebbleKit.PebbleDataLogReceiver(APP_UUID) {
            @Override
            public void receiveData(Context context, UUID logUuid, Long timestamp,
                                    Long tag, int data) {
                //Log.i(TAG, "New data for session " + tag + "!: " + data);
                drawView.addPoint(data);
            }

            @Override
            public void onFinishSession(Context context, UUID logUuid, Long timestamp,
                                        Long tag) {
                //Log.i(TAG, "Session " + tag + " finished!");
            }

        };

        PebbleKit.registerDataLogReceiver(getApplicationContext(), pdlr);
        /*
        for(int i = 0;i<100;i++){
            drawView.addPoint((int) (Math.random()*i));
        }
        */

    }

    @Override
    public void onStop(){
        try {
            unregisterReceiver(pdlr);
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onStop();
    }
}
