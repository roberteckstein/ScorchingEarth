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
    //PLAY METHOD
    //Used to start specified .wav file
    public static void play(String filename) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            if (filename.equals("src/com/sherwoodhs/audio/BGM.wav")){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    public static void stop(String filename){
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.stop();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }

    }
}
