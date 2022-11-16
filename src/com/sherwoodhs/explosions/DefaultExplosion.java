package com.sherwoodhs.explosions;

import com.sherwoodhs.Main;
import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;

public class DefaultExplosion {

    //  A simple class to represent an explosion. Extend and override methods if you want to
    //  make fancier explosions.

    public Terrain terrain;
    public boolean alive;

    public int xPosition;
    public int yPosition;

    public double radius;
    public double maxRadius;
    public Color color;
    public boolean expanding;

    public DefaultExplosion(Terrain terrain, int x, int y, double radius, double maxSize, Color color) {

        this.terrain = terrain;

        this.xPosition = x;
        this.yPosition = y;

        this.radius = radius;
        this.maxRadius = maxSize;

        this.color = color;

        alive = true;
        expanding = true;

    }

    public boolean isAlive() {
        return alive;
    }

    public void draw(Graphics2D g, ScorchGame game) {

        //  Check if the radius of the explosion is now 0. If it is, the explosion is finished,
        //  and we can set alive to false and return.

        if (radius == 0) {
            alive = false;
            return;
        }

        if (expanding) {
            //  If the explosion is expanding, then increase its radius by 1
            radius++;
        } else {
            //  Otherwise, draw a black oval first, then decrease the radius by 1
            radius--;

        }

        //  If the explosion has reached its maximum radius, then erase the terrain
        //  data from the explosion and set expanding to false. The explosion will
        //  now shrink.

        if (radius > maxRadius) {
            terrain.eraseCircleInTerrain(xPosition, yPosition, maxRadius);
            expanding = false;
            // Put in the tank death check at this point
            for (int i = 0; i < Main.numberOfPlayers; i++) {
                Tank checked = game.players.get(i);
                if (!checked.isDestroyed()) {
                    // Pythagorean theorem
                    double distance = Math.sqrt(Math.pow(checked.getX() + 15 - xPosition, 2) + Math.pow(checked.getY() - yPosition, 2));
                    if (distance < maxRadius) { // if distance is pushed
                        checked.setDestroyed(true); // set tank as destroyed
                        Main.alivePlayers--;
                    }
                }
            }
            System.out.println();
        }

        //  Finally, draw the explosion

        g.setPaint(color);
        g.fillOval((int)(xPosition- radius), (int)(yPosition- radius),
                (int) radius *2, (int) radius *2);

    }

}
