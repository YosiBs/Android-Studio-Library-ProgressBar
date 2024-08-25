package com.example.animatedpb.Models;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
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
    private float progressContainer;
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
            progressContainer = a.getFloat(R.styleable.AnimatedPB_progressContainer, DEFAULT_PROGRESS);

            minValue = a.getFloat(R.styleable.AnimatedPB_minValue, DEFAULT_MIN_VALUE);
            maxValue = a.getFloat(R.styleable.AnimatedPB_maxValue, DEFAULT_MAX_VALUE);
            logic = new Logic(minValue, maxValue);
            progressCornerRadius = a.getInt(R.styleable.AnimatedPB_progressCornerRadius, DEFAULT_PROGRESS_RADIUS);
            containerCornerRadius = a.getInt(R.styleable.AnimatedPB_containerCornerRadius, DEFAULT_CONTAINER_RADIUS);
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
            // Set progress and container colors programmatically
            setContainerCornerRadius(containerCornerRadius);
            setProgressCornerRadius(progressCornerRadius);
            setProgressColor();
            setContainerColor();

        } finally {

            animateProgress(progress,1000);
            setBarLabel(barLabel);
            a.recycle();
        }
    }




    public void animateProgress(float newProgress, int animDurationMillis) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", (int)progress, (int)newProgress);
        animator.setDuration(animDurationMillis);  // Set your animation duration

        // Choose the interpolator based on animationType
        switch (animationType) {
            case 0: // Linear
                Log.d("ddd","~~linear");
                animator.setInterpolator(new LinearInterpolator());
                break;
            case 1: // Ease-out
                Log.d("ddd","~~Ease-out");
                animator.setInterpolator(new DecelerateInterpolator(3.0f));
                break;
            case 2: // Ease-In
                Log.d("ddd","~~Ease-In");

                animator.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
        }

        animator.start();
    }
    public void setProgress(int value) {
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
        // Check if the barLabel can fit within the progress bar
        setBarLabel(barLabel);

        MaterialTextView percentageMTV = findViewById(R.id.Percentage_MTV);
        percentageMTV.setText((int)progress + "%");

        invalidate();  // Trigger redraw
    }

    public float getProgress() {
        return progress;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public int getContainerColor() {
        return containerColor;
    }
    private void setProgressColor() {
        RelativeLayout progressBar = findViewById(R.id.PB_progress);
        Drawable progressDrawable = progressBar.getBackground();  // Assuming background is the progress drawable

        if (progressDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) progressDrawable;
            Drawable progressLayer = layerDrawable.findDrawableByLayerId(android.R.id.progress);  // Find the progress layer
            if (progressLayer != null) {
                progressLayer.setColorFilter(progressColor, PorterDuff.Mode.SRC_IN);  // Apply color
            }
        }
    }

    private void setContainerColor() {
        RelativeLayout progressContainer = findViewById(R.id.PB_shell);
        Drawable containerDrawable = progressContainer.getBackground();  // Assuming background is the progress drawable

        if (containerDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) containerDrawable;
            Drawable ContainerLayer = layerDrawable.findDrawableByLayerId(R.id.progressContainer);  // Find the progress layer
            if (ContainerLayer != null) {
                Log.d("ddd", "Not null");
                ContainerLayer.setColorFilter(containerColor, PorterDuff.Mode.SRC_IN);  // Apply color
            }
        }
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

        // Measure the width of the barLabel text
        if (barLabel != null) {
            Paint paint = new Paint();
            paint.setTextSize(labelMTV.getTextSize());
            float textWidth = paint.measureText(barLabel);

            // Get the width of the progress bar
            RelativeLayout progressBar = findViewById(R.id.PB_progress);

            progressBar.post(() -> {
                int progressBarWidth = progressBar.getWidth();

                // Compare the text width with the progress bar width
                if (progressBarWidth > textWidth) {
                    // If the label is not visible yet, start the fade-in animation
                    if (labelMTV.getVisibility() != View.VISIBLE) {
                        labelMTV.setText(barLabel);
                        labelMTV.setVisibility(View.VISIBLE);
                        labelMTV.setAlpha(0f);  // Start with invisible

                        // Animate the alpha property to fade in
                        labelMTV.animate()
                                .alpha(1f)  // Fade to fully visible
                                .setDuration(500)  // 500 milliseconds duration
                                .setListener(null);  // No additional listener
                    }
                } else {
                    // Hide the label if the progress bar is not wide enough
                    labelMTV.setVisibility(View.GONE);
                }
            });
        }

        return this;
    }
    public int getProgressCornerRadius() {
        return progressCornerRadius;
    }

    public void setProgressCornerRadius(int progressCornerRadius) {

        RelativeLayout progressBar = findViewById(R.id.PB_progress);
        Drawable progressDrawable = progressBar.getBackground();  // Assuming background is the progress drawable

        if (progressDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) progressDrawable;
            Drawable progressLayer = layerDrawable.findDrawableByLayerId(android.R.id.progress);  // Find the progress layer
            if (progressLayer instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) progressLayer;
                gradientDrawable.setCornerRadius(progressCornerRadius);  // Apply the progress corner radius
            }
        }

    }

    public int getContainerCornerRadius() {
        return containerCornerRadius;
    }


    public void setContainerCornerRadius(int containerCornerRadius) {

        RelativeLayout progressContainer = findViewById(R.id.PB_shell);
        Drawable containerDrawable = progressContainer.getBackground();  // Assuming background is the progress drawable

        if (containerDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) containerDrawable;
            Drawable progressContainerLayer = layerDrawable.findDrawableByLayerId(R.id.progressContainer);  // Find the progress layer
            if (progressContainerLayer instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) progressContainerLayer;
                gradientDrawable.setCornerRadius(containerCornerRadius);  // Apply the progress corner radius
            }
        }

    }
}
