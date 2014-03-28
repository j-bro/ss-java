/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * 
 */
public class Asteroid extends Obstacle {

	/**
	 * The asteroid's default velocity
	 */
	private final Vector2 DEFAULT_VELOCITY = new Vector2(2, 0);
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Asteroid(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(EntityConstants.AsteroidHealth);
	}
	
	// constructor for serialization
	public Asteroid() {
		super(new Vector2(0, 0), EntityConstants.AsteroidWidth, EntityConstants.AsteroidHeight, EntityConstants.AsteroidRotation);
		setHealth(EntityConstants.AsteroidHealth);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}
	
	/**
	 * @return the object's description string
	 */
	public String toString() {
		return "Asteroid";
	}
}
