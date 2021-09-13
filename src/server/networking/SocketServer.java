package server.networking;

import server.connectionPool.ConnectionPool;
import server.model.BlackJackDataModelServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer
{
  private BlackJackDataModelServer blackJackDataModelServer;

  public SocketServer(BlackJackDataModelServer blackJackDataModelServer){
    this.blackJackDataModelServer = blackJackDataModelServer;
  }

  public void startServer(){
    try
    {
      ServerSocket serverSocket = new ServerSocket(2311);
      System.out.println("Server started...");
      ConnectionPool connectionPool = new ConnectionPool();
      while(true){
        System.out.println("Waiting for client..");
        Socket client = serverSocket.accept();
        ServerSocketHandler ssh = new ServerSocketHandler(blackJackDataModelServer, client, connectionPool);
        connectionPool.addConnection(ssh);
        if(connectionPool.getTheNumberOfConnections() == 2){
          connectionPool.startTheGame();
        }
        System.out.println("Client connected");
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
