package com.example.peermentor;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button login = findViewById(R.id.login);
        Button sign_up = findViewById(R.id.signup);

        final EditText loginusername  = findViewById(R.id.loginUserName);
        final EditText loginpassword  = findViewById(R.id.loginPassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginUser(loginusername.getText().toString(),loginpassword.getText().toString());
                Intent intent = new Intent(home.this, AllBroadcast.class);
                startActivity(intent);

            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, signUP.class);
                startActivity(intent);
            }
        });


    }
    void loginUser(String UserName,String password)
    {
        JsonObject json = new JsonObject();
        json.addProperty("username", UserName);
        json.addProperty("password", password);

        Ion.with(this)
                // 192.168.56.1
                .load("http://192.168.56.1:3000/login")
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                            String target;
                        if(result.equals("\"Login success\""))
                        {
                            Toast.makeText(home.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(home.this, AllBroadcast.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(home.this, result, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}

