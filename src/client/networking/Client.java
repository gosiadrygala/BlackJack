package client.networking;

import shared.util.PropertyChangeSubject;

import java.io.IOException;

public interface Client extends PropertyChangeSubject
{
  void getCardToPlayer() throws IOException;
  void giveCardToDealer();
  void giveCardToDealer2();
  void getCardToPlayer2();
  void getTheFinalScore();
}