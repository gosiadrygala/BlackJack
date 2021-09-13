package client.view.FirstView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class FirstViewController
{

  @FXML
  private Label statusLabel;
  @FXML
  private Button getTheCards;
  @FXML
  private Button getTheCards2;
  @FXML
  private Button getTheFinalScorebtn;

  @FXML
  private ImageView firstPlayerCardImage;

  @FXML
  private ImageView firstPlayerSecondCardImage;

  @FXML
  private ImageView SecondPlayerCardImage;

  @FXML
  private ImageView SecondPlayerSecondCardImage;

  @FXML
  private ImageView DealerFirstCardImage;

  @FXML
  private ImageView DealerSecondCardImage;

  @FXML
  void handleClickMe(ActionEvent event) {
    if(event.getSource() == getTheCards){
      firstViewModel.setCards();
      getTheCards.setTranslateX(504);
      getTheCards.setVisible(false);
      getTheCards.setDisable(false);
      getTheCards2.setVisible(true);
      getTheCards2.setOpacity(1);
    }
    else if(event.getSource() == getTheCards2){
      firstViewModel.setCards2();
      getTheCards.setTranslateX(504);
      getTheCards2.setVisible(false);
      getTheCards2.setDisable(false);
      getTheFinalScorebtn.setVisible(true);
      getTheFinalScorebtn.setOpacity(1);
    }
    else if(event.getSource() == getTheFinalScorebtn){
      firstViewModel.getTheScore();
      getTheFinalScorebtn.setDisable(false);
      getTheFinalScorebtn.setVisible(false);
    }
  }

  private FirstViewModel firstViewModel;

  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    this.viewHandler = viewHandler;
    try
    {
      this.firstViewModel = viewModelFactory.getFirstViewModel();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }


    statusLabel.textProperty().bind(firstViewModel.getStatusLabelProperty());
    getTheCards2.setVisible(false);
    getTheFinalScorebtn.setVisible(false);

    //
    firstPlayerCardImage.imageProperty().bind(firstViewModel.firstCardPlayerImage());
    firstPlayerSecondCardImage.imageProperty().bind(firstViewModel.secondCardPlayerImage());
    SecondPlayerCardImage.imageProperty().bind(firstViewModel.firstCardEnemyImage());
    SecondPlayerSecondCardImage.imageProperty().bind(firstViewModel.secondCardEnemyImage());
    DealerFirstCardImage.imageProperty().bind(firstViewModel.firstCardDealerImage());
    DealerSecondCardImage.imageProperty().bind(firstViewModel.secondCardDealerImage());
}


}
