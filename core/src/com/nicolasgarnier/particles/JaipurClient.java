package com.nicolasgarnier.particles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nicolasgarnier.particles.model.JaipurModel;
import com.nicolasgarnier.particles.view.JaipurView;

public class JaipurClient implements Runnable {

  private Socket socket;
  private JaipurModel model;
  private JaipurView view;
  
  public JaipurClient(JaipurModel model, Socket socket, JaipurView view) {
    this.model = model;
    this.socket = socket;
    this.view = view;
  }
  
  @Override
  public void run() {
    try {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      JaipurClientServerMessage msgReceived = (JaipurClientServerMessage) ois.readObject();
      if (view != null) view.readyToRender = false;
      model.copy(msgReceived.model);
      if (view != null) view.recomputeSpritesPositions();
      if (view != null) view.readyToRender = true;
      if (msgReceived.logMsg != null) {
        System.out.println(msgReceived.logMsg);
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

}
