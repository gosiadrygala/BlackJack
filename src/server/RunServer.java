package server;

import server.model.BlackJackDataModelServerManager;
import server.networking.SocketServer;

public class RunServer
{
  public static void main(String[] args)
  {
    SocketServer ss = new SocketServer(new BlackJackDataModelServerManager());
    ss.startServer();
  }
}
