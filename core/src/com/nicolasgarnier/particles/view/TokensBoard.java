package com.nicolasgarnier.particles.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolasgarnier.particles.Constants;
import com.nicolasgarnier.particles.model.JaipurModel;

public class TokensBoard {

  private int originX;
  private int originY;
  private int width;
  private int height;
  
  List<List<Box>> tokensBoxes = new ArrayList<List<Box>>();
  
  private final SpriteBatch spriteBatch;
  private final JaipurModel model;
  
  private final Texture tokensTexture;
  
  public TokensBoard(final int originX, final int originY, final int width, final int height, final SpriteBatch spriteBatch, final JaipurModel model, final Texture tokensTexture) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    this.spriteBatch = spriteBatch;
    this.model = model;
    this.tokensTexture = tokensTexture;
    for (int i = 0; i < Constants.GOODS.length; ++i) tokensBoxes.add(new ArrayList<Box>());
    computeTokensBoxes();
  }
  
  public void resetPos(final int originX, final int originY, final int width, final int height) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    computeTokensBoxes();
  }
  
  public void render() {
    for (int i = 0; i < model.goodTokens.size(); ++i) {
      List<Integer> remainingTokens = model.goodTokens.get(i);
      List<Box> boxes = tokensBoxes.get(i);
      for (int j = 0; j < remainingTokens.size(); ++j) {
        int value = remainingTokens.get(j);
        Box curBox = boxes.get(j);
        spriteBatch.draw(tokensTexture, (float) curBox.originX, (float) curBox.originY, (float) curBox.width, (float) curBox.height, Constants.TOKEN_TEXTURE_WIDTH * (value - 1), Constants.TOKEN_TEXTURE_HEIGHT * i, Constants.TOKEN_TEXTURE_WIDTH, Constants.TOKEN_TEXTURE_HEIGHT, false, false);
      }
    }
  }
  
  public void computeTokensBoxes() {
    double actualHeight = 0.9 * height;
    double actualWidth = 0.9 * width;
    
    double maxHeight = actualHeight / 6.0;
    double maxWidth = actualWidth / 5.0;
    
    double finalSize = (maxHeight < maxWidth) ? maxHeight : maxWidth;
    
    for (int kind : Constants.GOODS) {
      tokensBoxes.get(kind).clear();
      for (int i = 0; i < model.goodTokens.get(kind).size(); ++i) {
        tokensBoxes.get(kind).add(new Box(0.05 * width + i * ((actualWidth - finalSize) / 8.0), 0.05 * height + kind * (actualHeight / 6.0), finalSize, finalSize));
      }
    }
  }
  
}
