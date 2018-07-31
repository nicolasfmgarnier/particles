package com.nicolasgarnier.particles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ParticlesMain extends ApplicationAdapter {

  private JaipurGame jaipurGame;
  
  @Override
	public void create () {
    jaipurGame = new JaipurGame();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		jaipurGame.render();
	}
	
	@Override
	public void resize(int width, int height) {
	  jaipurGame.resize(width, height);
	}
	
	@Override
	public void dispose () {
	  jaipurGame.dispose();
	}
}
