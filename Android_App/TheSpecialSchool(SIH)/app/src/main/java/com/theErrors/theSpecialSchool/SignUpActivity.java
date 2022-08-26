package com.theErrors.theSpecialSchool;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    TextInputEditText etEmail, etPassword, etCnfPassword;
    TextView tvSignIn;
    AppCompatButton btnSignUp;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        etCnfPassword = findViewById(R.id.etCnfPassword);
        tvSignIn = findViewById(R.id.tvSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void registerNewUser() {
        String email, password, cnfpassword;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        cnfpassword = etCnfPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password length too short!", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.equals(cnfpassword) == false) {
            Toast.makeText(getApplicationContext(), "Passwords Do Not Match!!", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();
                            databaseReference = firebaseDatabase.getReference("Children").child(mAuth.getUid());
                            ProfileData data = new ProfileData("", email, 0, "", "No", "");
                            databaseReference.setValue(data);
                            Intent intent = new Intent(SignUpActivity.this, ProfileCreationActivity.class);
                            intent.putExtra("Email", email);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }


}