package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class Sniper extends DefaultBullet {
    protected int age;

    public Sniper(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
        age = 0;
    }

    public void update(ScorchGame game, Terrain terrain) {

        moveBullet();
        age ++;

        //  If the bullet is still alive (hasn't gone off the edge of the screen),
        //  check if it has triggered an explosion

        if (alive)
            checkForTriggeringExplosion(game.explosions, terrain, game.players);
    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        alive = false;
        if (age > 50) {
            explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, (int) ((age - 30) / 3), Color.red));
        }
    }
}