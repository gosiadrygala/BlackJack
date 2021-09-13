package server.model;

import shared.datatransfer.Card;
import shared.util.PropertyChangeSubject;

import java.util.List;

public interface BlackJackDataModelServer extends PropertyChangeSubject
{
  Card giveCardToPlayer();

  List<Card> getAllHandedCards();

  Card giveCardToDealer();

  Card giveCardToDealer2();

  Card getAllDealerCards();
  int getDealerScore();
  int getPlayersScore();

}
