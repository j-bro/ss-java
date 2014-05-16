package com.asdf.ssjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Audio player class for the application. 
 * Manages all application audio.  
 * Contains instances and controls. 
 * @author Jeremy Brown
 */
public class AudioPlayer {

	private AudioPlayer() {	}

	/**
	 * The music to be played in the game menus
	 */
	public static Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music/frenetic-tranceformation.mp3"));
	
	/**
	 * The music to be played during gameplay
	 */
	public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music/ambience.mp3"));
	
	/**
	 * The music to be played in the level creator
	 */
	public static Music creatorMusic = Gdx.audio.newMusic(Gdx.files.internal("data/music/sims_build_mode.mp3"));
	
	/**
	 * The sound to be played at the start of a level
	 */
	public static Sound levelStartSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/level_start.mp3"));
	
	/**
	 * The sound to be played at the end of a level 
	 */
	public static Sound levelCompleteSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/level_complete.mp3"));
	
	/**
	 * The sound to be played when a bullet is fired by the player's ship 
	 */
	public static Sound shotSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/pewpew.mp3"));
	
	/**
	 * The sound to be played when a bullet collides with an entity 
	 */
	public static Sound bulletImpactSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/explosion.mp3"));
	
	/**
	 * The sound to be played when the ship collides with an entity 
	 */
	public static Sound shipImpactSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/bump.mp3"));
	
	/**
	 * The sound to be played when the ship collects the health up powerup
	 */
	public static Sound healthUpSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/health_up.mp3"));
	
	/**
	 * The sound to be played when the ship collects the speed of light powerup 
	 */
	public static Sound speedOfLightOnSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/speed_on.mp3"));
	
	/**
	 * The sound to be played when the effect of the speed of light powerup ends 
	 */
	public static Sound speedOfLightOffSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/speed_off.mp3"));
	
	/**
	 * The sound to be played when the ship collects points
	 */
	public static Sound pointsSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/coin.mp3"));
	
	/**
	 * The sound to be played when the ship dies
	 */
	public static Sound shipDeathSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/ship_death.mp3"));
	
	/**
	 * The sound to be played when an enemy dies
	 */
	public static Sound enemyDeathSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/ratdeath.mp3"));
	
	/**
	 * Plays the game music. 
	 * @param looping true if the game music should loop; false otherwise
	 */
	public static void playGameMusic(boolean looping) {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play game music");
		gameMusic.setVolume(SSJava.prefs.getInteger("musicVolume", 100) / 100f);
		gameMusic.setLooping(looping);
		gameMusic.play();
	}
	
	/**
	 * Pauses the game music. 
	 */
	public static void pauseGameMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Pause game music");
		gameMusic.pause();
	}
	
	/**
	 * Stops the game music. 
	 */
	public static void stopGameMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Stop game music");
		gameMusic.stop();
	}
	
	/**
	 * Plays the menu music. 
	 * @param looping true if the menu music should loop
	 */
	public static void playMenuMusic(boolean looping) {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play menu music");
		menuMusic.setVolume(SSJava.prefs.getInteger("musicVolume", 100) / 100f);
		menuMusic.setLooping(looping);
		menuMusic.play();
	}
	/**
	 * Pauses the menu music. 
	 */
	public static void pauseMenuMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Pause menu music");
		menuMusic.pause();
	}
	/**
	 * Stops the menu music. 
	 */
	public static void stopMenuMusic() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Stop menu music");
		menuMusic.stop();
	}
	
	/**
	 * Plays the creator music. 
	 * @param looping true if the creator music should loop
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
	 * Plays the level start sound. 
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
				float volume = SSJava.prefs.getInteger("soundVolume", 100) / 100f;
				levelStartSound.play(volume - volume / 20);
			}
		}).start();
	}
	
	/**
	 * Plays the level start sound. 
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
	 * Plays the shot sound for a fired bullet. 
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
				float volume = SSJava.prefs.getInteger("soundVolume", 100) / 100f;
				shotSound.play(volume - volume / 20);
			}
		}).start();
	}
	
	/**
	 * Plays the explosion sound for the bullet impact. 
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
	 * Plays the explosion sound for the ship impact.
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
	 * Plays the health up activated sound. 
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
	 * Plays the speed of light activated. 
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
	 * Plays the speed of light deactivated sound. 
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
	 * Plays the points picked up sound. 
	 */
	public static void pointsPickedUp() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play points picked up sound");
		new Thread(new Runnable() {
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				pointsSound.play(SSJava.prefs.getInteger("soundVolume", 100) / 100f);
			}
		}).start();
	}
	
	/**
	 * Plays the ship death sound. 
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
	 * Plays the enemy death sound. 
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
	
	/**
	 * Pauses all the game sounds. 
	 */
	public static void pauseGameSounds() {
		levelStartSound.pause();
		shotSound.pause();
		bulletImpactSound.pause();
		shipImpactSound.pause();
		healthUpSound.pause();
		speedOfLightOnSound.pause();
		speedOfLightOffSound.pause();
		pointsSound.pause();
		shipDeathSound.pause();
		enemyDeathSound.pause();
	}
	
	/**
	 * Stops all the game sounds. 
	 */
	public static void stopGameSounds() {
		levelStartSound.stop();
		shotSound.stop();
		bulletImpactSound.stop();
		shipImpactSound.stop();
		healthUpSound.stop();
		speedOfLightOnSound.stop();
		speedOfLightOffSound.stop();
		pointsSound.stop();
		shipDeathSound.stop();
		enemyDeathSound.stop();
	}
	
	/**
	 * Resumes all the game sounds. 
	 */
	public static void resumeGameSounds() {
		levelStartSound.resume();
		shotSound.resume();
		bulletImpactSound.resume();
		shipImpactSound.resume();
		healthUpSound.resume();
		speedOfLightOnSound.resume();
		speedOfLightOffSound.resume();
		pointsSound.resume();
		shipDeathSound.resume();
		enemyDeathSound.resume();
	}
	
	/**
	 * Disposes all the sound and music instances. 
	 */
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
		pointsSound.dispose();
		shipDeathSound.dispose();
		enemyDeathSound.dispose();
	}
}
