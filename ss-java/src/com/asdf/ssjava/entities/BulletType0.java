/**
 * Concrete definition of a bullet to model a certain look and behaviour.
 * Behaviour is defined here and look in the WorldRenderer class.
 * This bullet will be used by the ship.
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
	public final int TYPE = 0;
	
	/**
	 * The bullet's default velocity
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(50, 0);
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 * @param shooter
	 */
	public BulletType0(Vector2 position, float width, float height,
			float rotation, AbstractEntity shooter) {
		super(position, width, height, rotation, shooter);
		damage = 1;
		hitbox.x = position.x + 0.85f;
		hitbox.y = position.y + 0.8f;
		hitbox.width = 1.35f;
		hitbox.height = 0.3f;
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Bullet#getType()
	 */
	@Override
	public int getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Bullet#update()
	 */
	@Override
	public void update() {
		super.update();
		hitbox.x = position.x + 0.85f;
		hitbox.y = position.y + 0.8f;
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
		return "Bullet Type 0";
	}
	
}
