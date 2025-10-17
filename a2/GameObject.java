package com.mycompany.a2;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import java.util.Random;

public abstract class GameObject {

    private final int size;
    private Point location;
    private int color;

    protected static final Random random = new Random();

    public GameObject(int color, int size, Point location) {
        this.color = color;
        this.size = size;
        this.location = location;
    }

    // Returns a random location in a 1000x1000 world space
    public static Point randomLocation() {
        return new Point(random.nextFloat() * 1000f, random.nextFloat() * 1000f);
    }

    public int getSize() { return size; }

    public float getX() { return location.getX(); }

    public float getY() { return location.getY(); }

    public void setLocation(float x, float y) { location = new Point(x, y); }

    public Point getLocation() { return location; }

    public void setLocation(Point p) { location = p; }

    public int getColor() { return color; }

    public void setColor(int color) { this.color = color; }

    public String toStringColor() {
        return "[" + ColorUtil.red(color) + ", " + ColorUtil.green(color) + ", " + ColorUtil.blue(color) + "]";
    }

    // kept for compatibility with A2 classes
    public String colorToString() { return toStringColor(); }
}
