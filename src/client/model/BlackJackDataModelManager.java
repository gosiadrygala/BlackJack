package client.model;

import client.networking.Client;
import shared.EventType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class BlackJackDataModelManager implements BlackJackDataModel
{
  private PropertyChangeSupport support = new PropertyChangeSupport(this);
  private Client client;

  public BlackJackDataModelManager(Client client)
  {
    this.client = client;
    client.addPropertyChangeListener(EventType.CARD_TO_DEALER.toString(), evt -> support.firePropertyChange(evt));
    client.addPropertyChangeListener(EventType.CARD_TO_PLAYER.toString(), evt -> support.firePropertyChange(evt));
    client.addPropertyChangeListener(EventType.STATUS_LABEL.toString(), evt -> support.firePropertyChange(evt));
    client.addPropertyChangeListener(EventType.CARD_TO_SECOND_PLAYER.toString(), evt -> support.firePropertyChange(evt));

    client.addPropertyChangeListener(EventType.CARD_TO_DEALER_2.toString(), evt -> support.firePropertyChange(evt));
    client.addPropertyChangeListener(EventType.CARD_TO_PLAYER_2.toString(), evt -> support.firePropertyChange(evt));
    client.addPropertyChangeListener(EventType.CARD_TO_SECOND_PLAYER_2.toString(), evt -> support.firePropertyChange(evt));
  }


  @Override public void giveCardToPlayer()
  {
    try
    {
      client.getCardToPlayer();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }



  @Override public void giveCardToDealer()
  {
      client.giveCardToDealer();
  }

  @Override public void giveCardToDealer2()
  {
    client.giveCardToDealer2();
  }

  @Override public void giveCardToPlayer2()
  {
    client.getCardToPlayer2();
  }

  @Override public void getTheScore()
  {
    client.getTheFinalScore();
  }

  @Override
  public void addPropertyChangeListener(String name, PropertyChangeListener listener) {

    if (null == name) {
      addPropertyChangeListener(listener);
    } else {
      support.addPropertyChangeListener(name, listener);
    }
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
    if (name == null) {
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
