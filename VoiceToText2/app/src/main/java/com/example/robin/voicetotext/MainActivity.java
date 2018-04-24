package com.example.robin.voicetotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    // user interface variables
    private TextView mTextView;

    // identifier code for this recongizer
    private final int SPEECH_REQUEST_CODE = 500;  // any random number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup ui variables
        mTextView = (TextView)findViewById(R.id.question);


    }


    public void buttonPressed(View view) {
        // start listening for text
        startSpeechRecognizer();
    }


    public void dialPhoneNumber(String phone){

        // 1. Convert the phone number into a format
        // that android will understand
        // Format:  "tel: 289-555-2222
        String phoneNumber = String.format("tel: %s", phone);

        // 2. Create code to dial a phone call

        // Create the "intent" for dialing a phone call
        // This will open your default phone dialer app
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);

        // What phone number do you want to call?
        dialIntent.setData(Uri.parse(phoneNumber));

        // check yoru Android phone to see if there is a default
        // application for making phone calls
        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            // if yes, open the app
            startActivity(dialIntent);
        } else {
            // if no, then show an error message
            String msg = "Sorry, cannot find an app that makes phone calls. ";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void startSpeechRecognizer() {

        // this is the default microphone popup box
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // SETUP NONSENSE: the next two lines are all setup nonsense
        // you can also do: "EXTRA_LANGUAGE_MODEL, en-us" for a specific locale
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // configure the message on the microphone popup box
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What color is the sky?");

        // uncomment this code if you want to get more than one result back
        // from the speech recognizer
        // intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        try {
            // show the popup box
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }
        catch (ActivityNotFoundException a) {
            // Sometimes you are using a phone that doesn't have speech to text functions
            // If this happens, then show error message.
            String msg = "Your phone doesn't support speech to text.";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }


    // This function gets called when the person finishes talking
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get results from speech recognizer and save to a list / array
                List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                // get the first item in the list -> this is what
                // google thinks the person said
                String answer = results.get(0);


                // uncomment this if you want to see more than one result
                /*
                for (int i = 0; i < results.size(); i++) {
                    String word = results.get(i);
                    mTextView.append(word);
                }
                */

                mTextView.setText("You said: " + answer);

                dialPhoneNumber(answer);

                if (answer.indexOf("blue") > -1)
                    Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
