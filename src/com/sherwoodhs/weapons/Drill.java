package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Drill extends DefaultBullet {
    protected int limit;
    public Drill(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        limit = 12;
    }

    @Override
    public void moveBullet() {

        super.moveBullet();

    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        ScorchAudioPlayer.play("src/com/sherwoodhs/audio/drillsound.wav");
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 12, Color.red));
        limit --;
        if (limit == 0) {
            alive = false;
        }
    }
    //um

}