/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author Simon Thompson
 *
 */
public class Planet extends Obstacle {
	
	/**
	 * The planet's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 4;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 100;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 1000; 
	
	public static final float DEFAULT_WIDTH = 4;
	public static final float DEFAULT_HEIGHT = 4;
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Planet(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(DEFAULT_HEALTH);
	}
	
	// constructor for serialization
		public Planet() {
			super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
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
		return "Planet";
	}

}
