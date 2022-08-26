package com.theErrors.theSpecialSchool;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PodcastActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    String subject[] = {"Hindi", "Mathematics", "English Language", "English Literature", "Environmental Sciences", "General Knowledge"};
    String chapter[];
    String pod;

    int comps[] = {0, 0, 0, 0, 0, 0};
    Intent i;
    int subj;
    int chap;
    TextView tvName;
    ImageView ivBack;
    MediaPlayer mp;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    AppCompatButton b1, b2, b3, b4;
    ImageView ivAlbum;
    String uid = "";
    double startTime = 0;
    double finalTime = 0;

    private Handler myHandler = new Handler();
    ;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1, tx2;

    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        uid = FirebaseAuth.getInstance().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        ivAlbum = findViewById(R.id.ivAlbum);
        tx1 = findViewById(R.id.textView2);
        tx2 = findViewById(R.id.textView3);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        b2.setEnabled(true);
        b3.setEnabled(false);

        i = getIntent();
        subj = i.getIntExtra("Subject", 0);
        chap = i.getIntExtra("Chapter", 0);
        chapInit();
        podInit();

        tvName = findViewById(R.id.tvName);
        ivBack = findViewById(R.id.ivBack);

        tvName.setText(chapter[chap]);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
                finish();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                mp.start();

                finalTime = mp.getDuration();
                startTime = mp.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                seekbar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);
                b2.setEnabled(true);
                b3.setEnabled(false);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                mp.pause();
                b2.setEnabled(false);
                b3.setEnabled(true);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mp.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mp.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public void chapInit() {
        //Toast.makeText(getApplicationContext(), "chap", Toast.LENGTH_SHORT).show();
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
    }

    public void podInit() {

        if (subj == 1) {
            pod = "C" + (chap + 1) + ".mp3";
        }
        if (subj == 3) {
            pod = "Eng_lit_ch_" + (chap + 1) + ".mp3";
        }
        if (subj == 4) {
            pod = "C" + (chap + 1) + ".mp3";
        }
        //Toast.makeText(getApplicationContext(), pod, Toast.LENGTH_SHORT).show();
        podPlay();

    }

    public void podPlay() {
        /*databaseReference = firebaseDatabase.getReference().child("Children").child(uid).child("Progress");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comps[0] = Integer.parseInt(snapshot.child("Hindi").getValue().toString());
                comps[1] = Integer.parseInt(snapshot.child("Mathematics").getValue().toString());
                comps[2] = Integer.parseInt(snapshot.child("EnglishLanguage").getValue().toString());
                comps[3] = Integer.parseInt(snapshot.child("EnglishLiterature").getValue().toString());
                comps[4] = Integer.parseInt(snapshot.child("Environmental").getValue().toString());
                comps[5] = Integer.parseInt(snapshot.child("GeneralKnowledge").getValue().toString());
                Toast.makeText(getApplicationContext(), Arrays.toString(comps), Toast.LENGTH_SHORT).show();
                if (subj == 1 && (chap + 1) > comps[1]) {
                    Toast.makeText(getApplicationContext(), "Runs", Toast.LENGTH_SHORT).show();
                    ProgressData pd = new ProgressData(comps[0] + "", (chap + 1) + "", comps[2] + "", comps[3] + "", comps[4] + "", comps[5] + "");
                    databaseReference.setValue(pd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Progress Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (subj == 3 && (chap + 1) > comps[3]) {
                    Toast.makeText(getApplicationContext(), "Runs", Toast.LENGTH_SHORT).show();
                    ProgressData pd = new ProgressData(comps[0] + "", comps[1] + "", comps[2] + "", (chap + 1) + "", comps[4] + "", comps[5] + "");
                    databaseReference.setValue(pd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Progress Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (subj == 4 && (chap + 1) > comps[4]) {
                    Toast.makeText(getApplicationContext(), "Runs", Toast.LENGTH_SHORT).show();
                    ProgressData pd = new ProgressData(comps[0] + "", comps[1] + "", comps[2] + "", comps[3] + "", (chap + 1) + "", comps[5] + "");
                    databaseReference.setValue(pd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Progress Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        firebaseStorage = FirebaseStorage.getInstance();
        if (subj == 1) {
            storageReference = firebaseStorage.getReference("Podcast").child("maths").child(pod);
        } else if (subj == 3) {
            storageReference = firebaseStorage.getReference("Podcast").child("english").child(pod);
        } else {
            storageReference = firebaseStorage.getReference("Podcast").child("environmental").child("kannada").child(pod);

        }

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                final String url = uri.toString();
                try {
                    mp.setDataSource(url);
                    mp.setOnPreparedListener(PodcastActivity.this);
                    mp.prepareAsync();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finalTime = mp.getDuration();
                            startTime = mp.getCurrentPosition();
                            seekbar.setMax((int) finalTime);
                            seekbar.setProgress((int) startTime);
                            myHandler.postDelayed(UpdateSongTime, 100);
                        }
                    }, 2000);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mp.getCurrentPosition();
            finalTime = mp.getDuration();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            tx2.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    finalTime)))
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

}