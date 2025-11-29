package com.mycompany.a3;

import com.codename1.ui.Display;
import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;


public class Sound {	
	private Media m;
	
	
	
	public Sound(String fileName) {
		
		if (Display.getInstance().getCurrent() == null) {
			
			System.out.println("Error: Create sound objects after calling show()!");
			
			System.exit(0);
			
		}
		
		
		while (m == null) {
			
			try {
				
				InputStream in = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
				
				m = MediaManager.createMedia(in, "audio/wav");
				
			} 
			
			catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	//Similar to BGSound
	
	public void play() {	
		m.setTime(0);		
		m.play();
		
	}
}