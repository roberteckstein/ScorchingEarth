package com.sherwoodhs.bullet;

import com.sherwoodhs.explosion.ScorchExplosion;
import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.terrain.ScorchTerrain;

import java.awt.*;
import java.util.ArrayList;

public class ScorchBullet {

    protected boolean alive;

    protected int xPosition;
    protected int yPosition;

    protected double deltaX;
    protected double deltaY;
    protected double wind;
    protected double gravity;

    protected ScorchGame game;

    public ScorchBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {

        this.xPosition = x;
        this.yPosition = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.gravity = gravity;
        this.game = game;

        alive = true;

    }

    public boolean isAlive() {
        return alive;
    }


    //  Override these as needed in subclasses to make more sophisticated
    //  weapons. See ScorchMIRVBullet for an example.

    public void erase(Graphics2D g) {

        g.setPaint(Color.black);
        g.fill3DRect(xPosition, yPosition, 3, 3, true);
    }

    public void update(ScorchGame game, ScorchTerrain terrain) {

        moveBullet();

        //  If the bullet is still alive (hasn't gone off the edge of the screen),
        //  check if it has triggered an explosion

        if (alive)
            checkForTriggeringExplosion(game.explosions, terrain);
    }

    public void draw(Graphics2D g) {

        g.setPaint(Color.white);
        g.fill3DRect(xPosition, yPosition, 3, 3, true);

    }

    public void moveBullet() {

        //  Standard move formula that accounts for delta X and delta Y
        //  as well as gravity and wind. Subclasses can call this method as
        //  needed.

        deltaY += (gravity/10);

        xPosition += deltaX;
        yPosition += deltaY;

        xPosition += (wind / 5);

        if (xPosition <= 0 || xPosition >= game.terrain.width)
            alive = false;

    }

    public void checkForTriggeringExplosion(ArrayList<ScorchExplosion> explosions, ScorchTerrain terrain) {

        //  If the bullet has hit ground, then it is no longer alive and is replaced
        //  by an explosion. Subclasses can override to generate larger, smaller, or different
        //  colored explosions.

        if (yPosition > terrain.getGroundLevelAtColumn(xPosition)) {
            alive = false;
            explosions.add(new ScorchExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));
        }
    }
}
