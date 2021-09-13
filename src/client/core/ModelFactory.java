package client.core;

import client.model.BlackJackDataModel;
import client.model.BlackJackDataModelManager;

import java.io.IOException;

public class ModelFactory
{
  private BlackJackDataModel blackJackDataModel;
  private final ClientFactory clientFactory;


  public ModelFactory(ClientFactory clientFactory){
    this.clientFactory = clientFactory;
  }

  public BlackJackDataModel getBlackJackDataModel() throws IOException
  {
    if(blackJackDataModel == null) {
      blackJackDataModel = new BlackJackDataModelManager(clientFactory.getClientModel());
    }
    return blackJackDataModel;
  }

}
