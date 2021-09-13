package client.view.FrontView;

import client.core.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FrontViewController
{
  @FXML
  private Button enterGameBtn;
  private ViewHandler viewHandler;


  @FXML
  void handleClickMe(ActionEvent event) {
    if(event.getSource().equals(enterGameBtn)){
      viewHandler.openBlackJackTableView();
    }
  }

  public void init(ViewHandler viewHandler)

  {
    this.viewHandler = viewHandler;
  }
}
