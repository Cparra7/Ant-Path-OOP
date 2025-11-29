package com.mycompany.a3;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;

public class SoundCommand extends Command {
	
	private GameWorld gw;	
	Toolbar toolBar;
	
    public SoundCommand(GameWorld gw, Toolbar toolBar) {    	
        super("Sound");        
        this.gw = gw;        
        this.toolBar = toolBar;
        
    }
    
    
    // The action for the sound box being clicked on
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	System.out.println("Sound before = " + gw.getSound());
    	
    	if (((CheckBox) e.getComponent()).isSelected()) {   		
    		gw.setSound(true);   		
    	} else {   		
    		gw.pauseSound();    		
    		gw.setSound(false);  		
    	}    	
    	toolBar.closeSideMenu();
        
    }
    
}