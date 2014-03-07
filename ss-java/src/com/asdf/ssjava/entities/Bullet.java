/**
 * Entity model for a bullet.
 * Adds starting velocity which can be in any direction, but will most likely be in that of the ship's velocity OR only horizontal.
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * 
 */
public abstract class Bullet extends MoveableEntity {
	
	/**
	 * The type of bullet
	 */
	int type;
	
	/**
	 * The bullet's default velocity
	 */
	Vector2 DEFAULT_VELOCITY;
	
	/**
	 * @param position the position of the bullet
	 * @param width the width of the bullet
	 * @param height the height of the bullet
	 * @param rotation the rotation of the bullet in degrees
	 */
	public Bullet(Vector2 position, float width, float height, float rotation) {
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
	
	public abstract int getType();	
	public abstract Vector2 getDEFAULT_VELOCITY();
	
}
