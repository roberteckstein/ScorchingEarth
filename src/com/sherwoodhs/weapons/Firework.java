package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Firework extends DefaultBullet {


    public Firework(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    public void moveBullet(Terrain terrain) {



        super.moveBullet();

        if (yPosition > terrain.getGroundLevelAtColumn(xPosition)) {
            alive = false;
        }

    }
    @Override
    public void draw(Graphics2D g) {

        g.setPaint(Color.yellow);
        g.fill3DRect(xPosition, yPosition, 3, 3, true);

    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        alive = false;
        ScorchAudioPlayer.play("src/com/sherwoodhs/audio/fireworks.wav");
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));
            for (int i = 0; i < 3; i++)
                game.bullets.add(new Shadowbullet(game,xPosition, yPosition, 2.0 - (Math.random() * 5.5), -3, .5));
        }
    }
