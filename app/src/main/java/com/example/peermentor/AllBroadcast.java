package com.example.peermentor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AllBroadcast extends AppCompatActivity {
    private LinearLayout myL;
    Button AddRequest;
    dymanicViews dmy;
    Context cxt;
    int i=0;
    String type;
    RadioGroup typeofbroadcast;
    RadioButton selected;
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
                 typeofbroadcast = findViewById(R.id.RGroup);
                typeofbroadcast.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        myL.addView(dmy.descriptionTextView(getApplicationContext()," "+(i++),type));
                    }
                });

            }
        });

    }
    void CheckButtton(View view)
    {
        int radId = typeofbroadcast.getCheckedRadioButtonId();
        selected = findViewById(radId);
        type = selected.getText().toString();
        Toast.makeText(this,type+" was selected",Toast.LENGTH_SHORT).show();

    }
}
