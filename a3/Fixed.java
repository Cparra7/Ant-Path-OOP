package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class Fixed extends GameObject implements ISelectable {
    
    // true if this object is currently selected (for repositioning)
    private boolean selected;

    // constructor passes setup values to GameObject
    public Fixed(int color, int size, Point location, GameWorld gw) {
        super(color, size, location, gw);
    }

    // checks if a tap/click happened within this object's square bounds
    public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
        int size = getSize();
        float px = pPtrRelPrnt.getX();
        float py = pPtrRelPrnt.getY();

        // find this object’s top-left corner in relation to the parent
        float x = getX() + pCmpRelPrnt.getX() - (size / 2f);
        float y = getY() + pCmpRelPrnt.getY() - (size / 2f);

        // return true if pointer coordinates are within the box
        return (px >= x && px <= x + size && py >= y && py <= y + size);
    }

    // whether or not this object is currently selected
    @Override
    public boolean isSelected() {
        return selected;
    }

    // update selection status
    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
