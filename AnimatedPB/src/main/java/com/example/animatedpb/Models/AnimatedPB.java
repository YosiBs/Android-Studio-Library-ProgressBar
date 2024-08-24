package com.example.animatedpb.Models;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.animatedpb.Logics.Logic;
import com.example.animatedpb.R;
import com.google.android.material.textview.MaterialTextView;

public class AnimatedPB extends ConstraintLayout {
    private int progressColor;
    private int containerColor;
    private int barShape;
    private float minValue;
    private float maxValue;
    private float progress;
    private String barLabel;

    private int animationType;
    private int animDurationMillis;
    private int progressCornerRadius;
    private int containerCornerRadius;
    private Logic logic;
    private final int DEFAULT_MIN_VALUE = 0;
    private final int DEFAULT_MAX_VALUE = 100;
    private final int DEFAULT_ANIM_TYPE = 0;
    private final int DEFAULT_ANIM_DURATION_MILLIS = 1000;
    private final int DEFAULT_PROGRESS_RADIUS = 0;
    private final int DEFAULT_CONTAINER_RADIUS = 0;
    private final int DEFAULT_PROGRESS = 0;
    private final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#005ECB");
    private final int DEFAULT_CONTAINER_COLOR = Color.parseColor("#E1BEE7");
    private final int DEFAULT_BAR_SHAPE = 0;



    public AnimatedPB(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    private void init(Context context,AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.horizontal_poll_progress_bar, this, true);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AnimatedPB,
                0, 0);

        try {
            progressColor = a.getColor(R.styleable.AnimatedPB_progressColor, DEFAULT_PROGRESS_COLOR);
            containerColor = a.getColor(R.styleable.AnimatedPB_containerColor, DEFAULT_CONTAINER_COLOR);
            barShape = a.getInt(R.styleable.AnimatedPB_barShape, DEFAULT_BAR_SHAPE);
            progress = a.getFloat(R.styleable.AnimatedPB_progress, DEFAULT_PROGRESS);
            minValue = a.getFloat(R.styleable.AnimatedPB_minValue, DEFAULT_MIN_VALUE);
            maxValue = a.getFloat(R.styleable.AnimatedPB_maxValue, DEFAULT_MAX_VALUE);
            logic = new Logic(minValue, maxValue);
            progressCornerRadius = a.getDimensionPixelSize(R.styleable.AnimatedPB_progressCornerRadius, DEFAULT_PROGRESS_RADIUS);
            containerCornerRadius = a.getDimensionPixelSize(R.styleable.AnimatedPB_containerCornerRadius, DEFAULT_CONTAINER_RADIUS);
            animationType = a.getInt(R.styleable.AnimatedPB_animationType, DEFAULT_ANIM_TYPE);
            animDurationMillis = a.getInt(R.styleable.AnimatedPB_animDurationMillis, DEFAULT_ANIM_DURATION_MILLIS);
            barLabel = a.getString(R.styleable.AnimatedPB_barLabel);
            Log.d("ddd", "\n~~~~~~~~~~~~~~\n" +
                    "@ ~ MyAttributes:" +
                    "\nprogressColor: " + progressColor +
                    "\ncontainerColor: " + containerColor +
                    "\nbarShape: " + barShape +
                    "\nprogress: " + progress +
                    "\nminValue: " + minValue +
                    "\nmaxValue: " + maxValue +
                    "\nanimationType: " + animationType +
                    "\nanimDurationMillis: " + animDurationMillis +
                    "\nbarLabel: " + barLabel +
                    "\n~~~~~~~~~~~~~~~~~~~~~~~\n");


        } finally {
            //setProgress(progress);
            animateProgress(progress,1000);
            setBarLabel(barLabel);
            a.recycle();
        }
    }


    public void startAnimation() {
        // This is where you apply animations
        invalidate(); // Redraw the view whenever necessary
    }

    public void animateProgress(float newProgress, int animDurationMillis) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", progress, newProgress);
        animator.setDuration(animDurationMillis);  // Set your animation duration

        // Choose the interpolator based on animationType
        switch (animationType) {
            case 0: // Linear
                animator.setInterpolator(new AccelerateInterpolator());
                break;
            case 1: // Ease-out
                animator.setInterpolator(new DecelerateInterpolator());
                break;
            case 2: // Ease-In
                animator.setInterpolator(new LinearInterpolator());
                break;
        }

        animator.start();
    }
    public void setProgress(float value) {
        this.progress = logic.calculateProgress(value);



        // Update UI component based on progress
        RelativeLayout progressBar = findViewById(R.id.PB_progress);
        RelativeLayout progressBarContainer = findViewById(R.id.PB_shell);

        progressBarContainer.post(() -> {
            int containerWidth = progressBarContainer.getWidth();
            int newWidth = logic.calculateWidth(containerWidth, progress);  // Use logic class to calculate width

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) progressBar.getLayoutParams();
            params.width = newWidth;
            progressBar.setLayoutParams(params);
        });

        MaterialTextView percentageMTV = findViewById(R.id.Percentage_MTV);
        percentageMTV.setText(progress + "%");

        invalidate();  // Trigger redraw
    }

    public float getProgress() {
        return progress;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public AnimatedPB setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        return this;
    }

    public int getContainerColor() {
        return containerColor;
    }

    public AnimatedPB setContainerColor(int containerColor) {
        this.containerColor = containerColor;
        return this;
    }

    public int getBarShape() {
        return barShape;
    }

    public AnimatedPB setBarShape(int barShape) {
        this.barShape = barShape;
        return this;
    }

    public float getMinValue() {
        return minValue;
    }

    public AnimatedPB setMinValue(float minValue) {
        this.minValue = minValue;
        return this;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public AnimatedPB setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public int getAnimationType() {
        return animationType;
    }

    public AnimatedPB setAnimationType(int animationType) {
        this.animationType = animationType;
        return this;
    }

    public String getBarLabel() {
        return barLabel;
    }

    public AnimatedPB setBarLabel(String barLabel) {
        this.barLabel = barLabel;
        MaterialTextView labelMTV = findViewById(R.id.label_MTV);
        if (barLabel != null){
            labelMTV.setText(barLabel);
        }
        return this;
    }

    public int getProgressCornerRadius() {
        return progressCornerRadius;
    }

    public AnimatedPB setProgressCornerRadius(int progressCornerRadius) {
        this.progressCornerRadius = progressCornerRadius;
        return this;
    }

    public int getContainerCornerRadius() {
        return containerCornerRadius;
    }

    public AnimatedPB setContainerCornerRadius(int containerCornerRadius) {
        this.containerCornerRadius = containerCornerRadius;
        return this;
    }
}
