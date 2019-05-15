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

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;


import org.w3c.dom.Text;

import java.net.URISyntaxException;

public class AllBroadcast extends AppCompatActivity {
    private LinearLayout myL;
    Button AddRequest;

    Context cxt;
    int i=0;
    int type=0;
    TextView name ;
    EditText broadcast_text;
    RadioGroup typeofbroadcast;
    RadioButton selected;
    Intent chat;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chat = new Intent(AllBroadcast.this, MainActivity.class);
        setContentView(R.layout.activity_all_broadcast);
        myL = findViewById(R.id.UsersBroadcasts);
        name  = findViewById(R.id.UserName);
        broadcast_text = findViewById(R.id.broadcast_text);
        name.setText(getIntent().getStringExtra("username"));


        try {
            socket = IO.socket("http://192.168.1.105:3000");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }
        AddRequest = findViewById(R.id.AddRequest);
        AddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("username",name.getText().toString().trim());
                jsonObject.addProperty("type",type);
                jsonObject.addProperty("message",broadcast_msg);
                socket.emit("broadcast", jsonObject);

               /* final TextView tv =dmy.descriptionTextView(getApplicationContext(),"hello, "+name.getText().toString().trim()+" needs help \n"+broadcast_msg,type);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =  new Intent(AllBroadcast.this, MainActivity.class);
                        i.putExtra("String",tv.getText().toString().trim());
                        i.putExtra("username",name.getText().toString());
                        startActivity(i);
                    }
                });
                myL.addView(tv);*/

            }
        });
        socket.on("new broadcast", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {

                            String s =(data.getString("username").trim());

                            String par[] =s.split("\"") ;

                            for (int i=0;i<par.length;i++){
                                System.out.println(par[i]+" "+i+"\n");
                            }
                           final String broadcast_user = par[3];
                            String broadcast_msg = par[9];
                            int type =  Character.getNumericValue(par[6].charAt(1));


                           // Toast.makeText(AllBroadcast.this,broadcast_msg,Toast.LENGTH_SHORT).show();
                            dymanicViews dmy = new dymanicViews(cxt);
                            final TextView tv =dmy.descriptionTextView(getApplicationContext(),"hello ,"+broadcast_user+" needs help\n"+broadcast_msg,type);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i =  new Intent(AllBroadcast.this, MainActivity.class);
                                    i.putExtra("username",name.getText().toString().trim());
                                    startActivity(i);
                                }
                            });
                            myL.addView(tv);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
             }
        });
    }
}