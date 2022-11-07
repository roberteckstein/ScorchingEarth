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

    public ScorchTerrain terrain;
    public ScorchStatus status;
    public ScorchTankSettings settings;
    public boolean waitForPlayerFire;

    private int currentWind;

    public ArrayList<ScorchTank> players = new ArrayList<>();
    public ArrayList<ScorchBullet> bullets = new ArrayList<>();
    public ArrayList<ScorchExplosion> explosions = new ArrayList<>();

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
        
        frame.setResizable(false);

        //  Create each player
        for (int i = 0; i < numberOfPlayers; i++) {
            ScorchTank t = new ScorchTank(i+1, playerPositions[i], 90, playerColors[i]);
            t.addPropertyChangeListener(this);
            t.setY(terrain.getGroundLevelAtColumn(playerPositions[i]));
            players.add(t);
        }

        frame.pack();
        frame.setVisible(true);

    }

    public int getCurrentWind() {
        return currentWind;
    }

    public void setCurrentWind(int currentWind) {
        this.currentWind = currentWind;
    }


    public void performTerrainCollapse() {

          boolean groundStillMoving = true;

          //  Keep collapsing the terrain until no more pixels move
          while (groundStillMoving) {
              groundStillMoving = terrain.collapseTerrain();
          }

          terrain.repaint();
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
