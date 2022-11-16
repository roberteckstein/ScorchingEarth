package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;

import java.awt.*;

public class FAEBullet extends DefaultBullet {
    protected boolean cansplit;
    public FAEBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        this.cansplit = true;
    }

    public void moveBullet() {

        double lastYPosition = yPosition;

        super.moveBullet();

        //  If the MIRV has reached its apex, break it apart into 4.0 (they end up ontop of eachother, deltaX is an int) bullets
        //  with slightly random trajectories.

        if (cansplit && alive && yPosition > lastYPosition) {
            cansplit = false;
            deltaY += 2;
            for (int i = 0; i < 3; i++)
                game.bullets.add(
                        new DefaultBullet(game, (int)xPosition, yPosition,
                            0.5-(Math.random()*1.0), -3, .5));

        }

    }
    public void draw(Graphics2D g) {

        g.setPaint(Color.green);
        g.fill3DRect(xPosition, yPosition, 3, 3, true);

    }

}
