package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Credits menu containing labels for the different people who worked on the project and resources used in the game. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class CreditsMenu implements Screen {

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The stage instance
	 */
	Stage stage;
	
	/**
	 * The text font
	 */
	BitmapFont whiteFont;
	
	/**
	 * The labels
	 */
	Label titleLabel, artistTitleLabel, artistLabel, coderTitleLabel, coderLabel, audioTitleLabel, audioLabel, miscTitleLabel, miscLabel;
	
	/**
	 * The buttons
	 */
	MenuButton backButton;
	
	/**
	 * The background image
	 */
	Image bgImage;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * Creates a credits menu with the specified parameters. 
	 * @param game the SSJava instance
	 * @param referrer the referring screen
	 */
	public CreditsMenu(SSJava game, Screen referrer) {
		this.game = game;
		this.referrer = referrer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw(); 
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			stage = new Stage(width, height, true) {
				@Override
		        public boolean keyDown(int keyCode) {
		            if (keyCode == Keys.ESCAPE) {
		                game.setScreen(referrer);
		            }
		            return super.keyDown(keyCode);
		        }
			};
		}

		stage.setViewport(width, height);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		// Exit to main menu button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 250);
				
		// Title text
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Credits", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		// Coders labels
		coderTitleLabel = new Label("Coders", ls);
		coderTitleLabel.setX(Gdx.graphics.getWidth() / 3 - coderTitleLabel.getWidth() / 2);
		coderTitleLabel.setY(Gdx.graphics.getHeight() / 2 - coderTitleLabel.getHeight() / 2 + 120);
		coderTitleLabel.setAlignment(Align.center);
		
		coderLabel = new Label("Jeremy Brown\nSimon Thompson", ls);
		coderLabel.setX(Gdx.graphics.getWidth() / 3 - coderLabel.getWidth() / 2);
		coderLabel.setY(Gdx.graphics.getHeight() / 2 - coderLabel.getHeight() / 2 + 50);
		coderLabel.setAlignment(Align.center);
		
		// Artist labels
		artistTitleLabel = new Label("Artist", ls);
		artistTitleLabel.setX(Gdx.graphics.getWidth() * 2 / 3 - artistTitleLabel.getWidth() / 2);
		artistTitleLabel.setY(Gdx.graphics.getHeight() / 2 - artistTitleLabel.getHeight() / 2 + 120);
		artistTitleLabel.setAlignment(Align.center);
		
		artistLabel = new Label("Julie Brown", ls);
		artistLabel.setX(Gdx.graphics.getWidth() * 2 / 3 - artistLabel.getWidth() / 2);
		artistLabel.setY(Gdx.graphics.getHeight() / 2 - artistLabel.getHeight() / 2 + 50);
		artistLabel.setAlignment(Align.center);
		
		// Audio labels
		audioTitleLabel = new Label("Audio", ls);
		audioTitleLabel.setX(Gdx.graphics.getWidth() / 3 - audioTitleLabel.getWidth() / 2);
		audioTitleLabel.setY(Gdx.graphics.getHeight() / 2 - audioTitleLabel.getHeight() / 2 - 50);
		audioTitleLabel.setAlignment(Align.center);
		
		audioLabel = new Label("freesound.org", ls);
		audioLabel.setX(Gdx.graphics.getWidth() / 3 - audioLabel.getWidth() / 2);
		audioLabel.setY(Gdx.graphics.getHeight() / 2 - audioLabel.getHeight() / 2 - 120);
		audioLabel.setAlignment(Align.center);
		
		// Misc labels
		miscTitleLabel = new Label("Misc", ls);
		miscTitleLabel.setX(Gdx.graphics.getWidth() * 2 / 3 - miscTitleLabel.getWidth() / 2);
		miscTitleLabel.setY(Gdx.graphics.getHeight() / 2 - miscTitleLabel.getHeight() / 2 - 50);
		miscTitleLabel.setAlignment(Align.center);
		
		miscLabel = new Label("LibGDX\nBox2D", ls);
		miscLabel.setX(Gdx.graphics.getWidth() * 2 / 3 - miscLabel.getWidth() / 2);
		miscLabel.setY(Gdx.graphics.getHeight() / 2 - miscLabel.getHeight() / 2 - 120);
		miscLabel.setAlignment(Align.center);
		
		// Background image
		bgImage = new Image(SSJava.assetManager.get("data/textures/backgrounds/menu_bg.png", Texture.class));
		bgImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(bgImage);
		
		stage.addActor(titleLabel);
		stage.addActor(artistTitleLabel);
		stage.addActor(artistLabel);
		stage.addActor(coderTitleLabel);
		stage.addActor(coderLabel);
		stage.addActor(audioTitleLabel);
		stage.addActor(audioLabel);
		stage.addActor(miscTitleLabel);
		stage.addActor(miscLabel);
		stage.addActor(backButton);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show credits");
		whiteFont = SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
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
