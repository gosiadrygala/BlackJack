package server.connectionPool;

import server.networking.ServerSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class ConnectionPool
{
  private List<ServerSocketHandler> connections = new ArrayList<>();

  public void addConnection(ServerSocketHandler serverSocketHandler){
    connections.add(serverSocketHandler);
  }

  public int getTheNumberOfConnections(){
    return connections.size();
  }

  public void broadcast(Object object)
{
  for (ServerSocketHandler connection : connections)
  {
    connection.sendAMessageToClient(object);
  }
}
  public  void sendAMessageToAnotherClient(
    ServerSocketHandler serverSocketHandler, Object object){
    List<ServerSocketHandler> connections2 = connections;
    connections2.remove(serverSocketHandler);
    for(ServerSocketHandler connection : connections2){
      connection.sendAMessageToClient(object);
    }
    connections2.add(serverSocketHandler);
  }

  public void startTheGame()
  {
    for (ServerSocketHandler connection : connections)
    {
      (new Thread(connection)).start();
      //connection.sendAMessageToClient(object);
    }
  }
}
