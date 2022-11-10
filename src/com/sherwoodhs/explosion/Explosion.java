package com.sherwoodhs.explosion;

import com.sherwoodhs.terrain.Terrain;

import java.awt.*;

public class Explosion {

    //  A simple class to represent an explosion. Extend and override methods if you want to
    //  make fancier explosions.

    private Terrain terrain;

    private boolean alive;

    private int xPosition;
    private int yPosition;

    private double radius;
    private double maxRadius;
    private Color color;
    private boolean expanding;

    public Explosion(Terrain terrain, int x, int y, double radius, double maxSize, Color color) {

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

    public void draw(Graphics2D g) {

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
            g.setPaint(Color.black);
            g.fillOval((int)(xPosition- maxRadius), (int)(yPosition- maxRadius),
                    (int) maxRadius *2, (int) maxRadius *2);
            radius--;

        }

        //  If the explosion has reached its maximum radius, then erase the terrain
        //  data from the explosion and set expanding to false. The explosion will
        //  now shrink.

        if (radius > maxRadius) {
            terrain.eraseCircleInTerrain(xPosition, yPosition, maxRadius);
            expanding = false;
        }

        //  Finally, draw the explosion

        g.setPaint(color);
        g.fillOval((int)(xPosition- radius), (int)(yPosition- radius),
                (int) radius *2, (int) radius *2);

    }

}
