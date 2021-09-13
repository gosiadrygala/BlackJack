package client.view.FirstView;

import client.model.BlackJackDataModel;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import shared.EventType;

import java.beans.PropertyChangeEvent;

public class FirstViewModel
{
  private BlackJackDataModel blackJackDataModel;
  private StringProperty playerCard;
  private StringProperty dealerCard;
  private StringProperty secondPlayerCard;
  private StringProperty statusLabel = null;

  private StringProperty playerCard2;
  private StringProperty dealerCard2;
  private StringProperty secondPlayerCard2;
  private ObjectProperty<Image> imagePropertyDealer1 = new SimpleObjectProperty<>();
  private ObjectProperty<Image> imagePropertyDealer2 = new SimpleObjectProperty<>();
  private ObjectProperty<Image> imagePropertyPlayer1 = new SimpleObjectProperty<>();
  private ObjectProperty<Image> imagePropertyPlayer1second = new SimpleObjectProperty<>();
  private ObjectProperty<Image> imagePropertyEnemy = new SimpleObjectProperty<>();
  private ObjectProperty<Image> imagePropertyEnemySecond = new SimpleObjectProperty<>();




  public FirstViewModel(BlackJackDataModel blackJackDataModel)
  {
    this.blackJackDataModel = blackJackDataModel;
    playerCard = new SimpleStringProperty();
    dealerCard = new SimpleStringProperty();
    statusLabel = new SimpleStringProperty();

    secondPlayerCard = new SimpleStringProperty();

    playerCard2 = new SimpleStringProperty();
    dealerCard2 = new SimpleStringProperty();
    secondPlayerCard2 = new SimpleStringProperty();

    blackJackDataModel.addPropertyChangeListener(EventType.CARD_TO_DEALER.toString(), this::onCardToDealer);
    blackJackDataModel.addPropertyChangeListener(EventType.CARD_TO_PLAYER.toString(), this::onCardToPlayer);
    blackJackDataModel.addPropertyChangeListener(EventType.STATUS_LABEL.toString(), this:: onStatusLabel);
    blackJackDataModel.addPropertyChangeListener(EventType.CARD_TO_SECOND_PLAYER.toString(), this:: onCardToSecondPlayer);
    blackJackDataModel.addPropertyChangeListener(EventType.CARD_TO_DEALER_2.toString(), this::onCardToDealer2);
    blackJackDataModel.addPropertyChangeListener(EventType.CARD_TO_PLAYER_2.toString(), this::onCardToPlayer2);
    blackJackDataModel.addPropertyChangeListener(EventType.CARD_TO_SECOND_PLAYER_2.toString(), this:: onCardToSecondPlayer2);

  }

  private void onCardToSecondPlayer2(PropertyChangeEvent propertyChangeEvent)
  {
    Platform.runLater(()-> {
      secondPlayerCard2.setValue(propertyChangeEvent.getNewValue().toString());
      Image image1 = new Image(propertyChangeEvent.getNewValue().toString());
      imagePropertyEnemySecond.setValue(image1);
    });
  }

  private void onCardToPlayer2(PropertyChangeEvent propertyChangeEvent)
  {
    Platform.runLater(()-> {
      playerCard2.setValue(propertyChangeEvent.getNewValue().toString());
      Image image1 = new Image(propertyChangeEvent.getNewValue().toString());
      imagePropertyPlayer1second.setValue(image1);
    });
  }

  private void onCardToDealer2(PropertyChangeEvent propertyChangeEvent)
  {
    Platform.runLater(()-> {
      dealerCard2.setValue(propertyChangeEvent.getNewValue().toString());
      Image image1 = new Image(propertyChangeEvent.getNewValue().toString());
      imagePropertyDealer2.setValue(image1);
    });
  }

  private void onCardToSecondPlayer(PropertyChangeEvent propertyChangeEvent)
  {
    if(propertyChangeEvent.getNewValue() == null){
      secondPlayerCard.setValue("no card");
    }else {
      Platform.runLater(() -> {
        secondPlayerCard.setValue(propertyChangeEvent.getNewValue().toString());
        Image image1 = new Image(propertyChangeEvent.getNewValue().toString());
        imagePropertyEnemy.setValue(image1);
      });
    }
  }

  private void onStatusLabel(PropertyChangeEvent propertyChangeEvent)
  {
    if(propertyChangeEvent.getNewValue() == null){
    statusLabel.setValue("no status");
    }else {
      Platform.runLater(() -> {
        statusLabel.setValue(propertyChangeEvent.getNewValue().toString());
      });
    }
  }

  private void onCardToDealer(PropertyChangeEvent propertyChangeEvent)
  {
    Platform.runLater(()-> {
      Image image1 = new Image(propertyChangeEvent.getNewValue().toString());
      imagePropertyDealer1.setValue(image1);
      dealerCard.setValue(propertyChangeEvent.getNewValue().toString());

    });
  }

  private void onCardToPlayer(PropertyChangeEvent propertyChangeEvent)
  {
    Platform.runLater(()-> {
      Image image1 = new Image(propertyChangeEvent.getNewValue().toString());
      imagePropertyPlayer1.setValue(image1);
      playerCard.setValue(propertyChangeEvent.getNewValue().toString());
    });
  }

  public StringProperty getStatusLabelProperty()
  {
    return statusLabel;
  }
  public void setCards()
  {
    blackJackDataModel.giveCardToDealer();
    blackJackDataModel.giveCardToPlayer();
  }

  public void setCards2()
  {
    blackJackDataModel.giveCardToDealer2();
    blackJackDataModel.giveCardToPlayer2();
  }

  public void getTheScore()
  {
    blackJackDataModel.getTheScore();
  }


  public ObservableValue firstCardPlayerImage(){
    return imagePropertyPlayer1;
  }

  public ObservableValue secondCardPlayerImage(){
    return imagePropertyPlayer1second;
  }

  public ObservableValue firstCardDealerImage(){
    return imagePropertyDealer1;
  }

  public ObservableValue secondCardDealerImage(){
    return imagePropertyDealer2;
  }

  public ObservableValue firstCardEnemyImage(){
    return imagePropertyEnemy;
  }

  public ObservableValue secondCardEnemyImage(){
    return imagePropertyEnemySecond;
  }

}
