/**
 * 
 */
package com.asdf.ssjava;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 * 
 */

import com.asdf.ssjava.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SSJava extends Game {

	/**
	 * 
	 */
	public static final String VERSION = "0.0.2";
	
	/**
	 * 
	 */
	public static final String LOG = "SS-Java";
	
	/**
	 * 
	 */
	public Preferences prefs;
	
	@Override
	public void create() {
		setScreen(new GameScreen(this));
		// setScreen(new SplashScreen(this)); TEMP FOR DEVELOPMENT
		prefs = Gdx.app.getPreferences("com.asdf.ssjava.preferences");
	}

	@Override
	public void dispose() { 
		super.dispose();
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
