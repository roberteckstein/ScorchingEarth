package com.sherwoodhs.bullet;

import com.sherwoodhs.explosion.Explosion;
import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class BulletTemplate {

    //  This is a simple bullet. It's only purpose in life is to fly through the
    //  playfield until it hits the terrain, at which point it changes alive to
    //  false and replaces itself with a new ScorchExplosion object.

    protected boolean alive;

    protected int xPosition;
    protected int yPosition;

    protected double deltaX;
    protected double deltaY;
    protected double wind;
    protected double gravity;

    protected ScorchGame game;

    private int moveWind = 0;

    public BulletTemplate(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {

        this.xPosition = x;
        this.yPosition = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.gravity = gravity;
        this.game = game;
        this.wind = game.getCurrentWind();

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

    public void update(ScorchGame game, Terrain terrain) {

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

        deltaY += (gravity/5);

        xPosition += (int)deltaX;
        if (Math.random() < deltaX % 1){
            xPosition++;
        }
        yPosition += deltaY;


        if (moveWind % 4 == 0) {
            xPosition += (wind / 10);
        }
        moveWind++;
        if (xPosition <= 0 || xPosition >= game.terrain.width)
            alive = false;

    }

    public void checkForTriggeringExplosion(ArrayList<Explosion> explosions, Terrain terrain) {

        //  If the bullet has hit ground, then it is no longer alive and is replaced
        //  by an explosion. Subclasses can override to generate larger, smaller, or different
        //  colored explosions.

        if (yPosition > terrain.getGroundLevelAtColumn(xPosition)) {
            alive = false;
            explosions.add(new Explosion(terrain, xPosition, yPosition, 1, 20, Color.red));
        }
    }
}
