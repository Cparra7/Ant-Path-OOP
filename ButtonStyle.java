package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;

/**
 * ButtonStyle - Same button look as professors.
 */
public class ButtonStyle extends Button {

    public ButtonStyle() {
        // Add a bit of padding for spacing
        getAllStyles().setPadding(TOP, 5);
        getAllStyles().setPadding(BOTTOM, 5);

        // Thin black border around the button
        getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));

        // Full background opacity
        getAllStyles().setBgTransparency(255);

        // Blue background with white text
        getUnselectedStyle().setBgColor(ColorUtil.BLUE);
        getAllStyles().setFgColor(ColorUtil.WHITE);
    }
}