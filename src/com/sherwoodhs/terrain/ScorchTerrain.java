package com.sherwoodhs.terrain;

import com.sherwoodhs.ScorchGame;
import com.sherwoodhs.tank.ScorchTank;
import com.sherwoodhs.bullet.ScorchBullet;
import com.sherwoodhs.explosion.ScorchExplosion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScorchTerrain extends JPanel {

    public ScorchGame game;

    public int height;
    public int width;

    private boolean animating;

    private Color[][] terrain;

    public ScorchTerrain(ScorchGame game, int width, int height) {

        super();

        this.game = game;

        this.height = height;
        this.width = width;
        this.terrain = new Color[width][height];

        initTerrain();

        setSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));

        animating = false;

    }

    public boolean isAnimating() {
        return animating;
    }

    public void setAnimating(boolean animating) {
        this.animating = animating;
    }

    public void initTerrain() {

        MidPointGenerator mpg = new MidPointGenerator(width, (int)(height * .60), 1.05);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                //  If it's too close to the bottom, just flatten it out
                if (mpg.map[i] > height-20)
                    mpg.map[i] = height-20;

                if (j > mpg.map[i]) {
                    //  Apply a gradient to the terrain color, making it darker as it goes down.
                    terrain[i][j] = new Color(
                            255-(int)(j/(double)height*128),
                            255-(int)(j/(double)height*128),
                            30);
                } else {
                    terrain[i][j] = Color.black;
                }
            }
        }

    }

    public void drawTerrain(Graphics g) {

        Graphics2D graphics = (Graphics2D)g;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                //  Set the color and draw a single pixel.
                graphics.setPaint(terrain[i][j]);
                graphics.drawLine(i,j,i,j);

            }
        }


    }

    public void drawTanks(ArrayList<ScorchTank> tanks, Graphics g) {

        Graphics2D graphics = (Graphics2D)g;

        for (ScorchTank t : tanks) {
            if (!t.isDestroyed())
                t.draw(graphics);
        }

    }

    public void drawBullets(ArrayList<ScorchBullet> bullets, Graphics g) {

        Graphics2D graphics = (Graphics2D) g;

        for (ScorchBullet b : (ArrayList<ScorchBullet>)bullets.clone()) {
            if (b.isAlive()) {
                b.erase(graphics);
                b.update(game, this);
                b.draw(graphics);
            }
        }
    }

    public void drawExplosions(ArrayList<ScorchExplosion> explosions, Graphics g) {

        Graphics2D graphics = (Graphics2D) g;

        for (ScorchExplosion e : explosions) {
            if (e.isAlive()) {
                e.draw(graphics);
            }
        }

    }

    public int getGroundLevelAtColumn(int i) {

        //  Start at the top and move down the column until we
        //  encounter a non-black pixel.

        for (int j = 0; j < height; j++) {
            if (!terrain[i][j].equals(Color.black))
                return j;
        }

        return height;

    }

    public boolean collapseTerrain() {

        boolean shiftedAnyPixels = false;

        for (int i = 0; i < width; i++) {
            for (int j = height-1; j >= 0; j--) {

                //  Start at the bottom of each column, working our way up
                //  and checking if there are any gaps in the terrain.

                if (terrain[i][j].equals(Color.black)) {

                    //  If there are, search upwards to see if there is any
                    //  terrain above us.

                    for (int k = j; k >= 1; k--) {

                        //  If terrain is found, then shift it down by one
                        //  and add one to j so that it checks the same pixel
                        //  again.

                        if (!terrain[i][k].equals(Color.black)) {
                            shiftColumnTerrainDown(i,j);
                            j++;
                            shiftedAnyPixels = true;
                            break;
                        }
                    }

                    // The top pixel after everything is shifted by 1 should
                    // now be black.

                    terrain[i][0] = Color.black;

                }
            }
        }

        return shiftedAnyPixels;
    }

    private void shiftColumnTerrainDown(int i, int j) {

        //  Starting at the target pixel specified, copy the color of
        //  the pixel above it to the target pixel. Then move up one
        //  pixel and repeat until we reach the top.

        for (int k = j; k >= 1; k--) {
            terrain[i][k] = terrain[i][k-1];
        }

        terrain[i][0] = Color.black;

    }

    public void paint(Graphics g) {

        drawTerrain(g);
        drawTanks(game.players, g);

        if (animating) {
            animating = updateBallisticItems(g);
        }

    }

    public boolean updateBallisticItems(Graphics g) {

        drawBullets(game.bullets, g);
        drawExplosions(game.explosions, g);

        for (ScorchBullet b : (ArrayList<ScorchBullet>)game.bullets.clone()) {
            if (!b.isAlive())
                game.bullets.remove(b);
        }

        for (ScorchExplosion e : (ArrayList<ScorchExplosion>)game.explosions.clone()) {
            if (!e.isAlive())
                game.explosions.remove(e);
        }

        if (game.bullets.size() > 0)
            return true;

        if (game.explosions.size() > 0)
            return true;

        collapseTerrain();

        return false;

    }

    public void eraseCircleInTerrain(int x, int y, double r) {

            // Consider a rectangle of size N*N
            int N = (int)(2.0*r);

            int xx, yy;  // Coordinates inside the rectangle

            // Draw a square of size N*N.
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    // Start from the left most corner point
                    xx = i-(int)r;
                    yy = j-(int)r;

                    // If this point is inside the circle, paint the terrain black in that color

                    if (xx*xx + yy*yy <= r*r+1 ) {
                        int coorX = x - xx;
                        int coorY = y - yy;
                        if (coorX >= 0 && coorX < width && coorY >= 0 && coorY < height)
                            terrain[coorX][coorY] = Color.black;
                    }
                }

            }
    }

}
