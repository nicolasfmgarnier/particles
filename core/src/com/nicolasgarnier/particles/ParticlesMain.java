package com.nicolasgarnier.particles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.nicolasgarnier.particles.controller.JaipurController;
import com.nicolasgarnier.particles.model.JaipurModel;
import com.nicolasgarnier.particles.view.Board;

public class ParticlesMain extends ApplicationAdapter {

  private Board jaipurBoard;
  private JaipurController controller;
  private JaipurModel model;
  private SpriteBatch spriteBatch;
  
  @Override
	public void create () {
    this.model = new JaipurModel();
    this.controller = new JaipurController(model);
    this.spriteBatch = new SpriteBatch();
    this.jaipurBoard = new Board(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), spriteBatch, model);
    this.controller.setView(this.jaipurBoard);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		jaipurBoard.render();
	}
	
	@Override
	public void resize(int width, int height) {
	  this.jaipurBoard.resize(width, height);
	  Matrix4 matrix = new Matrix4();
	  matrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	  this.spriteBatch.setProjectionMatrix(matrix);
	  super.resize(width, height);
	}
	
	@Override
	public void dispose () {
	  spriteBatch.dispose();
	  jaipurBoard.dispose();
	}
}
