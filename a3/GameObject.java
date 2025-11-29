package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Transform;

import java.util.ArrayList;
import java.util.Random;

public abstract class GameObject implements IDrawable, ICollider {

    private int size;
    private int color;
    private GameWorld gw;
    private ArrayList<GameObject> collisionItems;

    // Local transformations (translation/rotation/scale)
    private Transform myTranslate;
    private Transform myRotate;
    private Transform myScale;

    protected static final Random random = new Random();

    // Constructor - initializes color, size, location, and GameWorld reference
    public GameObject(int color, int size, Point location, GameWorld gw) {
        this.color = color;
        this.size = size;
        this.gw = gw;
        this.collisionItems = new ArrayList<>();

        // initialize transforms
        myTranslate = Transform.makeIdentity();
        myRotate = Transform.makeIdentity();
        myScale = Transform.makeIdentity();

        // initial placement
        if (location != null) {
            translate(location.getX(), location.getY());
        }
    }

    // Generates a random location within the given width and height
    public static Point randomLocation(int width, int height) {
        return new Point(random.nextFloat() * width, random.nextFloat() * height);
    }

    // Checks if this object collides with another based on circular bounds
    @Override
    public boolean collidesWith(GameObject obj) {
        double dx = this.getX() - obj.getX();
        double dy = this.getY() - obj.getY();

        int thisRadius = this.getSize() / 2;
        int otherRadius = obj.getSize() / 2;

        double distanceSquared = dx * dx + dy * dy;
        double radiusSumSquared = (thisRadius + otherRadius) * (thisRadius + otherRadius);

        return distanceSquared <= radiusSumSquared;
    }

    // Handles collision logic for Ant with FoodStation, Flag, or Spider
    @Override
    public void handleCollision(GameObject otherObject) {
        // Ignore if already processed or not the Ant
        if (collisionItems.contains(otherObject) || !(this instanceof Ant)) {
            return;
        }

        if (otherObject instanceof FoodStation) {
            FoodStation food = (FoodStation) otherObject;
            if (food.getCapacity() != 0) {
                gw.foodStationCollision(food);
            }
        } else if (otherObject instanceof Flag) {
            gw.flagCollision(((Flag) otherObject).getSequenceNumber());
        } else if (otherObject instanceof Spider) {
            gw.spiderCollision();
        }

        // Mark collision as handled
        collisionItems.add(otherObject);
    }

    // Removes an object from the current collision list
    public void removeObject(GameObject otherObject) {
        collisionItems.remove(otherObject);
    }

    // --- Getters and Setters ---

    public int getSize() {
        return size;
    }

    public float getX() {
        return myTranslate.getTranslateX();
    }

    public float getY() {
        return myTranslate.getTranslateY();
    }

    public void setLocation(float x, float y) {
        myTranslate.setTranslation(x, y);
    }

    public Point getLocation() {
        return new Point(myTranslate.getTranslateX(), myTranslate.getTranslateY());
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String colorToString() {
        return "[" + ColorUtil.red(color) + ", " + ColorUtil.green(color) + ", " + ColorUtil.blue(color) + "]";
    }

    // Gives subclasses access to the current GameWorld reference
    protected GameWorld getGameWorld() {
        return gw;
    }

    // --- Transform helpers ---
    public Transform getMyTranslate() { return myTranslate; }
    public Transform getMyRotate() { return myRotate; }
    public Transform getMyScale() { return myScale; }

    public void translate(float dx, float dy) {
        myTranslate.translate(dx, dy);
    }

    public void rotate(float degrees) {
        myRotate.rotate((float) Math.toRadians(degrees), 0, 0);
    }

    public void scale(float sx, float sy) {
        myScale.scale(sx, sy);
    }
}
