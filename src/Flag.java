package com.mycompany.a1;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public class Flag extends Fixed {

    private int sequenceNumber;

    public Flag(int sequenceNumber, Point location) {
        super(ColorUtil.BLUE, 10, location); // fixed blue color, size 10
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

    @Override
    public void setColor(int color) {
        // flags cannot change color once created
    }
}
