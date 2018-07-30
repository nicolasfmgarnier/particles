package com.nicolasgarnier.particles.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolasgarnier.particles.JaipurGame;
import com.nicolasgarnier.particles.model.JaipurModel;

public class JaipurView {

  private int width;
  private int height;
  
  private TokensBoard tokensBoard;
  private MarketBoard marketBoard;
  private PlayerSelfBoard playerSelfBoard;
  private PlayerOpponentBoard playerOpponentBoard;
  private ActionBoard actionBoard;
  private ScoreBoard scoreBoard;
  private GameOverBoard gameOverBoard;
  
  private final SpriteBatch spriteBatch;
  private final JaipurModel model;
  
  private final Texture cardsTexture;
  private final Texture tokensTexture;
  private final Texture bonusTokensTexture;
  private final Texture sellTexture;
  private final Texture getTexture;
  private final Texture exchangeTexture;
  private final Texture camelsActionsButtonsTexture;
  private final Texture starTexture;
  private final Texture continueTexture;
  private final Texture replayTexture;
  
  private final BitmapFont jaipurFont;
  private final BitmapFont jaipurFontLarge;
  
  public boolean readyToRender = true;
  
  public JaipurView(final int width, final int height, final SpriteBatch spriteBatch, final JaipurModel model) {
    this.width = width;
    this.height = height;
    this.spriteBatch = spriteBatch;
    this.model = model;
    this.cardsTexture = new Texture(Gdx.files.internal("cards.png"));
    this.tokensTexture = new Texture(Gdx.files.internal("tokens_goods.png"));
    this.bonusTokensTexture = new Texture(Gdx.files.internal("tokens_bonus.png"));
    this.sellTexture = new Texture(Gdx.files.internal("coins_icons.png"));
    this.getTexture = new Texture(Gdx.files.internal("take_icons.png"));
    this.exchangeTexture = new Texture(Gdx.files.internal("transfer_icons.png"));
    this.camelsActionsButtonsTexture = new Texture(Gdx.files.internal("camels_action_buttons.png"));
    this.starTexture = new Texture(Gdx.files.internal("star.png"));
    this.continueTexture = new Texture(Gdx.files.internal("continue.png"));
    this.replayTexture = new Texture(Gdx.files.internal("replay.png"));
    this.jaipurFont = new BitmapFont(Gdx.files.internal("jaipur_font_small.fnt"));
    this.jaipurFontLarge = new BitmapFont(Gdx.files.internal("jaipur_font_large.fnt"));
    createSubBoards();
  }
  
  public void render() {
    spriteBatch.begin();
    if (!model.roundOver && !model.gameOver) {
      if (readyToRender) {
        if (JaipurGame.playerID != model.playerTurn) Gdx.graphics.setTitle("Jaipur - Waiting for opponent action...");
        else Gdx.graphics.setTitle("Jaipur - Your turn to play !");
        tokensBoard.render();
        marketBoard.render();
        playerSelfBoard.render();
        playerOpponentBoard.render();
        actionBoard.render();
      }
    } else if (!model.gameOver) {
      scoreBoard.render();
    } else {
      gameOverBoard.render();
    }
    spriteBatch.end();
  }
  
  public void dispose() {
    cardsTexture.dispose();
    tokensTexture.dispose();
    bonusTokensTexture.dispose();
    sellTexture.dispose();
    getTexture.dispose();
    exchangeTexture.dispose();
    camelsActionsButtonsTexture.dispose();
    starTexture.dispose();
    replayTexture.dispose();
    jaipurFont.dispose();
    jaipurFontLarge.dispose();
  }
  
  public void resize(final int width, final int height) {
    this.width = width;
    this.height = height;
    recomputeSubBoards();
  }
  
  public int curPlayerCardClicked(final int x, final int y) {
    return playerSelfBoard.playerCardClicked(x, y);
  }
  
  public int marketCardClicked(final int x, final int y) {
    return marketBoard.marketCardClicked(x, y);
  }
  
  public boolean curPlayerCamelsClicked(final int x, final int y) {
    return playerSelfBoard.playerCamelsClicked(x, y);
  }
  
  public int curPlayerCamelsButtonClicked(final int x, final int y) {
    return playerSelfBoard.playerCamelsButtonClicked(x, y);
  }
  
