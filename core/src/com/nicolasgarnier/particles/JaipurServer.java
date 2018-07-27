package com.nicolasgarnier.particles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.nicolasgarnier.particles.model.JaipurModel;

public class JaipurServer implements Runnable {

  private int portPlayer1;
  private int portPlayer2;
  private JaipurModel model;
  private JaipurModel modelTransient;
  private int nbConnected;
  
  private boolean receivedFromSocketPlayer1;
  private boolean receivedFromSocketPlayer2;
  
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
    
    // Listening to both ports
    // Each time a model has been received on one port, we send it back
    // on the other one
    Thread player1Listener = new Thread(new ReceiveModelFromClients(portPlayer1), "Server - Player1 listener");
    player1Listener.start();
    Thread player2Listener = new Thread(new ReceiveModelFromClients(portPlayer2), "Server - Player2 listener");
    player2Listener.start();
    
    while (true) {
      try {
        if (receivedFromSocketPlayer1) {
          receivedFromSocketPlayer1 = false;
          // We need to send the model to the Player2
          ObjectOutputStream oos2 = new ObjectOutputStream(socketPlayer2.getOutputStream());
          oos2.writeObject(modelTransient);
        } else if (receivedFromSocketPlayer2) {
          receivedFromSocketPlayer2 = false;
          // We need to send the model to the Player1
          ObjectOutputStream oos1 = new ObjectOutputStream(socketPlayer1.getOutputStream());
          oos1.writeObject(modelTransient);
        }
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
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
        ss.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  class ReceiveModelFromClients implements Runnable {

    private int portNumber;
    private Socket socket;
    
    public ReceiveModelFromClients(int portNumber) {
      this.portNumber = portNumber;
      socket = (portNumber == portPlayer1) ? socketPlayer1 : socketPlayer2; 
    }
    
    @Override
    public void run() {
      while (true) {
        try {
          ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
          modelTransient = (JaipurModel) ois.readObject();
          if (portNumber == portPlayer1) {
            receivedFromSocketPlayer1 = true;
            socketPlayer1 = socket;
          }
          else {
            receivedFromSocketPlayer2 = true;
            socketPlayer2 = socket;
          }
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    
  }

}
