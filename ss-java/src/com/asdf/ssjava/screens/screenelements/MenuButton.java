package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.SSJava;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuButton extends TextButton {

	/**
	 * @param text
	 * @param width
	 * @param height
	 */
	public MenuButton(String text, float width, float height, final SSJava game) {
		super(text, new TextButtonStyle() {{
			Skin skin = new Skin() {{
				addRegions(game.assetManager.get("data/menu/button.pack", TextureAtlas.class));
			}};
			up = skin.getDrawable("buttonnormal");
			down = skin.getDrawable("buttonpressed");
			font = game.assetManager.get("data/fonts/font.fnt", BitmapFont.class);		

		}});
		
		setWidth(width);
		setHeight(height);
		
	}
}
