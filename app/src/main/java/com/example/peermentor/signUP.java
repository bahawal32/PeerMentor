package com.example.peermentor;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button signMeUP = findViewById(R.id.SignmeUp);
        final EditText newusername  = findViewById(R.id.SignUserName);
        final EditText newname      = findViewById(R.id.SignName);
        final EditText newpassword  = findViewById(R.id.loginPassword);

        signMeUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = newusername.getText().toString();
                final String name = newname.getText().toString();
                final String password = newpassword.getText().toString();
                registerUser(username,name,password);
            }
        });

    }

    public void registerUser(String usermane, String name, String password) {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("username",usermane);
        json.addProperty("password", password);

        Ion.with(this).load("http://192.168.56.1:3000/register").setJsonObjectBody(json).asJsonObject().
                setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Toast.makeText(signUP.this, "Check karo", Toast.LENGTH_SHORT).show();
                      /*  Intent intent = new Intent(signUP.this, AllBroadcast.class);
                        startActivity(intent);*/
                    }
                });

    }

}