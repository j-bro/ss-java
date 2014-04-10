/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Bullet;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * 
 * @author Jeremy Brown
 *
 */
public class GameCollisionListener implements ContactListener {

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#beginContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		// Get colliding bodies from contact points
		Body body1 = contact.getFixtureA().getBody();
		Body body2 = contact.getFixtureB().getBody();
		
		// TODO fix disappearing bullets
		if (body1.getUserData() instanceof Bullet) {			
			bulletImpact((Bullet) body1.getUserData(), (AbstractEntity) body2.getUserData());
			System.out.println("b1 is bullet, b2 is " + body2.getUserData().toString());
			
		}
		else if (body2.getUserData() instanceof Bullet) {
			bulletImpact((Bullet) body2.getUserData(), (AbstractEntity) body1.getUserData());
			System.out.println("b2 is bullet, b1 is " + body1.getUserData().toString());
		}
		
		
		
	}
	
	/**
	 * 
	 */
	public void bulletImpact(Bullet b, AbstractEntity e) {
		e.healthChange((-1) * b.getDamage()); 
		b.healthChange(-1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
