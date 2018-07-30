package com.nicolasgarnier.particles.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolasgarnier.particles.Constants;
import com.nicolasgarnier.particles.model.JaipurModel;

public class PlayerOpponentBoard {

  private int originX;
  private int originY;
  private int width;
  private int height;
  
  private int playerID; // 0 or 1
  
  private List<Box> playerCards = new ArrayList<Box>();
  private List<Box> playerCamels = new ArrayList<Box>();
  
  private Box scoreBox;
  private double scoreFontScale;
  private Box roundWon;
  
  private List<Box> playerTokens = new ArrayList<Box>();
  
  private final SpriteBatch spriteBatch;
  private final JaipurModel model;
  
  private final Texture cardsTexture;
  private final Texture tokensTexture;
  private final Texture bonusTokensTexture;
  private final Texture starTexture;
  
  private final BitmapFont jaipurFont;
  
  public PlayerOpponentBoard(final int originX, final int originY, final int width, final int height, final int playerID, final SpriteBatch spriteBatch, final JaipurModel model, final Texture cardsTexture, final Texture tokensTexture, final Texture bonusTokensTexture, final Texture camelsActionsButtonsTexture, final Texture starTexture, final BitmapFont jaipurFont) {
    this.originX = originX;
    this.originY = originY;
    this.width = width;
    this.height = height;
    this.playerID = playerID;
    this.spriteBatch = spriteBatch;
    this.model = model;
    this.cardsTexture = cardsTexture;
    this.tokensTexture = tokensTexture;
    this.bonusTokensTexture = bonusTokensTexture;
    this.starTexture = starTexture;
    this.jaipurFont = jaipurFont;
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
    for (int i = 0; i < playerCards.size(); ++i) {
      Box curBox = playerCards.get(i);
      spriteBatch.draw(cardsTexture, (float) curBox.originX, (float) curBox.originY, (float) curBox.width, (float) curBox.height, Constants.CARD_TEXTURE_WIDTH * Constants.SPECIAL_VERSO, 0, Constants.CARD_TEXTURE_WIDTH, Constants.CARD_TEXTURE_HEIGHT, false, false);
    }
    for (Box curBox : playerCamels) {
      spriteBatch.draw(cardsTexture, (float) curBox.originX, (float) curBox.originY, (float) curBox.width, (float) curBox.height, Constants.CARD_TEXTURE_WIDTH * Constants.SPECIAL_CAMELS, 0, Constants.CARD_TEXTURE_WIDTH, Constants.CARD_TEXTURE_HEIGHT, false, false);
    }
    jaipurFont.getData().setScale((float) scoreFontScale);
    jaipurFont.draw(spriteBatch, Integer.toString(model.playerScore.get(playerID)), (float) scoreBox.originX, (float) scoreBox.originY);
    jaipurFont.getData().setScale(1.0f);
    List<List<Integer>> curPlayerGoodTokens = model.playerGoodsTokens.get(playerID);
    int lastBoxIdx = 0;
    for (int goodID = 0; goodID < curPlayerGoodTokens.size(); ++goodID) {
      List<Integer> values = curPlayerGoodTokens.get(goodID);
      for (int curVal : values) {
        Box curBox = playerTokens.get(lastBoxIdx);
        spriteBatch.draw(tokensTexture, (float) curBox.originX, (float) curBox.originY, (float) curBox.width, (float) curBox.height, Constants.TOKEN_TEXTURE_WIDTH * (curVal - 1), Constants.TOKEN_TEXTURE_HEIGHT * goodID, Constants.TOKEN_TEXTURE_WIDTH, Constants.TOKEN_TEXTURE_HEIGHT, false, false);
        lastBoxIdx++;
      }
    }
    for (int i = 0; i < model.playerTokens123.get(playerID).size(); ++i) {
      Box curBox = playerTokens.get(lastBoxIdx);
      spriteBatch.draw(bonusTokensTexture, (float) curBox.originX, (float) curBox.originY, (float) curBox.width, (float) curBox.height, 0, 0, Constants.TOKEN_TEXTURE_WIDTH, Constants.TOKEN_TEXTURE_HEIGHT, false, false);
      lastBoxIdx++;
    }
    for (int i = 0; i < model.playerTokens456.get(playerID).size(); ++i) {
      Box curBox = playerTokens.get(lastBoxIdx);
      spriteBatch.draw(bonusTokensTexture, (float) curBox.originX, (float) curBox.originY, (float) curBox.width, (float) curBox.height, Constants.TOKEN_TEXTURE_WIDTH, 0, Constants.TOKEN_TEXTURE_WIDTH, Constants.TOKEN_TEXTURE_HEIGHT, false, false);
      lastBoxIdx++;
    }
    for (int i = 0; i < model.playerTokens789.get(playerID).size(); ++i) {
      Box curBox = playerTokens.get(lastBoxIdx);
      spriteBatch.draw(bonusTokensTexture, (float) curBox.originX, (float) curBox.originY, (float) curBox.width, (float) curBox.height, 2 * Constants.TOKEN_TEXTURE_WIDTH, 0, Constants.TOKEN_TEXTURE_WIDTH, Constants.TOKEN_TEXTURE_HEIGHT, false, false);
      lastBoxIdx++;
    }
    if (model.playerRounds.get(playerID) != 0) {
      spriteBatch.draw(starTexture, (float) roundWon.originX, (float) roundWon.originY, (float) roundWon.width, (float) roundWon.height, 0, 0, Constants.ICONS_TEXTURE_WIDTH, Constants.ICONS_TEXTURE_HEIGHT, false, false);
    }
  }
  
