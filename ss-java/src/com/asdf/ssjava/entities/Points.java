package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class Points extends Obstacle{

	/**
	 * The planet's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 100; 
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Points(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(DEFAULT_HEALTH);
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Points";
	}
}
