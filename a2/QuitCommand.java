package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

/**
 * QuitCommand - Confirms and exits the game when selected.
 */
public class QuitCommand extends Command {

    // Sets the command label
    public QuitCommand() {
        super("Exit");
    }

    // Runs when the command is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        // Ask the player if they want to quit
        boolean confirm = Dialog.show("Confirm Exit", "Are you sure you want to exit?", "Yes", "No");

        // Close the app if they choose Yes
        if (confirm) {
            Display.getInstance().exitApplication();
        }
    }
}
