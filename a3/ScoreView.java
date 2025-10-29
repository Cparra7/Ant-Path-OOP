package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer {

    private GameWorld gw;

    private Label healthLevel;
    private Label sound;
    private Label lives;
    private Label timer;
    private Label lastFlagReached;
    private Label foodLevel;

    public ScoreView(GameWorld gw) {
        this.gw = gw;
        setLayout(new FlowLayout(Component.CENTER));

        Label timerLabel = new LabelStyle("Time:");
        timer = new LabelStyle();
        add(timerLabel);
        add(timer);

        Label livesLabel = new LabelStyle("Lives:");
        lives = new LabelStyle();
        add(livesLabel);
        add(lives);

        Label flagLabel = new LabelStyle("Last Flag:");
        lastFlagReached = new LabelStyle();
        add(flagLabel);
        add(lastFlagReached);

        Label foodLabel = new LabelStyle("Food:");
        foodLevel = new LabelStyle();
        add(foodLabel);
        add(foodLevel);

        Label healthLabel = new LabelStyle("Health:");
        healthLevel = new LabelStyle();
        add(healthLabel);
        add(healthLevel);

        Label soundLabel = new LabelStyle("Sound:");
        sound = new LabelStyle();
        add(soundLabel);
        add(sound);
    }

    @Override
    public void update(Observable o, Object arg) {
        // Set the labels to the values using GameWorld getters
        lives.setText(" " + gw.getLives());
        timer.setText(" " + gw.getTimer());
        lastFlagReached.setText(" " + gw.getLastFlagReached());
        foodLevel.setText(" " + gw.getFoodLevel());
        healthLevel.setText(" " + gw.getHealthLevel());

        // Show sound state
        sound.setText(gw.getSound() ? "ON" : "OFF");

        revalidate(); // refresh the view
    }
}