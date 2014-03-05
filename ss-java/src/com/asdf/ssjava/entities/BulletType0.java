/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * 
 */
public class BulletType0 extends Bullet {

	/**
	 * The type of bullet
	 */
	final int type = 0;
	
	/**
	 * The bullet's default velocity
	 */
	public final Vector2 DEFAULT_VELOCITY = new Vector2(30, 0);
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 * @param type
	 */
	public BulletType0(Vector2 position, float width, float height,
			float rotation) {
		super(position, width, height, rotation);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Bullet#getType()
	 */
	@Override
	public int getType() {
		return type;
	}
}
