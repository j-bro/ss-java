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
	
	public static Sound shot = Gdx.audio.newSound(Gdx.files.internal("data/sounds/pewpew.mp3"));
	public static Sound bulletImpact = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.mp3"));
	public static Sound shipImpact = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.mp3"));
	
	public static void playGameMusic(boolean looping) {
		
		gameMusic.setVolume(SSJava.prefs.getInteger("musicVolume", 100) / 100f);
		gameMusic.setLooping(looping);
		gameMusic.play();
	}
	
	public static void pauseGameMusic() {
		gameMusic.pause();
	}
	
	public static void stopGameMusic() {
		gameMusic.stop();
	}
	/**
	 * Plays the shot sound for a fired bullet
	 */
	public static void shoot() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				shot.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
			
		}).start();
	}
	
	/**
	 * Plays the explosion sound for the bullet impact
	 */
	public static void bulletImpact() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				bulletImpact.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the explosion sound for the ship impact
	 */
	public static void shipImpact() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				shipImpact.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	public static void dispose() {
		gameMusic.dispose();
		shot.dispose();
		bulletImpact.dispose();
		shipImpact.dispose();
	}
}
