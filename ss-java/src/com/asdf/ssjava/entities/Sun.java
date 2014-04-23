package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class Sun extends Obstacle{

	/**
	 * @param args
	 */
	/**
	 * The planet's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Sun(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Sun";
	}

}
