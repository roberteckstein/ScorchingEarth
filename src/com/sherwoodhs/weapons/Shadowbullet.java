package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Shadowbullet extends DefaultBullet{
    //used for firework, not ment to be it's own weapon
    protected boolean shadow;
    public Shadowbullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        this.shadow = false;
    }
    @Override
    public void moveBullet() {
        double lastYPosition = yPosition;

        super.moveBullet();
        //deshades if moving down
        if (yPosition > lastYPosition) {
            shadow = true;
        }
    }
    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain){
        if (shadow) {
            alive = false;
            ScorchAudioPlayer.play("src/com/sherwoodhs/audio/explosion.wav");
            explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));
        }
    }
}
