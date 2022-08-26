package com.theErrors.theSpecialSchool;

import static com.theErrors.theSpecialSchool.R.drawable.btn_black;
import static com.theErrors.theSpecialSchool.R.drawable.btn_white;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class GameMathActivity extends AppCompatActivity {

    RelativeLayout rlGame, rlScore;

    ImageView ivBack;
    RelativeLayout btn1, btn2;
    TextView tvBtn1, tvBtn2;
    TextView tvOp, tvScore;
    EditText etInput;
    AppCompatButton btnSubmit;
    int score = 0;
    int i = 0;

    String arr[][] = {{"10", "+", "12", "22"},
            {"35", "-", "20", "15"},
            {"78", "/", "2", "39"},
            {"16", "+", "8", "24"},
            {"4", "*", "2", "8"},
            {"8", "/", "4", "2"},
            {"18", "-", "17", "1"},
            {"12", "+", "13", "25"},
            {"17", "*", "2", "34"},
            {"9", "/", "3", "3"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_math);
        init();
        setQues();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void setQues() {
        etInput.setText("");
        tvBtn1.setText(arr[i][0]);
        tvBtn2.setText(arr[i][2]);
        tvOp.setText(arr[i][1]);
        if (i % 2 == 0) {
            btn1.setBackground(getDrawable(btn_black));
            btn2.setBackground(getDrawable(btn_white));
            tvBtn1.setTextColor(R.color.yellow);
            tvBtn2.setTextColor(R.color.black);
        } else {
            btn1.setBackground(getDrawable(btn_white));
            btn2.setBackground(getDrawable(btn_black));
            tvBtn1.setTextColor(R.color.black);
            tvBtn2.setTextColor(R.color.yellow);
        }
        checkAnswer();
    }

    public void checkAnswer() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = etInput.getText().toString();
                if (TextUtils.isEmpty(a)) {
                    Toast.makeText(GameMathActivity.this, "Enter Answer", Toast.LENGTH_SHORT).show();
                } else {
                    // String b = ().toString();
                    if (a.equals(arr[i][3])) {
                        score++;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GameMathActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                                i++;
                                if (i < 10) {
                                    setQues();
                                } else {
                                    Toast.makeText(getApplicationContext(), score + "", Toast.LENGTH_SHORT).show();
                                    rlGame.setVisibility(View.GONE);
                                    rlScore.setVisibility(View.VISIBLE);
                                    tvScore.setText(score + "");
                                }

                            }
                        }, 500);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                i++;
                                Toast.makeText(GameMathActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                                //setQues();
                                if (i < 10) {
                                    setQues();
                                } else {
                                    Toast.makeText(getApplicationContext(), score + "", Toast.LENGTH_SHORT).show();
                                    rlGame.setVisibility(View.GONE);
                                    rlScore.setVisibility(View.VISIBLE);
                                    tvScore.setText(score + "");
                                }
                            }
                        }, 500);
                    }
                }
            }
        });
    }

    public void init() {
        ivBack = findViewById(R.id.ivBack);
        rlGame = findViewById(R.id.rlGame);
        rlScore = findViewById(R.id.rlScore);
        tvScore = findViewById(R.id.tvScore);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        tvBtn1 = findViewById(R.id.tvBtn1);
        tvBtn2 = findViewById(R.id.tvBtn2);
        tvOp = findViewById(R.id.tvOp);
        etInput = findViewById(R.id.etInput);
        btnSubmit = findViewById(R.id.btnSubmit);


    }

    @Override
    public void onBackPressed() {

    }
}