/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 *
 */

public class WorldRenderer {

	World world;
	SpriteBatch batch;
	Ship ship;
	OrthographicCamera cam;
	Texture shipTexture;
	float width, height;
	
	/**
	 * 
	 */
	public WorldRenderer(World world) {
		this.world = world;
		
		// TODO potentially fix this square/rectangle
		width = Gdx.graphics.getWidth() / 40;
		height = Gdx.graphics.getHeight() / 40;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		shipTexture = new Texture("data/ship.png");
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
		
		ship = world.getShip();
	}
	
	/**
	 * 
	 */
	public void render() {  
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);  
		
		cam.position.set(ship.getPosition().x, ship.getPosition().y, 0);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
			// TODO rotate around origin
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, 0, 0, ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(), shipTexture.getHeight(), false, false);
		batch.end();  
		
		Gdx.app.log("Ship", "x: " + ship.getPosition().x + " y: " + ship.getPosition().y);
	}

	/**
	 * 
	 */
	public void dispose() {
		batch.dispose();
		shipTexture.dispose();
	}
}
