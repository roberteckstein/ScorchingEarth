package com.sherwoodhs.bullet;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosion.Explosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class LineBullet extends BulletTemplate{

    public LineBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet() {

        super.moveBullet();

    }
    public void checkForTriggeringExplosion(ArrayList<Explosion> explosions, Terrain terrain, ArrayList<Tank> players) {

        if (yPosition > terrain.getGroundLevelAtColumn(xPosition)) {
            alive = false;
            explosions.add(new Explosion(terrain, xPosition, yPosition, 1, 20, Color.red));
            explosions.add(new Explosion(terrain, xPosition - 30, yPosition, 1, 20, Color.red));
            explosions.add(new Explosion(terrain, xPosition + 30, yPosition, 1, 20, Color.red));
        }
    }

}