package com.nicolasgarnier.particles.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nicolasgarnier.particles.Constants;

public class JaipurModel implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  // Market zone
  public List<Integer> market = new ArrayList<Integer>();
  public List<Boolean> marketSelected = new ArrayList<Boolean>();
  public List<Integer> deck = new ArrayList<Integer>();
  
  // Tokens zone
  public List<List<Integer>> goodTokens = new ArrayList<List<Integer>>(); 
  public List<Integer> rewardTokens123 = new ArrayList<Integer>();
  public List<Integer> rewardTokens456 = new ArrayList<Integer>();
  public List<Integer> rewardTokens789 = new ArrayList<Integer>();
  
  // Players
  public List<List<Integer>> playerCards = new ArrayList<List<Integer>>();
  public List<List<Boolean>> playerCardsSelected = new ArrayList<List<Boolean>>();
  public List<Integer> playerCamels = new ArrayList<Integer>();
  public List<Integer> playerScore = new ArrayList<Integer>();
  public List<Integer> playerRounds = new ArrayList<Integer>();
  public List<List<List<Integer>>> playerGoodsTokens = new ArrayList<List<List<Integer>>>();
  public List<List<Integer>> playerTokens123 = new ArrayList<List<Integer>>();
  public List<List<Integer>> playerTokens456 = new ArrayList<List<Integer>>();
  public List<List<Integer>> playerTokens789 = new ArrayList<List<Integer>>();
  
  // General status
  public boolean ready = false;
  public int playerTurn;
  public boolean marketCamelsSelected;
  public boolean readyToGetCards;
  public boolean readyToExchangeCards;
  public boolean readyToSellCards;
  public boolean playerCamelsSelected;
  public int nbPlayerCamelsSelected;
  public boolean roundOver;
  public boolean gameOver;
  public int lastToStart;
  
  // Copy internal values
  public void copy(final JaipurModel model) {
    this.market = model.market;
    this.marketSelected = model.marketSelected;
    this.deck = model.deck;
    this.goodTokens = model.goodTokens;
    this.rewardTokens123 = model.rewardTokens123;
    this.rewardTokens456 = model.rewardTokens456;
    this.rewardTokens789 = model.rewardTokens789;
    this.playerCards = model.playerCards;
    this.playerCardsSelected = model.playerCardsSelected;
    this.playerCamels = model.playerCamels;
    this.playerScore = model.playerScore;
    this.playerRounds = model.playerRounds;
    this.playerGoodsTokens = model.playerGoodsTokens;
    this.playerTokens123 = model.playerTokens123;
    this.playerTokens456 = model.playerTokens456;
    this.playerTokens789 = model.playerTokens789;
    this.ready = model.ready;
    this.playerTurn = model.playerTurn;
    this.marketCamelsSelected = model.marketCamelsSelected;
    this.readyToGetCards = model.readyToGetCards;
    this.readyToExchangeCards = model.readyToExchangeCards;
    this.readyToSellCards = model.readyToSellCards;
    this.nbPlayerCamelsSelected = model.nbPlayerCamelsSelected;
    this.roundOver = model.roundOver;
    this.gameOver = model.gameOver;
    this.lastToStart = model.lastToStart;
  }
  
  // Start game/round
  public void startGame() {
    ready = true;
    lastToStart = -1;
    playerRounds.clear();
    playerRounds.add(0);
    playerRounds.add(0);
    startRound();
  }
  
  public void startRound() {
    // Resetting all the Lists
    market.clear();
    marketSelected.clear();
    deck.clear();
    goodTokens.clear(); 
    rewardTokens123.clear();
    rewardTokens456.clear();
    rewardTokens789.clear();
    playerCards.clear();
    playerCardsSelected.clear();
    playerCamels.clear();
    playerScore.clear();
    playerGoodsTokens.clear();
    playerTokens123.clear();
    playerTokens456.clear();
    playerTokens789.clear();
    
    // Generating the deck
    deck = generateDeck();
    
    // Extracting the 5 first cards to put it in the market
    for (int i = 0; i < 5; ++i) {
      market.add(deck.get(deck.size() - 1));
      marketSelected.add(false);
      deck.remove(deck.size() - 1);
    }
    
    // Extracting the 10 following cards to deal to the players
    for (int playerID = 0; playerID < 2; ++playerID) {
      List<Integer> playerCardsTmp = new ArrayList<Integer>();
      List<Boolean> playerCardsSelectedTmp = new ArrayList<Boolean>();
      int playerCamelsTmp = 0;
      for (int i = 0; i < 5; ++i) {
        int nextCard = deck.get(deck.size() - 1);
        if (nextCard == Constants.SPECIAL_CAMELS) playerCamelsTmp++;
        else {
          playerCardsTmp.add(nextCard);
          playerCardsSelectedTmp.add(false);
        }
        deck.remove(deck.size() - 1);
      }
      playerCards.add(playerCardsTmp);
      playerCardsSelected.add(playerCardsSelectedTmp);
      playerCamels.add(playerCamelsTmp);
    }
    
    // Initializing the bonus tokens stacks
    for (int i = 0; i < Constants.INIT_BONUS_TOKENS[0].length; ++i) {
      rewardTokens123.add(Constants.INIT_BONUS_TOKENS[0][i]);
    }
    for (int i = 0; i < Constants.INIT_BONUS_TOKENS[1].length; ++i) {
      rewardTokens456.add(Constants.INIT_BONUS_TOKENS[1][i]);
    }
    for (int i = 0; i < Constants.INIT_BONUS_TOKENS[2].length; ++i) {
      rewardTokens789.add(Constants.INIT_BONUS_TOKENS[2][i]);
    }
    Collections.shuffle(rewardTokens123);
    Collections.shuffle(rewardTokens456);
    Collections.shuffle(rewardTokens789);
    
    // Setting the scores to 0
    playerScore.add(0);
    playerScore.add(0);
    
    // Initializing the goods tokens
    for (int[] values : Constants.INIT_GOODS_TOKENS) {
      List<Integer> curValuesList = new ArrayList<Integer>();
      for (int value : values) {
        curValuesList.add(value);
      }
      goodTokens.add(curValuesList);
    }
    
    // Initializing the players goods tokens
    for (int i = 0; i < 2; ++i) {
      List<List<Integer>> curPlayerTokens = new ArrayList<List<Integer>>();
      for (int j = 0; j < Constants.GOODS.length; ++j) {
        curPlayerTokens.add(new ArrayList<Integer>());
      }
      playerGoodsTokens.add(curPlayerTokens);
    }
    
    // Initializing the players bonus tokens
    playerTokens123.add(new ArrayList<Integer>());
    playerTokens123.add(new ArrayList<Integer>());
    playerTokens456.add(new ArrayList<Integer>());
    playerTokens456.add(new ArrayList<Integer>());
    playerTokens789.add(new ArrayList<Integer>());
    playerTokens789.add(new ArrayList<Integer>());
    
    // Deciding who is going to play first
    if (lastToStart == -1) {
      playerTurn = (int) (2 * Math.random());
      lastToStart = playerTurn;
    } else if (lastToStart == 0) {
      playerTurn = 1;
      lastToStart = 1;
    } else {
      playerTurn = 0;
      lastToStart = 0;
    }
    
    // Market camels are unselected
    marketCamelsSelected = false;
    
    // No action is ready yet
    readyToGetCards = false;
    readyToExchangeCards = false;
    readyToSellCards = false;
    
    // The game is not over yet...
    roundOver = false;
    gameOver = false;
  }
  
  private List<Integer> generateDeck() {
    List<Integer> ret = new ArrayList<Integer>();
    for (int i = 0; i < Constants.TOTAL_NB_DIAMONDS; ++i) {
      ret.add(Constants.GOOD_DIAMONDS);
    }
    for (int i = 0; i < Constants.TOTAL_NB_GOLD; ++i) {
      ret.add(Constants.GOOD_GOLD);
    }
    for (int i = 0; i < Constants.TOTAL_NB_SILVER; ++i) {
      ret.add(Constants.GOOD_SILVER);
    }
    for (int i = 0; i < Constants.TOTAL_NB_CLOTHES; ++i) {
      ret.add(Constants.GOOD_CLOTHES);
    }
    for (int i = 0; i < Constants.TOTAL_NB_SPICES; ++i) {
      ret.add(Constants.GOOD_SPICES);
    }
    for (int i = 0; i < Constants.TOTAL_NB_LEATHER; ++i) {
      ret.add(Constants.GOOD_LEATHER);
    }
    for (int i = 0; i < Constants.TOTAL_NB_CAMELS; ++i) {
      ret.add(Constants.SPECIAL_CAMELS);
    }
    Collections.shuffle(ret);
    
    return ret;
  }
  
  // Utils
  public void unselectAllCurPlayerCards() {
    for (int i = 0; i < playerCardsSelected.get(playerTurn).size(); ++i) {
      playerCardsSelected.get(playerTurn).set(i, false);
    }
  }
  
  public void unselectAllMarketCards() {
    for (int i = 0; i < marketSelected.size(); ++i) {
      marketSelected.set(i, false);
    }
  }
  
  public void selectAllMarketCamels() {
    for (int i = 0; i < marketSelected.size(); ++i) {
      if (market.get(i) == Constants.SPECIAL_CAMELS) {
        marketSelected.set(i, true);
      }
    }
    marketCamelsSelected = true;
  }
  
  public void unselectAllMarketCamels() {
    for (int i = 0; i < marketSelected.size(); ++i) {
      if (market.get(i) == Constants.SPECIAL_CAMELS) {
        marketSelected.set(i, false);
      }
    }
    marketCamelsSelected = false;
  }
  
  public boolean isACardSelectedInCurPlayerHand() {
    for (int i = 0; i < playerCardsSelected.get(playerTurn).size(); ++i) {
      if (playerCardsSelected.get(playerTurn).get(i)) return true;
    }
    return false;
  }
  
  public boolean isASingleCardSelectedInMarket() {
    boolean atLeastOne = false;
    for (int i = 0; i < marketSelected.size(); ++i) {
      if (marketSelected.get(i)) {
        if (!atLeastOne) atLeastOne = true;
        else return false;
      }
    }
    if (!atLeastOne) return false;
    else return true;
  }
  
  public int getSingleCardSelectedInMarketKind() {
    for (int i = 0; i < marketSelected.size(); ++i) {
      if (marketSelected.get(i)) {
        return market.get(i);
      }
    }
    return -1;
  }
  
  public int nbSelectedCardsInMarket() {
    int ret = 0;
    for (boolean b : marketSelected) {
      if (b) ret++;
    }
    return ret;
  }
  
  public int nbSelectedCardsInCurPlayerHand() {
    int ret = 0;
    for (boolean b : playerCardsSelected.get(playerTurn)) {
      if (b) ret++;
    }
    return ret;
  }
  
  public int nbTokensPlayer(final int playerID) {
    int ret = 0;
    List<List<Integer>> curPlayerTokens = playerGoodsTokens.get(playerID);
    for (List<Integer> curGoodTokens : curPlayerTokens) ret += curGoodTokens.size();
    ret += playerTokens123.get(playerID).size();
    ret += playerTokens456.get(playerID).size();
    ret += playerTokens789.get(playerID).size();
    return ret;
  }
  
  public boolean threeGoodsMissing() {
    int nbMissingGoods = 0;
    for (List<Integer> remaining : goodTokens) {
      if (remaining.isEmpty()) nbMissingGoods++;
    }
    if (nbMissingGoods >= 3) return true;
    else return false;
  }
  
  public boolean similarSelectedCardsInCurPlayerHand() {
    int kind = -1;
    for (int i = 0; i < playerCardsSelected.get(playerTurn).size(); ++i) {
      boolean b = playerCardsSelected.get(playerTurn).get(i);
      if (b) {
        if (kind == -1) {
          kind = playerCards.get(playerTurn).get(i);
        } else {
          if (kind != playerCards.get(playerTurn).get(i)) return false;
        }
      }
    }
    return true;
  }
  
  public int getKindOfFirstSelectedCardInCurPlayerHand() {
    int kind = -1;
    for (int i = 0; i < playerCardsSelected.get(playerTurn).size(); ++i) {
      boolean b = playerCardsSelected.get(playerTurn).get(i);
      if (b) {
        return playerCards.get(playerTurn).get(i);
      }
    }
    return kind;
  }
  
  public boolean areExchangedCardsDifferent() {
    boolean[] marketSelectedCards = new boolean[6];
    for (int i = 0; i < 6; ++i) marketSelectedCards[i] = false;
    for (int i = 0; i < marketSelected.size(); ++i) {
      if (marketSelected.get(i)) {
        marketSelectedCards[market.get(i)] |= true;
      }
    }
    for (int i = 0; i < playerCardsSelected.get(playerTurn).size(); ++i) {
      if (playerCardsSelected.get(playerTurn).get(i)) {
        if (marketSelectedCards[playerCards.get(playerTurn).get(i)]) return false;
      }
    }
    return true;
  }
  
  public String getCards() {
    String ret = "Opponent ";
    if (marketCamelsSelected) {
      ret += "took the camels from the market.";
      for (int i = 0; i < marketSelected.size(); ++i) {
        if (marketSelected.get(i)) {
          if (deck.size() != 0) {
            playerCamels.set(playerTurn, playerCamels.get(playerTurn) + 1);
            market.set(i, deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
          } else {
            roundOver = true;
          }
        }
      }
    } else {
      int kindOfSelectedCard = getSingleCardSelectedInMarketKind();
      ret += "took a single " + Constants.GOODS_NAMES[kindOfSelectedCard] + " card form the market.";
      for (int i = 0; i < marketSelected.size(); ++i) {
        if (marketSelected.get(i)) {
          if (deck.size() != 0) {
            playerCards.get(playerTurn).add(market.get(i));
            playerCardsSelected.get(playerTurn).add(false);
            market.set(i, deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
          } else {
            roundOver = true;
          }
        }
      }
    }
    return ret;
  }
  
  public String exchangeCards() {
    boolean curPlayerSelectedOver = false;
    int curPlayerSelectedCurIdx = 0;
    int[] marketSelectedIDs = {0, 0, 0, 0, 0, 0};
    int[] handSelectedIDs = {0, 0, 0, 0, 0, 0, 0};
    String ret = "Opponent ";
    for (int i = 0; i < marketSelected.size(); ++i) {
      if (marketSelected.get(i)) {
        marketSelectedIDs[market.get(i)]++;
        if (curPlayerSelectedOver) {
          handSelectedIDs[Constants.SPECIAL_CAMELS]++;
          int marketCardToExchange = market.get(i);
          market.set(i, Constants.SPECIAL_CAMELS);
          playerCamels.set(playerTurn, playerCamels.get(playerTurn) - 1);
          playerCards.get(playerTurn).add(marketCardToExchange);
          playerCardsSelected.get(playerTurn).add(false);
        } else {
          boolean found = false;
          for (; curPlayerSelectedCurIdx < playerCardsSelected.get(playerTurn).size(); ++curPlayerSelectedCurIdx) {
            if (playerCardsSelected.get(playerTurn).get(curPlayerSelectedCurIdx)) {
              found = true;
              break;
            }
          }
          if (found) {
            handSelectedIDs[playerCards.get(playerTurn).get(curPlayerSelectedCurIdx)]++;
            int marketCardToExchange = market.get(i);
            market.set(i, playerCards.get(playerTurn).get(curPlayerSelectedCurIdx));
            playerCards.get(playerTurn).set(curPlayerSelectedCurIdx, marketCardToExchange);
            curPlayerSelectedCurIdx++;
          } else {
            handSelectedIDs[Constants.SPECIAL_CAMELS]++;
            int marketCardToExchange = market.get(i);
            market.set(i, Constants.SPECIAL_CAMELS);
            playerCamels.set(playerTurn, playerCamels.get(playerTurn) - 1);
            playerCards.get(playerTurn).add(marketCardToExchange);
            playerCardsSelected.get(playerTurn).add(false);
          }
        }
      }
    }
    
    // Writing the log message
    ret += "exchanged ";
    for (int i = 0; i < handSelectedIDs.length; ++i) {
      if (handSelectedIDs[i] != 0) {
        if (i == Constants.SPECIAL_CAMELS) {
          ret += handSelectedIDs[i] + " " + Constants.SPECIAL_CAMELS_NAME + " ";
        } else {
          ret += handSelectedIDs[i] + " " + Constants.GOODS_NAMES[i] + " ";
        }
      }
    }
    ret += "from his hand with ";
    for (int i = 0; i < marketSelectedIDs.length; ++i) {
      if (marketSelectedIDs[i] != 0) {
        ret += marketSelectedIDs[i] + " " + Constants.GOODS_NAMES[i] + " ";
      }
    }
    ret += "from the market.";
    
    return ret;
  }
  
  public String sellCards() {
    int cardsToSellID = -1;
    int nbCardsToSell = 0;
    String ret = "Opponent ";
    for (int i = 0; i < playerCardsSelected.get(playerTurn).size(); ++i) {
      if (playerCardsSelected.get(playerTurn).get(i)) {
        cardsToSellID = playerCards.get(playerTurn).get(i);
        nbCardsToSell++;
      }
    }
    
    int curScore = playerScore.get(playerTurn);
    for (int i = 0; i < nbCardsToSell; ++i) {
      if (goodTokens.get(cardsToSellID).size() != 0) {
        curScore += goodTokens.get(cardsToSellID).get(goodTokens.get(cardsToSellID).size() - 1);
        playerGoodsTokens.get(playerTurn).get(cardsToSellID).add(goodTokens.get(cardsToSellID).get(goodTokens.get(cardsToSellID).size() - 1));
        goodTokens.get(cardsToSellID).remove(goodTokens.get(cardsToSellID).size() - 1);
      }
    }
    
    List<Integer> newHand = new ArrayList<Integer>();
    List<Boolean> newCardsSelected = new ArrayList<Boolean>();
    for (int i = 0; i < playerCardsSelected.get(playerTurn).size(); ++i) {
      if (!playerCardsSelected.get(playerTurn).get(i)) {
        newHand.add(playerCards.get(playerTurn).get(i));
        newCardsSelected.add(false);
      }
    }
    playerCards.set(playerTurn, newHand);
    playerCardsSelected.set(playerTurn, newCardsSelected);
    
    if (nbCardsToSell == 3) {
      if (!rewardTokens123.isEmpty()) {
        playerTokens123.get(playerTurn).add(rewardTokens123.get(rewardTokens123.size() - 1));
        curScore += rewardTokens123.get(rewardTokens123.size() - 1);
        rewardTokens123.remove(rewardTokens123.size() - 1);
      }
    } else if (nbCardsToSell == 4) {
      if (!rewardTokens456.isEmpty()) {
        playerTokens456.get(playerTurn).add(rewardTokens456.get(rewardTokens456.size() - 1));
        curScore += rewardTokens456.get(rewardTokens456.size() - 1);
        rewardTokens456.remove(rewardTokens456.size() - 1);
      }
    } else if (nbCardsToSell >= 5) {
      if (!rewardTokens789.isEmpty()) {
        playerTokens789.get(playerTurn).add(rewardTokens789.get(rewardTokens789.size() - 1));
        curScore += rewardTokens789.get(rewardTokens789.size() - 1);
        rewardTokens789.remove(rewardTokens789.size() - 1);
      }
    }
    
    playerScore.set(playerTurn, curScore);
    
    ret += " sold " + nbCardsToSell + " " + Constants.GOODS_NAMES[cardsToSellID] + " cards.";
    return ret;
  }
  
  public void nextPlayer() {
    for (int i = 0; i < marketSelected.size(); ++i) {
      marketSelected.set(i, false);
    }
    for (int i = 0; i < playerCardsSelected.get(playerTurn).size(); ++i) {
      playerCardsSelected.get(playerTurn).set(i, false);
    }
    marketCamelsSelected = false;
    readyToGetCards = false;;
    readyToExchangeCards = false;
    readyToSellCards = false;
    playerCamelsSelected = false;
    nbPlayerCamelsSelected = 0;
    playerTurn = 1 - playerTurn;
  }
  
  public void reevaluateAvailableActions() {
    // Get action
    if (marketCamelsSelected) {
      readyToGetCards = true;
    } else {
      if (isACardSelectedInCurPlayerHand()) readyToGetCards = false;
      else {
        if (isASingleCardSelectedInMarket()) {
          if (playerCards.get(playerTurn).size() < Constants.MAXIMUM_CARDS_IN_HAND) readyToGetCards = true;
          else readyToGetCards = false;
        } else {
          readyToGetCards = false;
        }
      }
    }
    
    // Exchange action
    if (marketCamelsSelected) {
      readyToExchangeCards = false;
    } else {
      int nbSelectedMarket = nbSelectedCardsInMarket();
      if (nbSelectedMarket < 2) {
        readyToExchangeCards = false;
      } else {
        int nbSelectedPlayer = nbSelectedCardsInCurPlayerHand() + nbPlayerCamelsSelected;
        if (nbSelectedMarket != nbSelectedPlayer) {
          readyToExchangeCards = false;
        } else {
          if (areExchangedCardsDifferent()) {
            // When camels are involved into the exchange, the player makes his hand grow
            // We should check he will not get more than Constants.MAXIMUM_CARDS_IN_HAND cards
            // in his hand after the exchange
            if (playerCards.get(playerTurn).size() - nbSelectedCardsInCurPlayerHand() + nbSelectedCardsInMarket() <= Constants.MAXIMUM_CARDS_IN_HAND) {
              readyToExchangeCards = true;
            } else {
              readyToExchangeCards = false;
            }
          }
          else readyToExchangeCards = false;
        }
      }
    }
    
    // Sell action
    int nbSelectedPlayer = nbSelectedCardsInCurPlayerHand();
    if (nbSelectedPlayer == 0) {
      readyToSellCards = false;
    } else {
      if (!similarSelectedCardsInCurPlayerHand()) {
        readyToSellCards = false;
      } else {
        int kind = getKindOfFirstSelectedCardInCurPlayerHand();
        if (nbSelectedPlayer == 1) {
          if (kind == Constants.GOOD_DIAMONDS || kind == Constants.GOOD_GOLD || kind == Constants.GOOD_SILVER) {
            readyToSellCards = false;
          } else {
            readyToSellCards = true;
          }
        } else {
          readyToSellCards = true;
        }
      }
    }
  }
}
