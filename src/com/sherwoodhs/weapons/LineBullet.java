package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class LineBullet extends DefaultBullet {

    public LineBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void moveBullet() {

        super.moveBullet();

    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        this.alive = false;
        ScorchAudioPlayer sap = new ScorchAudioPlayer();
        sap.play("src/com/sherwoodhs/audio/explosion.wav");

        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));
        explosions.add(new DefaultExplosion(terrain, xPosition - 30, yPosition, 1, 20, Color.red));
        explosions.add(new DefaultExplosion(terrain, xPosition + 30, yPosition, 1, 20, Color.red));
    }
    //um

}