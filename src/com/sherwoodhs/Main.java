package com.sherwoodhs;


import com.sherwoodhs.tank.Tank;

public class Main {

    public static final int numberOfPlayers = 5;

    public static void main(String[] args) {

        boolean gameOver = false;

        //  Randomly generate the current wind from -50 to 50
        int currentWind = (int)(50 - (Math.random() * 100));

        //  Create a game object and set the current wind
        ScorchGame game = new ScorchGame(numberOfPlayers);
        game.setCurrentWind(currentWind);

        //  Start with player 1, which is 0 since this is zero-based
        int currentPlayer = 0;

        while (!gameOver) {

            game.waitForPlayerFire = true;

            //  If we've gone through all the players, go back to the first player
            if (currentPlayer == numberOfPlayers)
                currentPlayer = 0;

            //  Get the tank representing the current player, and set the
            //  status and settings panels above and below the playfield
            //  accordingly.

            Tank currentTank = game.players.get(currentPlayer);
            game.status.setStatus(currentTank, currentWind);
            game.settings.setStatus(currentTank);

            //  Wait for a signal that the player has pressed the fire button.
            //  Do this by yielding to other threads that are running, such
            //  as the event dispatch thread.

            while (game.waitForPlayerFire) {
                Thread.yield();
            }

            //  Once the user has pressed the fire button, there should now be
            //  objects in the playfield that are animating. Set the appropriate
            //  flag in the game object and perform animations until the flag
            //  is set the false (when all animating objects are no longer alive.)

            game.terrain.setAnimating(true);
            while (game.terrain.isAnimating()) {
                game.performAnimation();
            }

            //  Move on to the next player.

            currentPlayer++;

        }

    }

}
