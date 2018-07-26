package com.nicolasgarnier.particles.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.nicolasgarnier.particles.Constants;
import com.nicolasgarnier.particles.model.JaipurModel;
import com.nicolasgarnier.particles.view.Board;

public class JaipurController {
  
  private JaipurModel model;
  private Board view;
  private InputProcessor inputProcessor;
  
  public JaipurController(final JaipurModel model) {
    this.model = model;
    this.model.startGame();
  }
  
  public void setView(final Board view) {
    this.view = view;
    initEventListener();
  }
  
  private void initEventListener() {
    this.inputProcessor = new InputProcessor() {
      
      @Override
      public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
      }
      
      @Override
      public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
      }
      
      @Override
      public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        
        int screenHeight = Gdx.graphics.getHeight();
        int x = screenX;
        int y = screenHeight - screenY;
        
        if (!model.roundOver && !model.gameOver) {
          // Check if a cur player good card has been clicked on
          int cardID = view.curPlayerCardClicked(x, y);
          if (cardID != -1) {
            boolean oldValue = model.playerCardsSelected.get(model.playerTurn).get(cardID);
            oldValue ^= true;
            model.playerCardsSelected.get(model.playerTurn).set(cardID, oldValue);
            model.unselectAllMarketCamels();
            view.recomputeSpritesPositions();
            model.reevaluateAvailableActions();
            return true;
          }
          
          // Check if a market card has been clicked on
          int marketCardID = view.marketCardClicked(x, y);
          if (marketCardID != -1) {
            if (model.market.get(marketCardID) == Constants.SPECIAL_CAMELS) {
              model.unselectAllCurPlayerCards();
              model.unselectAllMarketCards();
              if (!model.marketCamelsSelected) {
                model.selectAllMarketCamels();
              } else {
                model.unselectAllMarketCamels();
              }
            } else {
              boolean oldValue = model.marketSelected.get(marketCardID);
              oldValue ^= true;
              model.marketSelected.set(marketCardID, oldValue);
              model.unselectAllMarketCamels();
            }
            view.recomputeSpritesPositions();
            model.reevaluateAvailableActions();
            return true;
          }
          
          // Check if the curPlayer camels have been clicked on
          if (view.curPlayerCamelsClicked(x, y)) {
            model.playerCamelsSelected ^= true;
            if (!model.playerCamelsSelected) model.nbPlayerCamelsSelected = 0;
            else model.nbPlayerCamelsSelected = 1;
            view.recomputeSpritesPositions();
            model.reevaluateAvailableActions();
            return true;
          }
          
          // Check if the curPlayer camels buttons have been clicked on, iif the cur player camels
          // are active
          if (model.playerCamelsSelected) {
            int camelsButton = view.curPlayerCamelsButtonClicked(x, y);
            if (camelsButton != -1) {
              if (camelsButton == 0) {
                if (model.nbPlayerCamelsSelected > 1) model.nbPlayerCamelsSelected--;
              } else if (camelsButton == 1) {
                if (model.nbPlayerCamelsSelected != model.playerCamels.get(model.playerTurn)) model.nbPlayerCamelsSelected++;
              }
              view.recomputeSpritesPositions();
              model.reevaluateAvailableActions();
              return true;
            }
          }
          
          // Check if the get button has been clicked
          // Doing that only if applicable !
          if (model.readyToGetCards) {
            int actionButton = view.actionButtonClicked(x, y);
            if (actionButton == 1) {
              model.getCards();
              model.nextPlayer();
              view.recomputeSpritesPositions();
              model.reevaluateAvailableActions();
              return true;
            }
          }
          
          // Check if the exchange button has been clicked
          // Doing that only if applicable !
          if (model.readyToExchangeCards) {
            int actionButton = view.actionButtonClicked(x, y);
            if (actionButton == 2) {
              model.exchangeCards();
              model.nextPlayer();
              view.recomputeSpritesPositions();
              model.reevaluateAvailableActions();
              return true;
            }
          }
          
          // Check if the sell button has been clicked
          // Doing that only if applicable !
          if (model.readyToSellCards) {
            int actionButton = view.actionButtonClicked(x, y);
            if (actionButton == 0) {
              model.sellCards();
              if (model.threeGoodsMissing()) model.roundOver = true;
              if (!model.roundOver) {
                model.nextPlayer();
                view.recomputeSpritesPositions();
                model.reevaluateAvailableActions();
                return true;
              }
            }
          }
          
          return true;
        } else if (!model.gameOver) {
          // Check if the continue button has been clicked
          if (view.continueButtonClicked(x, y)) {
            if (model.playerScore.get(0) >= model.playerScore.get(1)) model.playerRounds.set(0, model.playerRounds.get(0) + 1);
            if (model.playerScore.get(1) >= model.playerScore.get(0)) model.playerRounds.set(1, model.playerRounds.get(1) + 1);
            if (model.playerRounds.get(0) != 2 && model.playerRounds.get(1) != 2) {
              model.startRound();
              view.recomputeSpritesPositions();
              model.reevaluateAvailableActions();
            } else {
              model.gameOver = true;
              view.recomputeSpritesPositions();
            }
            return true;
          }
        } else {
          // Check if the replay button has been clicked
          if (view.replayButtonClicked(x, y)) {
            model.startGame();
            view.recomputeSpritesPositions();
            model.reevaluateAvailableActions();
            return true;
          }
        }
        
        return true;
      }
      
      @Override
      public boolean scrolled(int amount) {
        return false;
      }
      
      @Override
      public boolean mouseMoved(int screenX, int screenY) {
        return false;
      }
      
      @Override
      public boolean keyUp(int keycode) {
        return false;
      }
      
      @Override
      public boolean keyTyped(char character) {
        return false;
      }
      
      @Override
      public boolean keyDown(int keycode) {
        return false;
      }
    };
    
    Gdx.input.setInputProcessor(inputProcessor);
  }
  
}
