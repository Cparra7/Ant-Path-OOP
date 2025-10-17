package com.mycompany.a2;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;

/**
 * SoundCommand - Toggles game sound on or off using a checkbox as shown in lecture.
 */
public class SoundCommand extends Command {

    private GameWorld gw;
    private Toolbar toolBar;

    public SoundCommand(GameWorld gw, Toolbar toolBar) {
        super("Sound");
        this.gw = gw;
        this.toolBar = toolBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Toggle sound based on checkbox state
        boolean enabled = ((CheckBox) e.getComponent()).isSelected();
        gw.setSound(enabled);

        // Close the side menu after selection
        toolBar.closeSideMenu();
    }
}
