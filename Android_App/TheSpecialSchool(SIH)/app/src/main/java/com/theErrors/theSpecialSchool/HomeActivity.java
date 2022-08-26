package com.theErrors.theSpecialSchool;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    public static final Integer RecordAudioRequestCode = 1;
    String res = "";
    SpeechRecognizer sr = null;
    String subject[] = {"Hindi", "Mathematics", "English Language", "English Literature", "Environmental Sciences", "General Knowledge"};
    RecyclerView rvHome;
    RelativeLayout rlFooter, rlSpeech;
    ImageView ivMic;
    CircleImageView civUser;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    int comps[] = {0, 0, 0, 0, 0, 0};
    TextToSpeech t1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String uid = "";

    AppCompatButton btnlearnIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        uid = FirebaseAuth.getInstance().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Children").child(uid);
        btnlearnIt = findViewById(R.id.btnLearnIt);

        databaseReference = databaseReference.child("Progress");
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Intent checkIntent = new Intent();
        //checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        //startActivityForResult(checkIntent, 0);


        // mTts.isLanguageAvailable(Locale.INDIA));

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    // t1.setLanguage(Locale.forLanguageTag("in"));
                    //Toast.makeText(getApplicationContext(),t1.isLanguageAvailable(Locale.ENGLISH)+"",Toast.LENGTH_SHORT).show();
                    //t1.setLanguage(new Locale("en"));
                    t1.setSpeechRate(.001f);
                }
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        rlSpeech = findViewById(R.id.rlSpeech);
        rlFooter = findViewById(R.id.rlFooter);
        ivMic = findViewById(R.id.ivMic);
        rvHome = findViewById(R.id.rvHome);
        civUser = findViewById(R.id.civUser);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        String uid = FirebaseAuth.getInstance().getUid();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Children").child(uid).child(uid);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(HomeActivity.this).load(uri).into(civUser);
            }
        });

        civUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, UserInfoActivity.class));
            }
        });

        btnlearnIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LearnInActivity.class));
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

        rlSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Subjects are: " + Arrays.toString(subject);
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        HomeAdapter ha = new HomeAdapter();
        rvHome.setAdapter(ha);
        rvHome.setLayoutManager(new GridLayoutManager(this, 2));
        ha.notifyDataSetChanged();
    }

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_single_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvSubject.setText(subject[position]);
            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == 1 || position == 3 || position == 4) {
                        Intent i = new Intent(HomeActivity.this, ChapterActivity.class);
                        i.putExtra("Subject", position);
                        i.putExtra("Complete", comps[position]);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Under Development", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return subject.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvSubject;
            RelativeLayout rlItem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                rlItem = itemView.findViewById(R.id.rlItem);
                tvSubject = itemView.findViewById(R.id.tvSubject);
            }
        }
    }

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            ivMic.setImageResource(R.drawable.ic_mic_on);
            Toast.makeText(HomeActivity.this, "Listening", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(HomeActivity.this, "Ended", Toast.LENGTH_SHORT).show();
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
            if (!(res.equals("HINDI") || res.equals("MATHEMATICS") || res.equals("ENGLISH LANGUAGE") || res.equals("ENGLISH LITERATURE")
                    || res.equals("ENVIRONMENTAL SCIENCES") || res.equals("GENERAL KNOWLEDGE") || res.equals("LEARN IT"))) {
                //tvmic.setText(res + "");
                Toast.makeText(HomeActivity.this, "Try Again " + res, Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //tvmic.setText("");
                        ivMic.performClick();
                    }
                }, 1000);
            } else {
                //tvmic.setText(res + "");
                Toast.makeText(HomeActivity.this, res, Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //tvmic.setText("");
                        if (res.equals("HINDI")) {
                            Toast.makeText(HomeActivity.this, "Under Developmment", Toast.LENGTH_SHORT).show();

                        } else if (res.equals("MATHEMATICS")) {
                            Intent i = new Intent(HomeActivity.this, ChapterActivity.class);
                            i.putExtra("Subject", 1);
                            startActivity(i);
                        } else if (res.equals("ENGLISH LANGUAGE")) {
                            Toast.makeText(HomeActivity.this, "Under Developmment", Toast.LENGTH_SHORT).show();

                        } else if (res.equals("ENGLISH LITERATURE")) {
                            Intent i = new Intent(HomeActivity.this, ChapterActivity.class);
                            i.putExtra("Subject", 3);
                            startActivity(i);
                        } else if (res.equals("ENVIRONMENTAL SCIENCES")) {
                            Intent i = new Intent(HomeActivity.this, ChapterActivity.class);
                            i.putExtra("Subject", 4);
                            startActivity(i);

                        } else if (res.equals("LEARN IT")) {
                            Intent i = new Intent(HomeActivity.this, LearnInActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(HomeActivity.this, "Under Developmment", Toast.LENGTH_SHORT).show();

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

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    //t1.setLanguage(Locale.US);
                   // Set<String> a = new HashSet<>();
                    //a.add("male");
                    //Voice v = new Voice("en-us-x-sfg#male_1-local", new Locale("en", "US"), 400, 200, true, a);
                    //t1.setVoice(v);
                    t1.setSpeechRate(0.001f);
                }
            }
        });
        super.onResume();
    }
}