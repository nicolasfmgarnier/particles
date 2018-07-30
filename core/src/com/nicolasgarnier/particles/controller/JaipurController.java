package com.nicolasgarnier.particles.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nicolasgarnier.particles.Constants;
import com.nicolasgarnier.particles.JaipurClient;
import com.nicolasgarnier.particles.JaipurClientServerMessage;
import com.nicolasgarnier.particles.JaipurGame;
import com.nicolasgarnier.particles.JaipurServer;
import com.nicolasgarnier.particles.MenuStage;
import com.nicolasgarnier.particles.model.JaipurModel;
import com.nicolasgarnier.particles.view.JaipurView;

public class JaipurController {
  
  private MenuStage menu;
  private JaipurModel model;
  private JaipurView view;
  private InputProcessor inputProcessor;
  private String serverIP;
  private int serverPort;
  
  private Socket clientSocket;
  
  public JaipurController(final JaipurModel model, final MenuStage menu) {
    this.menu = menu;
    this.model = model;
    initMenuListeners();
  }
  
  private void initMenuListeners() {
    menu.getCreateGameButton().addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        try {
          JaipurGame.playerID = 0;
          
          serverIP = InetAddress.getLocalHost().getHostAddress();
          serverPort = 22000;
          
          // Launching the server
          Thread serverThread = new Thread(new JaipurServer(serverPort, serverPort + 1), "JaipurServer");
          serverThread.start();
          
          // Creating the socket
          clientSocket = new Socket(serverIP, serverPort);
          
          // Getting the model
          Thread clientThread = new Thread(new JaipurClient(model, clientSocket, null), "JaipurClient");
          clientThread.start();
        } catch (UnknownHostException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    menu.getJoinGameButton().addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        try {
          JaipurGame.playerID = 1;
          
          //serverIP = menu.getIPToConnectTextField().getMessageText();
          serverIP = "192.168.205.57";
          serverPort = 22001;
          
          // Creating the socket
          clientSocket = new Socket(serverIP, serverPort);
          
          // Getting the model
          Thread clientThread = new Thread(new JaipurClient(model, clientSocket, null), "JaipurClient");
          clientThread.start();
        } catch (UnknownHostException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  public void setView(final JaipurView view) {
    // We start by running a ClientThread if the curPlayer is not me (to wait for the updated model)
    this.view = view;
    if (JaipurGame.playerID != model.playerTurn) waitForOpponentAction();
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
          if (JaipurGame.playerID == model.playerTurn) {
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
                String turnDescription = model.getCards();
                if (model.roundOver) {
                  sendUpdatedModel(null);
                } else {
                  model.nextPlayer();
                  view.recomputeSpritesPositions();
                  model.reevaluateAvailableActions();
                  sendUpdatedModel(turnDescription);
                  waitForOpponentAction();
                  return true;
                }
              }
            }
            
            // Check if the exchange button has been clicked
            // Doing that only if applicable !
            if (model.readyToExchangeCards) {
              int actionButton = view.actionButtonClicked(x, y);
              if (actionButton == 2) {
                String turnDescription = model.exchangeCards();
                model.nextPlayer();
                view.recomputeSpritesPositions();
                model.reevaluateAvailableActions();
                sendUpdatedModel(turnDescription);
                waitForOpponentAction();
                return true;
              }
            }
            
            // Check if the sell button has been clicked
            // Doing that only if applicable !
            if (model.readyToSellCards) {
              int actionButton = view.actionButtonClicked(x, y);
              if (actionButton == 0) {
                String turnDescription = model.sellCards();
                if (model.threeGoodsMissing()) {
                  model.roundOver = true;
                  sendUpdatedModel(null);
                }
                if (!model.roundOver) {
                  model.nextPlayer();
                  view.recomputeSpritesPositions();
                  model.reevaluateAvailableActions();
                  sendUpdatedModel(turnDescription);
                  waitForOpponentAction();
                  return true;
                }
              }
            }
            
            return true;
          }
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
  
  private void sendUpdatedModel(final String logMsg) {
    try {
      ObjectOutputStream oos1 = new ObjectOutputStream(clientSocket.getOutputStream());
      JaipurClientServerMessage msg = new JaipurClientServerMessage(model, logMsg);
      oos1.writeObject(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void waitForOpponentAction() {
    Thread clientThread = new Thread(new JaipurClient(model, clientSocket, view), "JaipurClient");
    clientThread.start();
  }
  
}
