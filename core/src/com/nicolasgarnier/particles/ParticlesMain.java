package com.nicolasgarnier.particles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ParticlesMain extends ApplicationAdapter {

  private JaipurGame jaipurGame;
  
  @Override
	public void create () {
    jaipurGame = new JaipurGame();
    /*this.model = new JaipurModel();
    this.controller = new JaipurController(model);
    this.jaipurBoard = new JaipurView(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), spriteBatch, model);
    this.controller.setView(this.jaipurBoard);*/
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		jaipurGame.render();
		//jaipurBoard.render();
	}
	
	@Override
	public void resize(int width, int height) {
	  jaipurGame.resize(width, height);
	  /*this.jaipurBoard.resize(width, height);
	  Matrix4 matrix = new Matrix4();
	  matrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	  this.spriteBatch.setProjectionMatrix(matrix);
	  super.resize(width, height);*/
	}
	
	@Override
	public void dispose () {
	  jaipurGame.dispose();
	  /*spriteBatch.dispose();
	  jaipurBoard.dispose();*/
	}
}
