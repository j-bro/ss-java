/**
* 
*/
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 *
 */

public abstract class MoveableEntity extends AbstractEntity {
	protected Vector2 velocity;
	protected float SPEED;
	protected float rotation;
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param SPEED
	 * @param rotation
	 */
	public MoveableEntity(Vector2 position, float width, float height, float SPEED, float rotation) {
		super(position, width, height);
		this.SPEED = SPEED;
		this.rotation = rotation;
		velocity = new Vector2(0, 0);
	}
	
	public abstract void advance(float  delta);
	
	/**
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void update() {
		hitbox.x = position.x;
		hitbox.y = position.y;
	}
}
