package com.sherwoodhs.bullet;

import com.sherwoodhs.ScorchGame;

public class FAEBullet extends BulletTemplate {

    public FAEBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet() {

        double lastYPosition = yPosition;

        super.moveBullet();

        //  If the MIRV has reached its apex, break it apart into 4.0 (they end up ontop of eachother, deltaX is an int) bullets
        //  with slightly random trajectories.

        if (alive && yPosition > lastYPosition) {
            alive = false;
            for (int i = 0; i < 5; i++)
                game.bullets.add(
                        new BulletTemplate(game, (int)xPosition, yPosition,
                            2.0-(Math.random()*4.0), -3, .5));

        }

    }

}
