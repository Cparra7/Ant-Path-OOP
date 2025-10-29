package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/**
 * HelpCommand - Shows control instructions for the game.
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("Help");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Show all key bindings for quick reference
        Dialog.show("Help",
                "A: Accelerate\n" +
                "B: Brake\n" +
                "L: Turn Left\n" +
                "R: Turn Right\n" +
                "F: Collide with Food Station\n" +
                "G: Collide with Spider\n" +
                "T: Game Clock Tick\n",
                "OK", null);
    }
}