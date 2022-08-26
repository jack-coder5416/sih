package com.theErrors.theSpecialSchool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class ScoreActivity extends AppCompatActivity {

    String subject[] = {"Hindi", "Mathematics", "English Language", "English Literature", "Environmental Sciences", "General Knowledge"};
    String chapter[];
    TextView tvScore;
    int subj = 0;
    int chap = 0;
    int score[] = {0, 0, 0, 0, 0, 0};
    String uid = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressData pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        uid = FirebaseAuth.getInstance().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Children").child(uid).child("Score");


        tvScore = findViewById(R.id.tvScore);
        Intent i = getIntent();
        subj = i.getIntExtra("Subject", 0);
        chap = i.getIntExtra("Chapter", 0);
        int sco = i.getIntExtra("Score", 0);
        tvScore.setText(sco + "");
        //Toast.makeText(this, subj + "", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, score[subj] + "", Toast.LENGTH_SHORT).show();
        //chapInit();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Toast.makeText(getApplicationContext(), snapshot.toString(), Toast.LENGTH_SHORT).show();
                score[0] = Integer.parseInt(snapshot.child("Hindi").getValue().toString());
                score[1] = Integer.parseInt(snapshot.child("Mathematics").getValue().toString());
                score[2] = Integer.parseInt(snapshot.child("EnglishLanguage").getValue().toString());
                score[3] = Integer.parseInt(snapshot.child("EnglishLiterature").getValue().toString());
                score[4] = Integer.parseInt(snapshot.child("Environmental").getValue().toString());
                score[5] = Integer.parseInt(snapshot.child("GeneralKnowledge").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ScoreActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getApplicationContext(), Arrays.toString(score), Toast.LENGTH_SHORT).show();
        //score[subj] = score[subj] + sco;
        Toast.makeText(getApplicationContext(), score[subj] + "", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                score[subj] += sco;
                pd = new ProgressData(score[0] + "", score[1] + "", score[2] + "", score[3] + "", score[4] + "", score[5] + "");
                databaseReference.setValue(pd).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Score Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 1000);


    }

}