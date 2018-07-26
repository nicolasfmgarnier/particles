package com.nicolasgarnier.particles.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolasgarnier.particles.Constants;
import com.nicolasgarnier.particles.model.JaipurModel;

public class ActionBoard {
  
  private int originX;
  private int originY;
  private int width;
  private int height;
  
  private Box sellButton;
  private Box getButton;
  private Box exchangeButton;
  
  private final SpriteBatch spriteBatch;
  private final JaipurModel model;
  
  private final Texture sellTexture;
  private final Texture getTexture;
  private final Texture exchangeTexture;
  
  public ActionBoard(final int originX, final int originY, final int width, final int height, final SpriteBatch spriteBatch, final JaipurModel model, final Texture sellTexture, final Texture getTexture, final Texture exchangeTexture) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    this.spriteBatch = spriteBatch;
    this.model = model;
    this.sellTexture = sellTexture;
    this.getTexture = getTexture;
    this.exchangeTexture = exchangeTexture;
    computeButtonsBoxes();
  }
  
  public void resetPos(final int originX, final int originY, final int width, final int height) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    computeButtonsBoxes();
  }
  
  public void render() {
    int sellIconVersion = (model.readyToSellCards) ? 0 : 1;
    spriteBatch.draw(sellTexture, (float) sellButton.originX, (float) sellButton.originY, (float) sellButton.width, (float) sellButton.height, Constants.ICONS_TEXTURE_WIDTH * sellIconVersion, 0, Constants.ICONS_TEXTURE_WIDTH, Constants.ICONS_TEXTURE_HEIGHT, false, false);
    int getIconVersion = (model.readyToGetCards) ? 0 : 1;
    spriteBatch.draw(getTexture, (float) getButton.originX, (float) getButton.originY, (float) getButton.width, (float) getButton.height, Constants.ICONS_TEXTURE_WIDTH * getIconVersion, 0, Constants.ICONS_TEXTURE_WIDTH, Constants.ICONS_TEXTURE_HEIGHT, false, false);
    int exchangeIconVersion = (model.readyToExchangeCards) ? 0 : 1;
    spriteBatch.draw(exchangeTexture, (float) exchangeButton.originX, (float) exchangeButton.originY, (float) exchangeButton.width, (float) exchangeButton.height, Constants.ICONS_TEXTURE_WIDTH * exchangeIconVersion, 0, Constants.ICONS_TEXTURE_WIDTH, Constants.ICONS_TEXTURE_HEIGHT, false, false);
  }
  
  public int actionButtonClicked(final int x, final int y) {
    if (sellButton.contains(x, y)) return 0;
    else if (getButton.contains(x, y)) return 1;
    else if (exchangeButton.contains(x, y)) return 2;
    else return -1;
  }
  
  public void computeButtonsBoxes() {
    double maxWidth = width;
    double maxHeight = height / 4.0;
    
    double finalSize = (maxHeight < maxWidth) ? maxHeight : maxWidth;
    
    sellButton = new Box(originX + (width - finalSize) / 2.0, originY + (height - 4.0 * finalSize) / 2.0, finalSize, finalSize);
    getButton = new Box(originX + (width - finalSize) / 2.0, originY + (height - 4.0 * finalSize) / 2.0 + 1.5 * finalSize, finalSize, finalSize);
    exchangeButton = new Box(originX + (width - finalSize) / 2.0, originY + (height - 4.0 * finalSize) / 2.0 + 3.0 * finalSize, finalSize, finalSize);
  }
  
}
