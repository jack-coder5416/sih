package com.theErrors.theSpecialSchool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ortiz.touchview.TouchImageView;

public class MindMapActivity extends AppCompatActivity {

    String chapter[] = {
            "Where To Look From",
            "Fun With Numbers",
            "Give and Take",
            "Long and Short",
            "Shapes and Designs",
            "Fun with Give and Take",
            "Time Goes On",
            "Who is Heavier",
            "How Many Times",
            "Play with Patterns",
            "Jugs and Mugs",
            "Can we Share",
            "Smart Charts",
            "Rupees and Paise"};

    Intent i;
    int subj;
    int chap;
    ImageView ivBack;
    TouchImageView ivMindMap;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String stat = "";
    String dyn = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind_map);

        i = getIntent();
        subj = i.getIntExtra("Subject", 0);
        chap = i.getIntExtra("Chapter", 0);

        ivBack = findViewById(R.id.ivBack);
        ivMindMap = findViewById(R.id.ivMindMap);

        mmInit();
        Toast.makeText(this, stat + "  " + dyn, Toast.LENGTH_SHORT).show();
        firebaseStorage = FirebaseStorage.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mmInflate(stat);
            }
        }, 500);
        /*storageReference = firebaseStorage.getReference().child("GIF").child("maths").child(stat);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // ivMindMap.setImageURI(uri);
                Glide.with(MindMapActivity.this).load(uri).into(ivMindMap);
            }
        });*/


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivMindMap.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                mmInflate(dyn);
            }

            public void onSwipeRight() {
                //Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                //Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                mmInflate(stat);
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    public void mmInit() {
        stat = "MMS" + (chap + 1) + ".png";
        dyn = "MMD" + (chap + 1) + ".gif";
        //mmInflate(stat);
    }

    public void mmInflate(String a) {
        storageReference = firebaseStorage.getReference().child("GIF").child("maths").child(a);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // ivMindMap.setImageURI(uri);
                Glide.with(MindMapActivity.this).load(uri).into(ivMindMap);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}