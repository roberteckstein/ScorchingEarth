package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class Shadowbullet extends DefaultBullet{
    protected boolean shadow;
    public Shadowbullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        this.shadow = false;
    }
    public void moveBullet() {
        double lastYPosition = yPosition;

        super.moveBullet();
        //deshades if moving down
        if (yPosition > lastYPosition) {
            shadow = true;
        }
    }
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain){
        if (shadow) {
            alive = false;
            explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));
        }
    }
}
