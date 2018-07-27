package com.nicolasgarnier.particles;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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
  
  private final Skin neonSkin;
  
  public MenuStage() {
    super(new ScreenViewport());
    neonSkin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
    createUIElmts();
  }
  
  private void createUIElmts() {
    try {
      uiElmtsGroup = new VerticalGroup();
      uiElmtsGroup.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      uiElmtsGroup.space(0.2f * Gdx.graphics.getHeight());
      this.addActor(uiElmtsGroup);
    
      InetAddress myIP = InetAddress.getLocalHost();
    
      myIPLabel = new Label("Your IP is : " + myIP.getHostAddress(), neonSkin);
      uiElmtsGroup.addActor(myIPLabel);
      
      uiElmtsNoIPGroup = new VerticalGroup();
      uiElmtsNoIPGroup.space(0.05f * Gdx.graphics.getHeight());
      uiElmtsGroup.addActor(uiElmtsNoIPGroup);
      
      instructions1Label = new Label("You can either create a game :", neonSkin);
      uiElmtsNoIPGroup.addActor(instructions1Label);
      
      createGameButton = new TextButton("Create game", neonSkin);
      uiElmtsNoIPGroup.addActor(createGameButton);
      
      instructions2Label = new Label("Or join an existing game :", neonSkin);
      uiElmtsNoIPGroup.addActor(instructions2Label);
      
      joinGameGroup = new HorizontalGroup();
      uiElmtsNoIPGroup.addActor(joinGameGroup);
      
      instructions3Label = new Label("IP to join : ", neonSkin);
      joinGameGroup.addActor(instructions3Label);
      
      ipToConnectTextField = new TextField("", neonSkin);
      joinGameGroup.addActor(ipToConnectTextField);
      
      joinGameButton = new TextButton("Join game", neonSkin);
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
  
  public void resize(final int width, final int height) {
    uiElmtsGroup.setBounds(0, 0, width, height);
    uiElmtsGroup.space(0.2f * height);
    uiElmtsNoIPGroup.space(0.05f * height);
  }
  
}
