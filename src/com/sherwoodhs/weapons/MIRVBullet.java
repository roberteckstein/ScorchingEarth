package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;

public class MIRVBullet extends DefaultBullet {

    public MIRVBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void moveBullet() {

        double lastYPosition = yPosition;

        super.moveBullet();

        //  If the MIRV has reached its apex, break it apart into 4.0 (they end up ontop of eachother, deltaX is an int) bullets
        //  with slightly random trajectories.

        if (alive && yPosition > lastYPosition) {
            alive = false;
            for (int i = 0; i < 5; i++)
                game.bullets.add(
                        new DefaultBullet(game, xPosition, yPosition, deltaX+(2.0-(Math.random()*4.0)), deltaY, .5));

        }

    }

}
