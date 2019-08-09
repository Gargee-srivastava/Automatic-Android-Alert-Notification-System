package com.example.hellow;
/**
 *DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.

 *Author: GARGEE SRIVASTAVA
 *Contact details: srivastava.gargee@gmail.com
 *Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.design.widget.Snackbar.*;
import static android.support.design.widget.Snackbar.LENGTH_INDEFINITE;


public class MainActivity<c, ACTION_HEADSET_PLUG, runnable> extends AppCompatActivity {
    LinearLayout mainLayout;
    public TextView tvData;
    Vibrator vb;
    String n1,n2;
    TimerTask mTimerTask;
    final Handler handler = new Handler();
    final Handler handle=new Handler();
    Timer t = new Timer();
    TextView hTextView;
    TableRow hTableRow;
    Button hButton, hButtonStop;
    LinearLayout s1;
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;

    private int nCounter = 0;
    public static final String URL_ATNDC = "http://192.168.43.140/notify/Test.php";  //insert your url here..you can get the ip of your device from typing ipconfig in command prompt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.mainLayout);
        vb=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        s1=(LinearLayout)findViewById(R.id.screen);
        s1.setVisibility(s1.GONE); // an invisible layout for screen flash

        mButtonSpeak = findViewById(R.id.button_speak);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
        s1=(LinearLayout)findViewById(R.id.screen);
        //  s1.setVisibility(s1.INVISIBLE);
        // mButtonSpeak.setOnClickListener(new View.OnClickListener() {


        // showSnackbar();
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
        //mEditText = findViewById(R.id.edit_text);
        // tvData = (TextView) findViewById(R.id.tvJsonItem);

handle.postDelayed(r,20000);


    }

    private Runnable r=new Runnable() {
        @Override
        public void run() {
            Server();

        handle.postDelayed(this,20000);


    }
    };


//async task to request the server
    public void Server(){
        //change the id and take it from the user page...here it has been saved manually.
        final String id ="1";
        Timer timer = new Timer();


        class Attendance extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute ();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler res = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<> ();
                //for sending id to the server
                params.put ("roll", id);
                //returning the response
                return res.sendPostRequest(URL_ATNDC, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute (s);
                try {
                    //converting response to json object
                        JSONObject obj = new JSONObject(s);
                        //if no error in response

                        String n1 = obj.getString("id");
                        String n2 = obj.getString("message");
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        //cases to check which alert to trigger
                        if (n1.equals(id)) {
                            if (n2.equals("1")) {           //1,2,3,4 here are used as alert id
                                vb.vibrate(2000);
                            } else if (n2.equals("2")) {
                                showSnackbar();
                            } else if (n2.equals("3")) {
                                speak();
                            } else if (n2.equals("4")) {
                                doTimerTask();
                            } else {
                                vb.vibrate(250);
                            }

                        }

                }catch(JSONException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                    }

            }

        }

        Attendance prod_exec = new Attendance ();
        prod_exec.execute ();
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.toString().equals("yes")) {
            showSnackbar();

        }
        else{

            Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
        }
    }*/
    //c=tvData.toString();

    public void showSnackbar() {
        //c=tvData.toString();

//used for showing the text in android
        Snackbar snackbar = (Snackbar) make(mainLayout, "Your Attendance is low", LENGTH_INDEFINITE)  //change the text from here and use the database to get the text automatically
                                                                                                                 //i had given here manually
                .setAction("close", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorAccent));
        //String c="no";
        // if(n2.equals("pass")) {
        //}
           snackbar.show();
    }

  //for the screen flash
    public void doTimerTask(){

        mTimerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        nCounter++;
                        // update TextView
                        s1.setVisibility(s1.VISIBLE);
                        if(nCounter==2)
                        {
                            stopTask();
                        }

                        Log.d("TIMER", "TimerTask run");
                    }
                });
            }};

        // public void schedule (TimerTask task, long delay, long period)
        t.schedule(mTimerTask, 3, 3000);  //

    }

    public void stopTask(){
        nCounter=0;

        if(mTimerTask!=null){
            s1.setVisibility(s1.GONE);
            mButtonSpeak.setVisibility(mButtonSpeak.GONE);

            Log.d("TIMER", "timer canceled");
            mTimerTask.cancel();

        }

    }

//for the audio
    public void speak() {
        nCounter=0;
        mTimerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        nCounter++;
                        // update TextView
                        mButtonSpeak.setVisibility(mButtonSpeak.VISIBLE);
                        String text = "your attendance is low";
                        //change the text from here and use the database to get the text automatically
                        //i had given here manually

                        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        if(nCounter==4)
                        {
                            stopTask();
                        }

                        Log.d("TIMER", "TimerTask run");
                    }
                });
            }};

        // public void schedule (TimerTask task, long delay, long period)
        t.schedule(mTimerTask, 3, 3000);  //


        String text = "your attendance is low";


        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }


}

