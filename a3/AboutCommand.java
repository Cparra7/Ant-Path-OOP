package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/**
 * Basic info about the game and other course details.
 */
public class AboutCommand extends Command {

    public AboutCommand() {
        super("About");
    }

    //Action performed *override*
    @Override
    public void actionPerformed(ActionEvent e) {
        Dialog.show("About", "Ant Path Game - Assignment 3 - Cristian Parra\nCSC 133", "OK", null);

    }
}
