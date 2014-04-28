/**
 * 
 */
package com.asdf.ssjava.screens;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.LevelSelectButton;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Jeremy Brown
 * 
 */

public class LevelSelectMenu implements Screen {

	SSJava game;
	Stage stage;
	SpriteBatch batch;	
	BitmapFont whiteFont;
	
	Label titleLabel;
	
	ScrollPane levelSelectPane;
	WidgetGroup levelWidgetGroup;
	

	MenuButton loadButton, backButton;
	
	LevelSelectButton level1Button, level2Button, level3Button, level4Button, level5Button;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * 
	 * @param game The game instance of type SSJava
	 */
	public LevelSelectMenu(SSJava game, Screen referrer) {
		this.game = game;
		this.referrer = referrer;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		
		batch.begin();
			stage.draw();
		batch.end();
	}

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
		
		// level buttons
		level1Button = new LevelSelectButton("Level 1", 280, 65, game, "levels/level1.json");
		level1Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level1Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 150);
		
		if (!SSJava.checkLevelCompletion(1 - 1)) {
			level1Button.setEnabled(false);
		}
		else {
			level1Button.setEnabled(true);
		}
		
		level2Button = new LevelSelectButton("Level 2", 280, 65, game, "");
		level2Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level2Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 100);
		if (!SSJava.checkLevelCompletion(2 - 1)) {
			level2Button.setEnabled(false);
		}
		else {
			level2Button.setEnabled(true);
		}
		
		level3Button = new LevelSelectButton("Level 3", 280, 65, game, "");
		level3Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level3Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 50);
		if (!SSJava.checkLevelCompletion(3 - 1)) {
			level3Button.setEnabled(false);
		}
		else {
			level3Button.setEnabled(true);
		}
		
		level4Button = new LevelSelectButton("Level 4", 280, 65, game, "");
		level4Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level4Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2);
		if (!SSJava.checkLevelCompletion(4 - 1)) {
			level4Button.setEnabled(false);
		}
		else {
			level4Button.setEnabled(true);
		}
		
		level5Button = new LevelSelectButton("Level 5", 280, 65, game, "");
		level5Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level5Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 - 50);
		if (!SSJava.checkLevelCompletion(5 - 1)) {
			level5Button.setEnabled(false);
		}
		else {
			level5Button.setEnabled(true);
		}
		
		// Load level button
		loadButton = new MenuButton("Load level", 280, 65, game);
		loadButton.setX(Gdx.graphics.getWidth() / 2 - loadButton.getWidth() / 2);
		loadButton.setY(Gdx.graphics.getHeight() / 2 - loadButton.getHeight() / 2 - 150);
		loadButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Load button down");
				return true;
			}
	
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Load button up");
				
				// File selection
				if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
					JFileChooser chooser = new JFileChooser("levels");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON level files", "json");
					chooser.setFileFilter(filter);
					int returnVal = chooser.showOpenDialog(new JPanel());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						String levelPath = chooser.getSelectedFile().getPath();
						game.gameScreen = new GameScreen(game, levelPath);
						game.setScreen(game.gameScreen);
					}
				}
			}
		});
		
		// exit to main menu button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 250);
		
		// Title text
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Select Level", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		// TODO for scrolling!
		/*
		levelWidgetGroup = new WidgetGroup();
		levelWidgetGroup.setWidth(Gdx.graphics.getWidth() / 3);
		levelWidgetGroup.setHeight(900);
		levelWidgetGroup.addActor(level1Button);
		levelWidgetGroup.addActor(new LevelSelectButton(level1Button, game) {{
			setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
			setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 + 100);
		}});
		levelWidgetGroup.addActor(new LevelSelectButton(level1Button, game) {{
			setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
			setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 + 50);
		}});
		levelWidgetGroup.addActor(new LevelSelectButton(level1Button, game) {{
			setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
			setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2);
		}});
		levelWidgetGroup.addActor(new LevelSelectButton(level1Button, game) {{
			setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
			setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 50);
		}});
		levelWidgetGroup.addActor(new LevelSelectButton(level1Button, game) {{
			setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
			setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 100);
		}});
		levelWidgetGroup.addActor(new LevelSelectButton(level1Button, game) {{
			setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
			setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 150);
		}});
		levelWidgetGroup.addActor(new LevelSelectButton(level1Button, game) {{
			setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
			setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 200);
		}});
		
		levelSelectPane = new ScrollPane(levelWidgetGroup);
		levelSelectPane.setBounds(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 150, 200, 600);
		Gdx.app.log(SSJava.LOG, levelSelectPane.getWidth() + ", " + levelSelectPane.getHeight());
		
		Gdx.app.log(SSJava.LOG, levelWidgetGroup.getWidth() + ", " + levelWidgetGroup.getHeight());
		stage.addActor(levelSelectPane);*/
		
		stage.addActor(level1Button);
		stage.addActor(titleLabel);
		stage.addActor(backButton);
		stage.addActor(level1Button);
		stage.addActor(level2Button);
		stage.addActor(level3Button);
		stage.addActor(level4Button);
		stage.addActor(level5Button);
		stage.addActor(loadButton);
		stage.addActor(backButton);
		stage.addActor(titleLabel);
	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show level selection");
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show level selection");
		
		batch = new SpriteBatch();
		whiteFont = game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);		
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
