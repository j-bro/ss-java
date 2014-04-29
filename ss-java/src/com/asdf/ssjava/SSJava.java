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
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
	public static final boolean DEBUG = false;
	
	/**
	 * 
	 */
	public static Preferences prefs;
	
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
	
	@Override
	public void create() {
		prefs = Gdx.app.getPreferences("com.asdf.ssjava.preferences");
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
}
