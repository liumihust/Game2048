package com.mygame;

import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URI;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;



public class CreateSound {
	    static  AudioClip  clip;
       public static void play(File musicFile){
    	   if(true){
    	    try {
				clip=Applet.newAudioClip(musicFile.toURI().toURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    clip.play();
           }
       }
       public static void play(){
   	        clip.loop();
      }

       public static void stop(){
    	   if(clip !=null){
    		   clip.stop();
    	   }
    	   
       }

}


