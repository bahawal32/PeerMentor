package com.example.peermentor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class signUP extends AppCompatActivity {
    EditText newusername ;
    EditText newname    ;
    EditText newpassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button signMeUP = findViewById(R.id.SignmeUp);
          newusername  = findViewById(R.id.SignUserName);
          newname      = findViewById(R.id.SignName);
          newpassword  = findViewById(R.id.loginPassword);

        signMeUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String username = newusername.getText().toString();
                 String name = newname.getText().toString();
                 String password = newpassword.getText().toString();
                registerUser(username,name,password);
            }
        });

    }

    public void registerUser(String usermane, String name, String password) {
        JsonObject json = new JsonObject();
        json.addProperty("username",usermane);
        json.addProperty("name", name);
        json.addProperty("password", password);
        Ion.with(this)
                // 192.168.56.1
                .load("http://10.20.216.89:3000/register")
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if(result.equals("\"Registration success\""))
                        {
                            Intent intent = new Intent(signUP.this, home.class);
                            String uname  = newusername.getText().toString();
                            intent.putExtra("username",uname);
                            Toast.makeText(signUP.this,"Welcome, "+uname,Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(signUP.this, result, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
/*
        Ion.with(this)
                .load("http://10.20.216.89/register")
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Toast.makeText(signUP.this,result , Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(signUP.this, home.class);
                        startActivity(intent);
                    }
                });
*/
    }

}