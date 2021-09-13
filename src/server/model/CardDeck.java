package server.model;

import shared.datatransfer.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class CardDeck implements Serializable
{

  private ArrayList<Card> cardDeck = new ArrayList<>();

  public CardDeck(){
    for(int i = 1; i < 14; i++){
      Card card = new Card("hearts", i , "file:C:/Users/Gosia/IdeaProjects/NewSemester/BlackJack/src/client/view/FirstView/cardImages/hearts/heart" +i +".png");
      Card card1 = new Card("clubs", i, "file:C:/Users/Gosia/IdeaProjects/NewSemester/BlackJack/src/client/view/FirstView/cardImages/clubs/clubs" +i +".png");
      Card card2 = new Card("spades", i, "file:C:/Users/Gosia/IdeaProjects/NewSemester/BlackJack/src/client/view/FirstView/cardImages/spades/spades" +i +".png");
      Card card3 = new Card("diamonds", i, "file:C:/Users/Gosia/IdeaProjects/NewSemester/BlackJack/src/client/view/FirstView/cardImages/diamonds/diamonds" +i +".png");
      cardDeck.add(card);
      cardDeck.add(card1);
      cardDeck.add(card2);
      cardDeck.add(card3);
    }
  }

  synchronized public void shuffleTheCards(){
    Collections.shuffle(cardDeck);
  }

  synchronized public ArrayList<Card> getTheDeck(){
    return cardDeck;
  }

  synchronized public void removeTheCardFromTheDeck(Card card){
    cardDeck.remove(card);
  }
}
