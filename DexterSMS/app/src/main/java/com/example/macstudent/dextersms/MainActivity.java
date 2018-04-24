package com.example.macstudent.dextersms;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "JENELLE";


    private PermissionListener sendSMSPermissionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createPermissionListener();


    }


    public void createPermissionListener() {
        if (sendSMSPermissionListener == null) {
            sendSMSPermissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    // permission was granted.
                    Log.d(TAG, "Permission granted!");
                    sendSMS();
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    // you don't have permissions
                    TextView t = (TextView) findViewById(R.id.statusMessage);
                    t.setText("Sorry, I don't have permission to send an SMS.");
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    // run this if the person pressed deny the first time
                    Log.d(TAG, "PERMISSION NEEDS TO BE REQUESTED!");
                    token.continuePermissionRequest();
                }
            };
        }
    }

    public void sendSMS() {
        // UI BAKWAS - get the phone number and message from the UI
        EditText editText1 = (EditText) findViewById(R.id.telephone);
        EditText editText2 = (EditText) findViewById(R.id.textMessage);

        String tel = editText1.getText().toString();
        String msg = editText2.getText().toString();


        // LOGIC - send the sms
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(tel,null, msg, null, null);


        // UI BEYKAR -

        // update the text box to show that the message was sent
        TextView t = (TextView) findViewById(R.id.statusMessage);
        t.setText("Message sent!");

        // clear the message text box
        editText2.setText("");
    }

    public void sendButtonPressed(View view) {
        Log.d(TAG, "sending a sms");

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(sendSMSPermissionListener)
                .check();

    }




}
