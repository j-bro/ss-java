/**
* Extends AbstactEntity to model a moveable entity.
* Adds fields for velocity and acceleration vectors, as well as default acceleration and rotation.
* Adds methods for ... TODO
* Implements update method for the entity's hitbox.
*/
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 *
 */

public abstract class MoveableEntity extends AbstractEntity {
	
	/**
	 * The entity's velocity
	 */
	protected Vector2 velocity;
	
	/**
	 * The entity's acceleration
	 */
	protected Vector2 acceleration;
	
	/**
	 * The entity's rotation, in degrees
	 */
	protected float rotation;
	
	/**
	 * The entity's default velocity vector, as values for x and y
	 */
	protected Vector2 DEFAULT_VELOCITY;
	
	/**
	 * Creates an entity with a position, dimensions, [SPEED], and rotation
	 * @param position the entity's position, as values for x and y
	 * @param width the entity's width in pixels
	 * @param height the entity's height in pixels
	 * @param rotation the entity's rotation in degrees
	 */
	protected MoveableEntity(Vector2 position, float width, float height, float rotation) {
		super(position, width, height);
		this.rotation = rotation;
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
	}
	
	/**
	 * Returns the entity's velocity
	 * @return the velocity of the entity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	/**
	 * Sets the entity's velocity
	 * @deprecated use the getVelocity() method to obtain the instance of velocity and set the x, y variables
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	/**
	 * Returns the entity's acceleration
	 * @return the acceleration of the entity
	 */
	public Vector2 getAcceleration() {
		return acceleration;
	}

	/**
	 * Sets the entity's acceleration
	 * @deprecated use the getAcceleration() method to obtain the instance of acceleration and set the x, y variables
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * Returns the entity's rotation
	 * @return the rotation of the entity in degrees
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * Sets the entity's rotation
	 * @param rotation the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Updates the entity's hitbox.
	 * Runs every time the game renders a frame.
	 */
	public void update() {
		hitbox.x = position.x;
		hitbox.y = position.y;
	}
}
