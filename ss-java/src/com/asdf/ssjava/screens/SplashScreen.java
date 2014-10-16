package com.asdf.ssjava.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.SpriteTween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * The first screen shown in the application. 
 * Displays a big text with the title of the game. 
 * Stays until all the game assets have finished loading. 
 * @author Jeremy Brown
 */
public class SplashScreen implements Screen {

	Texture splashTexture;
	Sprite splashSprite;
	SpriteBatch batch;
	SSJava game;
	TweenManager tweenManager;
	AssetManager assetManager;
	
	/**
	 * Creates a splash screen. 
	 * @param game the SSJava instance
	 */
	public SplashScreen(SSJava game) {
		this.game = game;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (assetManager.update()) {
			// Loading complete
		}
		else {
			float progress = assetManager.getProgress();
			int percentProgress = (int) (progress * 100);
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Assets loading (" + percentProgress + "%)");			
		}
		
		tweenManager.update(delta);
		
		batch.begin();
			splashSprite.draw(batch);
		batch.end();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show splash screen");
		
		assetManager = SSJava.assetManager;
		
		// Display splash title text
		splashTexture = new Texture("data/SplashScreen_black.png");
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashSprite = new Sprite(splashTexture);
		splashSprite.setPosition(Gdx.graphics.getWidth() / 2 - splashSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - splashSprite.getHeight() / 2);
		splashSprite.setColor(1, 1, 1, 0);
		
		batch = new SpriteBatch();
		
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		
		tweenManager = new TweenManager();
		
		TweenCallback cb = new TweenCallback() {
			/*
			 * (non-Javadoc)
			 * @see aurelienribon.tweenengine.TweenCallback#onEvent(int, aurelienribon.tweenengine.BaseTween)
			 */
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenCompleted();
			}
		};
		
		// Load assets
		loadAssets();
		
		Tween.to(splashSprite, SpriteTween.ALPHA, 2f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1, 1f ).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);
	}

	/**
	 * Sets the screen to the main menu when the fade out animation of the splash screen has completed. 
	 */
	private void tweenCompleted() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Splash screen tween complete");
		game.setScreen(new MainMenu(game));
		splashTexture.dispose();
	}
	
	/**
	 * Loads all the game assets into memory asynchronously. 
	 */
	private void loadAssets() {
		// Menu assets
		assetManager.load("data/fonts/font.fnt", BitmapFont.class);
		assetManager.load("data/fonts/whitefont.fnt", BitmapFont.class);
		assetManager.load("data/menu/button.pack", TextureAtlas.class);
		assetManager.load("data/textures/textfieldcursor.png", Texture.class);
		assetManager.load("data/textures/backgrounds/menu_bg.png", Texture.class);
		
		// Gameplay assets
		assetManager.load("data/textures/shipA.png", Texture.class);
		assetManager.load("data/textures/space_rock.png", Texture.class);
		assetManager.load("data/textures/asteroid.png", Texture.class);
		assetManager.load("data/textures/enemy_1.png", Texture.class);
		assetManager.load("data/textures/good_bullet.png", Texture.class);
		assetManager.load("data/textures/health_up.png", Texture.class);
		assetManager.load("data/textures/speed_of_light.png", Texture.class);
		assetManager.load("data/textures/coin.png", Texture.class);
		assetManager.load("data/textures/planet.png", Texture.class);
		assetManager.load("data/textures/sun.png", Texture.class);
		assetManager.load("data/textures/space_junk.png", Texture.class);
		
		assetManager.load("data/textures/heart_full.png", Texture.class);
		assetManager.load("data/textures/heart_empty.png", Texture.class);
		assetManager.load("data/textures/heart_half.png", Texture.class);
		
		assetManager.load("data/textures/backgrounds/background_sparks.png", Texture.class);
		assetManager.load("data/textures/backgrounds/black_50-opacity.png", Texture.class);
		
		// Debug text
		assetManager.load("data/fonts/debugFont-14.fnt", BitmapFont.class);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		
	}
}
