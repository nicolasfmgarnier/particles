package com.nicolasgarnier.particles.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolasgarnier.particles.Constants;
import com.nicolasgarnier.particles.model.JaipurModel;

public class GameOverBoard {
  
  private int originX;
  private int originY;
  private int width;
  private int height;
  
  private final SpriteBatch spriteBatch;
  private final JaipurModel model;
  private final Texture replayTexture;
  private final BitmapFont jaipurFontLarge;
  
  private Box gameOverMsg;
  private double gameOverMsgScale;
  private Box replayButton;
  
  public GameOverBoard(final int originX, final int originY, final int width, final int height, final SpriteBatch spriteBatch, final JaipurModel model, final Texture replayTexture, final BitmapFont jaipurFontLarge) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    this.spriteBatch = spriteBatch;
    this.model = model;
    this.replayTexture = replayTexture;
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
    int winnerID = -1;
    if (model.playerRounds.get(0) == 2 && model.playerRounds.get(1) != 2) winnerID = 1;
    else if (model.playerRounds.get(0) != 2 && model.playerRounds.get(1) == 2) winnerID = 2;
    
    String msg = (winnerID == -1) ? "Draw game !" : "Player " + winnerID + " won the game !";
    
    jaipurFontLarge.getData().setScale((float) gameOverMsgScale);
    jaipurFontLarge.draw(spriteBatch, msg, (float) gameOverMsg.originX, (float) gameOverMsg.originY);
    jaipurFontLarge.getData().setScale(1.0f);
    
    spriteBatch.draw(replayTexture, (float) replayButton.originX, (float) replayButton.originY, (float) replayButton.width, (float) replayButton.height, 0, 0, Constants.ICONS_TEXTURE_WIDTH, Constants.ICONS_TEXTURE_HEIGHT, false, false);
  }
  
  public boolean replayButtonClicked(final int x, final int y) {
    return replayButton.contains(x, y);
  }
  
  public void computeBoxes() {
    double msgTextBoxHeight = this.height / 10.0;
    
    int winnerID = -1;
    if (model.playerRounds.get(0) == 2 && model.playerRounds.get(1) != 2) winnerID = 1;
    else if (model.playerRounds.get(0) != 2 && model.playerRounds.get(1) == 2) winnerID = 2;
    
    GlyphLayout layoutGameOverMsg = (winnerID == -1) ? new GlyphLayout(jaipurFontLarge, "Draw game !") : new GlyphLayout(jaipurFontLarge, "Player " + winnerID + " won the game !");
    double layoutGameOverMsgHeight = layoutGameOverMsg.height;
    gameOverMsgScale = msgTextBoxHeight / layoutGameOverMsgHeight;
    
    if (layoutGameOverMsg.width * gameOverMsgScale > width) gameOverMsgScale = width / layoutGameOverMsg.width;
    
    gameOverMsg = new Box(originX + (width - layoutGameOverMsg.width * gameOverMsgScale) / 2.0, originY + height / 2.0 + height / 5.0, layoutGameOverMsg.width * gameOverMsgScale, layoutGameOverMsg.height * gameOverMsgScale);
    
    replayButton = new Box(originX + (width - 2 * msgTextBoxHeight) / 2.0, originY + 2.0 * msgTextBoxHeight, 2.0 * msgTextBoxHeight, 2.0 * msgTextBoxHeight);
  }

}
