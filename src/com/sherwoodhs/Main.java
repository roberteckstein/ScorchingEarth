package com.sherwoodhs;


import com.sherwoodhs.tank.ScorchTank;

public class Main {

    public static final int numberOfPlayers = 5;

    public static void main(String[] args) {

        boolean gameOver = false;
        int currentWind = (int)(50 - (Math.random() * 100));

        ScorchGame game = new ScorchGame(numberOfPlayers);
        game.setCurrentWind(currentWind);

        int currentPlayer = 0;

        while (!gameOver) {

            game.waitForPlayerFire = true;

            if (currentPlayer == numberOfPlayers)
                currentPlayer = 0;

            ScorchTank currentTank = game.players.get(currentPlayer);
            game.status.setStatus(currentTank, currentWind);
            game.settings.setStatus(currentTank);

            while (game.waitForPlayerFire) {
                Thread.yield();
            }

            game.terrain.setAnimating(true);
            while (game.terrain.isAnimating()) {
                game.performAnimation();
            }

            currentPlayer++;

        }

    }

}
