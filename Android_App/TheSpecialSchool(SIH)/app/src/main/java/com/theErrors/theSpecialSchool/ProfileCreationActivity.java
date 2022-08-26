package com.theErrors.theSpecialSchool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileCreationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 22;
    Uri filePath;
    int cls = 0;
    int disab = 0;
    int lan = 0;
    AppCompatButton btnSubmit;
    CircleImageView civProfilePicture;
    EditText etName;
    Spinner spClass, spLanguage, spDisability;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference drProgress;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String UID = FirebaseAuth.getInstance().getUid();
    String[] grade = {"Select Your Class", "1", "2", "3", "4", "5", "6", "7", "8"};
    String[] language = {"Preferred Language", "English", "Kannada"};
    String[] disability = {"Select Your Disability", "Blind", "Deaf", "Voiceless", "Cerebral Palsy", "Dyslyexia", "Blind , Voiceless", "Deaf , Voiceless"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        Intent i = getIntent();
        String email = i.getStringExtra("Email");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Children").child(UID);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Children").child(UID);

        btnSubmit = findViewById(R.id.btnSubmit);
        civProfilePicture = findViewById(R.id.civProfilePicture);
        etName = findViewById(R.id.etName);
        spClass = findViewById(R.id.spClass);
        spLanguage = findViewById(R.id.spLanguage);
        spDisability = findViewById(R.id.spDisability);

        ArrayAdapter ad1
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                grade);
        ad1.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        ArrayAdapter ad2
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                language);
        ad2.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        ArrayAdapter ad3
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                disability);
        ad3.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spClass.setAdapter(ad1);
        spLanguage.setAdapter(ad2);
        spDisability.setAdapter(ad3);
        spDisability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    disab = 0;
                    Toast.makeText(getApplicationContext(), "Select Disability", Toast.LENGTH_SHORT).show();
                } else {
                    disab = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    lan = 0;
                    Toast.makeText(getApplicationContext(), "Select Language", Toast.LENGTH_SHORT).show();
                } else {
                    lan = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    cls = 0;
                    Toast.makeText(getApplicationContext(), "Select Class", Toast.LENGTH_SHORT).show();
                } else {
                    cls = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        civProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                int grade = cls;
                String langg = language[lan];
                String disa = disability[disab];
                if (name.isEmpty() || cls == 0 || disab == 0) {
                    Toast.makeText(getApplicationContext(), "Fill all the details!", Toast.LENGTH_SHORT).show();
                } else {
                    ProfileData pd = new ProfileData(name, email, grade, langg, "Yes", disa);
                    ProgressData pgd = new ProgressData("0", "0", "0", "0", "0", "0");
                    databaseReference.setValue(pd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProfileCreationActivity.this, "Profile Created Successfully", Toast.LENGTH_SHORT).show();
                            drProgress = databaseReference.child("Progress");
                            drProgress.setValue(pgd).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Progress", Toast.LENGTH_SHORT).show();
                                }
                            });
                            drProgress = databaseReference.child("Score");
                            drProgress.setValue(pgd).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent i = new Intent(ProfileCreationActivity.this, HomeActivity.class);
                            startActivity(i);
                            finishAffinity();
                        }
                    });
                }
            }
        });
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                civProfilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
        uploadImage();
    }

    private void uploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref
                    = storageReference.child(UID);
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(ProfileCreationActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    StorageReference dateRef = storageReference.child(UID);
                                    dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri downloadUrl) {

                                        }
                                    });
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast
                                    .makeText(ProfileCreationActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }
}