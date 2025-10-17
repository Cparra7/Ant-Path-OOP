package com.mycompany.a2;

import com.codename1.charts.models.Point;

//
public abstract class Fixed extends GameObject {

    // Pass color, size, and location to GameObject constructor
    public Fixed(int color, int size, Point location) {
        super(color, size, location);
    }

    // Disable manual movement for fixed objects
    @Override
    public void setLocation(float x, float y) { }
}
