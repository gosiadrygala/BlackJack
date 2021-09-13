package client.core;

import client.view.FirstView.FirstViewController;
import client.view.FrontView.FrontViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewHandler
{
  private Scene blackJackScene;
  private Stage stage;
  private ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory){
    this.viewModelFactory = viewModelFactory;
  }

  public void start(){
    stage = new Stage();
    openFrontView();
  }

  public void openBlackJackTableView()
  {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/FirstView/FirstView.fxml"));
      Parent root = loader.load(); //TODO change controller

      FirstViewController view = loader.getController();
      view.init(this, viewModelFactory);
      Scene scene = new Scene(root);
      stage.setTitle("Black Jack Table");

      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void openFrontView()
  {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/FrontView/FrontView.fxml"));
      Parent root = loader.load();

      FrontViewController view = loader.getController();
      view.init(this);
      Scene scene = new Scene(root);
      stage.setTitle("Black Jack");

      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
