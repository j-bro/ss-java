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

	public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music/ambience.mp3")); // loc!
	
	public static Sound shot = Gdx.audio.newSound(Gdx.files.internal("data/sounds/pewpew.mp3")); // loc!
	public static Sound bulletImpact = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.mp3")); // loc!
	public static Sound shipImpact = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.mp3")); // loc!
	
	public static void playGameMusic(boolean looping) {
		gameMusic.setVolume(SSJava.prefs.getInteger("soundVolume", 100));
		gameMusic.setLooping(looping);
		gameMusic.play();
	}
	
	public static void stopGameMusic() {
		gameMusic.stop();
	}
	
	public static void shoot() {
		shot.play(SSJava.prefs.getInteger("soundVolume", 100));
	}
	
	public static void bulletImpact() {
		bulletImpact.play(SSJava.prefs.getInteger("soundVolume", 100));
	}
	
	public static void shipImpact() {
		shipImpact.play(SSJava.prefs.getInteger("soundVolume", 100));
	}
	
	public static void dispose() {
		gameMusic.dispose();
		shot.dispose();
		bulletImpact.dispose();
		shipImpact.dispose();
	}
	
}
