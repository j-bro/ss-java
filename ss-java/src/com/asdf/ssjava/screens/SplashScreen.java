/**
 * 
 */
package com.asdf.ssjava.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.tweenaccessors.SpriteTween;
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
 * @author Jeremy Brown
 *
 */

public class SplashScreen implements Screen {

	Texture splashTexture;
	Sprite splashSprite;
	SpriteBatch batch;
	SSJava game;
	TweenManager tweenManager;
	AssetManager assetManager;
	
	/**
	 * 
	 */
	public SplashScreen(SSJava game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (assetManager.update()) {
			// done loading
		}
		else {
			float progress = assetManager.getProgress();
			int percentProgress = (int) (progress * 100);
			Gdx.app.log(SSJava.LOG, "Assets loading (" + percentProgress + "%)");			
		}
		
		tweenManager.update(delta);
		
		batch.begin();
			splashSprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show splash screen"); // LOG
		
		assetManager = game.assetManager;
		
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
		
		// LOAD ASSETS
		loadAssets();
		
		Tween.to(splashSprite, SpriteTween.ALPHA, 2f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1, 1.5f ).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);
	}

	private void tweenCompleted() {
		Gdx.app.log(SSJava.LOG, "Splash screen tween complete");
		game.setScreen(new MainMenu(game));
		
		splashTexture.dispose();
	}
	
	private void loadAssets() {
		// Menu assets
		assetManager.load("data/fonts/font.fnt", BitmapFont.class);
		assetManager.load("data/fonts/whitefont.fnt", BitmapFont.class);
		assetManager.load("data/menu/button.pack", TextureAtlas.class);
		assetManager.load("data/textures/textfieldcursor.png", Texture.class);
		
		// Gameplay assets
		assetManager.load("data/textures/shipA.png", Texture.class);
		assetManager.load("data/textures/space_rock.png", Texture.class);
		assetManager.load("data/textures/asteroid.png", Texture.class);
		assetManager.load("data/textures/enemy_1.png", Texture.class);
		assetManager.load("data/textures/bullet_strip.png", Texture.class);
		assetManager.load("data/textures/health_up.png", Texture.class);
		assetManager.load("data/textures/speed_of_light.png", Texture.class);
		assetManager.load("data/textures/points.png", Texture.class);
		assetManager.load("data/textures/planet.png", Texture.class);
		assetManager.load("data/textures/sun.png", Texture.class);
		assetManager.load("data/textures/magnetic_object.png", Texture.class);
		
		assetManager.load("data/textures/heart_full.png", Texture.class);
		assetManager.load("data/textures/heart_empty.png", Texture.class);
		assetManager.load("data/textures/heart_half.png", Texture.class);
		
		assetManager.load("data/textures/backgrounds/background_sparks.png", Texture.class);
		assetManager.load("data/textures/backgrounds/black_50-opacity.png", Texture.class);
		
		// Debug text
		assetManager.load("data/fonts/debugFont-14.fnt", BitmapFont.class);
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
