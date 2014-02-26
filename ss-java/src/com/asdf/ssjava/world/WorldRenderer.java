/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

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
	ShapeRenderer sr;
	
	/**
	 * @param world
	 */
	public WorldRenderer(World world) {
		this.world = world;
		
		// TODO potentially fix this square/rectangle
		width = Gdx.graphics.getWidth() / 30;
		height = Gdx.graphics.getHeight() / 30;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		shipTexture = new Texture("data/textures/ship.png");
		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
		
		ship = world.getShip();
		
		sr = new ShapeRenderer();
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
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth() / 2, ship.getHeight() / 2 , ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 0, 0, shipTexture.getWidth(), shipTexture.getHeight(), false, false);
		batch.end();  
		
		
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Line);
		sr.setColor(Color.CYAN);
		sr.rect(ship.getPosition().x, ship.getPosition().y, ship.getWidth(), ship.getHeight());
		sr.end();
		
		
		Gdx.app.log("Ship", "x: " + ship.getPosition().x + " y: " + ship.getPosition().y);
	}

	/**
	 * 
	 */
	public void dispose() {
		batch.dispose();
		shipTexture.dispose();
		sr.dispose();
	}
}
