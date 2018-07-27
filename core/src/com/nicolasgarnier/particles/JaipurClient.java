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
      JaipurModel modelTmp = (JaipurModel) ois.readObject();
      model.copy(modelTmp);
      if (view != null) view.recomputeSpritesPositions();
      System.out.println("Model received");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

}
