/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Jeremy Brown
 * 
 */
public abstract class Powerup extends MoveableEntity {
	
	/**
	 * A trigger to stop the ship from colliding with the enemy multiple times
	 */
	public transient boolean alreadyCollided = false;

	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Powerup(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world) {
		super(position, width, height, rotation, gameWorld, world);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		gameWorld.getLevel().powerups.removeValue(this, true);
	}
	
	
	
}
