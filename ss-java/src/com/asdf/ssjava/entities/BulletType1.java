/**
 * Concrete definition of a bullet to model a certain look and behaviour.
 * Behaviour is defined here and look in the WorldRenderer class.
 * This bullet will be used by enemies (type 1).
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Jeremy Brown
 * 
 */
public class BulletType1 extends Bullet {

	/**
	 * The type of bullet
	 */
	public final int TYPE = 1;
	
	/**
	 * The bullet's default velocity
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(20, 0);
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public BulletType1(Vector2 position, float width, float height,
			float rotation, World world, AbstractEntity shooter) {
		super(position, width, height, rotation, world, shooter);
		damage = 1;
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

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Bullet#getShooter()
	 */
	@Override
	public AbstractEntity getShooter() {
		return shooter;
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Bullet#getDamage()
	 */
	@Override
	public int getDamage() {
		return damage;
	}
	
	/**
	 * @return the object's description string
	 */
	public String toString() {
		return "Bullet Type 1";
	}
	
}
