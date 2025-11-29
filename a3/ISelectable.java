package com.mycompany.a3;

import com.codename1.ui.Graphics; 
import com.codename1.charts.models.Point;

public interface ISelectable {
	
	public void setSelected(boolean object);
	
	public boolean isSelected();
	
        // Draws the selected object
        public void draw(Graphics g, Point pCmpRelPrnt);

        // If the pointer (relative to the MapView) is within the object's bounds
        public boolean contains(float px, float py);
	
}