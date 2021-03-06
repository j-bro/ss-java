package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Concrete definition of a bullet to model a certain look and behaviour. 
 * Behaviour is defined here and look in the WorldRenderer class. 
 * This bullet will be used by the ship. 
 * @author Jeremy Brown
 * @author Simon Thompson
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
	 * The damage dealt to an entity that collides with this bullet
	 */
	public static final int DEFAULT_DAMAGE = 1;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 0;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 0;
	
	/**
	 * Creates a Bullet with the specified parameters
	 * @param position the position of the bullet
	 * @param width the width of the bullet
	 * @param height the height of the bullet
	 * @param rotation the rotation of the bullet in degrees
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance
	 * @param shooter the entity that shot the bullet
	 */
	public BulletType0(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld, AbstractEntity shooter) {
		super(position, width, height, rotation, gameWorld, box2DWorld, shooter);
		damage = DEFAULT_DAMAGE;
		createDef();
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Bullet Type 0";
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#createDef()
	 */
	@Override
	public void createDef() {
		super.createDef();
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.1f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0f;
		fixtureDef.isSensor = true;
		
		body.createFixture(fixtureDef);
		initialize();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#getHitScore()
	 */
	@Override
	public int getHitScore() {
		return HIT_SCORE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#getKillScore()
	 */
	@Override
	public int getKillScore() {
		return KILL_SCORE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#initialize()
	 */
	@Override
	public void initialize() {
		body.setLinearVelocity(DEFAULT_VELOCITY);
		setInitialized(true);
	}
}
