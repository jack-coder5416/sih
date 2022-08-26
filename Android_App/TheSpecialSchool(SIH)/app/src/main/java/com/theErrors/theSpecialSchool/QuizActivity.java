package com.theErrors.theSpecialSchool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    public static final Integer RecordAudioRequestCode = 1;
    TextToSpeech t1;
    String res = "";
    SpeechRecognizer sr = null;
    TextView tvQuestion;
    AppCompatButton btn1, btn2, btn3, btn4;
    RelativeLayout rlSpeech, rlFooter, rlQuestion;
    Intent i;
    ImageView ivMic;

    String ques[][] = new String[5][6];

    int count = 0;
    int score = 0;
    int subj = 0;
    int chap = 0;

    char chosen = ' ';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                    t1.setSpeechRate(.001f);
                }
            }
        });


        tvQuestion = findViewById(R.id.tvQuestion);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        ivMic = findViewById(R.id.ivMic);

        rlSpeech = findViewById(R.id.rlSpeech);
        rlFooter = findViewById(R.id.rlFooter);
        rlQuestion = findViewById(R.id.rlQuestion);
        rlQuestion.setVisibility(View.GONE);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);


        i = getIntent();
        subj = i.getIntExtra("Subject", 0);
        chap = i.getIntExtra("Chapter", 0);

        initQues();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setQues();
                rlQuestion.setVisibility(View.VISIBLE);
            }
        }, 500);

        rlFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                sr.startListening(intent);
            }
        });
        rlSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ques[count - 1][0] + "Options Are " + ques[count - 1][1] + ques[count - 1][2] + ques[count - 1][3] + ques[count - 1][4];
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    public void setQues() {
        if (count < 5) {
            tvQuestion.setText(ques[count][0]);
            btn1.setText(ques[count][1]);
            btn2.setText(ques[count][2]);
            btn3.setText(ques[count][3]);
            btn4.setText(ques[count][4]);
            chosen = ' ';
            count++;
        } else {
            Intent in = new Intent(QuizActivity.this, ScoreActivity.class);
            in.putExtra("Subject", subj);
            in.putExtra("Chapter", chap);
            in.putExtra("Score", score * 2);
            startActivity(in);
            finish();
        }
    }

    public void check() {
        if (ques[count - 1][5].charAt(0) == chosen) {
            score++;
            rlQuestion.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setQues();
                    rlQuestion.setVisibility(View.VISIBLE);
                }
            }, 500);
        } else {
            rlQuestion.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setQues();
                    rlQuestion.setVisibility(View.VISIBLE);
                }
            }, 500);
        }
    }

    public void initQues() {

        if (subj == 1 && chap == 0) {
            ques[0][0] = "1. We can view an objects / things from the ?";
            ques[0][1] = "A. Front View";
            ques[0][2] = "B. Side View";
            ques[0][3] = "C. Top View";
            ques[0][4] = "D. ALl of the above";
            ques[0][5] = "D";
            ques[1][0] = "2. If we are travelling by an aeroplane , then from which view will we see the things of the earth?";
            ques[1][1] = "A. Front View";
            ques[1][2] = "B. Side View";
            ques[1][3] = "C. Top View";
            ques[1][4] = "D. ALl of the above";
            ques[1][5] = "D";
            ques[2][0] = "3. If we are travelling by a bus , then from which view will we see the things of the earth?";
            ques[2][1] = "A. Front View";
            ques[2][2] = "B. Side View";
            ques[2][3] = "C. Top View";
            ques[2][4] = "D. ALl of the above";
            ques[2][5] = "D";
            ques[3][0] = "4. If we are travelling by a train , then from which view will we see the things of the earth?";
            ques[3][1] = "A. Front View";
            ques[3][2] = "B. Side View";
            ques[3][3] = "C. Top View";
            ques[3][4] = "D. ALl of the above";
            ques[3][5] = "D";
            ques[4][0] = "5. If we are travelling by a car , then from which view will we see the things of the earth?";
            ques[4][1] = "A. Front View";
            ques[4][2] = "B. Side View";
            ques[4][3] = "C. Top View";
            ques[4][4] = "D. ALl of the above";
            ques[4][5] = "D";
        }
        if (subj == 1 && chap == 1) {
            ques[0][0] = "1. Counting in 10’s What should come next ? 210, 220, 230,";
            ques[0][1] = "A. 240";
            ques[0][2] = "B. 235";
            ques[0][3] = "C. 245";
            ques[0][4] = "D. 250";
            ques[0][5] = "A";
            ques[1][0] = "2. 600+50+6 = ";
            ques[1][1] = "A. 650";
            ques[1][2] = "B. 656";
            ques[1][3] = "C. 665";
            ques[1][4] = "D. 566";
            ques[1][5] = "B";
            ques[2][0] = "3. Jump 7 steps backward 107,100,93, ";
            ques[2][1] = "A. 73";
            ques[2][2] = "B. 81";
            ques[2][3] = "C. 83";
            ques[2][4] = "D. 86";
            ques[2][5] = "D";
            ques[3][0] = "4. Write the number in words – 198";
            ques[3][1] = "A. One hundred eighty-eight";
            ques[3][2] = "B. One hundred ninety nine";
            ques[3][3] = "C. One hundred eighty nine";
            ques[3][4] = "D. One hundred ninety eight";
            ques[3][5] = "D";
            ques[4][0] = "5. Dhoni is playing on 96 runs. How many run he needs to complete century ?";
            ques[4][1] = "A. 6 runs";
            ques[4][2] = "B. 3 runs";
            ques[4][3] = "C. 4 runs";
            ques[4][4] = "D. 2 runs";
            ques[4][5] = "C";
        }
        if (subj == 1 && chap == 2) {
            ques[0][0] = "1. 9 less than 34 is ";
            ques[0][1] = "A. 25";
            ques[0][2] = "B. 24";
            ques[0][3] = "C. 43";
            ques[0][4] = "D. 29";
            ques[0][5] = "A";
            ques[1][0] = "2. 9 added to 28 gives ";
            ques[1][1] = "A. 19";
            ques[1][2] = "B. 47";
            ques[1][3] = "C. 37";
            ques[1][4] = "D. 29";
            ques[1][5] = "C";
            ques[2][0] = "3. Reducing 78 by 34 gives ";
            ques[2][1] = "A. 34";
            ques[2][2] = "B. 54";
            ques[2][3] = "C. 112";
            ques[2][4] = "D. 44";
            ques[2][5] = "D";
            ques[3][0] = "4. A Factory made 276 bulbs on the first day. On the second day it made 123 bulbs. How many bulbs did the factory make altogether ?";
            ques[3][1] = "A. 153";
            ques[3][2] = "B. 399";
            ques[3][3] = "C. 299";
            ques[3][4] = "D. 158";
            ques[3][5] = "B";
            ques[4][0] = "5. 89 added to 29 gives ";
            ques[4][1] = "A. 109";
            ques[4][2] = "B. 118";
            ques[4][3] = "C. 222";
            ques[4][4] = "D. None of the above";
            ques[4][5] = "B";
        }
        if (subj == 1 && chap == 3) {
            ques[0][0] = "1. Height of a child can be in ";
            ques[0][1] = "A. Litre";
            ques[0][2] = "B. Kilometer";
            ques[0][3] = "C. Meter";
            ques[0][4] = "D. Kilolitre";
            ques[0][5] = "C";
            ques[1][0] = "2. Which covers shortest distance from the following ?";
            ques[1][1] = "A. 100 Centimeter";
            ques[1][2] = "B. 50 Meter";
            ques[1][3] = "C. 1 Millimeter";
            ques[1][4] = "D. None of the above";
            ques[1][5] = "C";
            ques[2][0] = "3. Which covers the longest distance from the following ";
            ques[2][1] = "A. 100 Centimeter";
            ques[2][2] = "B. 50 Meter";
            ques[2][3] = "C. 1 Millimeter";
            ques[2][4] = "D. None of the above";
            ques[2][5] = "B";
            ques[3][0] = "4.  Km stands for –";
            ques[3][1] = "A. kilogram";
            ques[3][2] = "B. kilomilligram";
            ques[3][3] = "C. kilometer";
            ques[3][4] = "D. ALl of the above";
            ques[3][5] = "C";
            ques[4][0] = "Which is the biggest unit of distance from the following ?";
            ques[4][1] = "A. Kilometer";
            ques[4][2] = "B. Meter";
            ques[4][3] = "C. Centimeter";
            ques[4][4] = "D. Millimeter";
            ques[4][5] = "A";
        }
        if (subj == 1 && chap == 4) {
            ques[0][0] = "1. How many corners will be there in a ludo dice ?";
            ques[0][1] = "A. 6";
            ques[0][2] = "B. 8";
            ques[0][3] = "C. 10";
            ques[0][4] = "D. 12";
            ques[0][5] = "B";
            ques[1][0] = "2. What is Tangram ?";
            ques[1][1] = "A. Name of a city";
            ques[1][2] = "B. An old chinese puzzle";
            ques[1][3] = "C. An animal";
            ques[1][4] = "D. None of the above";
            ques[1][5] = "B";
            ques[2][0] = "3. How many corners will be there in a wall?";
            ques[2][1] = "A. 6";
            ques[2][2] = "B. 8";
            ques[2][3] = "C. 0";
            ques[2][4] = "D. 2";
            ques[2][5] = "C";
            ques[3][0] = "4. How many edges will be there in a rectangular eraser  ?";
            ques[3][1] = "A. 4";
            ques[3][2] = "B. 8";
            ques[3][3] = "C. 6";
            ques[3][4] = "D. 12";
            ques[3][5] = "D";
            ques[4][0] = "5. How many corners will be there in a sphere ?";
            ques[4][1] = "A. 0";
            ques[4][2] = "B. 2";
            ques[4][3] = "C. 1";
            ques[4][4] = "D. 4";
            ques[4][5] = "A";
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                chosen = 'A';
                check();
                break;
            case R.id.btn2:
                chosen = 'B';
                check();
                break;
            case R.id.btn3:
                chosen = 'C';
                check();
                break;
            case R.id.btn4:
                chosen = 'D';
                check();
                break;
        }
    }

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            ivMic.setImageResource(R.drawable.ic_mic_on);
            Toast.makeText(QuizActivity.this, "Listening", Toast.LENGTH_SHORT).show();
        }

        public void onBeginningOfSpeech() {
            ivMic.setImageResource(R.drawable.ic_mic_on);
        }

        public void onRmsChanged(float rmsdB) {
        }

        public void onBufferReceived(byte[] buffer) {
        }

        public void onEndOfSpeech() {
            ivMic.setImageResource(R.drawable.ic_mic_off);
            Toast.makeText(QuizActivity.this, "Ended", Toast.LENGTH_SHORT).show();
            //Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
        }

        public void onResults(Bundle results) {
            String z = "";
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < matches.size(); i++) {
                z = z + matches.get(i);
            }
            res = z.toUpperCase();
            char inp = res.charAt(0);
            if (inp == 'A' || inp == 'B' || inp == 'C' || inp == 'D') {
                switch (inp) {
                    case 'A':
                        btn1.performClick();
                        break;
                    case 'B':
                        btn2.performClick();
                        break;
                    case 'C':
                        btn3.performClick();
                        break;
                    case 'D':
                        btn4.performClick();
                        break;
                }
            } else {
                Toast.makeText(QuizActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rlFooter.performClick();
                    }
                }, 1000);
            }
        }

        public void onPartialResults(Bundle partialResults) {

            //Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {

            //Log.d(TAG, "onEvent " + eventType);
        }
    }
}