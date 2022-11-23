package com.sherwoodhs.ui;

import java.io.*;
import javax.sound.sampled.*;


//Audioplayer class
/* To use create ScorchAudioPlayer object with any reference name:
ScorchAudioPlayer referencename = new ScorchAudioPlayer();

Then call the play method by doing:
referencename.play("src/com/sherwoodhs/audio/file.wav");

file.wav should be your audio file
all audio NEEDS to be in .wav format

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
}
