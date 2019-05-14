package com.example.peermentor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AllBroadcast extends AppCompatActivity {
    private LinearLayout myL;
    Button AddRequest;
    dymanicViews dmy;
    Context cxt;
    int i=0;
    int type=0;
    TextView name ;
    EditText broadcast_text;
    RadioGroup typeofbroadcast;
    RadioButton selected;
    Intent chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chat = new Intent(AllBroadcast.this, MainActivity.class);
        setContentView(R.layout.activity_all_broadcast);
        myL = findViewById(R.id.UsersBroadcasts);
        name  = findViewById(R.id.UserName);
        broadcast_text = findViewById(R.id.broadcast_text);
        name.setText(getIntent().getStringExtra("username"));

        AddRequest = findViewById(R.id.AddRequest);
        AddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dmy = new dymanicViews(cxt);
                 typeofbroadcast = findViewById(R.id.RGroup);
                typeofbroadcast.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                      type = group.getCheckedRadioButtonId();
                      if(type == (R.id.Tutor))
                      {type=1;}
                        if(type == (R.id.carpool))
                        {type=2;}
                        if(type == (R.id.Stationary))
                        {type=3;}

                    }
                });
                String broadcast_msg=broadcast_text.getText().toString().trim();
                broadcast_text.setText("");
                final TextView tv =dmy.descriptionTextView(getApplicationContext(),"hello, "+name.getText().toString().trim()+" needs help \n"+broadcast_msg,type);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =  new Intent(AllBroadcast.this, MainActivity.class);
                        i.putExtra("String",tv.getText().toString().trim());
                        i.putExtra("username",name.getText().toString());
                        startActivity(i);
                    }
                });
                myL.addView(tv);

            }
        });

    }



}
