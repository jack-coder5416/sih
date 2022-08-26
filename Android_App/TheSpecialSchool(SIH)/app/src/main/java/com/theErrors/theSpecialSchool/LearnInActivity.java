package com.theErrors.theSpecialSchool;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LearnInActivity extends AppCompatActivity {


    ImageView ivBack;
    VideoView vv;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_in);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("learn_it.mp4");

        ivBack = findViewById(R.id.ivBack);
        vv = findViewById(R.id.vvLearnIt);
        pb = findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        vv.setZOrderOnTop(true);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(vv);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vv.stopPlayback();
                finish();
            }
        });

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                vv.setMediaController(mediaController);
                vv.setVideoURI(uri);
                vv.requestFocus();
                vv.start();
               /* vv.setVideoURI(uri);
                vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        pb.setVisibility(View.GONE);
                        mp.setLooping(true);
                        vv.start();
                    }
                });*/
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}