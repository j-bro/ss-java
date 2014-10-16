package com.asdf.ssjava.screens.screenelements;

import com.badlogic.gdx.graphics.g2d.Sprite;
import aurelienribon.tweenengine.TweenAccessor;

/**
 * Tweet accessor for the splash screen sprite
 * @author Jeremy Brown
 */
public class SpriteTween implements TweenAccessor<Sprite> {
	
	/**
	 * The alpha value for the tween
	 */
	public static final int ALPHA = 1;
	
	/*
	 * (non-Javadoc)
	 * @see aurelienribon.tweenengine.TweenAccessor#getValues(java.lang.Object, int, float[])
	 */
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case ALPHA: returnValues[0] = target.getColor().a; return 1;
		default: return 0;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see aurelienribon.tweenengine.TweenAccessor#setValues(java.lang.Object, int, float[])
	 */
	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case ALPHA: target.setColor(1, 1, 1, newValues[0]); break;
		}	
	}
}
