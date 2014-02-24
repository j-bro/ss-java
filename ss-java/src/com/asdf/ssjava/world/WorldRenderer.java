package com.asdf.ssjava.world;

import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {

	World world;
	SpriteBatch batch;
	Ship ship;
	OrthographicCamera cam;
	Texture shipTexture;
	
	/**
	 * 
	 */
	public WorldRenderer(World world) {
		this.world = world;
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		shipTexture = new Texture("data/ship.png");
	}
	
	/**
	 * 
	 */
	public void render() {  
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);
		
		ship = world.getShip();
		batch.begin();
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y);
		batch.end();
	}

	/**
	 * 
	 */
	public void dispose() {
		batch.dispose();
		shipTexture.dispose();
	}
}
