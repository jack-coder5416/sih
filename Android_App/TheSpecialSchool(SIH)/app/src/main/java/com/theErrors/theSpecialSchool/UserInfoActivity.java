package com.theErrors.theSpecialSchool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

    ImageView ivBack;
    CircleImageView civUser;
    TextView tvEmail, tvClass, tvDisability, tvName, tvLanguage;
    AppCompatButton btnLogOut;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        init();

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(UserInfoActivity.this).load(uri).into(civUser);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvName.setText(snapshot.child("Name").getValue().toString());
                tvEmail.setText(snapshot.child("Email").getValue().toString());
                tvClass.setText(snapshot.child("Class").getValue().toString());
                tvLanguage.setText(snapshot.child("Language").getValue().toString());
                tvDisability.setText(snapshot.child("Disability").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserInfoActivity.this, "Failed To Fetch", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(UserInfoActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void init() {
        ivBack = findViewById(R.id.ivBack);
        uid = FirebaseAuth.getInstance().getUid();
        civUser = findViewById(R.id.civUser);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvClass = findViewById(R.id.tvClass);
        tvDisability = findViewById(R.id.tvDisability);
        tvLanguage = findViewById(R.id.tvLanguage);
        btnLogOut = findViewById(R.id.btnLogOut);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Children").child(uid);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Children").child(uid).child(uid);
    }

    @Override
    public void onBackPressed() {

    }
}