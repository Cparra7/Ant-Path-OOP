package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Label;

/**
 * LabelStyle - Blue text style used for all labels in the UI.
 */
public class LabelStyle extends Label {

    public LabelStyle(String text) {
        super(text);
        // Set label text color to blue
        getAllStyles().setFgColor(ColorUtil.BLUE);
    }

    public LabelStyle() {
        // Default label with blue text
        getAllStyles().setFgColor(ColorUtil.BLUE);
    }
}