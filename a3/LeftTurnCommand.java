package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * LeftTurnCommand - Rotates the Ant slightly to the left when triggered.
 */
public class LeftTurnCommand extends Command {

    // Reference to the active GameWorld
    private GameWorld gw;

    // Sets the command name and stores the GameWorld reference
    public LeftTurnCommand(GameWorld gw) {
        super("Left");
        this.gw = gw;
    }

    // Called when the player presses the left turn command
    @Override
    public void actionPerformed(ActionEvent e) {
        gw.Left(); // turn the ant left
    }
}