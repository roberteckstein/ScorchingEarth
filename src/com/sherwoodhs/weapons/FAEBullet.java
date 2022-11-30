package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class FAEBullet extends DefaultBullet {
    protected boolean cansplit;
    public FAEBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        this.cansplit = true;
    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }

    @Override
    public void moveBullet() {

        double lastYPosition = yPosition;

        super.moveBullet();

        //  If the MIRV has reached its apex, break it apart into 4.0 (they end up ontop of eachother, deltaX is an int) bullets
        //  with slightly random trajectories.

        if (cansplit && alive && yPosition > lastYPosition) {
            cansplit = false;
            deltaY += 2;
            ScorchAudioPlayer.play("src/com/sherwoodhs/audio/pop.wav");
            for (int i = 0; i < 3; i++)
                game.bullets.add(
                        new DefaultBullet(game, (int)xPosition, yPosition, 0.5-(Math.random()*1.0), -3, .5));

        }

    }
    @Override
    public void draw(Graphics2D g) {

        g.setPaint(Color.green);
        g.fill3DRect(xPosition, yPosition, 3, 3, true);

    }
}
