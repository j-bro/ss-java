/**
 * @author Jeremy Brown
 * 
 */
package com.asdf.ssjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Jeremy Brown
 * 
 */
public class AudioPlayer {

	private AudioPlayer() {	}

	public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music/ambience.mp3"));
	
	public static Sound shotSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/pewpew.mp3"));
	public static Sound bulletImpactSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.mp3"));
	public static Sound shipImpactSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.mp3"));
//	public static Sound healthUpSound = Gdx.audio.newSound(Gdx.files.internal(""));
	public static Sound speedOfLightOnSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/speed_on.mp3"));
	public static Sound speedOfLightOffSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/speed_off.mp3"));
	
	/**
	 * 
	 * @param looping whether or not the game is to loop
	 */
	public static void playGameMusic(boolean looping) {
		gameMusic.setVolume(SSJava.prefs.getInteger("musicVolume", 100) / 100f);
		gameMusic.setLooping(looping);
		gameMusic.play();
	}
	
	/**
	 * Pauses the game music
	 */
	public static void pauseGameMusic() {
		gameMusic.pause();
	}
	
	/**
	 * Stops the game music
	 */
	public static void stopGameMusic() {
		gameMusic.stop();
	}
	
	/**
	 * Plays the shot sound for a fired bullet
	 */
	public static void shoot() {
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				shotSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the explosion sound for the bullet impact
	 */
	public static void bulletImpact() {
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				bulletImpactSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the explosion sound for the ship impact
	 */
	public static void shipImpact() {
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
//				shipImpactSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the explosion sound for the ship impact
	 */
	public static void healthUp() {
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
//				healthUpSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the explosion sound for the ship impact
	 */
	public static void speedOfLightOn() {
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				speedOfLightOnSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the explosion sound for the ship impact
	 */
	public static void speedOfLightOff() {
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				speedOfLightOffSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	public static void dispose() {
		gameMusic.dispose();
		shotSound.dispose();
		bulletImpactSound.dispose();
		shipImpactSound.dispose();
//		healthUpSound.dispose();
		speedOfLightOnSound.dispose();
		speedOfLightOffSound.dispose();
	}
}
