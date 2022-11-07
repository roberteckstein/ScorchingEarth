package com.sherwoodhs.bullet;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosion.ScorchExplosion;
import com.sherwoodhs.terrain.ScorchTerrain;

import java.awt.*;
import java.util.ArrayList;

public class ScorchMIRVBullet extends ScorchBullet {

    public ScorchMIRVBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet() {

        double lastYPosition = yPosition;

        super.moveBullet();

        //  If the MIRV has reached its apex, break it apart into five bullets
        //  with slightly random trajectories.

        if (alive && yPosition > lastYPosition) {
            alive = false;
            for (int i = 0; i < 5; i++)
                game.bullets.add(
                        new ScorchBullet(game, (int)xPosition, yPosition,
                            deltaX+(2.0-(Math.random()*4.0)), deltaY, .5));

        }

    }

}
