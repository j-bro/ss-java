package com.asdf.ssjava.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuButton extends TextButton {

	
	
	/**
	 * @param text
	 * @param style
	 */
	public MenuButton(String text, float width, float height) {
		super(text, new TextButtonStyle() {{
			Skin skin = new Skin() {{
				addRegions(new TextureAtlas("data/menu/button.pack"));
			}};
			up = skin.getDrawable("buttonnormal");
			down = skin.getDrawable("buttonpressed");
			font = new BitmapFont(Gdx.files.internal("data/fonts/font.fnt"), false);
		}});
		
		setWidth(width);
		setHeight(height);
	}
}
