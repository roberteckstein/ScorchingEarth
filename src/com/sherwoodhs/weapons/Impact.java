package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Impact extends DefaultBullet {
    protected double momentia;
    public Impact(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        this.momentia = 0.0;
    }

    @Override
    public void moveBullet() {

        super.moveBullet();

    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        this.alive = false;
        this.momentia = Math.sqrt((deltaX * deltaX) + (deltaY * deltaX));
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));

        explosions.add(new DefaultExplosion(terrain, (xPosition + (int) (deltaY / momentia * 15)), (yPosition - (int) (deltaX / momentia * 15)), 1, 15, Color.red));
        explosions.add(new DefaultExplosion(terrain, (xPosition - (int) (deltaY / momentia * 15)), (yPosition + (int) (deltaX / momentia * 15)), 1, 15, Color.red));

        explosions.add(new DefaultExplosion(terrain, (xPosition + (int) (deltaY / momentia * 25)), (yPosition - (int) (deltaX / momentia * 25)), 1, 8, Color.red));
        explosions.add(new DefaultExplosion(terrain, (xPosition - (int) (deltaY / momentia * 25)), (yPosition + (int) (deltaX / momentia * 25)), 1, 8, Color.red));
    }
    //um

}