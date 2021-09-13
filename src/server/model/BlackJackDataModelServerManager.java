package server.model;

import shared.datatransfer.Card;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlackJackDataModelServerManager implements BlackJackDataModelServer
{
  private PropertyChangeSupport support;
  private CardDeck cardDeck;
  private List<Card> handedCards;
  private List<Card> cards;
  private List<Card> dealerCards;
  private Card cardForThePlayer;
  private Card cardForTheDealer;
  private Card cardForTheDealer2;

  private int dealerscore;
  private int playerscore;

  public BlackJackDataModelServerManager(){
    support = new PropertyChangeSupport(this);
    cardDeck = new CardDeck();
    cards = new ArrayList<>();
    handedCards = new ArrayList<>();
    dealerCards = new ArrayList<>();
    cardForTheDealer = getRandomCard();
    cardForTheDealer2 = getRandomCard();
    dealerCards.add(cardForTheDealer);
    dealerCards.add(cardForTheDealer2);
    dealerscore += cardForTheDealer.getCardNumber();
    dealerscore += cardForTheDealer2.getCardNumber();
  }

  public int getDealerScore(){
    return dealerscore;
  }

  @Override public Card giveCardToPlayer()
  {
    cardDeck.shuffleTheCards();
    cards = cardDeck.getTheDeck();
    Random rand = new Random();
    cardForThePlayer = cards.get(rand.nextInt(cards.size()));
    handedCards.add(cardForThePlayer);
    cardDeck.removeTheCardFromTheDeck(cardForThePlayer);
    playerscore += cardForThePlayer.getCardNumber();
    return cardForThePlayer;
  }

  public int getPlayersScore(){
    return playerscore;
  }

  @Override public List<Card> getAllHandedCards()
  {
    return new ArrayList<>(handedCards);
  }

  @Override  public  Card giveCardToDealer()
  {
    return cardForTheDealer;
  }

  @Override public Card giveCardToDealer2()
  {
    return cardForTheDealer2;
  }

  @Override public  Card getAllDealerCards()
  {
    return cardForTheDealer2;
  }

  synchronized private Card getRandomCard(){
    cardDeck.shuffleTheCards();
    cards = cardDeck.getTheDeck();
    Random rand = new Random();
    Card randomCard = cards.get(rand.nextInt(cards.size()));
    cardDeck.removeTheCardFromTheDeck(randomCard);
    return randomCard;
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
