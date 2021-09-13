package client.model;

import shared.util.PropertyChangeSubject;

public interface BlackJackDataModel extends PropertyChangeSubject
{

  void giveCardToPlayer();

  void giveCardToDealer();
  void giveCardToDealer2();
  void giveCardToPlayer2();
  void getTheScore();
}
