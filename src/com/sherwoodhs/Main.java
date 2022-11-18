package com.sherwoodhs;


import com.sherwoodhs.tank.Tank;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static final int numberOfPlayers = 5;
    public static int alivePlayers;

    private static int[] wins = {0,0,0,0,0};

    public static void main(String[] args) {

        boolean gameOver = false;
        alivePlayers = numberOfPlayers;
        //  Randomly generate the current wind from -50 to 50
        int currentWind = (int)(40 - (Math.random() * 80));

        //  Create a game object and set the current wind
        ScorchGame game = new ScorchGame(numberOfPlayers);
        game.setCurrentWind(currentWind);

        //  Start with player 1, which is 0 since this is zero-based
        int currentPlayer = 0;

        while (!gameOver) {

            game.waitForPlayerFire = true;

            //  If we've gone through all the players, go back to the first player. Uses modulus
            currentPlayer %= numberOfPlayers;

            //  Get the tank representing the current player, and set the
            //  status and settings panels above and below the playfield
            //  accordingly.

            Tank currentTank = game.players.get(currentPlayer);
            if (!currentTank.isDestroyed()) { // skips dead tanks
                if (currentTank.getWeaponsCount().size() > 1) {
                    game.startGame();
                }

                game.status.setStatus(currentTank, currentWind);
                game.settings.setStatus(currentTank);
                game.classer.setStatus(currentTank);

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

                // Tanks fall down if no terrain is immediately under
                for (int i = 0; i < Main.numberOfPlayers; i++) {
                    Tank updatingTank = game.players.get(i);
                    updatingTank.setY(game.terrain.getGroundLevelAtColumn(game.getTankPosition(i) + updatingTank.WIDTH / 2) - updatingTank.HEIGHT);
                }

                //change wind
                currentWind = (int) (40 - (Math.random() * 80));
                game.setCurrentWind(currentWind);
            }
            //  Move on to the next player.
            currentPlayer++;
            // Win conditions
            if (alivePlayers <= 1){ // 1 or 0 players remaining
                gameOver = true;
                // Check for the alive tank
                ArrayList<Integer> a = new ArrayList();
                for (int i = 0; i < Main.numberOfPlayers; i++) {
                    Tank updatingTank = game.players.get(i);
                    if (!updatingTank.isDestroyed()){
                        a.add(i+1);
                    }
                }
                if (a.size() == 0) {
                    game.showDialog("😢 Everyone loses.", "Game Over");
                } else {
                    wins[a.get(0)]++; // Add 1 to win counter of winner
                    game.showDialog("🏆 Player " + a.get(0) + " wins!\n Wins:" + getWins(), "Game Over");
                }

                // Closes current game panel
                game.closeFrame();
                // Creates another game
                Main.main(args);
            }
        }

    }

    private static String getWins (){
        String str = "";
        for (int i = 0; i < wins.length; i++) {
            str += "\n  Player " + i + ": " + wins[i];
        }
        return(str);
    }

}
