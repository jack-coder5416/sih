package com.theErrors.theSpecialSchool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChapterActivity extends AppCompatActivity {

    public static final Integer RecordAudioRequestCode = 1;
    String res = "";
    SpeechRecognizer sr = null;
    RelativeLayout rlFooter, rlSpeech;
    ImageView ivMic;
    TextView tvSubject;
    RecyclerView rvChapter;
    ImageView ivBack;
    CircleImageView civUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Intent i;
    int subj;
    int comps = 0;
    int chapCount = 0;
    String uid = "";
    String chapter[];
    boolean cmp[];
    int img[];
    String subject[] = {"Hindi", "Mathematics", "English Language", "English Literature", "Environmental Sciences", "General Knowledge"};

    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        i = getIntent();

        comps = i.getIntExtra("Complete", 0);
        Toast.makeText(getApplicationContext(), comps + "", Toast.LENGTH_SHORT).show();
        uid = FirebaseAuth.getInstance().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        subj = i.getIntExtra("Subject", 0);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        rlSpeech = findViewById(R.id.rlSpeech);
        rlFooter = findViewById(R.id.rlFooter);
        ivMic = findViewById(R.id.ivMic);
        ivBack = findViewById(R.id.ivBack);
        tvSubject = findViewById(R.id.tvSubject);
        rvChapter = findViewById(R.id.rvChapter);
        civUser = findViewById(R.id.civUser);

        String uid = FirebaseAuth.getInstance().getUid();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Children").child(uid).child(uid);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ChapterActivity.this).load(uri).into(civUser);
            }
        });

        civUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChapterActivity.this, UserInfoActivity.class));
            }
        });

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


        tvSubject.setText(subject[i.getIntExtra("Subject", 0)]);
        if (i.getIntExtra("Subject", 0) == 1) {
            chapter = new String[14];
            img = new int[14];
            cmp = new boolean[14];
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
            img[0] = R.mipmap.m1;
            img[1] = R.mipmap.m2;
            img[2] = R.mipmap.m3;
            img[3] = R.mipmap.m4;
            img[4] = R.mipmap.m5;
            img[5] = R.mipmap.m6;
            img[6] = R.mipmap.m7;
            img[7] = R.mipmap.m8;
            img[8] = R.mipmap.m9;
            img[9] = R.mipmap.m10;
            img[10] = R.mipmap.m11;
            img[11] = R.mipmap.m12;
            img[12] = R.mipmap.m13;
            img[13] = R.mipmap.m14;
            for (int i = 0; i < 14; i++) {
                if (i < comps)
                    cmp[i] = true;
                else
                    cmp[i] = false;
            }

        }
        if (i.getIntExtra("Subject", 0) == 3) {
            img = new int[20];
            chapter = new String[20];
            cmp = new boolean[20];
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
            img[0] = R.mipmap.elit1;
            img[1] = R.mipmap.elit2;
            img[2] = R.mipmap.elit3;
            img[3] = R.mipmap.elit4;
            img[4] = R.mipmap.elit5;
            img[5] = R.mipmap.elit6;
            img[6] = R.mipmap.elit7;
            img[7] = R.mipmap.elit8;
            img[8] = R.mipmap.elit9;
            img[9] = R.mipmap.elit10;
            img[10] = R.mipmap.elit11;
            img[11] = R.mipmap.elit12;
            img[12] = R.mipmap.elit13;
            img[13] = R.mipmap.elit14;
            img[14] = R.mipmap.elit15;
            img[15] = R.mipmap.elit16;
            img[16] = R.mipmap.elit17;
            img[17] = R.mipmap.elit18;
            img[18] = R.mipmap.elit19;
            img[19] = R.mipmap.elit20;
            for (int i = 0; i < 20; i++) {
                if (i < comps)
                    cmp[i] = true;
                else
                    cmp[i] = false;
            }
        }
        if (i.getIntExtra("Subject", 0) == 4) {
            img = new int[24];
            chapter = new String[24];
            cmp = new boolean[24];
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
            img[0] = R.mipmap.es1;
            img[1] = R.mipmap.es2;
            img[2] = R.mipmap.es3;
            img[3] = R.mipmap.es4;
            img[4] = R.mipmap.es5;
            img[5] = R.mipmap.es6;
            img[6] = R.mipmap.es7;
            img[7] = R.mipmap.es8;
            img[8] = R.mipmap.es9;
            img[9] = R.mipmap.es10;
            img[10] = R.mipmap.es11;
            img[11] = R.mipmap.es12;
            img[12] = R.mipmap.es13;
            img[13] = R.mipmap.es14;
            img[14] = R.mipmap.es15;
            img[15] = R.mipmap.es16;
            img[16] = R.mipmap.es17;
            img[17] = R.mipmap.es18;
            img[18] = R.mipmap.es19;
            img[19] = R.mipmap.es20;
            img[20] = R.mipmap.es20;
            img[21] = R.mipmap.es20;
            img[22] = R.mipmap.es20;
            img[23] = R.mipmap.es20;
            for (int i = 0; i < 24; i++) {
                if (i < comps)
                    cmp[i] = true;
                else
                    cmp[i] = false;
            }
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (t1 != null) {
                    t1.stop();
                    t1.shutdown();
                }
                finish();
            }
        });

        rlSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Chapters are: " + Arrays.toString(chapter);
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        ChapterAdapter chapterAdapter = new ChapterAdapter();
        rvChapter.setLayoutManager(new LinearLayoutManager(this));
        rvChapter.setAdapter(chapterAdapter);
        chapterAdapter.notifyDataSetChanged();
    }

    public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_single_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvName.setText(chapter[position]);
            holder.tvChapter.setText("Chapter " + (position + 1));
            Glide.with(getApplicationContext()).load(img[position]).into(holder.ivThumbnail);
            holder.cb.setChecked(cmp[position]);
            holder.rlChapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(ChapterActivity.this, LearningActivity.class);
                    in.putExtra("Subject", i.getIntExtra("Subject", 0));
                    in.putExtra("Chapter", position);
                    startActivity(in);
                }
            });
        }

        @Override
        public int getItemCount() {
            return chapter.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatCheckBox cb;
            TextView tvName, tvChapter;
            ImageView ivThumbnail;
            RelativeLayout rlChapter;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cb = itemView.findViewById(R.id.cb);
                rlChapter = itemView.findViewById(R.id.rlChapter);
                tvChapter = itemView.findViewById(R.id.tvChapter);
                tvName = itemView.findViewById(R.id.tvName);
                ivThumbnail = itemView.findViewById(R.id.ivThumbmnail);
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            ivMic.setImageResource(R.drawable.ic_mic_on);
            Toast.makeText(ChapterActivity.this, "Listening", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ChapterActivity.this, "Ended", Toast.LENGTH_SHORT).show();
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
            if (res.equals("BACK")) {
                finish();
            }
            if (subj == 1) {
                Toast.makeText(ChapterActivity.this, res, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //tvmic.setText("");
                        boolean flag = false;
                        String math[] = {"WHERE TO LOOK FROM", "FUN WITH NUMBERS", "GIVE AND TAKE"
                                , "LONG AND SHORT", "SHAPES AND DESIGNS", "FUN WITH GIVE AND TAKE", "TIME GOES ON",
                                "WHO IS HEAVIER", "HOW MANY TIMES", "PLAY WITH PATTERNS", "JUGS AND MUGS", "CAN WE SHARE"
                                , "SMART CHARTS", "RUPEES AND PAISE"};

                        for (int i = 0; i < math.length; i++) {
                            if (math[i].equals(res)) {
                                flag = true;
                                Intent in = new Intent(ChapterActivity.this, LearningActivity.class);
                                in.putExtra("Subject", subj);
                                in.putExtra("Chapter", i);
                                startActivity(in);
                            }
                        }
                        if (flag == false) {
                            Toast.makeText(ChapterActivity.this, "Try Again " + res, Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //tvmic.setText("");
                                    ivMic.performClick();
                                }
                            }, 1000);
                        }
                    }
                }, 1000);
            } else if (subj == 3) {
                Toast.makeText(ChapterActivity.this, res, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //tvmic.setText("");
                        boolean flag = false;
                        String elit[] = {"GOOD MORNING", "THE MAGIC GARDEN", "BIRD TALK"
                                , "NINA AND THE BABY SPARROWS", "LITTLE BY LITTLE", "THE ENORMOUS TURNIP", "SEA SONG",
                                "A LITTLE FISH STORY", "THE BALLOON MAN", "THE YELLOW BUTTERFLY", "TRAINS", "THE STORY OF THE ROAD"
                                , "PUPPY AND I", "LITTLE TIGER BIG TIGER", "WHAT'S IN THE MAILBOX", "MY SILLY SISTER",
                                "DON'T TELL", "HE IS  MY BROTHER", "HOW CREATURES MOVE", "THE SHIP OF THE DESERT"};
                        for (int i = 0; i < elit.length; i++) {
                            if (elit[i].equals(res)) {
                                flag = true;
                                Intent in = new Intent(ChapterActivity.this, LearningActivity.class);
                                in.putExtra("Subject", subj);
                                in.putExtra("Chapter", i);
                                startActivity(in);
                            }
                        }
                        if (flag == false) {
                            Toast.makeText(ChapterActivity.this, "Try Again " + res, Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //tvmic.setText("");
                                    ivMic.performClick();
                                }
                            }, 1000);
                        }
                    }
                }, 1000);
            } else {
                Toast.makeText(ChapterActivity.this, res, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //tvmic.setText("");
                        boolean flag = false;
                        String elit[] = {"POONAM DAY OUT", "THE MAGIC GARDEN", "BIRD TALK"
                                , "NINA AND THE BABY SPARROWS", "LITTLE BY LITTLE", "THE ENORMOUS TURNIP", "SEA SONG",
                                "A LITTLE FISH STORY", "THE BALLOON MAN", "THE YELLOW BUTTERFLY", "TRAINS", "THE STORY OF THE ROAD"
                                , "PUPPY AND I", "LITTLE TIGER BIG TIGER", "WHAT'S IN THE MAILBOX", "MY SILLY SISTER",
                                "DON'T TELL", "HE IS  MY BROTHER", "HOW CREATURES MOVE", "THE SHIP OF THE DESERT"};
                        for (int i = 0; i < elit.length; i++) {
                            if (elit[i].equals(res)) {
                                flag = true;
                                Intent in = new Intent(ChapterActivity.this, LearningActivity.class);
                                in.putExtra("Subject", subj);
                                in.putExtra("Chapter", i);
                                startActivity(in);
                            }
                        }
                        if (flag == false) {
                            Toast.makeText(ChapterActivity.this, "Try Again " + res, Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //tvmic.setText("");
                                    ivMic.performClick();
                                }
                            }, 1000);
                        }
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