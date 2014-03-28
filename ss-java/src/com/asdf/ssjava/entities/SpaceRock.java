package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class SpaceRock extends Obstacle {

	
	/**
	 * The space rock's default velocity (yes, it is static)
	 */
	private final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public SpaceRock(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(EntityConstants.SpaceRockHealth);
	}
	
	// constructor for serialization
	public SpaceRock() {
		super(new Vector2(0, 0), EntityConstants.SpaceRockWidth, EntityConstants.SpaceRockHeight, EntityConstants.SpaceRockRotation);
		setHealth(EntityConstants.SpaceRockHealth);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}

	public String toString() {
		return "Space Rock";
	}
}
