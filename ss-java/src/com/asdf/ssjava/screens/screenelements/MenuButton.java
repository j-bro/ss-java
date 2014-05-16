package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.SSJava;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Definition for a menu button. 
 * Skin is the default b&w skin for all the menu buttons. 
 * @author Jeremy Brown
 */
public class MenuButton extends TextButton {

	/**
	 * Creates a menu button with the pre-defined skin. 
	 * @param text the text of the button
	 * @param width the width of the button
	 * @param height the height of the button
	 */
	public MenuButton(String text, float width, float height, final SSJava game) {
		super(text, new TextButtonStyle() {{
			Skin skin = new Skin() {{
				addRegions(SSJava.assetManager.get("data/menu/button.pack", TextureAtlas.class));
			}};
			up = skin.getDrawable("buttonnormal");
			down = skin.getDrawable("buttonpressed");
			font = SSJava.assetManager.get("data/fonts/font.fnt", BitmapFont.class);		

		}});
		
		setWidth(width);
		setHeight(height);
		
	}
}
