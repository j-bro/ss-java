package com.asdf.ssjava;

import com.asdf.ssjava.screens.SplashScreen;
import com.badlogic.gdx.Game;

public class SSJava extends Game {

	public static final String VERSION = "0.0.1";
	public static final String LOG = "SS-Java";
	
	@Override
	public void create() {		
		setScreen(new SplashScreen(this));
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
