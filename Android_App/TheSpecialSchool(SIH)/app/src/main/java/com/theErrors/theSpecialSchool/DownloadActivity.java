package com.theErrors.theSpecialSchool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView progressText;
    int i = 0;
    TextView tvFinished;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        tvFinished = findViewById(R.id.tvFinished);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // set the limitations for the numeric
                // text under the progress bar
                if (i <= 100) {
                    progressText.setText(i + "%");
                    progressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 100);
                } else {
                    //Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    tvFinished.setVisibility(View.VISIBLE);
                    final Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent in = new Intent(DownloadActivity.this, LearningActivity.class);
                            startActivity(in);
                            finish();
                        }
                    }, 1000);
                    handler.removeCallbacks(this);
                }
            }
        }, 200);
    }

}

