package com.nicolasgarnier.particles;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.nicolasgarnier.particles.model.JaipurModel;

public class JaipurServer implements Runnable {

  private int portPlayer1;
  private int portPlayer2;
  private JaipurModel model;
  private int nbConnected;
  
  private Socket socketPlayer1;
  private Socket socketPlayer2;
  
  public JaipurServer(int portPlayer1, int portPlayer2) {
    this.portPlayer1 = portPlayer1;
    this.portPlayer2 = portPlayer2;
    nbConnected = 0;
  }
  
  @Override
  public void run() {
    // Waiting until the two clients are connected
    Thread player1Connection = new Thread(new ClientConnected(portPlayer1, 0), "WaitPlayer1Connection");
    player1Connection.start();
    Thread player2Connection = new Thread(new ClientConnected(portPlayer2, 1), "WaitPlayer2Connection");
    player2Connection.start();
    while (nbConnected != 2) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
    // Creating the model
    model = new JaipurModel();
    model.startGame();
    
    // Sending it to both clients
    try {
      ObjectOutputStream oos1 = new ObjectOutputStream(socketPlayer1.getOutputStream());
      oos1.writeObject(model);
      ObjectOutputStream oos2 = new ObjectOutputStream(socketPlayer2.getOutputStream());
      oos2.writeObject(model);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  class ClientConnected implements Runnable {
    
    int portNumber;
    int playerID;
    
    public ClientConnected(int portNumber, int playerID) {
      this.portNumber = portNumber;
      this.playerID = playerID;
    }
    
    @Override
    public void run() {
      try {
        ServerSocket ss = new ServerSocket(portNumber);
        Socket socket = ss.accept();
        System.out.println("A client connected");
        nbConnected++;
        if (playerID == 0) socketPlayer1 = socket;
        else socketPlayer2 = socket;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
