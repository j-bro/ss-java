/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

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
	public Obstacle(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() {
		position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime())); 
		super.update();
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		dead = true;
	}

	public abstract Vector2 getDEFAULT_VELOCITY();
	
}
