package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.tank.Tank;
import com.sherwoodhs.terrain.Terrain;

import java.awt.*;
import java.util.ArrayList;

public class RollerBullet extends DefaultBullet {
    public RollerBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void draw(Graphics2D g) {

        g.setPaint(Color.green);
        g.fill3DRect(xPosition - xWidth / 2, yPosition - yHight / 2, xWidth, yHight, true);

    }

    @Override
    public void checkForTriggeringExplosion(ArrayList<DefaultExplosion> explosions, Terrain terrain, ArrayList<Tank> Players) {

        //  If the bullet has hit ground, then it is no longer alive and is replaced
        //  by an explosion. Subclasses can override to generate larger, smaller, or different
        //  colored explosions.

        if (yPosition > terrain.getGroundLevelAtColumn(xPosition)) {
            this.explode(explosions, terrain);
        }
        for (Tank i: Players) {
            if (!(i.isDestroyed())) {
                if (((xPosition + xWidth / 2 >= i.getX()) && (xPosition - xWidth / 2 <= i.getX() + 30)) && ((yPosition + yHight / 2 >= i.getY()) && (yPosition - yHight / 2 <= i.getY() + 15))) {
                    this.explode(explosions, terrain);
                }
            }
        }
    }
}
