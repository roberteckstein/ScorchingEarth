package com.sherwoodhs;

import com.sherwoodhs.bullet.ScorchBullet;
import com.sherwoodhs.explosion.ScorchExplosion;
import com.sherwoodhs.tank.ScorchTank;
import com.sherwoodhs.terrain.ScorchTerrain;
import com.sherwoodhs.ui.ScorchStatus;
import com.sherwoodhs.ui.ScorchTankSettings;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ScorchGame implements PropertyChangeListener {

    private JFrame frame;
    private int currentWind;

    //  These are public because I just don't want to deal with accessors.
    //  Getters and setters are proper use of object-oriented code, but
    //  here I just don't feel like passing around singleton references
    //  via constructors to all these objects. I'll just pass the ScorchGame
    //  object and get at the terrain, status, and settings panels from there.

    public ScorchTerrain terrain;
    public ScorchStatus status;
    public ScorchTankSettings settings;
    public boolean waitForPlayerFire;

    //  Same thing here-- I'll be referencing these ArrayList's in a number
    //  of classes, and since I'm not creating APIs that have to do sanity
    //  checks on setters, I'll just make them public... because I'm lazy.

    public ArrayList<ScorchTank> players = new ArrayList<>();
    public ArrayList<ScorchBullet> bullets = new ArrayList<>();
    public ArrayList<ScorchExplosion> explosions = new ArrayList<>();

    //  This is just initialization data for each player. Change as you see fit.
    private static Color[] playerColors = {Color.red, Color.blue, Color.yellow, Color.green, Color.pink};
    private static int[] playerPositions = {50, 720, 200, 550, 300};


    public ScorchGame(int numberOfPlayers) {

        //  Create the Frame for the game, with the playfield in the center
        //  and two interface panels on the top and the bottom.

        frame = new JFrame("Scorching Earth");
        frame.getContentPane().setLayout(new BorderLayout());

        status = new ScorchStatus();
        frame.getContentPane().add(status, BorderLayout.NORTH);

        terrain = new ScorchTerrain(this, 800, 500);
        frame.getContentPane().add(terrain, BorderLayout.CENTER);

        settings = new ScorchTankSettings(this);
        frame.getContentPane().add(settings, BorderLayout.SOUTH);

        //  Create each player
        for (int i = 0; i < numberOfPlayers; i++) {
            ScorchTank t = new ScorchTank(i+1, playerPositions[i], 90, playerColors[i]);
            t.addPropertyChangeListener(this);
            t.setY(terrain.getGroundLevelAtColumn(playerPositions[i]));
            players.add(t);
        }

        //  Pack the components, which has the effect of "squishing" them together
        //  as best as possible, then display the frame.

        frame.pack();
        frame.setVisible(true);

    }

    public int getCurrentWind() {
        return currentWind;
    }

    public void setCurrentWind(int currentWind) {
        this.currentWind = currentWind;
    }

    public void performAnimation() {
        if (terrain.isAnimating())
            terrain.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        terrain.repaint();
    }

}
