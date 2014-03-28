package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class PowerupHealthUp extends Powerup {
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public PowerupHealthUp(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(EntityConstants.HealthUpHealth);
	}
	
	// constructor for serialization
	public PowerupHealthUp() {
		super(new Vector2(0, 0), EntityConstants.HealthUpWidth, EntityConstants.HealthUpHeight, EntityConstants.HealthUpRotation);
		setHealth(EntityConstants.HealthUpHealth);
	}
	
	/**
	 * 
	 */
	public void update() { // TODO
		super.update();
	}
	
	/**
	 * 
	 */
	public String toString() {
		return "Health Up Powerup";
	}

}
