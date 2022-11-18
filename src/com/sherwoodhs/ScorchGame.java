package com.sherwoodhs;

import com.sherwoodhs.weapons.DefaultBullet;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.GameStatusBar;
import com.sherwoodhs.ui.TankStatusBar;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.lang.Math;

public class ScorchGame implements PropertyChangeListener {

    private JFrame frame;
    private int currentWind;

    //  These are public because I just don't want to deal with accessors.
    //  Getters and setters are proper use of object-oriented code, but
    //  here I just don't feel like passing around singleton references
    //  via constructors to all these objects. I'll just pass the ScorchGame
    //  object and get at the terrain, status, and settings panels from there.

    public Terrain terrain;
    public GameStatusBar status;
    public TankStatusBar settings;
    public boolean waitForPlayerFire;

    public static boolean redrawTank = true;

    //  Same thing here-- I'll be referencing these ArrayList's in a number
    //  of classes, and since I'm not creating APIs that have to do sanity
    //  checks on setters, I'll just make them public... because I'm lazy.

    public ArrayList<Tank> players = new ArrayList<>();
    public ArrayList<DefaultBullet> bullets = new ArrayList<>();
    public ArrayList<DefaultExplosion> explosions = new ArrayList<>();

    //  This is just initialization data for each player. Change as you see fit.
    private static Color[] playerColors = {Color.red, Color.cyan, Color.yellow, Color.green, Color.pink};


    //PLAYER SPAWNING ALGORITHM
    //Has hardcoded ranges and chooses a random int between those ranges to spawn a tank at
    //the difference is small rn so might need some tweaking to make in more intense

    //Define lowest int in range. def = default
    static int min1 = 30; //def 50
    static int min2 = 700; //def 720
    static int min3 = 180; //def 200
    static int min4 = 530; //def 550
    static int min5 = 280; //def 300

    //define highest int in range
    static int max1 = 70;
    static int max2 = 740;
    static int max3 = 220;
    static int max4 = 570;
    static int max5 = 320;

    //create range int
    static int range1 = max1 - min1 + 1;
    static int range2 = max2 - min2 + 1;
    static int range3 = max3 - min3 + 1;
    static int range4 = max4 - min4 + 1;
    static int range5 = max5 - min5 + 1;

    //get random int
    static int rand1 = (int)(Math.random() * range1) + min1;
    static int rand2 = (int)(Math.random() * range2) + min2;
    static int rand3 = (int)(Math.random() * range3) + min3;
    static int rand4 = (int)(Math.random() * range4) + min4;
    static int rand5 = (int)(Math.random() * range5) + min5;

    //add randomised int to the playerPositions array
    private static int[] playerPositions = {rand1, rand2, rand3, rand4, rand5};


    public ScorchGame(int numberOfPlayers) {

        //  Create the Frame for the game, with the playfield in the center
        //  and two interface panels on the top and the bottom.

        frame = new JFrame("Scorching Earth");
        frame.getContentPane().setLayout(new BorderLayout());

        status = new GameStatusBar();
        frame.getContentPane().add(status, BorderLayout.NORTH);

        terrain = new Terrain(this, 1600, 800);
        frame.getContentPane().add(terrain, BorderLayout.CENTER);

        settings = new TankStatusBar(this);
        frame.getContentPane().add(settings, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        //  Create each player
        for (int i = 0; i < numberOfPlayers; i++) {
            Tank t = new Tank(i+1, playerPositions[i], 90, playerColors[i]);
            t.addPropertyChangeListener(this);
            //t.setY(terrain.height-20);
            t.setY(terrain.getGroundLevelAtColumn(playerPositions[i] + t.WIDTH/2)-t.HEIGHT);
            terrain.clearTerrainAroundTanks(t.getX(), t.getY());
            players.add(t);
        }

        // 'fire' button used to draw randomly in the top-left corner
        terrain.repaint();

        //  Pack the components, which has the effect of "squishing" them together
        //  as best as possible, then display the frame.

        frame.pack();
        frame.setVisible(true);

    }

    public int getCurrentWind() {
        return currentWind;
    }

    public void closeFrame() {frame.dispose();} //closes frame

    public void setCurrentWind(int currentWind) {
        this.currentWind = currentWind;
    }

    public void performAnimation() {
        if (terrain.isAnimating()) {
            redrawTank = false;
            terrain.repaint();
        }
    }
    public int getTankPosition(int i){
        return (playerPositions[i]);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        redrawTank = true;
        terrain.repaint();
    }

}
