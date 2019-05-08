package com.example.peermentor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AllBroadcast extends AppCompatActivity {
    private LinearLayout myL;
    Button AddRequest;
    dymanicViews dmy;
    Context cxt;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_broadcast);
        myL = findViewById(R.id.UsersBroadcasts);
        AddRequest = findViewById(R.id.AddRequest);
        AddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dmy = new dymanicViews(cxt);
                    myL.addView(dmy.descriptionTextView(getApplicationContext()," "+(i++)));
            }
        });
    }
}
