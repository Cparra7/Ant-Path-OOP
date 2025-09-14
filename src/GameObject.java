package com.mycompany.a1;

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

    public static Point randomLocation() {
        return new Point(random.nextFloat() * 1000f, random.nextFloat() * 1000f);
    }

    public int getSize() { return size; }

    public Point getLocation() { return location; }

    public float getX() { return location.getX(); }

    public float getY() { return location.getY(); }

    public void setLocation(float x, float y) {
        this.location = new Point(x, y);
    }

    public void setLocation(Point p) {
        this.location = p;
    }

    public int getColor() { return color; }

    public void setColor(int color) { this.color = color; }

    public String toStringColor() {
        return "[" + ColorUtil.red(color) + ", " + ColorUtil.green(color) + ", " + ColorUtil.blue(color) + "]";
    }

    // Optional: keep old name for compatibility if other code calls it
    public String colorToString() { return toStringColor(); }
}
