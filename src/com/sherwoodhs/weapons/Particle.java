package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.floor;

public class Particle extends DefaultBullet{
    public Particle (ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity){
        super(game, x, y, deltaX, deltaY, gravity);
        this.xWidth = 1;
        this.yHight = 1;

    }

    public void draw(Graphics2D g) {

        g.setPaint(Color.yellow);
        g.fill3DRect(xPosition - xWidth / 5, yPosition - yHight / 5, xWidth, yHight, true);

    }
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain){
        alive = false;
    }
    public void moveBullet() {

        //  Standard move formula that accounts for delta X and delta Y
        //  as well as gravity and wind. Subclasses can call this method as
        //  needed.

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

        if (xPosition <= 0 || xPosition >= game.terrain.width)
            alive = false;

    }
}
