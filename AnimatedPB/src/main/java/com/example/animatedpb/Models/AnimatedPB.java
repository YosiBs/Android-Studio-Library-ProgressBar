package com.example.animatedpb.Models;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
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
    private int valueToDisplay;
    private int animDurationMillis;
    private int progressCornerRadius;
    private int containerCornerRadius;

    private int progressMarginTop;
    private int progressMarginLeft;
    private int progressMarginBottom;
    private int progressMarginRight;

    private int labelColor;
    private int labelTextSize;
    private int valueColor;

    private Logic logic;
    private final int DEFAULT_MIN_VALUE = 0;
    private final int DEFAULT_MAX_VALUE = 100;
    private final int DEFAULT_ANIM_TYPE = 0;
    private final int DEFAULT_VALUE_TYPE = 0;
    private final int DEFAULT_ANIM_DURATION_MILLIS = 1000;
    private final int DEFAULT_PROGRESS_RADIUS = 0;
    private final int DEFAULT_CONTAINER_RADIUS = 0;
    private final int DEFAULT_PROGRESS = 0;
    private final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#005ECB");
    private final int DEFAULT_CONTAINER_COLOR = Color.parseColor("#E1BEE7");
    private final int DEFAULT_BAR_SHAPE = 0;
    private final int DEFAULT_TEXT_COLOR = Color.parseColor("#212121");
    private final int POLL = 0;
    private final int CIRCLE = 1;
    private final int DEFAULT_TEXT_SIZE = 14;
    private final float FULL_OPACITY = 1f;
    private final float NO_OPACITY = 0f;





    public AnimatedPB(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    private void init(Context context,AttributeSet attrs) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AnimatedPB,
                0, 0);

        try {
            barShape = a.getInt(R.styleable.AnimatedPB_barShape, DEFAULT_BAR_SHAPE);
            progressColor = a.getColor(R.styleable.AnimatedPB_progressColor, DEFAULT_PROGRESS_COLOR);
            containerColor = a.getColor(R.styleable.AnimatedPB_containerColor, DEFAULT_CONTAINER_COLOR);
            progress = a.getFloat(R.styleable.AnimatedPB_progress, DEFAULT_PROGRESS);
            minValue = a.getFloat(R.styleable.AnimatedPB_minValue, DEFAULT_MIN_VALUE);
            maxValue = a.getFloat(R.styleable.AnimatedPB_maxValue, DEFAULT_MAX_VALUE);
            logic = new Logic(minValue, maxValue);
            progressCornerRadius = a.getInt(R.styleable.AnimatedPB_progressCornerRadius, DEFAULT_PROGRESS_RADIUS);
            containerCornerRadius = a.getInt(R.styleable.AnimatedPB_containerCornerRadius, DEFAULT_CONTAINER_RADIUS);
            animationType = a.getInt(R.styleable.AnimatedPB_animationType, DEFAULT_ANIM_TYPE);
            valueToDisplay = a.getInt(R.styleable.AnimatedPB_valueToDisplay, DEFAULT_VALUE_TYPE);
            animDurationMillis = a.getInt(R.styleable.AnimatedPB_animDurationMillis, DEFAULT_ANIM_DURATION_MILLIS);
            barLabel = a.getString(R.styleable.AnimatedPB_barLabel);
            progressMarginTop = a.getInt(R.styleable.AnimatedPB_progressMarginTop,0);
            progressMarginLeft = a.getInt(R.styleable.AnimatedPB_progressMarginLeft,0);
            progressMarginBottom = a.getInt(R.styleable.AnimatedPB_progressMarginBottom,0);
            progressMarginRight = a.getInt(R.styleable.AnimatedPB_progressMarginRight,0);
            labelColor = a.getColor(R.styleable.AnimatedPB_labelColor,DEFAULT_TEXT_COLOR);
            valueColor = a.getColor(R.styleable.AnimatedPB_valueColor,DEFAULT_TEXT_COLOR);
            labelTextSize = a.getInt(R.styleable.AnimatedPB_labelTextSize,DEFAULT_TEXT_SIZE);

            // Conditionally inflate layout based on barShape
            if (barShape == POLL) { // Poll progress bar
                LayoutInflater.from(context).inflate(R.layout.horizontal_poll_progress_bar, this, true);
            } else if (barShape == CIRCLE) { // Circular progress bar
                LayoutInflater.from(context).inflate(R.layout.circle_progress_bar, this, true);
            }


            Log.d("ddd", "\n~~~~~~~~~~~~~~\n" +
                    "@ ~ MyAttributes:" +
                    "\nprogressColor: " + progressColor +
                    "\ncontainerColor: " + containerColor +
                    "\nbarShape: " + barShape +
                    "\nprogress: " + progress +
                    "\nminValue: " + minValue +
                    "\nmaxValue: " + maxValue +
                    "\nprogressCornerRadius: " + progressCornerRadius +
                    "\ncontainerCornerRadius: " + containerCornerRadius +
                    "\nanimationType: " + animationType +
                    "\nvalueToDisplay: " + valueToDisplay +
                    "\nanimDurationMillis: " + animDurationMillis +
                    "\nbarLabel: " + barLabel +
                    "\nprogressMarginTop: " + progressMarginTop +
                    "\nprogressMarginLeft: " + progressMarginLeft +
                    "\nprogressMarginBottom: " + progressMarginBottom +
                    "\nprogressMarginRight: " + progressMarginRight +
                    "\nlabelColor: " + labelColor +
                    "\nvalueColor: " + valueColor +

                    "\n~~~~~~~~~~~~~~~~~~~~~~~\n");

            setBarLabel(barLabel);
            setClickable(true);
            setFocusable(true);


        } finally {
            animateProgress(progress,1000);
            a.recycle();
        }
    }

    public void setOnClickListener(OnClickListener l) {
        // Apply the onClick listener to the root view of AnimatedPB
        super.setOnClickListener(l);
    }

    public void animateProgress(float newProgress, int animDurationMillis) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", (int)progress, (int)newProgress);
        animator.setDuration(animDurationMillis);  // Set your animation duration
        // Choose the interpolator based on animationType
        switch (animationType) {
            case 0: // Linear
                animator.setInterpolator(new LinearInterpolator());
                break;
            case 1: // Ease-out
                animator.setInterpolator(new DecelerateInterpolator(2.0f));
                break;
            case 2: // Ease-In
                animator.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 3:
                animator.setInterpolator(new BounceInterpolator());
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
            if(barShape == CIRCLE){
                params.height = newWidth;
                if(params.width == 0){
                    params.width = 1;
                    params.height = 1;
                }
            }
            progressBar.setLayoutParams(params);
        });
        // Check if the barLabel can fit within the progress bar

        setBarLabel(barLabel);
        setLabelColor(labelColor);
        setLabelTextSize(labelTextSize);
        setValueColor(valueColor);
        setValueToDisplay(valueToDisplay);
        setContainerCornerRadius(containerCornerRadius);
        setProgressCornerRadius(progressCornerRadius);
        setProgressColor(progressColor);
        setContainerColor(containerColor);
        setProgressMargin(progressMarginTop,progressMarginLeft,progressMarginBottom,progressMarginRight);
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

    private void setProgressColor(int progressColor) {
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

    private void setContainerColor(int containerColor) {
        RelativeLayout progressContainer = findViewById(R.id.PB_shell);
        Drawable containerDrawable = progressContainer.getBackground();  // Assuming background is the progress drawable

        if (containerDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) containerDrawable;
            Drawable ContainerLayer = layerDrawable.findDrawableByLayerId(R.id.progressContainer);  // Find the progress layer
            if (ContainerLayer != null) {
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
                if (progressBarWidth >= textWidth) {
                    textFadeIn(labelMTV, barLabel);
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
            LayerDrawable layerDrawable = (LayerDrawable) progressDrawable.mutate();  // Ensure unique instance
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
        Drawable containerDrawable = progressContainer.getBackground();  // Assuming background is the container drawable

        if (containerDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) containerDrawable.mutate();  // Ensure unique instance
            Drawable progressContainerLayer = layerDrawable.findDrawableByLayerId(R.id.progressContainer);  // Find the container layer
            if (progressContainerLayer instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) progressContainerLayer;
                gradientDrawable.setCornerRadius(containerCornerRadius);  // Apply the container corner radius
            }
        }

    }

    public int getValueToDisplay() {
        return valueToDisplay;
    }

    public void setValueToDisplay(int valueToDisplay) {
        MaterialTextView value_MTV = findViewById(R.id.value_MTV);
        switch (valueToDisplay) {
            case 0: // number
                value_MTV.setText((int)progress + "");
                break;
            case 1: // percentage
                value_MTV.setText((int)progress + "%");
                break;
            case 2: // none
                value_MTV.setText("");
                break;
        }
    }

    public void setProgressMargin(int top, int left, int bottom, int right) {
        RelativeLayout progressBar = findViewById(R.id.PB_progress);  // Assuming this is your progress bar view
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) progressBar.getLayoutParams();
        // Set the margins
        params.setMargins(left, top, right, bottom);
        // Apply the updated layout params
        progressBar.setLayoutParams(params);
        progressBar.requestLayout();  // Request layout to apply the changes
    }

    public int getLabelColor() {
        return labelColor;
    }

    public AnimatedPB setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        MaterialTextView labelMTV = findViewById(R.id.label_MTV);
        labelMTV.setTextColor(labelColor);
        return this;
    }

    public int getValueColor() {
        return valueColor;
    }

    public AnimatedPB setValueColor(int valueColor) {
        this.valueColor = valueColor;
        MaterialTextView valueMTV = findViewById(R.id.value_MTV);
        valueMTV.setTextColor(valueColor);
        return this;
    }

    public AnimatedPB resetProgress(){
        this.progress = 0;
        MaterialTextView labelMTV = findViewById(R.id.label_MTV);
        labelMTV.setVisibility(GONE);
        setProgress(0);

        return this;
    }

    public void textFadeIn(MaterialTextView mtv, String text){
        if (mtv.getVisibility() != View.VISIBLE) {
            mtv.setText(text);
            mtv.setVisibility(View.VISIBLE);
            mtv.setAlpha(NO_OPACITY);

            // Animate the alpha property to fade in
            mtv.animate()
                    .alpha(FULL_OPACITY)
                    .setDuration(1000)  // 500 milliseconds duration
                    .setListener(null);  // No additional listener
        }
    }

    public int getLabelTextSize() {
        return labelTextSize;
    }

    public AnimatedPB setLabelTextSize(int labelTextSize) {
        this.labelTextSize = labelTextSize; // Store the value
        MaterialTextView labelMTV = findViewById(R.id.label_MTV);  // Get the label TextView
        if (labelMTV != null) {
            labelMTV.setTextSize(labelTextSize);  // Apply the text size to the label
        }
        return this;  // Return the AnimatedPB instance for chaining
    }


}
