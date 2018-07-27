package com.nicolasgarnier.particles.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    //computeBoxes();
  }
  
}
