package com.skyblue.bing;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    static Clip bgmusic, sfx;
    static AudioInputStream bginput, sfxinput;
    static final String BG_MUSIC="musicgame.wav";
    static final String DUM="dum.wav";
    
    static{
        try{
        bgmusic= AudioSystem.getClip();
        sfx=AudioSystem.getClip();
        sfxinput=AudioSystem.getAudioInputStream(ClassLoader.getSystemResource(DUM));
        sfx.open(sfxinput);
        }
        catch(Exception ex){
            
        }
    }
    static void playBGMusic(){
        try {
            bginput= AudioSystem.getAudioInputStream(ClassLoader.getSystemResource(BG_MUSIC));
            bgmusic.open(bginput);
            bgmusic.loop(Clip.LOOP_CONTINUOUSLY);
            bgmusic.start();
        } catch (UnsupportedAudioFileException ex) {
            
        } catch (IOException ex) {
            
        } catch (LineUnavailableException ex) {
            
        }
    }
    
    static void stop_and_flushall(){
        try{
            if(bgmusic.isOpen()){
                bgmusic.close();
            }
            if(sfx.isOpen()){
                sfx.close();
            }
            sfxinput.close();
            bginput.close();
        }
        catch(Exception ex){
            
        }
    }
    
    static void playSFX(){
        sfx.setFramePosition(0);
        sfx.start();
    }
}
