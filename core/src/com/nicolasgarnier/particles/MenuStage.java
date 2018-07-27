package com.nicolasgarnier.particles;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuStage extends Stage {
  
  private VerticalGroup uiElmtsGroup;
  private Label myIPLabel;
  private VerticalGroup uiElmtsNoIPGroup;
  private Label instructions1Label;
  private TextButton createGameButton;
  private Label instructions2Label;
  private HorizontalGroup joinGameGroup;
  private Label instructions3Label;
  private TextField ipToConnectTextField;
  private TextButton joinGameButton;
  
  private final BitmapFont jaipurFont;
  
  public MenuStage(final BitmapFont jaipurFont) {
    super(new ScreenViewport());
    this.jaipurFont = jaipurFont;
    createUIElmts();
    Gdx.input.setInputProcessor(this);
  }
  
  private void createUIElmts() {
    try {
      uiElmtsGroup = new VerticalGroup();
      uiElmtsGroup.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      uiElmtsGroup.space(0.2f * Gdx.graphics.getHeight());
      this.addActor(uiElmtsGroup);
    
      InetAddress myIP = InetAddress.getLocalHost();
    
      LabelStyle labelStyle = new LabelStyle();
      labelStyle.font = jaipurFont;
      
      myIPLabel = new Label("Your IP is : " + myIP.getHostAddress(), labelStyle);
      uiElmtsGroup.addActor(myIPLabel);
      
      uiElmtsNoIPGroup = new VerticalGroup();
      uiElmtsNoIPGroup.space(0.05f * Gdx.graphics.getHeight());
      uiElmtsGroup.addActor(uiElmtsNoIPGroup);
      
      instructions1Label = new Label("You can either create a game :", labelStyle);
      uiElmtsNoIPGroup.addActor(instructions1Label);
      
      TextButtonStyle textButtonStyle = new TextButtonStyle();
      textButtonStyle.font = jaipurFont;
      
      createGameButton = new TextButton("Create game", textButtonStyle);
      uiElmtsNoIPGroup.addActor(createGameButton);
      
      instructions2Label = new Label("Or join an existing game :", labelStyle);
      uiElmtsNoIPGroup.addActor(instructions2Label);
      
      joinGameGroup = new HorizontalGroup();
      uiElmtsNoIPGroup.addActor(joinGameGroup);
      
      instructions3Label = new Label("IP to join : ", labelStyle);
      joinGameGroup.addActor(instructions3Label);
      
      TextFieldStyle textFieldStyle = new TextFieldStyle();
      textFieldStyle.font = jaipurFont;
      ipToConnectTextField = new TextField("", textFieldStyle);
      joinGameGroup.addActor(ipToConnectTextField);
      
      joinGameButton = new TextButton("Join game", textButtonStyle);
      joinGameGroup.addActor(joinGameButton);
    
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }
  
  public TextButton getCreateGameButton() {
    return createGameButton;
  }
  
  public TextButton getJoinGameButton() {
    return joinGameButton;
  }
  
  public TextField getIPToConnectTextField() {
    return ipToConnectTextField;
  }
  
}
