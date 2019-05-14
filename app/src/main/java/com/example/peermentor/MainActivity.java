package com.example.peermentor;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;




public class MainActivity extends AppCompatActivity  {

    private Socket socket;
    public RecyclerView myRecylerView ;
    public List<Message> MessageList ;
    public ChatBoxAdapter chatBoxAdapter;
    public  EditText messagetxt ;
    public  Button send ;
    public String Nickname;
    public  Button logout;
    public  TextView t;
    TextView dumy ;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        Button clear = findViewById(R.id.clear);
        dumy = findViewById(R.id.ttt);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dumy.setText("");
            }
        });
        Nickname=i.getStringExtra("username") ;
        messagetxt =  findViewById(R.id.message) ;
        send = findViewById(R.id.send);
        logout = findViewById(R.id.logout);
        MessageList = new ArrayList<>();
        t = findViewById(R.id.ttt);
        t.setMovementMethod(new ScrollingMovementMethod());
        myRecylerView = findViewById(R.id.messagelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());
        try {
            socket = IO.socket("http://192.168.1.105:3000");
            socket.connect();
            socket.emit("join",Nickname);
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }

        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public
            void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public
                    void run() {
                        String data = (String) args[0];
                        // get the extra data from the fired event and display a toast
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();




                    }
                });
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(!messagetxt.getText().toString().isEmpty()){
                            JsonObject jsonObject= new JsonObject();
                            jsonObject.addProperty("username",Nickname);
                            jsonObject.addProperty("message",messagetxt.getText().toString().trim());
                            socket.emit("send message",jsonObject);
                            messagetxt.setText("");
                        }
                    }
                });
        socket.on("new message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            t = findViewById(R.id.ttt);
                            String msg = data.getString("msg");

                            t.append(msg+'\n');
                            String nickname = data.getString("user");
                            String message = data.getString("msg");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, home.class);
                Toast.makeText(MainActivity.this, "GoodBye!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

}

