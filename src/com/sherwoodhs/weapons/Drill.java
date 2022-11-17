package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class Drill extends DefaultBullet {
    public Drill(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet() {

        super.moveBullet();

    }
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 12, Color.red));
        if (yPosition - 20 > terrain.getGroundLevelAtColumn(xPosition)) {
            alive = false;
        }
    }
    //um

}