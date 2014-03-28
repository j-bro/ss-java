/**
 * Base class for an entity.
 * Includes fields for position, dimensions, and hitbox.
 * Includes abstract update method.
 */
package com.asdf.ssjava.entities;

/**
 * @author Jeremy Brown
 *
 */

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
   
public abstract class AbstractEntity {
	
	/**
	 * The entity's position
	 */
	protected Vector2 position;
	
	/**
	 * The entity's width in pixels
	 */
	protected float width;
	
	/**
	 * The entity's height in pixels
	 */
	protected float height;
	
	/**
	 * The entity's hitbox
	 */
	protected transient Rectangle hitbox;
	
	/**
	 * The entity's health levels. One point (integer) is equivalent to half a heart in gameplay.
	 */
	protected transient int health;
	
	/**
	 * Creates an entity
	 */
	protected AbstractEntity(Vector2 position, float width, float height) {
		this.position = position;
		this.width = width;
		this.height = height;
		hitbox = new Rectangle(position.x, position.y, width, height);
	}
	
	/**
	 * Returns the entity's position
	 * @return the position of the entity
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Sets the entity's position
	 * @deprecated use the getPosition method to obtain the instance of position and set the x, y variables
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	/**
	 * Returns the entity's width
	 * @return the width of the entity
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Sets the entity's width
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	
	/**
	 * Returns the entity's height
	 * @return the height of the entity
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Sets the entity's height
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/**
	 * Returns the entity's hitbox
	 * @return the hitbox of the entity
	 */
	public Rectangle getHitbox() {
		return hitbox;
	}
	/**
	 * Sets the entity's hitbox
	 * @deprecated use the getHitbox() method to obtain the instance of hitbox and set the x, y variables
	 * @param hitbox the hitbox to set
	 */
	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
	
	public int getHealth() {
		return health;
	}
	
	public synchronized void setHealth(int health) {
		this.health = health;
	}
	
	public synchronized void healthChange(int increment) {
		health += increment;
	}
	
	public abstract void die();
}
    