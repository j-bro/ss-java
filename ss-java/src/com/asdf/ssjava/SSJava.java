/**
 * 
 */
package com.asdf.ssjava;

/**
 * @author Jeremy Brown
 * 
 */

import com.asdf.ssjava.screens.GameScreen;
import com.asdf.ssjava.screens.SplashScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SSJava extends Game {

	/**
	 * Version number
	 */
	public static final String VERSION = "0.0.3";
	
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
	
	public int width;
	
	public int height;
	
	@Override
	public void create() {
		if (SSJava.DEBUG) { // straight to game screen for DEBUG
			setScreen(new GameScreen(this));			
		}
		else {
			setScreen(new SplashScreen(this));			
		}
		prefs = Gdx.app.getPreferences("com.asdf.ssjava.preferences");
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	@Override
	public void dispose() { 
		super.dispose();
//		AudioPlayer.dispose();
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
}
