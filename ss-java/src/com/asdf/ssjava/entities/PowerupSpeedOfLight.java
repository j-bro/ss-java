package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class PowerupSpeedOfLight extends Powerup {

	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public PowerupSpeedOfLight(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(EntityConstants.SpeedOfLightHealth);
	}

	// constructor for serialization
		public PowerupSpeedOfLight() {
			super(new Vector2(0, 0), EntityConstants.SpeedOfLightWidth, EntityConstants.SpeedOfLightHeight, EntityConstants.SpeedOfLightRotation);
			setHealth(EntityConstants.SpeedOfLightHealth);
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
		return "Speed of Light Powerup";
	}
}
