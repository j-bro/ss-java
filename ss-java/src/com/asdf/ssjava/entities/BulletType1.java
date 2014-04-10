/**
 * Concrete definition of a bullet to model a certain look and behaviour.
 * Behaviour is defined here and look in the WorldRenderer class.
 * This bullet will be used by enemies (type 1).
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
	 * The damage dealt to an entity that collides with this bullet
	 */
	public static final int DEFAULT_DAMAGE = 1;
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public BulletType1(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world, AbstractEntity shooter) {
		super(position, width, height, rotation, gameWorld, world, shooter);
		damage = DEFAULT_DAMAGE;
		
		createFixtureDef();
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

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#createFixtureDef()
	 */
	@Override
	public void createFixtureDef() {
		// TODO Box2D stuff
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.1f;
		
		body.createFixture(fixtureDef);

	}
	
}
