package com.sherwoodhs.weapons;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.explosions.DefaultExplosion;
import com.sherwoodhs.terrain.Terrain;
import com.sherwoodhs.ui.ScorchAudioPlayer;

import java.awt.*;
import java.util.ArrayList;

public class Skipper extends DefaultBullet {
    public Skipper(ScorchGame game, int x, int y, double deltaX, double deltaY, double gravity) {
        super(game, x, y, deltaX, deltaY, gravity);
    }

    @Override
    public void explode(ArrayList<DefaultExplosion> explosions, Terrain terrain) {
        alive = false;
        ScorchAudioPlayer.play("src/com/sherwoodhs/audio/explosion.wav");
        game.bullets.add(new Shadowbullet(game, xPosition, yPosition - 100, deltaX, 1, 0.5));
        explosions.add(new DefaultExplosion(terrain, xPosition, yPosition, 1, 20, Color.red));
    }
}

