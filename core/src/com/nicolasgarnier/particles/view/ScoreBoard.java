package com.nicolasgarnier.particles.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolasgarnier.particles.Constants;
import com.nicolasgarnier.particles.JaipurGame;
import com.nicolasgarnier.particles.model.JaipurModel;

public class ScoreBoard {
  
  private int originX;
  private int originY;
  private int width;
  private int height;
  
  private final SpriteBatch spriteBatch;
  private final JaipurModel model;
  private final Texture starTexture;
  private final Texture continueTexture;
  private final BitmapFont jaipurFontLarge;
  
  private Box score0;
  private double score0Scale;
  private Box score1;
  private double score1Scale;
  private Box victoryStar0;
  private Box victoryStar1;
  private Box continueButton;
  
  public ScoreBoard(final int originX, final int originY, final int width, final int height, final SpriteBatch spriteBatch, final JaipurModel model, final Texture starTexture, final Texture continueTexture, final BitmapFont jaipurFontLarge) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    this.spriteBatch = spriteBatch;
    this.model = model;
    this.starTexture = starTexture;
    this.continueTexture = continueTexture;
    this.jaipurFontLarge = jaipurFontLarge;
    computeBoxes();
  }
  
  public void resetPos(final int originX, final int originY, final int width, final int height) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    computeBoxes();
  }
  
  public void render() {
    jaipurFontLarge.getData().setScale((float) score0Scale);
    jaipurFontLarge.draw(spriteBatch, "Score : " + Integer.toString(model.playerScore.get(JaipurGame.playerID)), (float) score0.originX, (float) score0.originY);
    jaipurFontLarge.getData().setScale(1.0f);
    
    jaipurFontLarge.getData().setScale((float) score1Scale);
    jaipurFontLarge.draw(spriteBatch, "Score : " + Integer.toString(model.playerScore.get(1 - JaipurGame.playerID)), (float) score1.originX, (float) score1.originY);
    jaipurFontLarge.getData().setScale(1.0f);
    
    if (model.playerScore.get(JaipurGame.playerID) >= model.playerScore.get(1 - JaipurGame.playerID)) {
      spriteBatch.draw(starTexture, (float) victoryStar0.originX, (float) victoryStar0.originY, (float) victoryStar0.width, (float) victoryStar0.height, 0, 0, Constants.ICONS_TEXTURE_WIDTH, Constants.ICONS_TEXTURE_HEIGHT, false, false);
    }
    if (model.playerScore.get(1 - JaipurGame.playerID) >= model.playerScore.get(JaipurGame.playerID)) {
      spriteBatch.draw(starTexture, (float) victoryStar1.originX, (float) victoryStar1.originY, (float) victoryStar1.width, (float) victoryStar1.height, 0, 0, Constants.ICONS_TEXTURE_WIDTH, Constants.ICONS_TEXTURE_HEIGHT, false, false);
    }
    
    spriteBatch.draw(continueTexture, (float) continueButton.originX, (float) continueButton.originY, (float) continueButton.width, (float) continueButton.height, 0, 0, Constants.ICONS_TEXTURE_WIDTH, Constants.ICONS_TEXTURE_HEIGHT, false, false);
  }
  
  public boolean continueButtonClicked(final int x, final int y) {
    return continueButton.contains(x, y);
  }
  
  public void computeBoxes() {
    double scoreTextBoxHeight = this.height / 10.0;
    
    GlyphLayout layoutScore0 = new GlyphLayout(jaipurFontLarge, "Score : " + Integer.toString(model.playerScore.get(0)));
    double layoutScore0Height = layoutScore0.height;
    score0Scale = scoreTextBoxHeight / layoutScore0Height;
    
    GlyphLayout layoutScore1 = new GlyphLayout(jaipurFontLarge, "Score : " + Integer.toString(model.playerScore.get(1)));
    double layoutScore1Height = layoutScore1.height;
    score1Scale = scoreTextBoxHeight / layoutScore1Height;
    
    score0 = new Box(originX + (width - layoutScore0.width * score0Scale) / 2.0, originY + height / 2.0 - height / 10.0, layoutScore0.width * score0Scale, layoutScore0.height * score0Scale);
    score1 = new Box(originX + (width - layoutScore1.width * score1Scale) / 2.0, originY + height / 2.0 + height / 5.0, layoutScore1.width * score1Scale, layoutScore1.height * score1Scale);
    
    victoryStar0 = new Box(originX + (width - scoreTextBoxHeight) / 2.0, originY + 1.0 * scoreTextBoxHeight, scoreTextBoxHeight, scoreTextBoxHeight);
    victoryStar1 = new Box(originX + (width - scoreTextBoxHeight) / 2.0, originY + 8.0 * scoreTextBoxHeight, scoreTextBoxHeight, scoreTextBoxHeight);
    
    continueButton = new Box(originX + width - 2.0 * scoreTextBoxHeight, originY + height / 2.0 - scoreTextBoxHeight, 2 * scoreTextBoxHeight, 2 * scoreTextBoxHeight);
  }
  
}
