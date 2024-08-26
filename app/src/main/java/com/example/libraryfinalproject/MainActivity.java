package com.example.libraryfinalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.animatedpb.Models.AnimatedPB;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private AnimatedPB animatedPB_3;
    private AnimatedPB animatedPB_1;
    private AnimatedPB animatedPB_2;
    private AnimatedPB animatedPB_4;
    private AnimatedPB animatedPB_5;
    private AnimatedPB animatedPB_6;
    private AnimatedPB animatedPB_7;

    private MaterialButton test_BTN;
    private MaterialButton reset_BTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();




    }

    private void initViews() {
        animatedPB_3 = findViewById(R.id.animatedPB_3);
        animatedPB_1 = findViewById(R.id.animatedPB_1);
        animatedPB_2 = findViewById(R.id.animatedPB_2);
        animatedPB_4 = findViewById(R.id.animatedPB_4);
        animatedPB_5 = findViewById(R.id.animatedPB_5);
        animatedPB_6 = findViewById(R.id.animatedPB_6);
        animatedPB_7 = findViewById(R.id.animatedPB_7);

        animatedPB_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatedPB_2.animateProgress( animatedPB_2.getProgress() + 5,1000);
            }
        });


        test_BTN= findViewById(R.id.test_BTN);
        reset_BTN= findViewById(R.id.reset_BTN);
        test_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float progress = animatedPB_3.getProgress();


                int value = (progress < 50 ? 100 : -100);
                animatedPB_3.animateProgress(progress + value,1000);
                animatedPB_1.animateProgress(progress + value,1000);
                animatedPB_2.animateProgress(progress + value,1000);
                animatedPB_4.animateProgress(progress + value,1000);
                animatedPB_5.animateProgress(progress + value,1000);
                animatedPB_6.animateProgress(progress + value,1000);
                animatedPB_7.animateProgress(progress + value,1000);


            }
        });
        reset_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatedPB_3.resetProgress();
                animatedPB_1.resetProgress();
                animatedPB_2.resetProgress();
                animatedPB_4.resetProgress();
                animatedPB_5.resetProgress();
                animatedPB_6.resetProgress();
                animatedPB_7.resetProgress();
            }
        });
    }


}