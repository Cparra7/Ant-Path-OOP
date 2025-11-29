package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/**
 * ShockWave objects are spawned when the ant collides with a spider. They move
 * slowly across the world and expire after a short lifetime. The curve is drawn
 * using a recursive cubic Bezier implementation.
 */
public class ShockWave extends Movable {

    private final Point[] controlPoints;
    private int lifetime;
    private final int maxLifetime;

    public ShockWave(Point origin, int heading, int speed, GameWorld gw) {
        super(ColorUtil.rgb(200, 200, 255), 40, origin, heading, speed, gw);

        // random local control points so each curve is unique
        controlPoints = new Point[4];
        for (int i = 0; i < controlPoints.length; i++) {
            float x = (random.nextFloat() - 0.5f) * getSize() * 3;
            float y = (random.nextFloat() - 0.5f) * getSize() * 3;
            controlPoints[i] = new Point(x, y);
        }

        // lifetime roughly proportional to current world window
        int windowSpan = gw.getWidth() + gw.getHeight();
        maxLifetime = Math.max(50, windowSpan / 10);
        lifetime = maxLifetime;
    }

    public boolean isExpired() {
        return lifetime <= 0;
    }

    public void tick(int elapsedMs) {
        move(elapsedMs);
        lifetime -= elapsedMs;
    }

    @Override
    public void draw(Graphics g, Point pCmpRelPrnt) {
        // draw the cubic curve in display space (current translation used for center)
        g.setColor(getColor());

        Point p0 = translateToWorld(controlPoints[0]);
        Point p1 = translateToWorld(controlPoints[1]);
        Point p2 = translateToWorld(controlPoints[2]);
        Point p3 = translateToWorld(controlPoints[3]);

        recursiveDraw(g, p0, p1, p2, p3, 0, pCmpRelPrnt);
    }

    private Point translateToWorld(Point local) {
        return new Point(local.getX() + getX(), local.getY() + getY());
    }

    // Recursive cubic Bezier drawing
    private void recursiveDraw(Graphics g, Point p0, Point p1, Point p2, Point p3, int depth, Point parentOrigin) {
        if (depth > 5 || flatEnough(p0, p1, p2, p3)) {
            int x1 = (int) (p0.getX() + parentOrigin.getX());
            int y1 = (int) (p0.getY() + parentOrigin.getY());
            int x2 = (int) (p3.getX() + parentOrigin.getX());
            int y2 = (int) (p3.getY() + parentOrigin.getY());
            g.drawLine(x1, y1, x2, y2);
            return;
        }

        // subdivide
        Point q0 = midpoint(p0, p1);
        Point q1 = midpoint(p1, p2);
        Point q2 = midpoint(p2, p3);

        Point r0 = midpoint(q0, q1);
        Point r1 = midpoint(q1, q2);

        Point s = midpoint(r0, r1);

        recursiveDraw(g, p0, q0, r0, s, depth + 1, parentOrigin);
        recursiveDraw(g, s, r1, q2, p3, depth + 1, parentOrigin);
    }

    private boolean flatEnough(Point p0, Point p1, Point p2, Point p3) {
        float dx = p3.getX() - p0.getX();
        float dy = p3.getY() - p0.getY();
        float d1 = Math.abs((p1.getX() - p3.getX()) * dy - (p1.getY() - p3.getY()) * dx);
        float d2 = Math.abs((p2.getX() - p3.getX()) * dy - (p2.getY() - p3.getY()) * dx);
        return (d1 + d2) < 1.5f * (dx * dx + dy * dy);
    }

    private Point midpoint(Point a, Point b) {
        return new Point((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
    }
}
