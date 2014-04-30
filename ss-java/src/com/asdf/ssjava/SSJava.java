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
	public static final String VERSION = "0.0.4";
	
	/**
	 * Log tag
	 */
	public static final String LOG = "SS-Java";
	
	/**
	 * Debugging switch
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * 
	 */
	public static Preferences prefs;
	
	/**
	 * 
	 */
	public HighScores highScores;
	
	/**
	 * 
	 */
	public AssetManager assetManager;
	
	/**
	 * A reference to the game screen
	 */
	public GameScreen gameScreen;
	
	public TextureRegion screenshot;
	
	public int width;
	
	public int height;
	
	static String highScoresPath = "hs.json";
	
	@Override
	public void create() {
		prefs = Gdx.app.getPreferences("com.asdf.ssjava.preferences");
		
		highScores = loadHighScores(highScoresPath);
		
		assetManager = new AssetManager();
		
		setScreen(new SplashScreen(this));			
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	@Override
	public void dispose() { 
		super.dispose();
		AudioPlayer.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	// Game state methods
	public static boolean checkLevelCompletion(int level) {
		if (SSJava.prefs.getInteger("greatestCompletedLevel", 0) >= level) {
			return true;
		}
		return false;
	}
	
	/**
	 * Load the high scores
	 * @param filePath the path where the high scores file is located
	 * @return the HighScores object containing all the high scores
	 */
	public static HighScores loadHighScores(String filePath) {
		FileHandle file = Gdx.files.local(filePath);
		if (file.exists()) {			
			Json json = new Json();
			return json.fromJson(HighScores.class, Gdx.files.local(filePath)); 
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
