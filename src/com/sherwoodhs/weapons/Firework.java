package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;

public class Firework extends DefaultBullet {


    public Firework(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet(Terrain terrain) {



        super.moveBullet();

        if (yPosition > terrain.getGroundLevelAtColumn(xPosition)) {
            alive = false;
        }

        if (alive = false) {
            for (int i = 0; i < 5; i++)
                game.bullets.add(
                        new DefaultBullet(game, (int)xPosition, yPosition,
                                2.0-(Math.random()*5.0), -3, .5));

        }

    }
    @Override
    public void draw(Graphics2D g) {

        g.setPaint(Color.yellow);
        g.fill3DRect(xPosition, yPosition, 3, 3, true);

    }

}
