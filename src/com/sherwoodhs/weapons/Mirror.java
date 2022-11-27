package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Mirror extends DefaultBullet {
    protected int limit;
    public Mirror(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        limit = 0;
    }

    @Override
    public void moveBullet() {

        super.moveBullet();

    }

    @Override
    public void checkForTriggeringExplosion(ArrayList<DefaultExplosion> explosions, Terrain terrain, ArrayList<Tank> Players) {

            //  If the bullet has hit ground, then it is no longer alive and is replaced
            //  by an explosion. Subclasses can override to generate larger, smaller, or different
            //  colored explosions.

            if ((yPosition > terrain.getGroundLevelAtColumn(xPosition)) && limit == 0) {
                this.explode(explosions, terrain);
            }else if ((yPosition < terrain.getGroundLevelAtColumn(xPosition)) && limit == 1) {
                this.explode(explosions, terrain);
            }
            for (Tank i: Players) {
                if (!(i.isDestroyed())) {
                    if (((xPosition + xWidth / 2 >= i.getX()) && (xPosition - xWidth / 2 <= i.getX() + 30)) && ((yPosition + yHight / 2 >= i.getY()) && (yPosition - yHight / 2 <= i.getY() + 15))) {
                        this.explode(explosions, terrain);
                    }
                }
            }
        }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        if (limit == 0) {
            limit ++;
            gravity *= -1;
            ScorchAudioPlayer.play("src/com/sherwoodhs/audio/explosion.wav");
            explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 17, Color.red));
        } else {
            alive = false;
            ScorchAudioPlayer.play("src/com/sherwoodhs/audio/explosion.wav");
            explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 30, Color.red));
        }
    }
    //um

}