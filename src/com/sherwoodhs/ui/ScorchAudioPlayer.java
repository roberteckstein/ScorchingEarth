package com.sherwoodhs.ui;

import java.io.*;
import javax.sound.sampled.*;


//Audioplayer class
/* To use:

ScorchAudioPlayer.play("src/com/sherwoodhs/audio/file.wav");

file.wav should be your audio file
all audio NEEDS to be in .wav format

 */

/*
For explosions:
ScorchAudioPlayer.play("src/com/sherwoodhs/audio/explosion.wav");

For shooting:
ScorchAudioPlayer.play("src/com/sherwoodhs/audio/firegun.wav");
 */
public class ScorchAudioPlayer {
    public static void play(String filename) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    public static void dispose() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.drain();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
}
