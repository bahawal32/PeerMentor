package com.example.peermentor;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button send = findViewById(R.id.send);
         final EditText messsage =  findViewById(R.id.msg);
         final TextView msgBox =  findViewById(R.id.messageBox);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgBox.append("\n"+messsage.getText());
                messsage.setText("");
            }
        });
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,home.class);
                startActivity(intent);
            }
        });
    }
}
