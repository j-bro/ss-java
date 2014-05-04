/**
 * 
 */
package com.asdf.ssjava;

import com.asdf.ssjava.screens.GameScreen;
import com.asdf.ssjava.screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;

/**
 * @author Jeremy Brown
 * 
 */
public class SSJava extends Game {

	/**
	 * Version number
	 */
	public static final String VERSION = "0.1";
	
	/**
	 * Log tag
	 */
	public static final String LOG = "SS-Java";
	
	/**
	 * Debugging switch
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * The preferences instance
	 */
	public static Preferences prefs;
	
	/**
	 * The high scores instance
	 */
	public HighScores highScores;
	
	/**
	 * The asset manager instance
	 */
	public static AssetManager assetManager;
	
	/**
	 * A reference to the game screen
	 */
	public GameScreen gameScreen;
	
	/**
	 * The reference to the most recent screenshot
	 */
	public TextureRegion screenshot;
	
	public int width;
	
	public int height;
	
	/**
	 * The path to the high scores file
	 */
	static String highScoresPath = "hs.json";
	
	/**
	 * The preferences key for the highest completed level
	 */
	static String highestCompletedLevelKey = "highestCompletedLevel";
	
	@Override
	public void create() {
		prefs = Gdx.app.getPreferences("com.asdf.ssjava.preferences");
		
		highScores = loadHighScores(highScoresPath);
		
		assetManager = new AssetManager();
		
		setScreen(new SplashScreen(this));			
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() { 
		super.dispose();
		AudioPlayer.dispose();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {		
		super.render();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#pause()
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#resume()
	 */
	@Override
	public void resume() {
		super.resume();
	}
	
	/**
	 * 
	 * @param level the specified level
	 * @return true if the highest completed level is greater than the specified level
	 */
	public static boolean checkLevelCompletion(int level) {
		if (SSJava.prefs.getInteger(highestCompletedLevelKey, 0) >= level) {
			return true;
		}
		return false;
	}
	
	/**
	 * Save the highest level completed by the player
	 * @param level the level completed
	 */
	public static void writeCompletedLevel(int level) {
		if (level > SSJava.prefs.getInteger(highestCompletedLevelKey, 0)) {
			prefs.putInteger(highestCompletedLevelKey, level);
			prefs.flush();
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Updated game completion: " + level);
		}
	}
	
	/**
	 * Load the high scores
	 * @param filePath the path where the high scores file is located
	 * @return the HighScores object containing all the high scores
	 */
	public static HighScores loadHighScores(String filePath) {
		FileHandle file = Gdx.files.local(filePath);
		if (file.exists()) {			
			return new Json().fromJson(HighScores.class, Gdx.files.local(filePath)); 
		}
		// In case high scores file is not found, create a new instance filled with zeros
		else {
			return new HighScores() {{
				for (int i = 0; i < 10; i++) {					
					add(new Score("AAA", 0));
				}
			}};
		}
	}
/**
 * 
 */
package com.asdf.ssjava;

import com.asdf.ssjava.screens.GameScreen;
import com.asdf.ssjava.screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;

/**
 * @author Jeremy Brown
 * 
 */
public class SSJava extends Game {

	/**
	 * Version number
	 */
	public static final String VERSION = "0.1";
	
	/**
	 * Log tag
	 */
	public static final String LOG = "SS-Java";
	
	/**
	 * Debugging switch
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * The preferences instance
	 */
	public static Preferences prefs;
	
	/**
	 * The high scores instance
	 */
	public HighScores highScores;
	
	/**
	 * The asset manager instance
	 */
	public static AssetManager assetManager;
	
	/**
	 * A reference to the game screen
	 */
	public GameScreen gameScreen;
	
	/**
	 * The reference to the most recent screenshot
	 */
	public TextureRegion screenshot;
	
	public int width;
	
	public int height;
	
	/**
	 * The path to the high scores file
	 */
	static String highScoresPath = "hs.json";
	
	/**
	 * The preferences key for the highest completed level
	 */
	static String highestCompletedLevelKey = "highestCompletedLevel";
	
	@Override
	public void create() {
		prefs = Gdx.app.getPreferences("com.asdf.ssjava.preferences");
		
		highScores = loadHighScores(highScoresPath);
		
		assetManager = new AssetManager();
		
		setScreen(new SplashScreen(this));			
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() { 
		super.dispose();
		AudioPlayer.dispose();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {		
		super.render();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#pause()
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Game#resume()
	 */
	@Override
	public void resume() {
		super.resume();
	}
	
	/**
	 * 
	 * @param level the specified level
	 * @return true if the highest completed level is greater than the specified level
	 */
	public static boolean checkLevelCompletion(int level) {
		if (SSJava.prefs.getInteger(highestCompletedLevelKey, 0) >= level) {
			return true;
		}
		return false;
	}
	
	/**
	 * Save the highest level completed by the player
	 * @param level the level completed
	 */
	public static void writeCompletedLevel(int level) {
		if (level > SSJava.prefs.getInteger(highestCompletedLevelKey, 0)) {
			prefs.putInteger(highestCompletedLevelKey, level);
			prefs.flush();
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Updated game completion: " + level);
		}
	}
	
	/**
	 * Load the high scores
	 * @param filePath the path where the high scores file is located
	 * @return the HighScores object containing all the high scores
	 */
	public static HighScores loadHighScores(String filePath) {
		FileHandle file = Gdx.files.local(filePath);
		if (file.exists()) {			
			return new Json().fromJson(HighScores.class, Gdx.files.local(filePath)); 
		}
		// In case high scores file is not found, create a new instance filled with zeros
		else {
			return new HighScores() {{
				for (int i = 0; i < 10; i++) {					
					add(new Score("AAA", 0));
				}
			}};
		}
	}
}