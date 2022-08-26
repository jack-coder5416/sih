package com.theErrors.theSpecialSchool;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.ortiz.touchview.TouchImageView;


public class GameEngActivity extends AppCompatActivity {

    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_eng);

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animat);
        TextView a = (TextView) findViewById(R.id.t1);
        TouchImageView b = (TouchImageView) findViewById(R.id.t2);
        AppCompatButton c = (AppCompatButton) findViewById(R.id.b1);
        AppCompatButton d = (AppCompatButton) findViewById(R.id.b2);
        AppCompatButton e = (AppCompatButton) findViewById(R.id.b3);
        AppCompatButton f = (AppCompatButton) findViewById(R.id.b4);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(GameEngActivity.this).load(R.mipmap.trees).into(b);
                b.startAnimation(anim);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(GameEngActivity.this).load(R.mipmap.brid).into(b);
                b.startAnimation(anim);
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(GameEngActivity.this).load(R.mipmap.sun).into(b);
                b.startAnimation(anim);
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(GameEngActivity.this).load(R.mipmap.sky).into(b);
                b.startAnimation(anim);
            }
        });

    }
}