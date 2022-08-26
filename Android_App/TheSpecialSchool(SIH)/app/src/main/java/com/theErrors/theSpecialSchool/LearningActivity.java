package com.theErrors.theSpecialSchool;

import android.content.Intent;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class LearningActivity extends AppCompatActivity {

    static boolean flag = false;
    public static final Integer RecordAudioRequestCode = 1;
    String res = "";
    SpeechRecognizer sr = null;
    String subject[] = {"Hindi", "Mathematics", "English Language", "English Literature", "Environmental Sciences", "General Knowledge"};
    String chapter[];
    RelativeLayout rlMindmaps, rlPodcasts, rlQuiz, rlGames, rlFooter, rlSpeech;
    FirebaseAuth mAuth;
    TextView tvChapter;
    ImageView ivBack, ivMic;
    CircleImageView civUser;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String uid = "";


    int subj, chap;

    int comps[] = {0, 0, 0, 0, 0, 0};

    TextToSpeech t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        uid = FirebaseAuth.getInstance().getUid();
        Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        rlSpeech = findViewById(R.id.rlSpeech);
        rlFooter = findViewById(R.id.rlFooter);
        ivMic = findViewById(R.id.ivMic);
        civUser = findViewById(R.id.civUser);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Children").child(uid).child(uid);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(LearningActivity.this).load(uri).into(civUser);
            }
        });

        civUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningActivity.this, UserInfoActivity.class));
            }
        });

        Intent i = getIntent();
        subj = i.getIntExtra("Subject", 0);
        chap = i.getIntExtra("Chapter", 0);
        //Toast.makeText(getApplicationContext(), subj + " " + chap, Toast.LENGTH_SHORT).show();
        if (subj == 1) {
            chapter = new String[14];
            chapter[0] = "Where To Look From";
            chapter[1] = "Fun With Numbers";
            chapter[2] = "Give and Take";
            chapter[3] = "Long and Short";
            chapter[4] = "Shapes and Designs";
            chapter[5] = "Fun with Give and Take";
            chapter[6] = "Time Goes On";
            chapter[7] = "Who is Heavier";
            chapter[8] = "How Many Times";
            chapter[9] = "Play with Patterns";
            chapter[10] = "Jugs and Mugs";
            chapter[11] = "Can we Share";
            chapter[12] = "Smart Charts";
            chapter[13] = "Rupees and Paise";
        }
        if (subj == 3) {
            chapter = new String[20];
            chapter[0] = "Good Morning";
            chapter[1] = "The Magic Garden";
            chapter[2] = "Bird Talk";
            chapter[3] = "Nina and The Baby Sparrows";
            chapter[4] = "Little By Little";
            chapter[5] = "The Enormous Turnip";
            chapter[6] = "Sea Song";
            chapter[7] = "A Little Fish Story";
            chapter[8] = "The Balloon Man";
            chapter[9] = "The Yellow Butterfly";
            chapter[10] = "Trains";
            chapter[11] = "The Story of The Road";
            chapter[12] = "Puppy and I";
            chapter[13] = "Little Tiger , Big Tiger";
            chapter[14] = "What's in the Mailbox?";
            chapter[15] = "My Silly Sister";
            chapter[16] = "Don't Tell";
            chapter[17] = "He is my Brother";
            chapter[18] = "How Creatures Move";
            chapter[19] = "The Ship of The Desert";
        }
        if (subj == 4) {
            chapter = new String[24];
            chapter[0] = "Poonam's Day Out";
            chapter[1] = "The Plant Fairy";
            chapter[2] = "Water O Water";
            chapter[3] = "Our First School";
            chapter[4] = "Chhotu's House";
            chapter[5] = "Foods We Eat";
            chapter[6] = "Saying Without Speaking";
            chapter[7] = "Flying High";
            chapter[8] = "It's Raining";
            chapter[9] = "What Is Cooking";
            chapter[10] = "From Here To There";
            chapter[11] = "Work We Do";
            chapter[12] = "Sharing Our Feeling";
            chapter[13] = "The Story Of Food";
            chapter[14] = "Making Pots";
            chapter[15] = "Games We Play";
            chapter[16] = "Here Comes a Letter";
            chapter[17] = "A House Like This!";
            chapter[18] = "Our Friends - Animals";
            chapter[19] = "Drop By Drop";
            chapter[20] = "Families Can Be Different";
            chapter[21] = "Left - Right";
            chapter[22] = "A Beautiful Cloth";
            chapter[23] = "Web Of Life";
        }

        mAuth = FirebaseAuth.getInstance();

        ivBack = findViewById(R.id.ivBack);
        tvChapter = findViewById(R.id.tvChapter);
        rlGames = findViewById(R.id.rlGames);
        rlMindmaps = findViewById(R.id.rlMindmaps);
        rlPodcasts = findViewById(R.id.rlPodcasts);
        rlQuiz = findViewById(R.id.rlQuiz);

        tvChapter.setText(chapter[i.getIntExtra("Chapter", 0)]);

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

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rlGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subj == 1 && chap == 0) {
                    startActivity(new Intent(LearningActivity.this, GameMathActivity.class));
                } else if (subj == 3 && chap == 0) {
                    startActivity(new Intent(LearningActivity.this, GameEngActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rlPodcasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subj == 4 && chap != 0) {
                    Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();
                } else {
                    Intent in = new Intent(LearningActivity.this, PodcastActivity.class);
                    in.putExtra("Subject", subj);
                    in.putExtra("Chapter", chap);
                    startActivity(in);
                }
            }
        });
        rlQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subj == 1 && chap < 5) {
                    givenQuiz();
                    //Intent in = new Intent(LearningActivity.this, QuizActivity.class);
                    //in.putExtra("Subject", subj);
                    //in.putExtra("Chapter", chap);
                    //startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rlMindmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subj == 1) {
                    Intent in = new Intent(LearningActivity.this, MindMapActivity.class);
                    in.putExtra("Subject", subj);
                    in.putExtra("Chapter", chap);
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();

                }
            }
        });

        rlSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "How would you like to learn : Mind Maps , Podcasts , Games , Quiz ";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            ivMic.setImageResource(R.drawable.ic_mic_on);
            Toast.makeText(LearningActivity.this, "Listening", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(LearningActivity.this, "Ended", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
            if (res.equals("BACK")) {
                finish();
            } else if (res.equals("PODCAST")) {
                rlPodcasts.performClick();
            } else if (res.equals("MIND MAPS")) {
                rlMindmaps.performClick();
            } else if (res.equals("GAMES")) {
                rlGames.performClick();
            } else if (res.equals("QUIZ")) {
                rlQuiz.performClick();
            } else {
                Toast.makeText(LearningActivity.this, "Try Again " + res, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivMic.performClick();
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


    public void givenQuiz() {
        //flag = false;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Children").child(uid).child("Progress");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comps[0] = Integer.parseInt(snapshot.child("Hindi").getValue().toString());
                comps[1] = Integer.parseInt(snapshot.child("Mathematics").getValue().toString());
                comps[2] = Integer.parseInt(snapshot.child("EnglishLanguage").getValue().toString());
                comps[3] = Integer.parseInt(snapshot.child("EnglishLiterature").getValue().toString());
                comps[4] = Integer.parseInt(snapshot.child("Environmental").getValue().toString());
                comps[5] = Integer.parseInt(snapshot.child("GeneralKnowledge").getValue().toString());
                //Toast.makeText(getApplicationContext(), Arrays.toString(comps), Toast.LENGTH_SHORT).show();
                if (subj == 1 && (chap + 1) > comps[1]) {
                    //flag = true;
                   // Toast.makeText(getApplicationContext(), "Runs", Toast.LENGTH_SHORT).show();
                    ProgressData pd = new ProgressData(comps[0] + "", (chap + 1) + "", comps[2] + "", comps[3] + "", comps[4] + "", comps[5] + "");
                    databaseReference.setValue(pd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                           // Toast.makeText(getApplicationContext(), "Progress Updated" + flag, Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(LearningActivity.this, QuizActivity.class);
                            in.putExtra("Subject", subj);
                            in.putExtra("Chapter", chap);
                            startActivity(in);
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Already Given", Toast.LENGTH_SHORT).show();
                }
                if (subj == 3 && (chap + 1) > comps[3]) {
                    //flag = true;
                    Toast.makeText(getApplicationContext(), "Runs", Toast.LENGTH_SHORT).show();
                    ProgressData pd = new ProgressData(comps[0] + "", comps[1] + "", comps[2] + "", (chap + 1) + "", comps[4] + "", comps[5] + "");
                    databaseReference.setValue(pd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                           // Toast.makeText(getApplicationContext(), "Progress Updated", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(LearningActivity.this, QuizActivity.class);
                            in.putExtra("Subject", subj);
                            in.putExtra("Chapter", chap);
                            startActivity(in);
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Already Given", Toast.LENGTH_SHORT).show();
                }
                if (subj == 4 && (chap + 1) > comps[4]) {
                    //flag = true;
                    //Toast.makeText(getApplicationContext(), "Runs", Toast.LENGTH_SHORT).show();
                    ProgressData pd = new ProgressData(comps[0] + "", comps[1] + "", comps[2] + "", comps[3] + "", (chap + 1) + "", comps[5] + "");
                    databaseReference.setValue(pd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //Toast.makeText(getApplicationContext(), "Progress Updated", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(LearningActivity.this, QuizActivity.class);
                            in.putExtra("Subject", subj);
                            in.putExtra("Chapter", chap);
                            startActivity(in);
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Already Given", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(getApplicationContext(), flag + "", Toast.LENGTH_SHORT).show();

    }
}


