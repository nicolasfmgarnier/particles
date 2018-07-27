package com.nicolasgarnier.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.nicolasgarnier.particles.controller.JaipurController;
import com.nicolasgarnier.particles.model.JaipurModel;
import com.nicolasgarnier.particles.view.JaipurView;

public class JaipurGame {
  
  private SpriteBatch spriteBatch;

  private MenuStage menuStage;
  private JaipurView jaipurBoard;
  private JaipurController controller;
  private JaipurModel model;
  
  private final Texture bgdTexture;
  
  private final BitmapFont jaipurFont;
  
  public JaipurGame() {
    this.spriteBatch = new SpriteBatch();
    this.bgdTexture = new Texture(Gdx.files.internal("pool_table.png"));
    bgdTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    this.jaipurFont = new BitmapFont(Gdx.files.internal("jaipur_font_small.fnt"));
    
    menuStage = new MenuStage(jaipurFont);
    model = new JaipurModel();
    controller = new JaipurController(model, menuStage);
    jaipurBoard = null;
  }
  
  public void render() {
    spriteBatch.begin();
    spriteBatch.draw(bgdTexture, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    spriteBatch.end();
    if (model.ready) {
      if (jaipurBoard == null) jaipurBoard = new JaipurView(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), spriteBatch, model);
      jaipurBoard.render();
    }
    else menuStage.draw();
  }
  
  public void resize(final int width, final int height) {
    menuStage.getViewport().update(width, height);
    Matrix4 matrix = new Matrix4();
    matrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.spriteBatch.setProjectionMatrix(matrix);
  }
  
  public void dispose() {
    bgdTexture.dispose();
    jaipurFont.dispose();
    spriteBatch.dispose();
  }
  
}