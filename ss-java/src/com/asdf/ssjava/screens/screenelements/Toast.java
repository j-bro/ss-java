package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.SSJava;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Utility class to display a toast on the screen. 
 * @author Jeremy Brown
 */
public class Toast {
	
	private Toast() { }
	
	/**
	 * Toasts a message from a ToastMessage object
	 * @param tm the ToastMessage specified to be shown on the screen
	 * @return the new window actor to be added to a stage
	 */
	public static Actor create(ToastMessage tm) {
		return create(tm.message, tm.duration);
	}
	
	/**
	 * Toasts a message from the specified text and duration. 
	 * @param text the message text
	 * @param time the duration to display the toast
	 * @return the new window actor to be added to a stage
	 */
	public static Actor create(final String text, final float time) {
		final Window window = new Window("", new Window.WindowStyle(SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class), Color.WHITE, null));

		window.setMovable(false);

		window.defaults().spaceBottom(5);

		BitmapFont whiteFont = SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		Label toastLabel = new Label(text, ls);
		toastLabel.setAlignment(Align.center);
		toastLabel.setWrap(true);

		window.row().fillX().expandX();
		window.add(toastLabel).fillX().padLeft(10);

		window.invalidate();

		window.setWidth(Gdx.graphics.getWidth() * 0.95f);
		window.setHeight(toastLabel.getTextBounds().height + 20 + window.getStyle().titleFont.getLineHeight());

		window.setX(Gdx.graphics.getWidth() * 0.5f - window.getWidth() * 0.5f);
		window.setY(window.getHeight());
		
		final Timer t = new Timer();
		t.scheduleTask(new Task() {
			@Override
			public void run() {
				window.remove();
			}
		}, time);
		
		window.addAction(new Action(){ 
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.Action#act(float)
			 */
			@Override
			public boolean act(float delta) {
				t.start();
				return true;
			}
		});
		
		return window;
	}
}
