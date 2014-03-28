/**
 * 
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.LevelSelectButton;
import com.asdf.ssjava.screens.screenelements.MenuButton;
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
	
	MenuButton backButton;
	
	ScrollPane levelSelectPane;
	WidgetGroup levelWidgetGroup;
	
	LevelSelectButton level1Button;
	LevelSelectButton level2Button;
	LevelSelectButton level3Button;
	LevelSelectButton level4Button;
	LevelSelectButton level5Button;
	
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
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		// level buttons
		level1Button = new LevelSelectButton("Level 1", 280, 65, game, "levels/level1.json");
		level1Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level1Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 150);
		
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
		
	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show level selection");
		
		batch = new SpriteBatch();
		whiteFont = game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
