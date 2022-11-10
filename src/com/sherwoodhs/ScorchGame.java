package com.sherwoodhs;

import com.sherwoodhs.bullet.BulletTemplate;
import com.sherwoodhs.explosion.Explosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.GameStatus;
import com.sherwoodhs.ui.TankStatus;

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

    public Terrain terrain;
    public GameStatus status;
    public TankStatus settings;
    public boolean waitForPlayerFire;

    //  Same thing here-- I'll be referencing these ArrayList's in a number
    //  of classes, and since I'm not creating APIs that have to do sanity
    //  checks on setters, I'll just make them public... because I'm lazy.

    public ArrayList<Tank> players = new ArrayList<>();
    public ArrayList<BulletTemplate> bullets = new ArrayList<>();
    public ArrayList<Explosion> explosions = new ArrayList<>();

    //  This is just initialization data for each player. Change as you see fit.
    private static Color[] playerColors = {Color.red, Color.cyan, Color.yellow, Color.green, Color.pink};
    private static int[] playerPositions = {50, 720, 200, 550, 300};


    public ScorchGame(int numberOfPlayers) {

        //  Create the Frame for the game, with the playfield in the center
        //  and two interface panels on the top and the bottom.

        frame = new JFrame("Scorching Earth");
        frame.getContentPane().setLayout(new BorderLayout());

        status = new GameStatus();
        frame.getContentPane().add(status, BorderLayout.NORTH);

        terrain = new Terrain(this, 800, 500);
        frame.getContentPane().add(terrain, BorderLayout.CENTER);

        settings = new TankStatus(this);
        frame.getContentPane().add(settings, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int highestTerrainPoint = 0;
        //  Create each player
        for (int i = 0; i < numberOfPlayers; i++) {
            Tank t = new Tank(i+1, playerPositions[i], 90, playerColors[i]);
            t.addPropertyChangeListener(this);
            // sets variable to ground height at left edge of tank
            highestTerrainPoint = terrain.getGroundLevelAtColumn(playerPositions[i]+t.WIDTH);
            // if the height at right edge is greater than left edge update the highest point
            if (highestTerrainPoint > terrain.getGroundLevelAtColumn(playerPositions[i])) {
                highestTerrainPoint = terrain.getGroundLevelAtColumn(playerPositions[i]);
                System.out.println(highestTerrainPoint+"\n");
            }
            t.setY(highestTerrainPoint-t.HEIGHT);
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
