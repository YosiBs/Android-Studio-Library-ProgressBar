package com.example.animatedpb.Logics;

import android.util.Log;

public class Logic {

    private float minValue;
    private float maxValue;

    public Logic(float minValue, float maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public float calculateProgress(float progress) {
        return Math.max(minValue, Math.min(progress, maxValue));  // Ensure progress is within bounds
    }

    public int calculateWidth(int containerWidth, float progress) {
        return (int) (containerWidth * (progress / maxValue));  // Calculate new width based on progress
    }


}
