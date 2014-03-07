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

	public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("data/assets/music/")); // loc!
	
	public static Sound shot = Gdx.audio.newSound(Gdx.files.internal("data/assets/sounds/")); // loc!
	public static Sound bulletImpact = Gdx.audio.newSound(Gdx.files.internal("data/assets/sounds/")); // loc!
	public static Sound shipImpact = Gdx.audio.newSound(Gdx.files.internal("data/assets/sounds/")); // loc!
	
	
	public static void playGameMusic() {
		gameMusic.play();
	}
	
	public static void stopGameMusic() {
		gameMusic.stop();
	}
	
	public static void shoot() {
		
	}
	
	public static void dispose() {
		gameMusic.dispose();
		shot.dispose();
		bulletImpact.dispose();
		shipImpact.dispose();
	}
	
}
