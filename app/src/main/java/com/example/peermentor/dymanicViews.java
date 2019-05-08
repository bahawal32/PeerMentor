package com.example.peermentor;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.TextView;

public class dymanicViews {
Context cnt;

    public dymanicViews(Context cnt) {
        this.cnt = cnt;
    }
    public TextView descriptionTextView(Context context,String text,String type ){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(20);
        textView.setHeight(30);
        textView.setTextColor(Color.rgb(0,0,0));
        textView.setText("Dynamically added text view " + text);
        textView.setMaxEms(8);
        switch (type)
        {
            case "Tutor":
                textView.setBackgroundResource(R.drawable.tutor);
                break;
            case "carpool":
                textView.setBackgroundResource(R.drawable.carpooling);
                break;
            case "stationary":
                textView.setBackgroundResource(R.drawable.stationary);
                break;
                default:
                    textView.setBackgroundResource(R.drawable.logo);
        }
        return textView;
    }


}
