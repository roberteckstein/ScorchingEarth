package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class AntimatterBomb extends DefaultBullet {
    public AntimatterBomb(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.lightGray);
        g.fill3DRect(xPosition - xWidth / 2, yPosition - yHight / 2, xWidth, yHight, true);
    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        game.bullets.add(new Shadowbullet(game, (int) xPosition, 200, 5, 1, 0.5));
    }
}

