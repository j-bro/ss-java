/**
 * 
 */
package com.asdf.ssjava.entities;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 *
 */

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
   
public abstract class AbstractEntity {
	
	/**
	 * 
	 */
	protected Vector2 position;
	
	/**
	 * 
	 */
	protected float width;
	
	/**
	 * 
	 */
	protected float height;
	
	/**
	 * 
	 */
	protected Rectangle hitbox;
	
	/**
	 * 
	 */
	public AbstractEntity(Vector2 position, float width, float height) {
		this.position = position;
		this.width = width;
		this.height = height;
		hitbox = new Rectangle(position.x, position.y, width, height);
	}
	
	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	
	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/**
	 * @return the hitbox
	 */
	public Rectangle getHitbox() {
		return hitbox;
	}
	/**
	 * @param hitbox the hitbox to set
	 */
	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
}
    