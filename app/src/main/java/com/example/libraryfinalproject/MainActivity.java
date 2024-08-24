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
    private AnimatedPB animatedPB;
    private MaterialButton text_BTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();




    }

    private void initViews() {
        animatedPB = findViewById(R.id.animatedPB_text);

        Log.d("ddd", "\n~~~~~~~~~~~~~~\n" +
                "@ ~ MainActivityy Attributes:" +
                "\nprogressColor: " + animatedPB.getProgressColor() +
                "\ncontainerColor: " + animatedPB.getContainerColor() +
                "\nbarShape: " + animatedPB.getBarShape() +
                "\nprogress: " + animatedPB.getProgress()  +
                "\nminValue: " + animatedPB.getMinValue() +
                "\nmaxValue: " + animatedPB.getMaxValue() +
                "\nanimationType: " + animatedPB.getAnimationType() +
                "\n~~~~~~~~~~~~~~~~~~~~~~~\n");
        text_BTN= findViewById(R.id.text_BTN);
        text_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float progress = animatedPB.getProgress();
                Log.d("ddd", "current Progress: " + progress);
                //animatedPB.setProgress(progress + 10);
                animatedPB.animateProgress(progress+10,1000);


            }
        });
    }


}