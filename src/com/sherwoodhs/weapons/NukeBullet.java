package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class NukeBullet extends DefaultBullet {

    public NukeBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet() {

        super.moveBullet();

    }
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        alive = false;
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 50, Color.orange));

    }
//u
}