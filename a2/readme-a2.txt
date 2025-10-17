I verified that my Assignment 2 submission builds and runs correctly on the ECS-General AVD remote lab machine.
1.) First tested the project on my Mac by running it inside Eclipse (Codename One simulator launched and displayed the GUI with buttons, score view, and bordered map view).
2.) Then tested from the Mac terminal by navigating into the A2Prj directory and running it with the Mac command (using “:” as the classpath separator).
3.) After confirming everything worked locally, I copied the entire A2Prj project folder (including src and dist/A2Prj.jar) into the eclipse-workspace directory on the ECS-General AVD remote machine.
4.) Logged into the ECS-General AVD environment as instructed on Canvas.
5.) Opened Command Prompt, navigated into the project folder (C:\Users\cristiandavidparra\eclipse-workspace\A2Prj).
6.) Ran the program from the command prompt using the Windows command:
java -cp dist\A2Prj.jar;JavaSE.jar com.codename1.impl.javase.Simulator com.mycompany.a2.Starter  


The Codename One simulator launched successfully on the lab machine and displayed the main game form with GUI components (buttons, side menu, score view, and map view). The layout followed BorderLayout with ScoreView at the top, MapView in the center, and control containers on the left, right, and bottom.
Testing results:
 All required design patterns and GUI features function as specified:
• Observer/Observable – ScoreView and MapView update automatically when GameWorld changes.
• Iterator – Implemented custom IIterator and ICollection interfaces; all game objects accessed through the iterator.
• Command – Each command object (Accelerate, Brake, Left, Right, etc.) executes correctly through buttons, side menu, and key bindings.
• Singleton – Only one Ant instance exists.
GUI tests:
• Buttons and key bindings trigger correct GameWorld methods (accelerate, brake, turn, tick, etc.).
• Side menu includes Accelerate, Sound (checkbox), About, and Exit.
• “Help” button displays dialog with valid key commands.
• “About” dialog shows my name, course info, and version.
• “Exit” opens confirmation dialog.
• ScoreView updates labels for lives, clock, flag, food level, health level, and sound ON/OFF.
• MapView container visible with red border confirming successful installation in the form.
Updates from Assignment 1:
 From A1 to A2, I corrected missing fields and setters in several UML classes, such as adding color and location setters to GameObject and heading, speed, and food level fields to Movable. Spider now properly overrides setColor() and setFoodLevel(). I also introduced GameObjectCollection with custom ICollection and IIterator interfaces to organize and access all game objects through iteration instead of direct access. The Ant class was converted to a Singleton to ensure a single instance. The game structure was refactored to follow MVC, separating logic into GameWorld, MapView, and ScoreView. I replaced text-based commands with GUI buttons, side menu, and key bindings using the Command pattern, ensuring the UML now correctly represents the new observers, commands, and relationships.
Submission contents (in the zip file):
 • src folder with all .java source files.
• A2Prj.jar built and tested on the ECS-General AVD machine.
• UML Diagram (PDF).
Submission contents (outside zip file):
 • readme-a2.txt
Conclusion:
 I confirmed that my submission (src + jar + UML in one zip) runs correctly on the ECS-General AVD lab machine from the Windows command prompt, and that all MVC structure, GUI layout, and required design patterns work as described in the assignment instructions.