package com.nicolasgarnier.particles;

public class Constants {
  public static int TOTAL_NB_DIAMONDS = 6;
  public static int TOTAL_NB_GOLD = 6;
  public static int TOTAL_NB_SILVER = 6;
  public static int TOTAL_NB_CLOTHES = 8;
  public static int TOTAL_NB_SPICES = 8;
  public static int TOTAL_NB_LEATHER = 10;
  public static int TOTAL_NB_CAMELS = 11;

  public static int GOOD_DIAMONDS = 0;
  public static int GOOD_GOLD = 1;
  public static int GOOD_SILVER = 2;
  public static int GOOD_CLOTHES = 3;
  public static int GOOD_SPICES = 4;
  public static int GOOD_LEATHER = 5;
  public static int SPECIAL_CAMELS = 6;
  public static int SPECIAL_VERSO = 7;
  
  public static int[] GOODS = {GOOD_DIAMONDS, GOOD_GOLD, GOOD_SILVER, GOOD_CLOTHES, GOOD_SPICES, GOOD_LEATHER};
  
  public static String[] GOODS_NAMES = {"Diamonds",
                                        "Gold",
                                        "Silver",
                                        "Clothes",
                                        "Spices",
                                        "Leather"};
  
  public static String SPECIAL_CAMELS_NAME = "Camels";
  
  public static int[][] INIT_GOODS_TOKENS = {{5, 5, 5, 7, 7},
                                             {5, 5, 5, 6, 6},
                                             {5, 5, 5, 5, 5},
                                             {1, 1, 2, 2, 3, 3, 5},
                                             {1, 1, 2, 2, 3, 3, 5},
                                             {1, 1, 1, 1, 1, 1, 2, 3, 4}};
  /*public static int[][] INIT_GOODS_TOKENS = {{5},
                                             {5},
                                             {5},
                                             {1},
                                             {1},
                                             {1}};*/
  
  public static int[][] INIT_BONUS_TOKENS = {{1, 1, 2, 2, 2, 3, 3},
                                             {4, 4, 5, 5, 6, 6},
                                             {8, 8, 9, 10, 10}};
  
  public static double CARD_RATIO = 64.0 / 89.0;
  public static int CARD_TEXTURE_WIDTH = 102;
  public static int CARD_TEXTURE_HEIGHT = 140;
  public static int TOKEN_TEXTURE_WIDTH = 65;
  public static int TOKEN_TEXTURE_HEIGHT = 65;
  public static int ICONS_TEXTURE_WIDTH = 128;
  public static int ICONS_TEXTURE_HEIGHT = 128;
  
  public static int MAXIMUM_CARDS_IN_HAND = 7;
  
}
