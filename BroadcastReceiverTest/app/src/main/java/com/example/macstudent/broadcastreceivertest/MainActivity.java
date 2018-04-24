package com.example.macstudent.broadcastreceivertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("JENELLE", "app laoded!");
    }

    public void publishButtonPressed(View view) {
        // create a custom intent
        Intent intent = new Intent("com.example.macstudent.broadcastreceivertest.MYCUSTOMINTENT");

        // send the intent
        sendBroadcast(intent);
    }

}