  public int actionButtonClicked(final int x, final int y) {
    return actionBoard.actionButtonClicked(x, y);
  }
  
  public boolean continueButtonClicked(final int x, final int y) {
    return scoreBoard.continueButtonClicked(x, y);
  }
  
  public boolean replayButtonClicked(final int x, final int y) {
    return gameOverBoard.replayButtonClicked(x, y);
  }
  
  public void recomputeSpritesPositions() {
    if (!model.roundOver && !model.gameOver) {
      tokensBoard.computeTokensBoxes();
      marketBoard.computeCardsBoxes();
      playerSelfBoard.computeBoxes();
      playerOpponentBoard.computeBoxes();
      actionBoard.computeButtonsBoxes();
    } else if (!model.gameOver) {
      scoreBoard.computeBoxes();
    } else {
      gameOverBoard.computeBoxes();
    }
  }
  
  private void createSubBoards() {
    // TOKENS BOARD
    tokensBoard = new TokensBoard(0, 0, (int) (0.3 * this.width), this.height, this.spriteBatch, this.model, this.tokensTexture);
    
    // SELF PLAYER BOARD
    playerSelfBoard = new PlayerSelfBoard((int) (0.3 * this.width), 0, this.width - ((int) (0.3 * this.width)), (int) (0.25 * this.height), JaipurGame.playerID, this.spriteBatch, this.model, this.cardsTexture, this.tokensTexture, this.bonusTokensTexture, this.camelsActionsButtonsTexture, this.starTexture, this.jaipurFont);
    
    // OPPOSITE PLAYER BOARD
    playerOpponentBoard = new PlayerOpponentBoard((int) (0.3 * this.width), (int) (0.75 * this.height), this.width - ((int) (0.3 * this.width)), (int) (0.25 * this.height), 1 - JaipurGame.playerID, this.spriteBatch, this.model, this.cardsTexture, this.tokensTexture, this.bonusTokensTexture, this.camelsActionsButtonsTexture, this.starTexture, this.jaipurFont);
    
    // MARKET BOARD
    marketBoard = new MarketBoard((int) (0.3 * this.width), (int) (0.25 * this.height), this.width - ((int) (0.3 * this.width)), (int) (0.5 * this.height), this.spriteBatch, this.model, this.cardsTexture, this.jaipurFont);
    
    // ACTION BOARD
    actionBoard = new ActionBoard((int) (0.2 * this.width), 0, (int) (0.1 * this.width), (int) (0.25 * this.height), this.spriteBatch, this.model, this.sellTexture, this.getTexture, this.exchangeTexture);
    
    // SCORE BOARD
    scoreBoard = new ScoreBoard(0, 0, this.width, this.height, this.spriteBatch, this.model, this.starTexture, this.continueTexture, this.jaipurFontLarge);
    
    // GAME OVER BOARD
    gameOverBoard = new GameOverBoard(0, 0, this.width, this.height, this.spriteBatch, this.model, this.replayTexture, this.jaipurFontLarge);
  }
  
  private void recomputeSubBoards() {
    // TOKENS BOARD
    tokensBoard.resetPos(0, 0, (int) (0.3 * this.width), this.height);
    
    // SELF PLAYER BOARD
    playerSelfBoard.resetPos((int) (0.3 * this.width), 0, this.width - ((int) (0.3 * this.width)), (int) (0.25 * this.height));
    
    // OPPOSITE PLAYER BOARD
    playerOpponentBoard.resetPos((int) (0.3 * this.width), (int) (0.75 * this.height), this.width - ((int) (0.3 * this.width)), (int) (0.25 * this.height));
    
    // MARKET BOARD
    marketBoard.resetPos((int) (0.3 * this.width), (int) (0.25 * this.height), this.width - ((int) (0.3 * this.width)), (int) (0.5 * this.height));
    
    // ACTION BOARD
    actionBoard.resetPos((int) (0.2 * this.width), 0, (int) (0.1 * this.width), (int) (0.25 * this.height));
    
    // SCORE BOARD
    scoreBoard.resetPos(0, 0, this.width, this.height);
    
    // GAME OVER BOARD
    gameOverBoard.resetPos(0, 0, this.width, this.height);
  }
  
}
