package com.theErrors.theSpecialSchool;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputEditText etEmail;
    AppCompatButton btnReset;
    FirebaseAuth firebaseAuth;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                if (TextUtils.isEmpty(email) == false) {
                    firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ForgotPasswordActivity.this, "Reset Link Sent to Registered Email", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ForgotPasswordActivity.this, "Failed to Send Link", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void init() {
        ivBack = findViewById(R.id.ivBack);
        etEmail = findViewById(R.id.etMail);
        btnReset = findViewById(R.id.btnReset);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {

    }
}