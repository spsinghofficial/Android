package com.example.macstudent.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by macstudent on 2018-04-17.
 */

public class AirplaneModeReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        Log.d("JENELLE", "message received");

        // 1. what message do you want to subscribe to?
        if (intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {

            // 2. what do you want to do when your receive that message?
            Toast.makeText(context, "Airplane mode changed!", Toast.LENGTH_LONG).show();
        }
    }
}