  public void computeBoxes() {
    computeCardsBoxes();
    computeScoreBoxes();
  }
  
  private void computeCardsBoxes() {
    double actualHeight = 0.9 * height;
    double actualWidth = 0.70 * width;
    
    double maxWidth = (0.9 * actualWidth) / 8.9;
    double maxHeight = actualHeight;
    
    if (maxHeight * Constants.CARD_RATIO > maxWidth) {
      maxHeight = maxWidth / Constants.CARD_RATIO;
    } else {
      maxWidth = maxHeight * Constants.CARD_RATIO;
    }
    
    playerCards.clear();
    for (int i = 0; i < model.playerCards.get(playerID).size(); ++i) {
      playerCards.add(new Box(originX + 0.025 * width + i * maxWidth, originY + (height - maxHeight) / 2.0, maxWidth, maxHeight));
    }
    
    playerCamels.clear();
    for (int i = 0; i < model.playerCamels.get(playerID); ++i) {
      playerCamels.add(new Box(originX + 0.025 * width + 7 * maxWidth + 0.1 * actualWidth + i * maxWidth / 10.0, originY + (height - maxHeight) / 2.0, maxWidth, maxHeight));
    }
  }
  
  private void computeScoreBoxes() {
    GlyphLayout layout = new GlyphLayout(jaipurFont, Integer.toString(model.playerScore.get(playerID)));
    double actualHeight = 0.5 * height;
    double actualWidth = 0.3 * width;
    
    if (layout.height > actualHeight) scoreFontScale = actualHeight / layout.height;
    else scoreFontScale = 1.0;
    
    scoreBox = new Box(originX + width - layout.width * scoreFontScale - 0.05 * width, originY + layout.height * scoreFontScale + 0.05 * height, actualWidth * scoreFontScale, actualHeight * scoreFontScale);
    
    int nbTokens = model.nbTokensPlayer(playerID);
    playerTokens.clear();
    double step = 0.3 * width / nbTokens;
    for (int i = 0; i < nbTokens; ++i) {
      playerTokens.add(new Box(originX + 0.7 * width + step / 2.0 + i * step - actualHeight / 2.0, originY + actualHeight, actualHeight, actualHeight));
    }
    
    roundWon = new Box(originX + width - layout.width * scoreFontScale - 0.05 * width - layout.height * scoreFontScale, originY + 0.05 * height, layout.height * scoreFontScale, layout.height * scoreFontScale);
  }
  
}
