package com.example.macstudent.smswithdexter;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.telephony.SmsManager;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "JENELLE";

    PermissionListener smsPermissionListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createPermissionListener();
    }

    // setup your permission listener
    public void createPermissionListener() {

        // check if permission listenr variable is null
        // if yes, then give it a value
        // otherwise, do nothing
        if (smsPermissionListener == null) {
            // give the varaible a value

            smsPermissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    // person pressed ALLOW

                    // send the sms
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("4150005555",null, "the test is thursday", null, null);

                    // show a message to the user that the message
                    TextView t = (TextView) findViewById(R.id.statusMessage);
                    t.setText("Message sent!");
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    // person presses DENY

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    // if current permission is set to DENY
                    token.continuePermissionRequest();
                }
            };


        }

    }

    public void sendButtonPressed(View view) {
        Log.d(TAG, "sending a sms");

        // ask for permissions
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(smsPermissionListener)
                .check();


    }
}













