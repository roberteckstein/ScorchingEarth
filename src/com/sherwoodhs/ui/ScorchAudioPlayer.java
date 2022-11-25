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
ScorchAudioPlayer.drain();

For shooting:
ScorchAudioPlayer.play("src/com/sherwoodhs/audio/firegun.wav");
ScorchAudioPlayer.drain();
 */
public class ScorchAudioPlayer {
    //PLAY METHOD
    //Used to start specified .wav file
    public static void play(String filename) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    //DRAIN METHOD
    //Used to drain audio clip, improves performance, I think
    //Currently WIP
    public static void drain() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.drain();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
}
