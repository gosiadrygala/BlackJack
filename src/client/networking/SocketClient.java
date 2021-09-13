package client.networking;

import shared.EventType;
import shared.datatransfer.Request;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements Client{

  private PropertyChangeSupport support;
  private Socket socket;
  private ObjectOutputStream outToServer;

  public SocketClient() {
    support = new PropertyChangeSupport(this);
    start();
  }

   public void start() {
    try {
      socket = new Socket("localhost", 2311);
      Thread thread = new Thread(this::listenToServer);
      thread.setDaemon(true);
      thread.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

   public void listenToServer()  {
    try {
      ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
      outToServer = new ObjectOutputStream(socket.getOutputStream());
      // listen for requests from the server
      while(true) {
        Request req = (Request) inFromServer.readObject();
        support.firePropertyChange(req.type.toString(), null, req.arg);
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void getCardToPlayer()
  {
    Request req = new Request(EventType.CARD_TO_PLAYER, null);
    //Request result =
        sendToServer(req, EventType.CARD_TO_PLAYER);
    //return (Card) result.arg;
  }

  @Override
  public void giveCardToDealer() {
    Request req = new Request(EventType.CARD_TO_DEALER, null);
        sendToServer(req, EventType.CARD_TO_DEALER);
  }

  @Override public void giveCardToDealer2()
  {
    Request req = new Request(EventType.CARD_TO_DEALER_2, null);
    sendToServer(req, EventType.CARD_TO_DEALER_2);
  }

  @Override public void getCardToPlayer2()
  {
    Request req = new Request(EventType.CARD_TO_PLAYER_2, null);
    sendToServer(req, EventType.CARD_TO_PLAYER_2);
  }

  @Override public void getTheFinalScore()
  {
    Request req = new Request(EventType.GET_SCORE, null);
    sendToServer(req, EventType.GET_SCORE);
  }

  public void sendToServer(Request req, EventType registerResult) {
    try {
      outToServer.writeObject(req);
    } catch (IOException e) {
      support.firePropertyChange(registerResult.toString(), null, "Connection lost, restart program");
    }
  }

  @Override
  public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
    if(null == name) {
      addPropertyChangeListener(listener);
    } else {
      support.addPropertyChangeListener(name, listener);
      String s = "";
    }
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
    if(name == null) {
      removePropertyChangeListener(listener);
    } else {
      support.removePropertyChangeListener(name, listener);
    }
  }

  @Override
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }
}
