package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;

import static java.lang.Math.floor;

public class Sniper extends DefaultBullet{
    public Sniper (ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void moveBullet() {

        //  Standard move formula that accounts for delta X and delta Y.
        //  Subclasses can call this method as
        //  needed.

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

        if (xPosition <= 0 || xPosition >= game.terrain.width || yPosition <= 0 || (deltaX+deltaY) == 0)
            alive = false;
    }
}
