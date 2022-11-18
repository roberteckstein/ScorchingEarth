package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class Impact extends DefaultBullet {

    public Impact(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void moveBullet() {

        super.moveBullet();

    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        this.alive = false;
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));

        explosions.add(new DefaultExplosion(terrain, (xPosition + (int) (deltaY * 5)), (yPosition - (int) (deltaX * 5)), 1, 15, Color.red));
        explosions.add(new DefaultExplosion(terrain, (xPosition - (int) (deltaY * 5)), (yPosition + (int) (deltaX * 5)), 1, 15, Color.red));

        explosions.add(new DefaultExplosion(terrain, (xPosition + (int) (deltaY * 7)), (yPosition - (int) (deltaX * 7)), 1, 8, Color.red));
        explosions.add(new DefaultExplosion(terrain, (xPosition - (int) (deltaY * 7)), (yPosition + (int) (deltaX * 7)), 1, 8, Color.red));
    }
    //um

}