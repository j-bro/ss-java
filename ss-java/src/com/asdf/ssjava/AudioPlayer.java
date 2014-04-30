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

	public static Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music/frenetic-tranceformation.mp3"));
	public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music/ambience.mp3"));
	public static Music creatorMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music/sims_build_mode.mp3"));
	
	public static Sound levelStartSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/level_start.mp3"));
	public static Sound levelCompleteSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/level_complete.mp3"));
	
	public static Sound shotSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/pewpew.mp3"));
	public static Sound bulletImpactSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.mp3"));
	public static Sound shipImpactSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/bump.mp3"));
	public static Sound healthUpSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/health_up.mp3"));
	public static Sound speedOfLightOnSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/speed_on.mp3"));
	public static Sound speedOfLightOffSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/speed_off.mp3"));
	public static Sound shipDeathSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/ship_death.mp3"));
	public static Sound enemyDeathSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/ratdeath.mp3"));
	
	/**
	 * 
	 * @param looping whether or not the game music is to loop
	 */
	public static void playGameMusic(boolean looping) {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play game music");
		gameMusic.setVolume(SSJava.prefs.getInteger("musicVolume", 100) / 100f);
		gameMusic.setLooping(looping);
		gameMusic.play();
	}
	
	/**
	 * Pauses the game music
	 */
	public static void pauseGameMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Pause game music");
		gameMusic.pause();
	}
	
	/**
	 * Stops the game music
	 */
	public static void stopGameMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Stop game music");
		gameMusic.stop();
	}
	
	/**
	 * 
	 * @param looping whether or not the menu music is to loop
	 */
	public static void playMenuMusic(boolean looping) {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play menu music");
		menuMusic.setVolume(SSJava.prefs.getInteger("musicVolume", 100) / 100f);
		menuMusic.setLooping(looping);
		menuMusic.play();
	}
	/**
	 * Pauses the menu music
	 */
	public static void pauseMenuMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Pause menu music");
		menuMusic.pause();
	}
	/**
	 * Stops the menu music
	 */
	public static void stopMenuMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Stop menu music");
		menuMusic.stop();
	}
	
	/**
	 * 
	 * @param looping whether or not the creator music is to loop
	 */
	public static void playCreatorMusic(boolean looping) {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play game music");
		creatorMusic.setVolume(SSJava.prefs.getInteger("musicVolume", 100) / 100f);
		creatorMusic.setLooping(looping);
		creatorMusic.play();
	}
	/**
	 * Pauses the creator music
	 */
	public static void pauseCreatorMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Pause game music");
		creatorMusic.pause();
	}
	/**
	 * Stops the creator music
	 */
	public static void stopCreatorMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Stop game music");
		creatorMusic.stop();
	}
	
	/**
	 * Plays the level start sound
	 */
	public static void levelStart() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play level start sound");
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				levelStartSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the level start sound
	 */
	public static void levelComplete() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play level complete sound");
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				levelCompleteSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the shot sound for a fired bullet
	 */
	public static void shoot() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play shot sound");
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
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play bullet impact sound");
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
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play ship impact (bump) sound");
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				shipImpactSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the health up activated sound
	 */
	public static void healthUp() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play health up sound");
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				healthUpSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the speed of light activated
	 */
	public static void speedOfLightOn() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play speed of light activated sound");
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
	 * Plays the speed of light deactivated sound
	 */
	public static void speedOfLightOff() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play speed of light deactivated sound");
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
	
	/**
	 * Plays the ship death sound
	 */
	public static void shipDeath() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play ship death sound");
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				shipDeathSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the enemy death sound
	 */
	public static void enemyDeath() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play enemy death sound");
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				enemyDeathSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	public static void dispose() {
		gameMusic.dispose();
		menuMusic.dispose();
		
		levelStartSound.dispose();
		levelCompleteSound.dispose();
		
		shotSound.dispose();
		bulletImpactSound.dispose();
		shipImpactSound.dispose();
		healthUpSound.dispose();
		speedOfLightOnSound.dispose();
		speedOfLightOffSound.dispose();
		shipDeathSound.dispose();
		enemyDeathSound.dispose();
	}
}
