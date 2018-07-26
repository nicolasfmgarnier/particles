package com.nicolasgarnier.particles.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.nicolasgarnier.particles.Constants;
import com.nicolasgarnier.particles.model.JaipurModel;

public class MarketBoard {

  private int originX;
  private int originY;
  private int width;
  private int height;
  
  private Box deck;
  private List<Box> market = new ArrayList<Box>();
  
  private final SpriteBatch spriteBatch;
  private final JaipurModel model;
  
  private final Texture cardsTexture;
  
  private final BitmapFont jaipurFont;
  
  public MarketBoard(final int originX, final int originY, final int width, final int height, final SpriteBatch spriteBatch, final JaipurModel model, final Texture cardsTexture, final BitmapFont jaipurFont) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    this.spriteBatch = spriteBatch;
    this.model = model;
    this.cardsTexture = cardsTexture;
    this.jaipurFont = jaipurFont;
    computeCardsBoxes();
  }
  
  public void resetPos(final int originX, final int originY, final int width, final int height) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    computeCardsBoxes();
  }
  
  public void render() {
    spriteBatch.draw(cardsTexture, (float) deck.originX, (float) deck.originY, (float) deck.width, (float) deck.height, Constants.CARD_TEXTURE_WIDTH * Constants.SPECIAL_VERSO, 0, Constants.CARD_TEXTURE_WIDTH, Constants.CARD_TEXTURE_HEIGHT, false, false);
    for (int i = 0; i < market.size(); ++i) {
      Box curBox = market.get(i);
      spriteBatch.draw(cardsTexture, (float) curBox.originX, (float) curBox.originY, (float) curBox.width, (float) curBox.height, Constants.CARD_TEXTURE_WIDTH * model.market.get(i), 0, Constants.CARD_TEXTURE_WIDTH, Constants.CARD_TEXTURE_HEIGHT, false, false);
    }
    jaipurFont.draw(spriteBatch, Integer.toString(model.deck.size()), (float) deck.originX, (float) (deck.originY + deck.height / 3.0), (float) deck.width, Align.center, true);
  }
  
  public int marketCardClicked(final int x, final int y) {
    for (int i = 0; i < model.market.size(); ++i) {
      if (market.get(i).contains(x, y)) {
        return i;
      }
    }
    return -1;
  }
  
  public void computeCardsBoxes() {
    double actualHeight = 0.9 * height;
    double actualWidth = 0.8 * width;
    
    double maxHeight = actualHeight;
    double maxWidth = actualWidth / 6.5;
    
    if (maxHeight * Constants.CARD_RATIO > maxWidth) {
      maxHeight = maxWidth / Constants.CARD_RATIO;
    } else {
      maxWidth = maxHeight * Constants.CARD_RATIO;
    }
    
    deck = new Box(originX + (actualWidth - (6.5 * maxWidth)) / 2.0, originY + (actualHeight - maxHeight) / 2.0, maxWidth, maxHeight);
    
    market.clear();
    for (int i = 0; i < 5; ++i) {
      double offsetY = (model.marketSelected.get(i)) ? 0.05 * height : 0.0;
      market.add(new Box(originX + (actualWidth - (6.5 * maxWidth)) / 2.0 + maxWidth + maxWidth / 2.0 + maxWidth * i, originY + (actualHeight - maxHeight) / 2.0 + offsetY, maxWidth, maxHeight));
    }
  }
  
}
