package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class CannonBullet extends DefaultBullet {
    public CannonBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.gray);
        g.fillOval(xPosition, yPosition, 10, 10);
    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain){
        alive = false;
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 0, Color.darkGray));
    }

}
