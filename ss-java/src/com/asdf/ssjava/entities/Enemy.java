/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 *
 */

public abstract class Enemy extends MoveableEntity {
	
	/**
	 * The enemy's type
	 */
	transient int type;
	
	/**
	 * The enemy's default velocity
	 */
	Vector2 DEFAULT_VELOCITY;
	
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
	public Enemy(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
	}
	
	public abstract void fire();
	public abstract int getType();

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() {
		super.update();
	}
	

}
