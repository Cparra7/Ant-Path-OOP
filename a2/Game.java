package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

/**
 * Game
 * - Main UI controller for A2.
 * - Sets up layout, toolbar, buttons, and connects GameWorld to MapView and ScoreView.
 */
public class Game extends Form {

    private GameWorld gw;
    private MapView mv;
    private ScoreView sv;

    public Game() {

        // Main layout
        this.setLayout(new BorderLayout());

        // GameWorld and views
        gw = new GameWorld();
        mv = new MapView(gw);
        sv = new ScoreView(gw);

        // Register observers
        gw.addObserver(mv);
        gw.addObserver(sv);

        // Toolbar setup
        Toolbar toolBar = new Toolbar();
        setToolbar(toolBar);
        toolBar.setTitle("Ant Path Game");

        // Containers for layout sections
        Container south = new Container(new FlowLayout(Component.CENTER));
        Container east = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container west = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        // Simple borders
        south.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
        east.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
        west.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));

        // Commands
        AboutCommand aboutCmd = new AboutCommand();
        QuitCommand quitCmd = new QuitCommand();
        HelpCommand helpCmd = new HelpCommand();
        FlagCollisionCommand flagCmd = new FlagCollisionCommand(gw);
        SpiderCollision spiderCmd = new SpiderCollision(gw);
        FoodStationCollisionCommand foodCmd = new FoodStationCollisionCommand(gw);
        AccelerateCommand accelCmd = new AccelerateCommand(gw);
        BrakeCommand brakeCmd = new BrakeCommand(gw);
        RightTurnCommand rightCmd = new RightTurnCommand(gw);
        LeftTurnCommand leftCmd = new LeftTurnCommand(gw);
        ClockTickCommand tickCmd = new ClockTickCommand(gw);
        SoundCommand soundCmd = new SoundCommand(gw, toolBar);

        // Sound toggle
        CheckBox soundToggle = new CheckBox();
        soundToggle.setCommand(soundCmd);
        soundToggle.getAllStyles().setBgTransparency(255);
        soundToggle.getAllStyles().setPadding(TOP, 2);
        soundToggle.getAllStyles().setPadding(BOTTOM, 2);
        soundToggle.getAllStyles().setBgColor(ColorUtil.rgb(118, 139, 168));
        soundToggle.getAllStyles().setFgColor(ColorUtil.WHITE);

        // Toolbar commands
        toolBar.addCommandToRightBar(helpCmd);
        toolBar.addCommandToSideMenu(accelCmd);
        toolBar.addCommandToSideMenu(aboutCmd);
        toolBar.addCommandToSideMenu(quitCmd);
        toolBar.addComponentToSideMenu(soundToggle);

        // South controls
        Button flagBtn = new ButtonStyle(); flagBtn.setCommand(flagCmd);
        Button spiderBtn = new ButtonStyle(); spiderBtn.setCommand(spiderCmd);
        Button foodBtn = new ButtonStyle(); foodBtn.setCommand(foodCmd);
        Button tickBtn = new ButtonStyle(); tickBtn.setCommand(tickCmd);
        south.add(flagBtn); south.add(spiderBtn); south.add(foodBtn); south.add(tickBtn);

        // East controls
        Button brakeBtn = new ButtonStyle(); brakeBtn.setCommand(brakeCmd);
        brakeBtn.getAllStyles().setMarginTop(100);
        Button rightBtn = new ButtonStyle(); rightBtn.setCommand(rightCmd);
        east.add(brakeBtn); east.add(rightBtn);

        // West controls
        Button accelBtn = new ButtonStyle(); accelBtn.setCommand(accelCmd);
        accelBtn.getAllStyles().setMarginTop(100);
        Button leftBtn = new ButtonStyle(); leftBtn.setCommand(leftCmd);
        west.add(accelBtn); west.add(leftBtn);

        // Keyboard shortcuts
        addKeyListener('a', accelCmd);
        addKeyListener('b', brakeCmd);
        addKeyListener('l', leftCmd);
        addKeyListener('r', rightCmd);
        addKeyListener('f', foodCmd);
        addKeyListener('g', spiderCmd);
        addKeyListener('t', tickCmd);

        // Layout regions
        add(BorderLayout.NORTH, sv);
        add(BorderLayout.CENTER, mv);
        add(BorderLayout.SOUTH, south);
        add(BorderLayout.EAST, east);
        add(BorderLayout.WEST, west);

        // Initialize world
        gw.init();
        this.show();

        // Sync dimensions
        gw.setWidth(mv.getWidth());
        gw.setHeight(mv.getHeight());
    }
}
