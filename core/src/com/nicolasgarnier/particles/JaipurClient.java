package com.nicolasgarnier.particles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nicolasgarnier.particles.model.JaipurModel;

public class JaipurClient implements Runnable {

  private String ipServer;
  private int portServer;
  private JaipurModel model;
  
  public JaipurClient(final String ipServer, final int portServer, JaipurModel model) {
    this.ipServer = ipServer;
    this.portServer = portServer;
    this.model = model;
  }
  
  @Override
  public void run() {
    try {
      Socket socket = new Socket(ipServer, portServer);
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      JaipurModel modelTmp = (JaipurModel) ois.readObject();
      model.copy(modelTmp);
      System.out.println("Model received");
      socket.close();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

}
