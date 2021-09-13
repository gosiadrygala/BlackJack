package server.networking;

import server.connectionPool.ConnectionPool;
import server.model.BlackJackDataModelServer;
import shared.EventType;
import shared.datatransfer.Card;
import shared.datatransfer.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketHandler implements Runnable
{
  private BlackJackDataModelServer blackJackDataModelServer;
  private Socket socket;
  private ObjectOutputStream outToClient;
  private ObjectInputStream inFromClient;
  private ConnectionPool connectionPool;

  private Card cardForPlayer1firstCard = null;
  private Card cardForPlayer1secondCard = null;
  private Card cardForDealer = null;
  private Card cardForDealer2 = null;

  public ServerSocketHandler(BlackJackDataModelServer blackJackDataModelServer,
      Socket socket, ConnectionPool connectionPool)
  {
    this.blackJackDataModelServer = blackJackDataModelServer;
    this.socket = socket;
    this.connectionPool = connectionPool;
    try
    {
      outToClient = new ObjectOutputStream(socket.getOutputStream());
      inFromClient = new ObjectInputStream(socket.getInputStream());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

  }

  @Override public void run()
  {
        while (true)
        {
          try
          {
            Request request = (Request) inFromClient.readObject();
            if (request.type == EventType.CARD_TO_PLAYER)
            {
              Request statusLabel = new Request(EventType.STATUS_LABEL,
                "Handing out the first cards");
              outToClient.writeObject(statusLabel);
              Thread.sleep(1000);
              System.out.println("Card for the player requested...");
              cardForPlayer1firstCard = blackJackDataModelServer.giveCardToPlayer();
              Request response = new Request(EventType.CARD_TO_PLAYER,
                  cardForPlayer1firstCard);
              outToClient.writeObject(response);
              Request cardForAnother = new Request(EventType.CARD_TO_SECOND_PLAYER, cardForPlayer1firstCard);
              //sending the same card to the second player
              Thread.sleep(2000);
              connectionPool.sendAMessageToAnotherClient(this, cardForAnother);
              System.out.println("Sending... " + cardForPlayer1firstCard.getCardNumber() + " of " + cardForPlayer1firstCard.getCardSymbol() );
              Request statusLabel3 = new Request(EventType.STATUS_LABEL,
                  "Do you want to request another card?");
              outToClient.writeObject(statusLabel3);
            }
            else if (request.type == EventType.CARD_TO_DEALER)
            {
              System.out.println("Card for the dealer requested...");
              cardForDealer = blackJackDataModelServer.giveCardToDealer();
              Request response = new Request(EventType.CARD_TO_DEALER,
                  cardForDealer);
              outToClient.writeObject(response);
              System.out.println("Sending... " + cardForDealer.getCardNumber() + " of " + cardForDealer.getCardSymbol());

            }
            else if(request.type == EventType.CARD_TO_DEALER_2)
            {
              System.out.println("Card for the dealer requested...");
              cardForDealer2 = blackJackDataModelServer.giveCardToDealer2();
              Request response = new Request(EventType.CARD_TO_DEALER_2,
                  cardForDealer2);
              outToClient.writeObject(response);
              System.out.println("Sending... " + cardForDealer2.getCardNumber() + " of " + cardForDealer2.getCardSymbol());
            }
            else if(request.type == EventType.CARD_TO_PLAYER_2){
              Request statusLabel = new Request(EventType.STATUS_LABEL,
                  "Handing out the next cards");
              outToClient.writeObject(statusLabel);
              Thread.sleep(2000);
              System.out.println("Card for the player requested...");
              cardForPlayer1secondCard = blackJackDataModelServer.giveCardToPlayer();
              Request response = new Request(EventType.CARD_TO_PLAYER_2,
                  cardForPlayer1secondCard);
              outToClient.writeObject(response);
              Request cardForAnother = new Request(EventType.CARD_TO_SECOND_PLAYER_2, cardForPlayer1secondCard);
              //sending the same card to the second player
              connectionPool.sendAMessageToAnotherClient(this, cardForAnother);
              System.out.println("Sending... " + cardForPlayer1secondCard.getCardNumber() + " of " + cardForPlayer1secondCard.getCardSymbol());
            }else if(request.type == EventType.GET_SCORE){
              int dealerscore = getActualDealerScore();
              int playerScore = getActualPlayerScore();
              int enemyScore = getActualEnemyScore();

              int dealerDifference = 21 - dealerscore;
              int playerDifference = 21 - playerScore;
              int enemyDifference = 21 - enemyScore;
              if(playerScore > 21){
                Request statusLabel = new Request(EventType.STATUS_LABEL,
                    "You lost your cards are above 21");
                outToClient.writeObject(statusLabel);
              }
              else if(playerScore == 21){
                Request statusLabel = new Request(EventType.STATUS_LABEL,
                    "You won with the perfect score");
                outToClient.writeObject(statusLabel);
              }
              else if(dealerscore > 21 && enemyScore >21){
                Request statusLabel = new Request(EventType.STATUS_LABEL,
                    "You beat the dealer and the second player!");
                outToClient.writeObject(statusLabel);
              }
              else if(playerScore < 21 && dealerscore < 21){
                if(playerDifference < dealerDifference){
                  if(enemyScore > 21){
                    Request statusLabel = new Request(EventType.STATUS_LABEL,
                        "You beat the dealer and the second player!");
                    outToClient.writeObject(statusLabel);
                  }
                  else{
                    if(playerDifference < enemyDifference){
                      Request statusLabel = new Request(EventType.STATUS_LABEL,
                          "You beat the dealer and the second player!");
                      outToClient.writeObject(statusLabel);
                    }
                    else if(playerDifference == enemyDifference){
                      Request statusLabel = new Request(EventType.STATUS_LABEL,
                          "You had a draw with the second player!");
                      outToClient.writeObject(statusLabel);
                    }
                    else{
                      Request statusLabel = new Request(EventType.STATUS_LABEL,
                          "You lost this game");
                      outToClient.writeObject(statusLabel);
                    }
                  }
                }else{
                  Request statusLabel = new Request(EventType.STATUS_LABEL,
                      "You lost this game");
                  outToClient.writeObject(statusLabel);
                }
              }
              else if(playerScore <21 && dealerscore == 21){
                Request statusLabel = new Request(EventType.STATUS_LABEL,
                    "You lost this game");
                outToClient.writeObject(statusLabel);
              }
              else if(playerScore < 21 && enemyScore == 21){
                Request statusLabel = new Request(EventType.STATUS_LABEL,
                    "You lost this game");
                outToClient.writeObject(statusLabel);
              }
              else if(playerScore < 21 && enemyScore < 21){
                if(playerDifference < enemyDifference){
                  if(dealerscore > 21){
                    Request statusLabel = new Request(EventType.STATUS_LABEL,
                        "You won this game!");
                    outToClient.writeObject(statusLabel);
                  }
                  else{
                    if(playerDifference < dealerDifference){
                      Request statusLabel = new Request(EventType.STATUS_LABEL,
                          "You won this game!");
                      outToClient.writeObject(statusLabel);
                    }
                    else if (playerDifference == dealerDifference){
                      Request statusLabel = new Request(EventType.STATUS_LABEL,
                          "You had a draw with the dealer");
                      outToClient.writeObject(statusLabel);
                    }
                    else{
                      Request statusLabel = new Request(EventType.STATUS_LABEL,
                          "You lost this game, good luck next time");
                      outToClient.writeObject(statusLabel);
                    }
                  }
                }
              }
              else if( playerDifference == enemyDifference && playerDifference == dealerDifference){
                Request statusLabel = new Request(EventType.STATUS_LABEL,
                    "All of you got the same cards!! WOOW");
                outToClient.writeObject(statusLabel);
              }

            }

          }
          catch (Exception e)
          {

          }
        }
      }

  // additional method
  public void sendAMessageToClient(Object object)
  {
    try
    {
      outToClient.writeObject(object);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public int getActualDealerScore(){
    int real_score = 0;

    if(cardForDealer.getCardNumber() == 1 && cardForDealer2.getCardNumber() == 1){
      real_score += 12;
    }
    else if(cardForDealer.getCardNumber() == 1 && cardForDealer2.getCardNumber() != 1){
      if(11 + cardForDealer2.getCardNumber() > 21){
        real_score += (1 + cardForDealer2.getCardNumber());
        }
      else if(11 + cardForDealer2.getCardNumber() < 21){
        real_score += (11 + cardForDealer2.getCardNumber());
      }
    }
    else if(cardForDealer.getCardNumber() != 1 && cardForDealer2.getCardNumber() == 1){
      if(11 + cardForDealer.getCardNumber() > 21){
        real_score += (1 + cardForDealer.getCardNumber());
      }
      else if(11 + cardForDealer.getCardNumber() < 21){
        real_score += (11 + cardForDealer.getCardNumber());
      }
    }
    else if(cardForDealer.getCardNumber() != 1 && cardForDealer2.getCardNumber() != 1){
      real_score += (cardForDealer.getCardNumber() + cardForDealer2.getCardNumber());
    }

    return real_score;
  }

  public int getActualPlayerScore(){
    int real_score = 0;

    if(cardForPlayer1firstCard.getCardNumber() == 1 && cardForPlayer1secondCard.getCardNumber() == 1){
      real_score += 12;
    }
    else if(cardForPlayer1firstCard.getCardNumber() == 1 && cardForPlayer1secondCard.getCardNumber() != 1){
      if(11 + cardForPlayer1secondCard.getCardNumber() > 21){
        real_score += (1 + cardForPlayer1secondCard.getCardNumber());
      }
      else if(11 + cardForPlayer1secondCard.getCardNumber() < 21){
        real_score += (11 + cardForPlayer1secondCard.getCardNumber());
      }
    }
    else if(cardForPlayer1firstCard.getCardNumber() != 1 && cardForPlayer1secondCard.getCardNumber() == 1){
      if(11 + cardForPlayer1firstCard.getCardNumber() > 21){
        real_score += (1 + cardForPlayer1firstCard.getCardNumber());
      }
      else if(11 + cardForPlayer1firstCard.getCardNumber() < 21){
        real_score += (11 + cardForPlayer1firstCard.getCardNumber());
      }
    }
    else if(cardForPlayer1firstCard.getCardNumber() != 1 && cardForPlayer1secondCard.getCardNumber() != 1){
      real_score += (cardForPlayer1firstCard.getCardNumber() + cardForPlayer1secondCard.getCardNumber());
    }
    return real_score;
  }

  public int getActualEnemyScore(){
    ArrayList<Card> enemycards = new ArrayList<>(
        blackJackDataModelServer.getAllHandedCards());
    enemycards.remove(cardForPlayer1firstCard);
    enemycards.remove(cardForPlayer1secondCard);

    Card enemyFirstCard = enemycards.get(0);
    Card enemySecondCard = enemycards.get(1);

    int real_score = 0;

    if(enemyFirstCard.getCardNumber() == 1 && enemySecondCard.getCardNumber() == 1){
      real_score += 12;
    }
    else if(enemyFirstCard.getCardNumber() == 1 && enemySecondCard.getCardNumber() != 1){
      if(11 + enemySecondCard.getCardNumber() > 21){
        real_score += (1 + enemySecondCard.getCardNumber());
      }
      else if(11 + enemySecondCard.getCardNumber() < 21){
        real_score += (11 + enemySecondCard.getCardNumber());
      }
    }
    else if(enemyFirstCard.getCardNumber() != 1 && enemySecondCard.getCardNumber() == 1){
      if(11 + enemyFirstCard.getCardNumber() > 21){
        real_score += (1 + enemyFirstCard.getCardNumber());
      }
      else if(11 + enemyFirstCard.getCardNumber() < 21){
        real_score += (11 + enemyFirstCard.getCardNumber());
      }
    }
    else if(enemyFirstCard.getCardNumber() != 1 && enemySecondCard.getCardNumber() != 1){
      real_score += (enemyFirstCard.getCardNumber() + enemySecondCard.getCardNumber());
    }
    return real_score;
  }
}

