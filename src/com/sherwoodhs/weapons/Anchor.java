package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Anchor extends DefaultBullet {
    public Anchor(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet() {

        double lastYPosition = yPosition;

        super.moveBullet();

        //  If the MIRV has reached its apex, break it apart into 4.0 (they end up ontop of eachother, deltaX is an int) bullets
        //  with slightly random trajectories.

        if (alive && yPosition > lastYPosition) {
            deltaX = (deltaX * 4) / 5;

        }

    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain){
        alive = false;
        ScorchAudioPlayer.play("src/com/sherwoodhs/audio/explosion.wav");
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 37, Color.red));
    }
}//e
