package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class LineBullet extends DefaultBullet {

    public LineBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet() {

        super.moveBullet();

    }
    public void checkForTriggeringExplosion(ArrayList<DefaultExplosion> explosions, Terrain terrain, ArrayList<Tank> players) {

        if (yPosition > terrain.getGroundLevelAtColumn(xPosition)) {
            alive = false;
            explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));
            explosions.add(new DefaultExplosion(terrain, xPosition - 30, yPosition, 1, 20, Color.red));
            explosions.add(new DefaultExplosion(terrain, xPosition + 30, yPosition, 1, 20, Color.red));
        }
    }

}