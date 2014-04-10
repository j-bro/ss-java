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

public abstract class Obstacle extends MoveableEntity {

	/**
	 * The bullet's default velocity
	 */
	Vector2 DEFAULT_VELOCITY;
	
	/**
	 * A trigger to stop the ship from colliding with the obstacle multiple times
	 */
	public transient boolean alreadyCollided = false;
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Obstacle(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world) {
		super(position, width, height, rotation, gameWorld, world);
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		if (this instanceof Planet) {			
			gameWorld.getLevel().gameChangers.removeValue(this, true);
		}
		else {
			gameWorld.getLevel().obstacles.removeValue(this, true);
		}
	}

	public abstract Vector2 getDEFAULT_VELOCITY();
	
}
