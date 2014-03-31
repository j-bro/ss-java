package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class PowerupHealthUp extends Powerup {
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * The health points given to the ship when it picks up this power up
	 */
	public static final int HEALTH_GIVEN = 2;
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public PowerupHealthUp(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(DEFAULT_HEALTH);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Powerup#update()
	 */
	public void update() {
		super.update();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Health Up Powerup";
	}

}
