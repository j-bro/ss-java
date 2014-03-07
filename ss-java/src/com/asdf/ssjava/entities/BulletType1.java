/**
 * Concrete definition of a bullet to model a certain look and behaviour.
 * Behaviour is defined here and look in the WorldRenderer class.
 * This bullet will be used by enemies (type 1).
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * 
 */
public class BulletType1 extends Bullet {

	/**
	 * The type of bullet
	 */
	final int type = 1;
	
	/**
	 * The bullet's default velocity
	 */
	private final Vector2 DEFAULT_VELOCITY = new Vector2(20, 0);
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public BulletType1(Vector2 position, float width, float height,
			float rotation, AbstractEntity shooter) {
		super(position, width, height, rotation, shooter);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Bullet#getType()
	 */
	@Override
	public int getType() {
		return type;
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Bullet#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}
}
