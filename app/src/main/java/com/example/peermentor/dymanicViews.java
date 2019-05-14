package com.example.peermentor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class dymanicViews  extends AllBroadcast{
Context cnt;
    Intent chat;
    public dymanicViews(Context cnt) {
        this.cnt = cnt;
    }
    public TextView descriptionTextView(Context context, String text, int type ,   Intent i){
        chat = i;
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(20);
        textView.setHeight(30);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(chat);
            }
        });
        textView.setTextColor(Color.rgb(0,0,0));
        textView.setText("Dynamically added text view " + text);
        textView.setMaxEms(8);
        switch (type)
        {
            case 1:
                textView.setBackgroundResource(R.drawable.tutor);

                break;
            case 2:
                textView.setBackgroundResource(R.drawable.carpooling);
                break;
            case 3:
                textView.setBackgroundResource(R.drawable.stationary);
                break;
                default:
                    textView.setBackgroundResource(R.drawable.tutor);
        }
        return textView;
    }


}
