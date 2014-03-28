package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class Planet extends Obstacle{

	/**
	 * @param args
	 */
	
	/**
	 * The planet's default velocity (static)
	 */
	private final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	
	public Planet(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(4);
	}


	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}
	
	public String toString() {
		return "Planet";
	}

}
