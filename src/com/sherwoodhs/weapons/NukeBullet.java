package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class NukeBullet extends DefaultBullet {

    public static final int WIDTH = 8;
    public static final int HEIGHT = 16;

    public NukeBullet(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void moveBullet() {

        super.moveBullet();

    }
    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        alive = false;
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 50, Color.orange));
    }

    @Override
    public void erase(Graphics2D g) {
        g.setPaint(Color.black);
        g.fillOval(xPosition, yPosition, WIDTH, HEIGHT);
        int [] x = {xPosition-(WIDTH/8), xPosition+(WIDTH/2), xPosition+WIDTH+(WIDTH/8)};
        int [] y = {yPosition-(HEIGHT/2), yPosition+2, yPosition-(HEIGHT/2)};
        g.fillPolygon(x, y, 3);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.orange);
        g.fillOval(xPosition, yPosition, WIDTH, HEIGHT);
        int [] x = {xPosition-(WIDTH/8), xPosition+(WIDTH/2), xPosition+WIDTH+(WIDTH/8)};
        int [] y = {yPosition-(HEIGHT/2), yPosition+2, yPosition-(HEIGHT/2)};
        g.fillPolygon(x, y, 3);
    }
//u
}