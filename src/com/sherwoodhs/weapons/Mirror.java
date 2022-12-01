package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.floor;

public class Mirror extends DefaultBullet {
    protected int limit;
    private int moveWind = 0;
    public Mirror(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        limit = 0;
    }

    @Override
    public void moveBullet() {


        deltaY += (gravity/5);
        // gravity

        if (deltaX >= 0.0) {
            xPosition += (int) deltaX;
            decX += deltaX - floor(deltaX);
            if (decX >= 1) {
                decX --;
                xPosition ++;
            }
        }
        if (deltaX < 0.0) {
            xPosition -= Math.abs((int) deltaX);
            decX -= Math.abs(deltaX) - floor(Math.abs(deltaX));
            if (decX <= -1) {
                decX++;
                xPosition--;
            }
        }
        //x motion


        yPosition += deltaY;
        //y motion

        if ((moveWind % 4 == 0) && (limit == 0)) {
            deltaX += (wind / 250);
        }
        moveWind++;
        if (xPosition <= 0 || xPosition >= game.terrain.width)
            alive = false;

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
            ScorchAudioPlayer.play("src/com/sherwoodhs/audio/magic.wav");
        } else {
            alive = false;
            ScorchAudioPlayer.play("src/com/sherwoodhs/audio/explosion.wav");
            explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 30, Color.red));
        }
    }
    //um

}