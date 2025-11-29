package com.mycompany.a3;

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
import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable {

    // world + views
    private GameWorld gw;
    private MapView mv;
    private ScoreView sv;

    // UI shell
    private Toolbar toolBar;
    private UITimer timer;

    // buttons/toggles
    private Button accelerateButton;
    private Button brakeButton;
    private Button leftButton;
    private Button rightButton;
    private Button pauseButton;
    private Button positionButton;
    private CheckBox soundCheckBox;

    // commands
    private AboutCommand aboutCmd;
    private QuitCommand exitCmd;
    private HelpCommand helpCmd;

    private AccelerateCommand accelerateCmd;
    private BrakeCommand brakeCmd;
    private RightTurnCommand rightCmd;
    private LeftTurnCommand leftCmd;
    private SoundCommand soundCmd;

    // A3 additions
    private PauseCommand pauseCmd;       // calls gamePausePlay()
    private PositionCommand positionCmd; // toggles position mode in GW (used only while paused)

    public Game() {
        // base layout
        setLayout(new BorderLayout());

        // create toolbar FIRST (so commands that reference it are safe)
        toolBar = new Toolbar();
        setToolbar(toolBar);
        toolBar.setTitle("Ant Path Game");

        // world + views
        gw = new GameWorld();
        mv = new MapView(gw);
        sv = new ScoreView(gw);

        // observe world
        gw.addObserver(mv);
        gw.addObserver(sv);

        // layout containers
        Container south = new Container(new FlowLayout(Component.CENTER));
        Container east  = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container west  = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        // simple borders to match spec look
        south.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
        east.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
        west.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));

        // commands
        aboutCmd = new AboutCommand();
        exitCmd  = new QuitCommand();
        helpCmd  = new HelpCommand();

        accelerateCmd = new AccelerateCommand(gw);
        brakeCmd      = new BrakeCommand(gw);
        rightCmd      = new RightTurnCommand(gw);
        leftCmd       = new LeftTurnCommand(gw);

        soundCmd    = new SoundCommand(gw, toolBar); // toolbar now non-null
        pauseCmd    = new PauseCommand(this);        // will call gamePausePlay()
        positionCmd = new PositionCommand(gw);

        // sound toggle
        soundCheckBox = new CheckBox();
        soundCheckBox.setCommand(soundCmd);
        soundCheckBox.getAllStyles().setBgTransparency(255);
        soundCheckBox.getAllStyles().setBgColor(ColorUtil.rgb(118, 139, 168));
        soundCheckBox.getAllStyles().setFgColor(ColorUtil.WHITE);

        // toolbar entries
        toolBar.addCommandToRightBar(helpCmd);
        toolBar.addCommandToSideMenu(accelerateCmd);
        toolBar.addCommandToSideMenu(aboutCmd);
        toolBar.addCommandToSideMenu(exitCmd);
        toolBar.addComponentToSideMenu(soundCheckBox);

        // buttons (use your ButtonStyle)
        positionButton = new ButtonStyle();
        positionButton.setCommand(positionCmd);
        positionButton.setEnabled(false); // only usable while paused

        pauseButton = new ButtonStyle();
        pauseButton.setCommand(pauseCmd);

        brakeButton = new ButtonStyle();
        brakeButton.getAllStyles().setMarginTop(100);
        brakeButton.setCommand(brakeCmd);

        rightButton = new ButtonStyle();
        rightButton.setCommand(rightCmd);

        accelerateButton = new ButtonStyle();
        accelerateButton.getAllStyles().setMarginTop(100);
        accelerateButton.setCommand(accelerateCmd);

        leftButton = new ButtonStyle();
        leftButton.setCommand(leftCmd);

        // wire layout
        south.add(positionButton);
        south.add(pauseButton);
        east.add(brakeButton);
        east.add(rightButton);
        west.add(accelerateButton);
        west.add(leftButton);

        add(BorderLayout.NORTH, sv);
        add(BorderLayout.CENTER, mv);
        add(BorderLayout.SOUTH, south);
        add(BorderLayout.EAST, east);
        add(BorderLayout.WEST, west);

        // key bindings 
        addKeyListener('a', accelerateCmd);
        addKeyListener('b', brakeCmd);
        addKeyListener('l', leftCmd);
        addKeyListener('r', rightCmd);

        // show + init world
        show();
        gw.setWidth(mv.getWidth());
        gw.setHeight(mv.getHeight());
        gw.init();
      
        gw.createSounds();
        revalidate();

        // timer
        timer = new UITimer(this);
        timer.schedule(100, true, this);
    }

    // tick -> world clock
    @Override
    public void run() {
        gw.clockTick();
    }

    // pause/resume toggle with UI lock when paused
    public void gamePausePlay() {
        if (!gw.getPaused()) {
            // --- PAUSE ---
            pauseButton.setText("Play");
            gw.pauseSound();
            gw.setPaused(true);
            timer.cancel();

            // position/selection mode allowed in pause
            positionButton.setEnabled(true);

            // lock inputs
            accelerateButton.setEnabled(false);
            brakeButton.setEnabled(false);
            rightButton.setEnabled(false);
            leftButton.setEnabled(false);
            soundCheckBox.setEnabled(false);

            // disable menu commands (note to self - don’t remove to avoid CN1 inconsistencies)
            accelerateCmd.setEnabled(false);
            aboutCmd.setEnabled(false);
            exitCmd.setEnabled(false);
            helpCmd.setEnabled(false);

            // remove key listeners
            removeKeyListener('r', rightCmd);
            removeKeyListener('l', leftCmd);
            removeKeyListener('a', accelerateCmd);
            removeKeyListener('b', brakeCmd);

        } else {
            // --- RESUME ---
            pauseButton.setText("Pause");
            gw.setPosition(false); // exit position mode when resuming
            gw.playSound();
            gw.setPaused(false);
            timer.schedule(100, true, this);

            positionButton.setEnabled(false);

            // unlock inputs
            accelerateButton.setEnabled(true);
            brakeButton.setEnabled(true);
            rightButton.setEnabled(true);
            leftButton.setEnabled(true);
            soundCheckBox.setEnabled(true);

            // re-enable menu commands
            accelerateCmd.setEnabled(true);
            aboutCmd.setEnabled(true);
            exitCmd.setEnabled(true);
            helpCmd.setEnabled(true);

            // re-add key listeners
            addKeyListener('r', rightCmd);
            addKeyListener('l', leftCmd);
            addKeyListener('a', accelerateCmd);
            addKeyListener('b', brakeCmd);
        }
    }
}
