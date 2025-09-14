package com.mycompany.a1;

import com.codename1.charts.models.Point;

public abstract class Fixed extends GameObject {
    public Fixed(int color, int size, Point location) {
        super(color, size, location);
    }
    @Override
    public void setLocation(float x, float y) { }
}
