package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;

public class FoodStation extends Fixed {
   
	// Amount of food energy stored in this station (equals size at start)

    private int capacity;

    public FoodStation() {
        super(ColorUtil.GREEN, random.nextInt(41) + 10, randomLocation());
        capacity = getSize();
    }

    // String summary for map output and debugging

    @Override
    public String toString() {
        return "FoodStation: loc=" + (Math.round(getX() * 10.0) / 10.0) + "," + (Math.round(getY() * 10.0) / 10.0) +
               " color=" + toStringColor() +
               " size=" + getSize() +
               " capacity=" + capacity;
    }
    
    // Getter and setter for capacity so it can shrink as ants eat the food

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
}
