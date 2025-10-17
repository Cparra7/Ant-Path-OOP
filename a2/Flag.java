package com.mycompany.a2;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public class Flag extends Fixed {

    private int sequenceNumber;

    public Flag(int sequenceNumber, Point location) {
        super(ColorUtil.BLUE, 10, location); // fixed color and size
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public String toString() {
        return "Flag: loc=" + (Math.round(getX() * 10.0) / 10.0) + "," + (Math.round(getY() * 10.0) / 10.0) +
               " color=" + toStringColor() +
               " size=" + getSize() +
               " seqNum=" + sequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    // flags are not allowed to change color
    @Override
    public void setColor(int color) { }
}
