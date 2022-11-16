package com.sherwoodhs.weapons;

import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.floor;

public class DefaultBullet {

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

    protected double decX;

    public DefaultBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {

        this.xPosition = x;
        this.yPosition = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.gravity = gravity;
        this.game = game;
        this.wind = game.getCurrentWind();
        this.decX = 0.0;

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
            checkForTriggeringExplosion(game.explosions, terrain, game.players);
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
        // gravity

        if (deltaX >= 0.0) {
            xPosition += (int) deltaX;
            decX += deltaX - floor(deltaX);
            if (decX >= 1) {
                decX --;
                xPosition ++;
            }
        }
        if (deltaX < 0.0) {
            xPosition -= Math.abs((int) deltaX);
            decX -= Math.abs(deltaX) - floor(Math.abs(deltaX));
            if (decX <= -1) {
                decX++;
                xPosition--;
            }
        }
        //x motion


        yPosition += deltaY;
        //y motion

        if (moveWind % 4 == 0) {
            deltaX += (wind / 250);
        }
        moveWind++;
        if (xPosition <= 0 || xPosition >= game.terrain.width)
            alive = false;

    }

    public void checkForTriggeringExplosion(ArrayList<DefaultExplosion> explosions, Terrain terrain, ArrayList<Tank> Players) {

        //  If the bullet has hit ground, then it is no longer alive and is replaced
        //  by an explosion. Subclasses can override to generate larger, smaller, or different
        //  colored explosions.

        if (yPosition > terrain.getGroundLevelAtColumn(xPosition)) {
            this.detonate(explosions, terrain);
        }
        for (Tank i: Players) {
            if (((xPosition >= i.getX()) && (xPosition <= i.getX() + 30)) && ((yPosition >= i.getY()) && (yPosition <= i.getY() + 15))) {
                this.detonate(explosions, terrain);
            }
        }
    }
    public void detonate(ArrayList<DefaultExplosion> explosions, Terrain terrain){
        alive = false;
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));
    }
}
